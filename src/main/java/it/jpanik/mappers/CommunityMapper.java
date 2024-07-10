package it.jpanik.mappers;

import it.jpanik.dto.CommunityDto;
import it.jpanik.entities.Community;
import org.springframework.stereotype.Service;

@Service
public class CommunityMapper extends Mapper<CommunityDto, Community> {

    @Override
    public CommunityDto convertEntityToDtoImpl(Community community) {

        CommunityDto communityDto = new CommunityDto();
        communityDto.setPublic(community.isPublic());
        communityDto.setId(community.getId());
        communityDto.setName(community.getName());
        communityDto.setUrlImage(community.getUrlImage());

        return communityDto;
    }

    @Override
    public Community convertDtoToEntityImpl(CommunityDto communityDto) {

        Community community = new Community();
        community.setPublic(communityDto.isPublic());
        community.setId(communityDto.getId());
        community.setName(communityDto.getName());
        community.setUrlImage(communityDto.getUrlImage());

        return community;
    }
}
