package it.jpanik.services.servicesImp;

import it.jpanik.dto.FriendshipDto;
import it.jpanik.entities.Friendship;
import it.jpanik.entities.UserCommunity;
import it.jpanik.exceptions.ValidationException;
import it.jpanik.mappers.FriendshipMapper;
import it.jpanik.mappers.UserCommunityMapper;
import it.jpanik.repositories.FriendshipRepository;
import it.jpanik.repositories.UserCommunityRepository;
import it.jpanik.services.FriendshipService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FriendshipServiceImp implements FriendshipService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FriendshipServiceImp.class);

    private final FriendshipMapper friendshipMapper;

    private final FriendshipRepository friendshipRepository;

    private final UserCommunityMapper userCommunityMapper;

    private final UserCommunityRepository userCommunityRepository;

    @Autowired
    public FriendshipServiceImp(FriendshipMapper friendshipMapper,
                                FriendshipRepository friendshipRepository,
                                UserCommunityMapper userCommunityMapper,
                                UserCommunityRepository userCommunityRepository)  {
        this.friendshipMapper = friendshipMapper;
        this.friendshipRepository = friendshipRepository;
        this.userCommunityMapper = userCommunityMapper;
        this.userCommunityRepository = userCommunityRepository;
    }

    @Override
    public List<FriendshipDto> get()    {
        List<FriendshipDto> friendshipDtoList = new ArrayList<>();

        this.friendshipRepository.findAll().forEach( friendshipItem -> {
            FriendshipDto friendshipDto = this.friendshipMapper.convertEntityToDto(friendshipItem);
            friendshipDtoList.add(friendshipDto);
        });

        return friendshipDtoList;
    }

    @Override
    public FriendshipDto get(Long id) throws ValidationException {
        Friendship friendship = this.friendshipRepository.findById(id)
                .orElseThrow(() -> new ValidationException(String.format("Friendship with Id: %s not found",id)));

        FriendshipDto friendshipDto = this.friendshipMapper.convertEntityToDto(friendship);
        LOGGER.info("Get Friendship with Id: {} Success: {}",id,friendshipDto);
        return friendshipDto;
    }

    @Override
    public FriendshipDto saveOrUpdate(@RequestBody FriendshipDto friendshipDto) throws ValidationException {
        FriendshipDto finalFriendshipDto = friendshipDto;
        UserCommunity userCommunityWhoSends =  this.userCommunityRepository.findById(friendshipDto.getUserCommunityWhoSends().getId())
                .orElseThrow(() -> new ValidationException(String.format("UserCommunity with Id: %s not found", finalFriendshipDto.getUserCommunityWhoSends().getId())));
        UserCommunity userCommunityWhoReceives = this.userCommunityRepository.findById(friendshipDto.getUserCommunityWhoReceives().getId())
                .orElseThrow(() -> new ValidationException(String.format("UserCommunity with Id: %s not found",finalFriendshipDto.getUserCommunityWhoReceives().getId())));

        Friendship friendship = this.friendshipMapper.convertDtoToEntity(friendshipDto);

        friendship = this.friendshipRepository.save(friendship);
        friendshipDto = this.friendshipMapper.convertEntityToDto(friendship);

        LOGGER.info("Friendship With Id: {} Successfully saved: {}",friendshipDto.getId(),friendshipDto);
        return friendshipDto;
    }

    @Override
    public FriendshipDto delete(Long id) throws ValidationException {

        Friendship friendship = this.friendshipRepository.findById(id)
                .orElseThrow(() -> new ValidationException(String.format("Friendship with Id: %s not found",id)));

        FriendshipDto friendshipDto = this.friendshipMapper.convertEntityToDto(friendship);

        // FIXME perchè tirarti su tutta l'entity invece di fare deleteById?
        this.friendshipRepository.delete(friendship);
        return friendshipDto;
    }


}
