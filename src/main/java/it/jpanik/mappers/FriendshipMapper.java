package it.jpanik.mappers;

import it.jpanik.dto.FriendshipDto;
import it.jpanik.entities.Friendship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FriendshipMapper extends Mapper<FriendshipDto, Friendship> {

    private final UserCommunityMapper userCommunityMapper;

    @Autowired
    public FriendshipMapper(UserCommunityMapper userCommunityMapper) {
        this.userCommunityMapper = userCommunityMapper;
    }

    @Override
    public FriendshipDto convertEntityToDtoImpl(Friendship friendship) {

        FriendshipDto friendshipDto = new FriendshipDto();
        friendshipDto.setTypeOfFriendship(friendship.getTypeOfFriendship());
        friendshipDto.setId(friendship.getId());
        friendshipDto.setAccepted(friendship.isAccepted());
        friendshipDto.setUserCommunityWhoSends(this.userCommunityMapper.convertEntityToDto(friendship.getUserCommunityWhoSends()));
        friendshipDto.setUserCommunityWhoReceives(this.userCommunityMapper.convertEntityToDto(friendship.getUserCommunityWhoReceives()));
        return friendshipDto;

    }

    @Override
    public Friendship convertDtoToEntityImpl(FriendshipDto friendshipDto) {

        Friendship friendship = new Friendship();
        friendship.setTypeOfFriendship(friendshipDto.getTypeOfFriendship());
        friendship.setId(friendshipDto.getId());
        friendship.setAccepted(friendshipDto.isAccepted());
        friendship.setUserCommunityWhoSends(this.userCommunityMapper.convertDtoToEntity(friendshipDto.getUserCommunityWhoSends()));
        friendship.setUserCommunityWhoReceives(this.userCommunityMapper.convertDtoToEntity(friendshipDto.getUserCommunityWhoReceives()));
        return friendship;
    }
}
