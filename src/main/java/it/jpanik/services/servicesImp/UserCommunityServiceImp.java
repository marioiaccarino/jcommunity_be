package it.jpanik.services.servicesImp;

import it.jpanik.dto.UserCommunityCreateDto;
import it.jpanik.dto.UserCommunityDto;
import it.jpanik.dto.UserCommunitySearchDto;
import it.jpanik.entities.*;
import it.jpanik.exceptions.ValidationException;
import it.jpanik.mappers.*;
import it.jpanik.repositories.*;
import it.jpanik.services.UserCommunityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserCommunityServiceImp implements UserCommunityService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserCommunityServiceImp.class);

    private final UserCommunityMapper userCommunityMapper;

    private final UserCommunityRepository userCommunityRepository;

    private final CommunityMapper communityMapper;

    private final CommunityRepository communityRepository;

    private final UserRepository userRepository;

    private final InteractionRepository interactionRepository;

    private final PostRepository postRepository;

    private final InteractionMapper interactionMapper;

    private final UserMapper userMapper;

    private final PostMapper postMapper;

    private final FriendshipMapper friendshipMapper;

    private final FriendshipRepository friendshipRepository;

    @Autowired
    public UserCommunityServiceImp(UserCommunityMapper userCommunityMapper,
                                   UserRepository userRepository,
                                   CommunityRepository communityRepository,
                                   CommunityMapper communityMapper,
                                   UserCommunityRepository userCommunityRepository,
                                   InteractionRepository interactionRepository,
                                   InteractionMapper interactionMapper,
                                   UserMapper userMapper,
                                   PostRepository postRepository,
                                   PostMapper postMapper,
                                   FriendshipMapper friendShipMapper,
                                   FriendshipRepository friendshipRepository)   {
        this.userCommunityMapper = userCommunityMapper;
        this.userCommunityRepository = userCommunityRepository;
        this.communityMapper = communityMapper;
        this.communityRepository = communityRepository;
        this.userRepository = userRepository;
        this.interactionRepository = interactionRepository;
        this.interactionMapper = interactionMapper;
        this.userMapper = userMapper;
        this.postRepository = postRepository;
        this.postMapper = postMapper;
        this.friendshipMapper = friendShipMapper;
        this.friendshipRepository = friendshipRepository;
    }

    @Override
    public UserCommunityDto get(Long id) throws ValidationException {
        Optional<UserCommunity> userCommunityResult = this.userCommunityRepository.findById(id);

        UserCommunityDto userCommunity = userCommunityResult.map( uc -> {
            UserCommunityDto userCommunityDto = this.userCommunityMapper.convertEntityToDto(uc);

            // FIXME queste cose qui sotto vanno tutte nel mapper
            userCommunityDto.setInteractions(this.interactionMapper.convertEntityListToDtoList(uc.getInteractions()));
            userCommunityDto.setPublishedPosts(this.postMapper.convertEntityListToDtoList(uc.getPublishedPosts()));
            userCommunityDto.setSentFriendshipRequests(this.friendshipMapper.convertEntityListToDtoList(uc.getSentFriendshipRequests()));
            userCommunityDto.setReceivedFriendshipRequests(this.friendshipMapper.convertEntityListToDtoList(uc.getReceivedFriendshipRequests()));
            return userCommunityDto;

        }).orElseThrow( () -> new ValidationException(String.format("UserCommunity with Id: {} dowsn't exists", id)));

        LOGGER.info(String.format("User with Id: {} = {}",id,userCommunity));
        return userCommunity;
    }

    @Override
    public List<UserCommunityDto> get() {
        List<UserCommunityDto> usersCommunityDto = new ArrayList<>();

        this.userCommunityRepository.findAll().forEach(uc -> {
            UserCommunityDto userCommunityDto = this.userCommunityMapper.convertEntityToDto(uc);

            userCommunityDto.setSentFriendshipRequests(this.friendshipMapper.convertEntityListToDtoList(uc.getSentFriendshipRequests()));
            userCommunityDto.setReceivedFriendshipRequests(this.friendshipMapper.convertEntityListToDtoList(uc.getReceivedFriendshipRequests()));
            userCommunityDto.setInteractions(this.interactionMapper.convertEntityListToDtoList(uc.getInteractions()));
            userCommunityDto.setPublishedPosts(this.postMapper.convertEntityListToDtoList(uc.getPublishedPosts()));
            userCommunityDto.setSentFriendshipRequests(this.friendshipMapper.convertEntityListToDtoList(uc.getSentFriendshipRequests()));
            userCommunityDto.setReceivedFriendshipRequests(this.friendshipMapper.convertEntityListToDtoList(uc.getReceivedFriendshipRequests()));
            usersCommunityDto.add(userCommunityDto);
        });

        LOGGER.info("All UsersCommunity: {}", usersCommunityDto);
        return usersCommunityDto;
    }

    @Override
    public UserCommunityDto saveOrUpdate(UserCommunityDto userCommunityDto) throws ValidationException {

        UserCommunity userCommunity = this.userCommunityMapper.convertDtoToEntity(userCommunityDto);

        UserCommunityDto finalUserCommunityDto = userCommunityDto;
        User user = userRepository.findById(userCommunityDto.getUser().getId()).
                orElseThrow(() -> new ValidationException(String.format("User with Id: %d not found ", finalUserCommunityDto.getUser().getId())));

        Community community = communityRepository.findById(userCommunityDto.getSubscribedCommunity().getId())
                .orElseThrow(() -> new ValidationException(String.format("Commmunity with Id: %d not found", finalUserCommunityDto.getSubscribedCommunity().getId())));

        // FIXME queste cose qui sotto vanno tutte nel mapper quando converti da DTO
        List<Interaction> interactions = interactionRepository.findByUserCommunityId(userCommunityDto.getId());
        List<Post> posts = postRepository.findByAuthorId(userCommunityDto.getId());
        List<Friendship> receivedFriendshipRequests = this.friendshipRepository.findAllByUserCommunityWhoReceivesId(userCommunityDto.getId());
        List<Friendship> sentFriendshipRequests = this.friendshipRepository.findAllByUserCommunityWhoSendsId(userCommunityDto.getId());

        UserCommunity userCommunityResult = this.userCommunityMapper.convertDtoToEntity(userCommunityDto);
        userCommunityResult.setInteractions(interactions);
        userCommunityResult.setPublishedPosts(posts);
        userCommunityResult.setReceivedFriendshipRequests(receivedFriendshipRequests);
        userCommunityResult.setSentFriendshipRequests(sentFriendshipRequests);

        userCommunity = userCommunityRepository.save(userCommunityResult);

        userCommunityDto = this.userCommunityMapper.convertEntityToDto(userCommunity);

        // FIXME queste cose qui sotto vanno tutte nel mapper
        userCommunityDto.setInteractions(interactionMapper.convertEntityListToDtoList(interactions));
        userCommunityDto.setReceivedFriendshipRequests(this.friendshipMapper.convertEntityListToDtoList(userCommunity.getReceivedFriendshipRequests()));
        userCommunityDto.setSentFriendshipRequests(this.friendshipMapper.convertEntityListToDtoList(userCommunity.getSentFriendshipRequests()));

        LOGGER.info("UserCommunity Saved: {}", userCommunityDto);
        return userCommunityDto;
    }

    @Override
    public UserCommunityDto delete(Long id) throws ValidationException {

        UserCommunity userCommunity = this.userCommunityRepository.findById(id)
                .orElseThrow(() -> new ValidationException(String.format("UserCommunity with Id: %s doesn't exists", id)));

        UserCommunityDto userCommunityDto = this.userCommunityMapper.convertEntityToDto(userCommunity);

        // FIXME queste cose qui sotto vanno tutte nel mapper (ti dice anche che la prima get può darti NullPointer)
        userCommunityDto.setReceivedFriendshipRequests(this.friendshipMapper.convertEntityListToDtoList(userCommunity.getReceivedFriendshipRequests()));
        userCommunityDto.setSentFriendshipRequests(this.friendshipMapper.convertEntityListToDtoList(userCommunity.getSentFriendshipRequests()));

        // FIXME poi non ho capito una cosa... perchè ti costruisci tutte l'entity per poi eliminarla? Non potevi fare deleteById?
        this.userCommunityRepository.delete(userCommunity);

        LOGGER.info("UserCommunity Deleted: {}", userCommunityDto);
        return userCommunityDto;
    }

    @Override
    public List<UserCommunityDto> getAllUsersCommunityOfLoggedUser(UserCommunitySearchDto userCommunitySearchDto) throws ValidationException {
        List<UserCommunityDto> usersCommunity = new ArrayList<>();

        User user = this.userRepository.findById(userCommunitySearchDto.getLoggedUserId())
                .orElseThrow(() -> new ValidationException(String.format("User with Id: {} doesn't exists",userCommunitySearchDto.getLoggedUserId())));

            user.getUserCommunityList().forEach(uc -> {
                UserCommunityDto userCommunityDto = this.userCommunityMapper.convertEntityToDto(uc);
                usersCommunity.add(userCommunityDto);
            });


        LOGGER.info("Users Community Of Logged User with Id: {} = {}",userCommunitySearchDto.getLoggedUserId(), usersCommunity);

        return usersCommunity;
    }

    @Override
    public UserCommunityDto createUserCommunity(UserCommunityCreateDto userCommunityCreateDto) throws ValidationException {
        User user = this.userRepository.findById(userCommunityCreateDto.getLoggedUserId()).
                orElseThrow(() -> new ValidationException(String.format("User with Id: {} not found",userCommunityCreateDto.getLoggedUserId())));
        Community community = this.communityRepository.findById(userCommunityCreateDto.getCommunityId())
                .orElseThrow(() -> new ValidationException(String.format("Community with Id: {} not found",userCommunityCreateDto.getCommunityId())));

        UserCommunity userCommunity = new UserCommunity();
        userCommunity.setNickName(userCommunityCreateDto.getNickname());
        userCommunity.setSubscribedCommunity(community);
        userCommunity.setReferredUser(user);
        this.userCommunityRepository.save(userCommunity);

        LOGGER.info("Created User Community: {}",userCommunity);
        return this.userCommunityMapper.convertEntityToDto(userCommunity);
    }
}
