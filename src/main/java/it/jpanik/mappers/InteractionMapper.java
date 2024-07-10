package it.jpanik.mappers;

import it.jpanik.dto.InteractionDto;
import it.jpanik.entities.Interaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InteractionMapper extends Mapper<InteractionDto, Interaction> {

    private final PostMapper postMapper;

    private final UserCommunityMapper userCommunityMapper;

    @Autowired
    public InteractionMapper(PostMapper postMapper, UserCommunityMapper userCommunityMapper) {
        this.postMapper = postMapper;
        this.userCommunityMapper = userCommunityMapper;
    }

    @Override
    public InteractionDto convertEntityToDtoImpl(Interaction interaction) {

        InteractionDto interactionDto = new InteractionDto();
        interactionDto.setId(interaction.getId());
        interactionDto.setTypeOfInteraction(interaction.getTypeOfInteraction());

        interactionDto.setPostDto(this.postMapper.convertEntityToDto(interaction.getPost()));
        interactionDto.setUserCommunity(this.userCommunityMapper.convertEntityToDto(interaction.getUserCommunity()));
        return interactionDto;
    }

    @Override
    public Interaction convertDtoToEntityImpl(InteractionDto interactionDto) {

        Interaction interaction = new Interaction();
        interaction.setId(interactionDto.getId());
        interaction.setTypeOfInteraction(interactionDto.getTypeOfInteraction());

        interaction.setPost(this.postMapper.convertDtoToEntity(interactionDto.getPostDto()));
        interaction.setUserCommunity(this.userCommunityMapper.convertDtoToEntityImpl(interactionDto.getUserCommunity()));
        return interaction;
    }
}
