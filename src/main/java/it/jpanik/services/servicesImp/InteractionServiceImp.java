package it.jpanik.services.servicesImp;

import it.jpanik.dto.InteractionDto;
import it.jpanik.entities.Interaction;
import it.jpanik.mappers.InteractionMapper;
import it.jpanik.mappers.PostMapper;
import it.jpanik.mappers.UserCommunityMapper;
import it.jpanik.repositories.InteractionRepository;
import it.jpanik.services.InteractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InteractionServiceImp implements InteractionService {

    // FIXME l'autowired sui singoli campi è deprecato, bisogna usare il costruttore
    @Autowired
    private InteractionRepository interactionRepository;

    // FIXME l'autowired sui singoli campi è deprecato, bisogna usare il costruttore
    @Autowired
    private InteractionMapper interactionMapper;

    // FIXME l'autowired sui singoli campi è deprecato, bisogna usare il costruttore
    @Autowired
    private PostMapper postMapper;

    // FIXME l'autowired sui singoli campi è deprecato, bisogna usare il costruttore
    @Autowired
    private UserCommunityMapper userCommunityMapper;

    @Override
    public InteractionDto get(Long id)  {
        Optional<Interaction> interaction = this.interactionRepository.findById(id);

        return interaction.map( interac -> {
            InteractionDto interactionDto = this.interactionMapper.convertEntityToDto(interac);

            // FIXME queste cose qui sotto vanno tutte nel mapper
            interactionDto.setUserCommunity(this.userCommunityMapper.convertEntityToDto(interac.getUserCommunity()));
            interactionDto.setPostDto(this.postMapper.convertEntityToDto(interac.getPost()));

            return interactionDto;

        }).orElse(null);
        // FIXME orElseNull? Sarebbe bene lanciare un'eccezione invece, di tipo ServiceException o ValidationException
        // che avverta che l'utente sta riuscendo a fare una cosa impossibile, altrimenti nessuno ne ha visibilità
        // del fatto che c'è un bug

        // FIXME è buona cosa mettere un logger di ritorno con l'oggetto risultato
    }

    @Override
    public List<InteractionDto> getAllInteractions()   {
        List<InteractionDto> interactionDtoList = new ArrayList<>();

        this.interactionRepository.findAll().forEach( i -> {
            InteractionDto interactionDto = this.interactionMapper.convertEntityToDto(i);

            // FIXME queste cose qui sotto vanno tutte nel mapper
            interactionDto.setPostDto(this.postMapper.convertEntityToDto(i.getPost()));
            interactionDto.setUserCommunity(this.userCommunityMapper.convertEntityToDto(i.getUserCommunity()));

            interactionDtoList.add(interactionDto);
        });

        // FIXME è buona cosa mettere un logger di ritorno con l'oggetto risultato

        return interactionDtoList;
    }

    @Override
    public InteractionDto saveOrUpdate(InteractionDto interactionDto)   {
        Interaction interaction = this.interactionMapper.convertDtoToEntity(interactionDto);

        // FIXME queste cose qui sotto vanno tutte nel mapper
        interaction.setPost(this.postMapper.convertDtoToEntity(interactionDto.getPostDto()));
        interaction.setUserCommunity(this.userCommunityMapper.convertDtoToEntity(interactionDto.getUserCommunity()));

        this.interactionRepository.save(interaction);

        InteractionDto interactionDtoRes = this.interactionMapper.convertEntityToDto(interaction);

        // FIXME queste cose qui sotto vanno tutte nel mapper
        interactionDtoRes.setPostDto(this.postMapper.convertEntityToDto(interaction.getPost()));
        interactionDtoRes.setUserCommunity(this.userCommunityMapper.convertEntityToDto(interaction.getUserCommunity()));

        // FIXME è buona cosa mettere un logger di ritorno con l'oggetto risultato

        return interactionDtoRes;
    }

    @Override
    public InteractionDto delete(Long id)   {
        Interaction interaction = this.interactionRepository.findById(id).orElse(null);
        // FIXME orElseNull? Sarebbe bene lanciare un'eccezione invece, di tipo ServiceException o ValidationException
        // che avverta che l'utente sta riuscendo a fare una cosa impossibile, altrimenti nessuno ne ha visibilità
        // del fatto che c'è un bug

        InteractionDto interactionDtoRes = this.interactionMapper.convertEntityToDto(interaction);

        // FIXME queste cose qui sotto vanno tutte nel mapper (occhio alla NullPointer)
        interactionDtoRes.setPostDto(this.postMapper.convertEntityToDto(interaction.getPost()));
        interactionDtoRes.setUserCommunity(this.userCommunityMapper.convertEntityToDto(interaction.getUserCommunity()));

        // FIXME perchè tirarti su tutta l'entity invece di fare deleteById?
        this.interactionRepository.delete(interaction);

        // FIXME è buona cosa mettere un logger di ritorno con l'oggetto risultato

        return  interactionDtoRes;
    }
}
