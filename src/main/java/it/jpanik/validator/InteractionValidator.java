package it.jpanik.validator;

import it.jpanik.dto.InteractionDto;
import it.jpanik.exceptions.ValidationException;
import org.springframework.stereotype.Service;

@Service
public class InteractionValidator extends Validator {

    public static void validate(InteractionDto interactionDto)  throws ValidationException  {
        checkEmpty(interactionDto.getId(), "ID: required field");
        checkNull(interactionDto.getTypeOfInteraction(),"unknown Type of Interaction");
        checkNull(interactionDto.getPostDto(), "Referred Post: unknown");
        checkNull(interactionDto.getUserCommunity(), "Author: required");
    }
}
