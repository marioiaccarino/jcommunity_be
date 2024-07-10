package it.jpanik.validator;

import it.jpanik.dto.CommunityDto;
import it.jpanik.exceptions.ValidationException;

// FIXME In linea generale conviene fare dei validator che sono anche dei service perchè così se hai bisogno di fare
// dei controlli che richiedono il DB puoi farlo
public class CommunityValidator extends Validator {

    public static void validate(CommunityDto communityDto) throws ValidationException {
        checkEmpty(communityDto.getId(), "ID: required field");
        checkEmpty(communityDto.getName(),"Name: Required field");
    }
}
