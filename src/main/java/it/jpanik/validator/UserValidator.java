package it.jpanik.validator;

import it.jpanik.controllers.AuthenticationController;
import it.jpanik.dto.UserDto;
import it.jpanik.exceptions.ValidationException;
import it.jpanik.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserValidator extends Validator {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);

    private final UserRepository userRepository;

    @Autowired
    public UserValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void validate(UserDto userDto) throws ValidationException {
        checkEmpty(userDto.getFirstName(), "Name: required field");
        checkEmpty(userDto.getLastName(), "Surname: required field");
        checkEmpty(userDto.getEmail(), "Email: required field");
        checkEmpty(userDto.getPassword(), "Password: required field");
        if (this.userRepository.findByUsername(userDto.getEmail()).isPresent())   {
            LOGGER.error(String.format("User with email: %s already exists!",userDto.getEmail()));
            throw new ValidationException(String.format("User with email: %s already exists!", userDto.getEmail()));
        }
    }
}
