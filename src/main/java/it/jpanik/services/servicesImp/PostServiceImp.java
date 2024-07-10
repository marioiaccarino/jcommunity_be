package it.jpanik.services.servicesImp;

import it.jpanik.dto.PostSearchDto;
import it.jpanik.entities.Community;
import it.jpanik.entities.Post;
import it.jpanik.exceptions.ValidationException;
import it.jpanik.mappers.InteractionMapper;
import it.jpanik.mappers.PostMapper;
import it.jpanik.mappers.UserCommunityMapper;
import it.jpanik.repositories.CommunityRepository;
import it.jpanik.repositories.PostRepository;
import it.jpanik.repositories.UserCommunityRepository;
import it.jpanik.services.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import it.jpanik.dto.PostDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImp implements PostService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PostServiceImp.class);

    private final PostRepository postRepository;

    private final PostMapper postMapper;

    private final UserCommunityMapper userCommunityMapper;

    private final InteractionMapper interactionMapper;

    private final CommunityRepository communityRepository;

    private final UserCommunityRepository userCommunityRepository;

    @Autowired
    public PostServiceImp(PostRepository postRepository,
                          PostMapper postMapper,
                          UserCommunityMapper userCommunityMapper,
                          InteractionMapper interactionMapper,
                          CommunityRepository communityRepository,
                          UserCommunityRepository userCommunityRepository) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
        this.userCommunityMapper = userCommunityMapper;
        this.interactionMapper = interactionMapper;
        this.communityRepository = communityRepository;
        this.userCommunityRepository = userCommunityRepository;
    }

    @Override
    public PostDto get(Long id) throws ValidationException {
        Post post = this.postRepository.findById(id)
                .orElseThrow(() -> new ValidationException(String.format("Post with Id: %d not found",id)));


        PostDto postDto = this.postMapper.convertEntityToDto(post);

        // FIXME queste cose qui sotto vanno tutte nel mapper
        postDto.setInteractions(this.interactionMapper.convertEntityListToDtoList(post.getInteractions()));
        LOGGER.info("Post with Id: {} = {}",id, postDto);
        return postDto;
    }

    @Override
    public List<PostDto> getAllPosts(PostSearchDto postSearchDto) throws ValidationException {
        List<PostDto> posts = new ArrayList<>();

        if(postSearchDto.getUsersCommunityId().size()!=0)   {

            this.postRepository.findAll().forEach(p -> {
                if (postSearchDto.getUsersCommunityId().contains(p.getAuthor().getId())) {
                    posts.add(this.postMapper.convertEntityToDto(p));
                }
            });

            LOGGER.info("Posts of All UsersCommunity {}",posts);
            return posts;
        }
        else if(postSearchDto.getCommunityId()!=null) {
            Community community = this.communityRepository.findById(postSearchDto.getCommunityId())
                    .orElseThrow(() -> new ValidationException(String.format("Community with Id: %s not found",postSearchDto.getCommunityId())));

            LOGGER.info("Posts of The Selected Community with Id: {} = {}",postSearchDto.getCommunityId(),posts);
            return community.getUserCommunityList().stream().flatMap(userCommunity ->
                    userCommunity.getPublishedPosts().stream().map(post -> this.postMapper.convertEntityToDto(post))).toList();
        }

        this.postRepository.findAll().forEach( p -> {
            PostDto postDto = this.postMapper.convertEntityToDto(p);

            // FIXME queste cose qui sotto vanno tutte nel mapper
            postDto.setInteractions(this.interactionMapper.convertEntityListToDtoList(p.getInteractions()));

            posts.add(postDto);
        });

        LOGGER.info("Get All Posts: {}",posts);
        return posts;
    }

    @Override
    public PostDto saveOrUpdate(PostDto postDto)    {
        Post post = this.postMapper.convertDtoToEntity(postDto);

        // FIXME queste cose qui sotto vanno tutte nel mapper
        post.setInteractions(this.interactionMapper.convertDtoListToEntityList(postDto.getInteractions()));

        this.postRepository.save(post);

        PostDto postDtoRes = this.postMapper.convertEntityToDto(post);

        // FIXME queste cose qui sotto vanno tutte nel mapper
        postDtoRes.setAuthor(this.userCommunityMapper.convertEntityToDto(post.getAuthor()));
        postDtoRes.setInteractions(this.interactionMapper.convertEntityListToDtoList(post.getInteractions()));

        LOGGER.info("Post Saved Successfully: {}",postDtoRes);
        return postDtoRes;
    }

    @Override
    public PostDto delete(Long id) throws ValidationException {
        Post post = this.postRepository.findById(id)
                .orElseThrow(() -> new ValidationException(String.format("Post with Id: %s not found",id)));

        PostDto postDto = this.postMapper.convertEntityToDto(post);
        postDto.setInteractions(this.interactionMapper.convertEntityListToDtoList(post.getInteractions()));
        this.postRepository.delete(post);
        LOGGER.info("Deleted Post Successfully: {}",postDto);
        return postDto;
    }
}
