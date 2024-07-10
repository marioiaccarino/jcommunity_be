package it.jpanik.mappers;

import it.jpanik.dto.UserDto;
import it.jpanik.entities.User;
import it.jpanik.enums.UserRolesEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

@Service
public class UserMapper extends Mapper<UserDto, User> {

    private final PasswordEncoder encoder;

    @Autowired
    public UserMapper(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @Override
    public UserDto convertEntityToDtoImpl(User user) {

        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setUrlImage(user.getUrlImage());
        userDto.setEmail(user.getUsername());

        return userDto;
    }


    @Override
    public User convertDtoToEntityImpl(UserDto dto) {

        User user = new User();
        user.setDateOfBirth(new Date());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setUrlImage(dto.getUrlImage());
        user.setUsername(dto.getEmail());
        user.setPassword(encoder.encode(dto.getPassword()));
        user.setUserCommunityList(new ArrayList<>());
        user.setRole(UserRolesEnum.DEFAULT);

        return user;
    }
}
