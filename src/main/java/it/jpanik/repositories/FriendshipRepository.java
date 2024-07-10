package it.jpanik.repositories;

import it.jpanik.entities.Friendship;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import java.util.*;

@Repository
public interface FriendshipRepository extends CrudRepository<Friendship, Long> {

    List<Friendship> findAllByUserCommunityWhoReceivesId(Long id);
    List<Friendship> findAllByUserCommunityWhoSendsId(Long id);
}
