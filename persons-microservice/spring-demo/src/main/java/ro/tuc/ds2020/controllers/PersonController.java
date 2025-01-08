package ro.tuc.ds2020.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.dtos.PersonDTO;
import ro.tuc.ds2020.dtos.PersonDetailsDTO;
import ro.tuc.ds2020.services.PersonService;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(value = "/person")
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping()
    public ResponseEntity<List<PersonDTO>> getPersons() {
        List<PersonDTO> dtos = personService.findPersons();
        for (PersonDTO dto : dtos) {
            Link personLink = linkTo(methodOn(PersonController.class)
                    .getPerson(dto.getId())).withRel("personDetails");
            dto.add(personLink);
        }
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> checkPersonExists(@PathVariable("id") UUID personId) {
        boolean exists = personService.existsById(personId);
        return new ResponseEntity<>(exists, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UUID> createPerson(@RequestBody PersonDetailsDTO personDetailsDTO) {
        UUID id = personService.insert(personDetailsDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PersonDetailsDTO> getPerson(@PathVariable("id") UUID personId) {
        PersonDetailsDTO dto = personService.findPersonById(personId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> updatePerson(@PathVariable("id") UUID personId,
                                             @Valid @RequestBody PersonDetailsDTO personDTO) {
        personService.updatePerson(personId, personDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable("id") UUID personId) {
        personService.deletePerson(personId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
