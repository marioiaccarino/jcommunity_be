package it.jpanik.validator;

import it.jpanik.dto.FriendshipDto;

import it.jpanik.exceptions.ValidationException;
import it.jpanik.repositories.FriendshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FriendshipValidator extends Validator {

    private final FriendshipRepository friendshipRepository;

    // FIXME manca il final
    @Autowired
    public FriendshipValidator(FriendshipRepository friendshipRepository) {
        this.friendshipRepository = friendshipRepository;
    }

    public static void validate(FriendshipDto friendshipDto) throws ValidationException {
        checkEmpty(friendshipDto.getId(), "ID: required field");
        checkNull(friendshipDto.isAccepted(),"IsAccepted: could not be null");
        checkNull(friendshipDto.getTypeOfFriendship(), "TypeOfFriendship: required field");
        checkNull(friendshipDto.getUserCommunityWhoSends(), "UserCommunity who sends the request cannot be null");
        checkNull(friendshipDto.getUserCommunityWhoReceives(),"UserCommunity who receive the request cannot be null");
    }

    // FIXME errore nel nome del metodo
    public void checkIfTheyAreAlreadyFriendsBySendedRequests(FriendshipDto friendshipDto) throws ValidationException  {

        // FIXME Sarebbe bello non fare delle if così, ma mettere perlomeno tutto in una variabile
        if (this.friendshipRepository
                .findAllByUserCommunityWhoSendsId(friendshipDto.getUserCommunityWhoSends().getId())
                .stream().map(f -> f.getUserCommunityWhoReceives().getId())
                .toList().contains(friendshipDto.getUserCommunityWhoReceives().getId()) )    {
            throw new ValidationException
                    (friendshipDto.getUserCommunityWhoSends().getId() +" already sent friend request to "+ friendshipDto.getUserCommunityWhoReceives().getId());
        }
    }

    public void checkIfTheyAreAlreadyFriendsByReceivedRequests(FriendshipDto friendshipDto) throws ValidationException  {

        // FIXME Sarebbe bello non fare delle if così, ma mettere perlomeno tutto in una variabile
        if (this.friendshipRepository
                .findAllByUserCommunityWhoReceivesId(friendshipDto.getUserCommunityWhoSends().getId())
                .stream().map(f -> f.getUserCommunityWhoSends().getId())
                .toList().contains(friendshipDto.getUserCommunityWhoReceives().getId()) ) {

            throw new ValidationException
                    (friendshipDto.getUserCommunityWhoSends().getId() +" already received friend request from "+ friendshipDto.getUserCommunityWhoReceives().getId());

        }
    }
}
