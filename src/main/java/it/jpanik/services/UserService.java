package it.jpanik.services;

import it.jpanik.dto.UserDto;
import it.jpanik.dto.UserSearchDto;
import it.jpanik.exceptions.ValidationException;

import java.util.List;

public interface UserService {
    UserDto updateUser(UserDto userDto);
    List<UserDto> getAllUsers();
    UserDto get(Long id) throws ValidationException;
    UserDto delete(Long id) throws ValidationException;
    UserDto getUserById(UserSearchDto userSearchDto) throws ValidationException;
}
