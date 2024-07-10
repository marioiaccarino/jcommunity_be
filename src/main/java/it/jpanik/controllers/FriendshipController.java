package it.jpanik.controllers;

import it.jpanik.dto.FriendshipDto;
import it.jpanik.exceptions.ValidationException;
import it.jpanik.services.FriendshipService;
import it.jpanik.validator.FriendshipValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jCommunity/friendship")
@CrossOrigin(origins = "*", maxAge = 3600)
public class FriendshipController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FriendshipController.class);
    private final FriendshipService friendshipService;
    private final FriendshipValidator friendshipValidator;

    @Autowired
    public FriendshipController(
            final FriendshipService friendshipService,
            final FriendshipValidator friendshipValidator
    ) {
        this.friendshipService = friendshipService;
        this.friendshipValidator = friendshipValidator;
    }

    // FIXME sarebbe bello chiamarlo getAll (anche il service)
    @GetMapping
    public List<FriendshipDto> getAllFriendships()    {
        LOGGER.info("Called GET /friendship");
        return this.friendshipService.get();
    }

    @GetMapping("/{id}")
    public FriendshipDto getAllFriendships(@PathVariable Long id) throws ValidationException {
        LOGGER.info("Called GET /friendship/{}",id);
        return this.friendshipService.get(id);
    }

    @PostMapping
    public FriendshipDto saveOrUpdate(@RequestBody FriendshipDto friendshipDto) throws ValidationException {
        LOGGER.info("Called POST /friendship");
        FriendshipValidator.validate(friendshipDto);

       this.friendshipValidator.checkIfTheyAreAlreadyFriendsBySendedRequests(friendshipDto);
       this.friendshipValidator.checkIfTheyAreAlreadyFriendsByReceivedRequests(friendshipDto);

       return this.friendshipService.saveOrUpdate(friendshipDto);
    }

    @DeleteMapping("/{id}")
    public FriendshipDto delete(@PathVariable Long id) throws ValidationException  {
        LOGGER.info("Called DELETE /friendship/{}",id);
        return this.friendshipService.delete(id);
    }

}
