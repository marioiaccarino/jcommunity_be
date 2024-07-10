package it.jpanik.services.servicesImp;

import it.jpanik.exceptions.ValidationException;
import it.jpanik.mappers.UserCommunityMapper;
import it.jpanik.mappers.UserMapper;
import it.jpanik.repositories.UserRepository;
import it.jpanik.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import it.jpanik.entities.*;
import it.jpanik.dto.*;


import java.util.*;

/*
 * @author Mario Iaccarino
 */
@Service
public class UserServiceImp implements UserService{

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImp.class);

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final UserCommunityMapper userCommunityMapper;

    @Autowired
    public UserServiceImp(UserMapper userMapper,
                          UserRepository userRepository,
                          UserCommunityMapper userCommunityMapper
                          )    {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.userCommunityMapper = userCommunityMapper;
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<UserDto> userDtoList = new ArrayList<>();
        userRepository.findAll().forEach(user -> {
            UserDto userDto = userMapper.convertEntityToDto(user);
            // FIXME perchè lo fai qui e non nel mapper di User? Servono a questo i mapper, a non porcare i service con i setter
            userDto.setUsersCommunity(userCommunityMapper.convertEntityListToDtoList(user.getUserCommunityList()));
            userDtoList.add(userDto);
        });
        LOGGER.info("All Users: {}", userDtoList);
        return userDtoList;
    }

    @Override
    public UserDto get(Long id) throws ValidationException {
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new ValidationException(String.format("User with Id: {} doesn't exists!",id )));
        UserDto userDto = this.userMapper.convertEntityToDto(user);
        // FIXME perchè lo fai qui e non nel mapper di User? Servono a questo i mapper, a non porcare i service con i setter
        userDto.setUsersCommunity(this.userCommunityMapper.convertEntityListToDtoList(user.getUserCommunityList()));
        LOGGER.info(String.format("Get User: {}, by Id: {}",userDto, userDto.getId()));
        return userDto;
    }

    @Override
    public UserDto updateUser(UserDto userDto)  {
        User user = this.userMapper.convertDtoToEntity(userDto);
        // FIXME perchè lo fai qui e non nel mapper di User? Servono a questo i mapper, a non porcare i service con i setter
        user.setUserCommunityList(this.userCommunityMapper.convertDtoListToEntityList(userDto.getUsersCommunity()));

        this.userRepository.save(user);

        UserDto userDtoResult = this.userMapper.convertEntityToDto(user);
        // FIXME perchè lo fai qui e non nel mapper di User? Servono a questo i mapper, a non porcare i service con i setter
        userDtoResult.setUsersCommunity(this.userCommunityMapper.convertEntityListToDtoList(user.getUserCommunityList()));

        LOGGER.info(String.format("Updated User: {}", userDto));
        return userDtoResult;
    }

    @Override
    public UserDto delete(Long id) throws ValidationException {
        User user = this.userRepository.findById(id)

                .orElseThrow(()->{
                    LOGGER.error(String.format("User with ID: %s doesn' exists", id));
                    return new ValidationException(String.format("User with ID: %s doesn' exists", id));
                });

        UserDto userDto = this.userMapper.convertEntityToDto(user);
        this.userRepository.delete(user);

        LOGGER.info("Deleted User: {}", userDto);
        return userDto;
    }

    @Override
    public UserDto getUserById(UserSearchDto userSearchDto) throws ValidationException{
        User user = this.userRepository.findById(userSearchDto.getUserId()).
                orElseThrow(() -> new ValidationException(String.format("Can't find User with Email: %s"+userSearchDto.getUserId())));

        UserDto userDto = this.userMapper.convertEntityToDto(user);
        LOGGER.info("User found by Id:{} = {}",userSearchDto.getUserId(), userDto);
        return userDto;
    }

}
