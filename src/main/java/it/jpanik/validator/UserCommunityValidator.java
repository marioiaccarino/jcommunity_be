package it.jpanik.validator;

import it.jpanik.dto.UserCommunityCreateDto;
import it.jpanik.dto.UserCommunityDto;
import it.jpanik.entities.Community;
import it.jpanik.entities.User;
import it.jpanik.exceptions.ValidationException;
import it.jpanik.mappers.CommunityMapper;
import it.jpanik.mappers.UserMapper;
import it.jpanik.repositories.CommunityRepository;
import it.jpanik.repositories.UserCommunityRepository;
import it.jpanik.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserCommunityValidator extends Validator {

    private final UserCommunityRepository userCommunityRepository;

    private final UserMapper userMapper;

    private final CommunityMapper communityMapper;

    private final CommunityRepository communityRepository;

    private final UserRepository userRepository;

    @Autowired
    public UserCommunityValidator(UserCommunityRepository userCommunityRepository, UserMapper userMapper, CommunityMapper communityMapper, CommunityRepository communityRepository, UserRepository userRepository) {
        this.userCommunityRepository = userCommunityRepository;
        this.userMapper = userMapper;
        this.communityMapper = communityMapper;
        this.communityRepository = communityRepository;
        this.userRepository = userRepository;
    }

    public void validate(UserCommunityDto userCommunityDto) throws ValidationException {
        checkEmpty(userCommunityDto.getId(), "ID: required field");
        checkEmpty(userCommunityDto.getNickname(), "Nickname: required field");
        checkNull(userCommunityDto.getUser(), "ReferredUser: required field");
        checkNull(userCommunityDto.getSubscribedCommunity(), "Community: required field");
        if(this.userCommunityRepository.findUserCommunityBySubscribedCommunityIdAndReferredUserId(
                userCommunityDto.getSubscribedCommunity().getId(),
                userCommunityDto.getUser().getId()
        ).isPresent())    {
            throw new ValidationException(String.format("UserCommunity With User: {} and Community: {} already exists",userCommunityDto.getUser(),userCommunityDto.getSubscribedCommunity()));
        }
    }

    public void validateUserCommunityCreate(UserCommunityCreateDto userCommunityCreateDto)  throws ValidationException  {
        Community community = this.communityRepository.findById(userCommunityCreateDto.getCommunityId())
                .orElseThrow(()-> new ValidationException(String.format("Community With Id: {} not found",userCommunityCreateDto.getCommunityId())));
        User user = this.userRepository.findById(userCommunityCreateDto.getLoggedUserId())
                .orElseThrow(()-> new ValidationException(String.format("User With Id: {} not found",userCommunityCreateDto.getLoggedUserId())));

        if(this.userCommunityRepository.findUserCommunityBySubscribedCommunityIdAndReferredUserId(userCommunityCreateDto.getCommunityId(),userCommunityCreateDto.getLoggedUserId()).isPresent())    {
            throw new ValidationException(String.format("UserCommunity With User: {} and Community: {} already exists",user,community));
        }
    }
}
