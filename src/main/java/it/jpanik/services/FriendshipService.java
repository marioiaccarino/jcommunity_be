package it.jpanik.services;

import it.jpanik.dto.FriendshipDto;
import it.jpanik.exceptions.ValidationException;

import java.util.List;

public interface FriendshipService {

    List<FriendshipDto> get();

    FriendshipDto get(Long id) throws ValidationException;
    
    FriendshipDto saveOrUpdate(FriendshipDto friendshipDto) throws ValidationException;

    FriendshipDto delete(Long id) throws ValidationException;

}
