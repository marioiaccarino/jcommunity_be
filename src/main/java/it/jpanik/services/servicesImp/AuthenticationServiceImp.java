package it.jpanik.services.servicesImp;
import it.jpanik.dto.UserDto;
import it.jpanik.entities.User;
import it.jpanik.exceptions.ValidationException;
import it.jpanik.mappers.UserMapper;
import it.jpanik.model.JwtResponse;
import it.jpanik.repositories.UserRepository;
import it.jpanik.security.JwtUtils;
import it.jpanik.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthenticationServiceImp {

    private final UserMapper userMapper;

    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    public AuthenticationServiceImp(UserMapper userMapper, UserRepository userRepository, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    public ResponseEntity<?> register(UserDto request)   throws ValidationException   {
        User user = this.userMapper.convertDtoToEntity(request);

        user = userRepository.save(user);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        return ResponseEntity.ok(new JwtResponse(jwt,user.getId(),user.getUsername(),user.getRole()));
    }
}
