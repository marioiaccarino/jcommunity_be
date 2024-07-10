package it.jpanik.controllers;

import it.jpanik.dto.PostDto;
import it.jpanik.dto.PostSearchDto;
import it.jpanik.exceptions.ValidationException;
import it.jpanik.services.PostService;
import it.jpanik.validator.PostValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/jCommunity/post")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PostController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PostController.class);
    private final PostService postService;

    @Autowired
    public PostController(final PostService postService)   {
        this.postService = postService;
    }

    @PutMapping
    public List<PostDto> getAllPosts(@RequestBody PostSearchDto postSearchDto) throws ValidationException {
        LOGGER.info("Called GET /post");
        return this.postService.getAllPosts(postSearchDto);
    }

    @GetMapping("/{id}")
    public PostDto get(@PathVariable Long id) throws ValidationException  {
        LOGGER.info("Called GET /posts/{}",id);
        return this.postService.get(id);
    }

    @PostMapping
    public PostDto saveOrUpdate(@RequestBody PostDto postDto) throws ValidationException {
        LOGGER.info("Called POST /posts");
        PostValidator.validate(postDto);
        return this.postService.saveOrUpdate(postDto);
    }

    @DeleteMapping("/{id}")
    public PostDto delete(@PathVariable Long id) throws ValidationException {
        LOGGER.info("Called DELETE /posts/{}",id);
        return this.postService.delete(id);
    }
}
