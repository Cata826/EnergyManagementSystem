package ro.tuc.ds2020.services;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.controllers.handlers.exceptions.model.ResourceNotFoundException;
import ro.tuc.ds2020.entities.PersonReference;
import ro.tuc.ds2020.repositories.PersonReferenceRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PersonReferenceService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PersonReferenceService.class);
    private final PersonReferenceRepository personReferenceRepository;

    @Autowired
    public PersonReferenceService(PersonReferenceRepository personReferenceRepository) {
        this.personReferenceRepository = personReferenceRepository;
    }
    public PersonReference getPersonReferenceById(UUID id) {
        Optional<PersonReference> reference = personReferenceRepository.findById(id);
        if (!reference.isPresent()) {
            LOGGER.error("PersonReference with id {} was not found", id);
            throw new ResourceNotFoundException("PersonReference with id: " + id + " not found");
        }
        return reference.get();
    }
    public List<PersonReference> getAllPersonReferences() {
        List<PersonReference> references = personReferenceRepository.findAll();
        LOGGER.info("Retrieved {} person references", references.size());
        return references;
    }
    public UUID addPersonReference(UUID personId) {
        PersonReference personReference = new PersonReference(personId);
        personReference = personReferenceRepository.save(personReference);
        LOGGER.info("PersonReference for personId {} was added with ID: {}", personId, personReference.getPersonId());
        return personReference.getPersonId();
    }

    public void updatePersonReference(UUID id, UUID newPersonId) {
        PersonReference personReference = personReferenceRepository.findById(id).orElseThrow(() -> {
            LOGGER.error("PersonReference with id {} was not found for update", id);
            return new ResourceNotFoundException("PersonReference with id: " + id + " not found");
        });

        personReference.setPersonId(newPersonId);
        personReferenceRepository.save(personReference);
        LOGGER.info("Updated personReference with id: {} to new personId: {}", id, newPersonId);
    }

    public void deletePersonReference(UUID id) {
        if (!personReferenceRepository.existsById(id)) {
            LOGGER.error("PersonReference with id {} was not found", id);
            throw new ResourceNotFoundException("PersonReference with id: " + id + " not found");
        }
        personReferenceRepository.deleteById(id);
        LOGGER.info("Deleted person reference with id: {}", id);
    }

    public void deleteReferencesByPersonId(UUID personId) {
        List<PersonReference> references = personReferenceRepository.findAllByPersonId(personId);
        for (PersonReference reference : references) {
            personReferenceRepository.delete(reference);
            LOGGER.info("Deleted person reference for personId: {}", personId);
        }
    }

}
