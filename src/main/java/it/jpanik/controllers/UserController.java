package it.jpanik.controllers;

import it.jpanik.dto.*;
import it.jpanik.services.UserService;
import it.jpanik.services.servicesImp.AuthenticationServiceImp;
import it.jpanik.validator.UserValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import it.jpanik.exceptions.*;

import java.util.*;

@RestController
@RequestMapping("/jCommunity/user")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final AuthenticationServiceImp authenticationServiceImp;
    private final UserValidator userValidator;

    @Autowired
    public UserController(UserService userService, AuthenticationServiceImp authenticationServiceImp, UserValidator userValidator) {
        this.userService = userService;
        this.authenticationServiceImp = authenticationServiceImp;
        this.userValidator = userValidator;
    }

    @GetMapping
    public List<UserDto> getAll()  {
        LOGGER.info("Called GET /user");
        return this.userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserDto getAll(@PathVariable Long id) throws ValidationException {
        LOGGER.info("Called GET /user/{}", id);
        return this.userService.get(id);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody UserDto request) throws ValidationException {
        LOGGER.info("Called POST /auth/register");
        this.userValidator.validate(request);
        return this.authenticationServiceImp.register(request);
    }

    @DeleteMapping("/{id}")
    public UserDto delete(@PathVariable Long id) throws ValidationException {
        LOGGER.info("Called DELETE /user/{}", id);
        return this.userService.delete(id);
    }

    @PutMapping("/getUserById")
    public UserDto getUserById(@RequestBody UserSearchDto userSearchDto) throws ValidationException {
        LOGGER.info("Called PUT /User/getUserByEmail");
        return this.userService.getUserById(userSearchDto);
    }
}
