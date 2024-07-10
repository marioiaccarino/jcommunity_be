package it.jpanik.services;

import it.jpanik.dto.PostDto;
import it.jpanik.dto.PostSearchDto;
import it.jpanik.exceptions.ValidationException;

import java.util.List;

public interface PostService {

    PostDto get(Long id) throws ValidationException;
    List<PostDto> getAllPosts(PostSearchDto postSearchDto) throws ValidationException;
    PostDto saveOrUpdate(PostDto postDto);

    PostDto delete(Long id) throws ValidationException;
}
