package it.jpanik.services;

import it.jpanik.dto.UserCommunityCreateDto;
import it.jpanik.dto.UserCommunityDto;
import it.jpanik.dto.UserCommunitySearchDto;
import it.jpanik.exceptions.ValidationException;

import java.util.List;

public interface UserCommunityService {
    UserCommunityDto get(Long id) throws ValidationException;

    List<UserCommunityDto> get();

    UserCommunityDto saveOrUpdate(UserCommunityDto userCommunityDto) throws ValidationException;

    UserCommunityDto delete(Long id) throws ValidationException;

    List<UserCommunityDto> getAllUsersCommunityOfLoggedUser(UserCommunitySearchDto userCommunitySearchDto) throws ValidationException;

    UserCommunityDto createUserCommunity(UserCommunityCreateDto userCommunityCreateDto) throws ValidationException;
}
