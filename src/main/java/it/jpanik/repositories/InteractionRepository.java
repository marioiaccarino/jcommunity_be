package it.jpanik.repositories;

import it.jpanik.entities.Interaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InteractionRepository extends CrudRepository<Interaction,Long> {

    List<Interaction> findByUserCommunityId(Long id);
}
