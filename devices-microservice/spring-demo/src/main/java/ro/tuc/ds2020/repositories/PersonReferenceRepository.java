package ro.tuc.ds2020.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.tuc.ds2020.entities.PersonReference;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PersonReferenceRepository extends JpaRepository<PersonReference, UUID> {

    Optional<PersonReference> findByPersonId(UUID personId);
    List<PersonReference> findAllByPersonId(UUID personId);
    boolean existsByPersonId(UUID personId);
}
