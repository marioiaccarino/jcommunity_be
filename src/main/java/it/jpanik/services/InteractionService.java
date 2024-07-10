package it.jpanik.services;

import it.jpanik.dto.InteractionDto;
import org.springframework.stereotype.Service;

import java.util.List;

public interface InteractionService {

    InteractionDto get(Long id);
    List<InteractionDto> getAllInteractions();
    InteractionDto saveOrUpdate(InteractionDto interactionDto);
    InteractionDto delete(Long id);
}
