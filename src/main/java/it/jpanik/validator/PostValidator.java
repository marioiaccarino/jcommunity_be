package it.jpanik.validator;

import it.jpanik.dto.PostDto;
import it.jpanik.exceptions.ValidationException;

// FIXME In linea generale conviene fare dei validator che sono anche dei service perchè così se hai bisogno di fare
// dei controlli che richiedono il DB puoi farlo
public class PostValidator extends Validator {

    public static void validate(PostDto postDto) throws ValidationException {
        checkEmpty(postDto.getId(), "ID: required field");
        checkEmpty(postDto.getTitle(), "Title: required field");
        checkEmpty(postDto.getDescription(), "Description: required field");
        checkNull(postDto.getAuthor(), "Author: required field");
    }
}
