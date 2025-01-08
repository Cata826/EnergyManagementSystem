package ro.tuc.ds2020.services;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ro.tuc.ds2020.controllers.handlers.exceptions.model.ResourceNotFoundException;
import ro.tuc.ds2020.dtos.LoginResponseDTO;
import ro.tuc.ds2020.dtos.PersonDTO;
import ro.tuc.ds2020.dtos.PersonDetailsDTO;
import ro.tuc.ds2020.dtos.builders.PersonBuilder;
import ro.tuc.ds2020.entities.Person;
import ro.tuc.ds2020.jwt.JwtUtil;
import ro.tuc.ds2020.jwt.JwtUtil;
import ro.tuc.ds2020.repositories.PersonRepository;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PersonService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PersonService.class);
    private final PersonRepository personRepository;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }
    public boolean existsById(UUID personId) {
        return personRepository.existsById(personId);
    }
    public List<PersonDTO> findPersons() {
        List<Person> personList = personRepository.findAll();
        return personList.stream()
                .map(PersonBuilder::toPersonDTO)
                .collect(Collectors.toList());
    }
    public LoginResponseDTO login(String email, String password) {
        Person person = personRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Verify the password
        if (!passwordEncoder.matches(password, person.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        // Generate JWT token
        String token = jwtUtil.generateToken(person.getEmail(), person.isAdmin());

        // Return token, id, and isAdmin
        return new LoginResponseDTO(token, person.getId(), person.isAdmin());
    }



    private void deletePersonReference(UUID personId) {
//        String url = "http://tomcat-db-api2:8081/person-reference/" + personId;

//        String url = "http://localhost:8081/person-reference/" + personId;
        String url = "http://devices.localhost/person-reference/" + personId;
        ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.DELETE, null, Void.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            LOGGER.info("PersonReference deleted for personId: {}", personId);
        } else {
            LOGGER.error("Failed to delete PersonReference for personId: {}. Response: {}", personId, response.getStatusCode());
            throw new ResourceNotFoundException("Failed to delete PersonReference for personId: " + personId);
        }

    }

    private void deleteDevicePersonReference(UUID personId) {

//        String url2 = "http://tomcat-db-api2:8081/device/person/" + personId;
//        String url2 = "http://localhost:8081/device/person/" + personId;
        String url2 = "http://devices.localhost/device/person/" + personId;
        ResponseEntity<Void> response2 = restTemplate.exchange(url2, HttpMethod.DELETE, null, Void.class);

        if (response2.getStatusCode().is2xxSuccessful()) {
            LOGGER.info("PersonReference deleted for personId: {}", personId);
        } else {
            LOGGER.error("Failed to delete PersonReference for personId: {}. Response: {}", personId, response2.getStatusCode());
            throw new ResourceNotFoundException("Failed to delete PersonReference for personId: " + personId);
        }


    }
    private String getCurrentJwtToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getCredentials() instanceof String) {
            return (String) authentication.getCredentials();
        }
        return null; // or throw an appropriate exception if the token is mandatory
    }



    private void createPersonReference(UUID personId) {
      //  String url = "http://tomcat-db-api2:8081/person-reference";
//        String url = "http://localhost:8081/person-reference";
        String url = "http://devices.localhost/person-reference";
        ResponseEntity<Void> response = restTemplate.postForEntity(url, personId, Void.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            LOGGER.info("PersonReference created for personId: {}", personId);
        } else {
            LOGGER.error("Failed to create PersonReference for personId: {}. Response: {}", personId, response.getStatusCode());
            throw new ResourceNotFoundException("Failed to create PersonReference for personId: " + personId);
        }
    }

    public PersonDetailsDTO findPersonById(UUID id) {
        Optional<Person> personOptional = personRepository.findById(id);
        if (!personOptional.isPresent()) {
            LOGGER.error("Person with id {} was not found in db", id);
            throw new ResourceNotFoundException(Person.class.getSimpleName() + " with id: " + id);
        }
        return PersonBuilder.toPersonDetailsDTO(personOptional.get());
    }



    public UUID insert(PersonDetailsDTO personDTO) {
        LOGGER.debug("Inserting person with isAdmin: {}", personDTO.isAdmin());

        // Encode the password before saving
        String encodedPassword = passwordEncoder.encode(personDTO.getPassword());

        Person person = new Person(
                personDTO.getName(),
                personDTO.getUsername(),
                personDTO.getEmail(),
                encodedPassword,
                personDTO.isAdmin()
        );

        person = personRepository.save(person);

        LOGGER.debug("Inserted person with id {} and isAdmin: {}", person.getId(), person.isAdmin());
        createPersonReference(person.getId());

        return person.getId();
    }


    public void updatePerson(UUID id, PersonDetailsDTO personDTO) {
        Optional<Person> personOptional = personRepository.findById(id);
        if (!personOptional.isPresent()) {
            LOGGER.error("Person with id {} was not found in db", id);
            throw new ResourceNotFoundException(Person.class.getSimpleName() + " with id: " + id);
        }

        Person person = personOptional.get();
        person.setName(personDTO.getName());
        person.setUsername(personDTO.getUsername());
        person.setEmail(personDTO.getEmail());
        person.setPassword(personDTO.getPassword());
        person.setAdmin(personDTO.isAdmin());

        personRepository.save(person);
        LOGGER.debug("Person with id {} was updated in db", person.getId());
    }

    public boolean isAdmin(UUID personId) {
        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new EntityNotFoundException("Person not found"));
        return person.isAdmin();
    }
    public void deletePerson(UUID id) {
        Optional<Person> personOptional = personRepository.findById(id);
        if (!personOptional.isPresent()) {
            LOGGER.error("Person with id {} was not found in db", id);
            throw new ResourceNotFoundException(Person.class.getSimpleName() + " with id: " + id);
        }
        deleteDevicePersonReference(id);
        deletePersonReference(id);

        personRepository.deleteById(id);
        LOGGER.debug("Person with id {} was deleted from db", id);
    }
}
