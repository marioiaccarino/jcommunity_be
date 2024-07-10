package it.jpanik.repositories;

import it.jpanik.entities.UserCommunity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCommunityRepository extends CrudRepository<UserCommunity,Long> {

    Optional<UserCommunity> findUserCommunityBySubscribedCommunityIdAndReferredUserId(Long communityId, Long userId);

}
