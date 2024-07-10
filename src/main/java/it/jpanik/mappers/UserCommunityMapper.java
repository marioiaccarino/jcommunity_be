package it.jpanik.mappers;

import it.jpanik.dto.UserCommunityDto;
import it.jpanik.entities.UserCommunity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserCommunityMapper extends Mapper<UserCommunityDto, UserCommunity> {

    private final CommunityMapper communityMapper;

    private final UserMapper userMapper;

    @Autowired
    public UserCommunityMapper(CommunityMapper communityMapper, UserMapper userMapper) {
        this.communityMapper = communityMapper;
        this.userMapper = userMapper;
    }

    @Override
    public UserCommunityDto convertEntityToDtoImpl(UserCommunity userCommunity) {

        UserCommunityDto ucd = new UserCommunityDto();
        ucd.setId(userCommunity.getId());
        ucd.setNickname(userCommunity.getNickName());
        ucd.setSubscribedCommunity(this.communityMapper.convertEntityToDto(userCommunity.getSubscribedCommunity()));
        ucd.setUser(this.userMapper.convertEntityToDto(userCommunity.getReferredUser()));
        return ucd;
    }

    @Override
    public UserCommunity convertDtoToEntityImpl(UserCommunityDto userCommunityDto) {

        UserCommunity userCommunity = new UserCommunity();
        userCommunity.setId(userCommunityDto.getId());
        userCommunity.setNickName(userCommunityDto.getNickname());
        userCommunity.setSubscribedCommunity(this.communityMapper.convertDtoToEntity(userCommunityDto.getSubscribedCommunity()));
        userCommunity.setReferredUser(this.userMapper.convertDtoToEntity(userCommunityDto.getUser()));
        return userCommunity;
    }



}
