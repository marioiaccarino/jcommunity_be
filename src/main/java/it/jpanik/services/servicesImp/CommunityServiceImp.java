package it.jpanik.services.servicesImp;

import it.jpanik.dto.CommunityDto;
import it.jpanik.entities.Community;
import it.jpanik.exceptions.ValidationException;
import it.jpanik.mappers.CommunityMapper;
import it.jpanik.mappers.UserCommunityMapper;
import it.jpanik.services.CommunityService;
import it.jpanik.repositories.CommunityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommunityServiceImp implements CommunityService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommunityServiceImp.class);
    private final UserCommunityMapper userCommunityMapper;
    private final CommunityMapper communityMapper;
    private final CommunityRepository communityRepository;

    @Autowired
    public CommunityServiceImp(CommunityRepository communityRepostory, CommunityMapper communityMapper, UserCommunityMapper userCommunityMapper)  {
        this.communityRepository = communityRepostory;
        this.communityMapper = communityMapper;
        this.userCommunityMapper = userCommunityMapper;
    }

    @Override
    public List<CommunityDto> getAllCommunities() {
        List<CommunityDto> communityDtoList = new ArrayList<>();
        this.communityRepository.findAll().forEach(c -> {
            CommunityDto communityDto = this.communityMapper.convertEntityToDto(c);

            // FIXME queste cose qui sotto vanno tutte nel mapper
            communityDto.setUsersCommunity(this.userCommunityMapper.convertEntityListToDtoList(c.getUserCommunityList()));

            communityDtoList.add(communityDto);
        });

        LOGGER.info("All Communities: {}", communityDtoList);
        return communityDtoList;
    }

    @Override
    public CommunityDto get(Long id) throws ValidationException {
        Community community = this.communityRepository.findById(id)
                .orElseThrow(() -> new ValidationException(String.format("Community with Id: %d not found", id)));

        CommunityDto communityDto = this.communityMapper.convertEntityToDto(community);

        // FIXME queste cose qui sotto vanno tutte nel mapper
        communityDto.setUsersCommunity(this.userCommunityMapper.convertEntityListToDtoList(community.getUserCommunityList()));

        LOGGER.info("Community with Id: {} found!", id);
        return communityDto;
    }

    @Override
    public CommunityDto saveOrUpdate(CommunityDto communityDto)    {
        Community community = this.communityMapper.convertDtoToEntity(communityDto);

        // FIXME queste cose qui sotto vanno tutte nel mapper
        community.setUserCommunityList(this.userCommunityMapper.convertDtoListToEntityList(communityDto.getUsersCommunity()));

        this.communityRepository.save(community);

        CommunityDto communityDtoResult = this.communityMapper.convertEntityToDto(community);

        // FIXME queste cose qui sotto vanno tutte nel mapper
        communityDtoResult.setUsersCommunity(this.userCommunityMapper.convertEntityListToDtoList(community.getUserCommunityList()));

        LOGGER.info("Community saved ì: {}", communityDtoResult);

        return communityDtoResult;
    }

    @Override
    public CommunityDto delete(Long id) throws ValidationException {
        Community community = this.communityRepository.findById(id)
                .orElseThrow(() -> new ValidationException(String.format("Community with Id: %d not found", id)));


        CommunityDto communityDto = this.communityMapper.convertEntityToDto(community);

            // FIXME queste cose qui sotto vanno tutte nel mapper
        communityDto.setUsersCommunity(this.userCommunityMapper.convertEntityListToDtoList(community.getUserCommunityList()));

            // FIXME perchè tirarti su tutta l'entity invece di fare deleteById?
        this.communityRepository.delete(community);
        LOGGER.info("Community with Id: %d Successfully Deleted", id);
        return communityDto;
    }

}
