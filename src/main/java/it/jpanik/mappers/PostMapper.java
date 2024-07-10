package it.jpanik.mappers;

import it.jpanik.dto.PostDto;
import it.jpanik.entities.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostMapper extends Mapper<PostDto, Post> {

    private final UserCommunityMapper userCommunityMapper;

    @Autowired
    public PostMapper(UserCommunityMapper userCommunityMapper)  {
        this.userCommunityMapper = userCommunityMapper;
    }

    @Override
    public PostDto convertEntityToDtoImpl(Post post) {

        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setDescription(post.getDescription());
        postDto.setTitle(post.getTitle());
        postDto.setUrlImage(post.getUrlImage());
        postDto.setAuthor(this.userCommunityMapper.convertEntityToDto(post.getAuthor()));

        return postDto;
    }

    @Override
    public Post convertDtoToEntityImpl(PostDto postDto) {

        Post post = new Post();
        post.setId(postDto.getId());
        post.setDescription(postDto.getDescription());
        post.setTitle(postDto.getTitle());
        post.setUrlImage(postDto.getUrlImage());
        post.setAuthor(this.userCommunityMapper.convertDtoToEntity(postDto.getAuthor()));

        return post;
    }
}
