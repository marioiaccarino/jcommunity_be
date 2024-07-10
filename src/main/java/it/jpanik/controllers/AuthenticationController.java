package it.jpanik.controllers;

import it.jpanik.dto.LoginDto;
import it.jpanik.dto.UserDto;
import it.jpanik.entities.User;
import it.jpanik.exceptions.ValidationException;
import it.jpanik.model.JwtResponse;
import it.jpanik.security.JwtUtils;
import it.jpanik.services.servicesImp.AuthenticationServiceImp;
import it.jpanik.validator.UserValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/jCommunity/auth")
public class AuthenticationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    private final AuthenticationServiceImp authenticationServiceImp;

    private final UserValidator userValidator;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, JwtUtils jwtUtils, AuthenticationServiceImp authenticationServiceImp, UserValidator userValidator) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.authenticationServiceImp = authenticationServiceImp;
        this.userValidator = userValidator;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody LoginDto loginDto) throws ValidationException {
        LOGGER.info("Called POST /auth/login");
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        User user = (User) authentication.getPrincipal();
        if(user!=null)        {
            LOGGER.info(String.format("Login Success, User with id: {}, email: {}",user.getId(), user.getUsername()));
            return ResponseEntity.ok(new JwtResponse(jwt,user.getId(),user.getUsername(),user.getRole()));
        }
        LOGGER.error("Login User Failed Bad Credentials. email: {}, password: {}", loginDto.getEmail(), loginDto.getPassword());
        return ResponseEntity.badRequest().body("user not found");
    }

}
