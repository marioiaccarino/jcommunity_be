package it.jpanik.controllers;

import it.jpanik.dto.UserCommunityCreateDto;
import it.jpanik.dto.UserCommunityDto;

import it.jpanik.dto.UserCommunitySearchDto;
import it.jpanik.exceptions.ValidationException;
import it.jpanik.services.UserCommunityService;
import it.jpanik.validator.UserCommunityValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 *@author Mario Iaccarino
 */
@RestController
@RequestMapping("/jCommunity/userCommunity")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserCommunityController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserCommunityController.class);
    private final UserCommunityService userCommunityService;
    private final UserCommunityValidator userCommunityValidator;

    @Autowired
    public UserCommunityController(UserCommunityService userCommunityService, UserCommunityValidator userCommunityValidator) {
        this.userCommunityService = userCommunityService;
        this.userCommunityValidator = userCommunityValidator;
    }

    @GetMapping
    public List<UserCommunityDto> getAll()  {
        LOGGER.info("Called GET /userCommunity");
        return this.userCommunityService.get();
    }

    @GetMapping("/{id}")
    public UserCommunityDto get(@PathVariable Long id) throws ValidationException {
        LOGGER.info("Called GET /userCommunity/{}", id);
        return this.userCommunityService.get(id);
    }

    @PostMapping
    public UserCommunityDto saveOrUpdate(@RequestBody UserCommunityDto userCommunityDto) throws ValidationException {
        LOGGER.info("Called POST /userCommunity");
        this.userCommunityValidator.validate(userCommunityDto);
        return this.userCommunityService.saveOrUpdate(userCommunityDto);
    }

    @PostMapping("/create")
    public UserCommunityDto createUserCommunity(@RequestBody UserCommunityCreateDto userCommunityCreateDto  ) throws ValidationException {
        LOGGER.info("Called POST /userCommunity/create");
        this.userCommunityValidator.validateUserCommunityCreate(userCommunityCreateDto);
        return this.userCommunityService.createUserCommunity(userCommunityCreateDto);
    }

    @PutMapping("/loggedUserId")
    public List<UserCommunityDto> getAllUsersCommunityOfLoggedUser(@RequestBody UserCommunitySearchDto userCommunitySearchDto) throws ValidationException {
       LOGGER.info("Called PUT /loggedUserId ID: {}",userCommunitySearchDto.getLoggedUserId());
        return this.userCommunityService.getAllUsersCommunityOfLoggedUser(userCommunitySearchDto);
    }

    @DeleteMapping("/{id}")
    public UserCommunityDto delete(@PathVariable Long id) throws ValidationException {
        return this.userCommunityService.delete(id);
    }
}
