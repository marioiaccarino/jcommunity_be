package it.jpanik.services;

import it.jpanik.dto.CommunityDto;
import it.jpanik.dto.UserCommunityDto;
import it.jpanik.exceptions.ValidationException;

import java.util.List;

public interface CommunityService {

    List<CommunityDto> getAllCommunities();

    CommunityDto get(Long id) throws ValidationException;

    CommunityDto saveOrUpdate(CommunityDto communityDto);

    CommunityDto delete(Long id) throws ValidationException;
}
