package ro.tuc.ds2020.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.entities.PersonReference;
import ro.tuc.ds2020.services.PersonReferenceService;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
@RestController
//@CrossOrigin(origins = "http://localhost:3000")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequestMapping("/person-reference")
public class PersonReferenceController {
    private final PersonReferenceService personReferenceService;

    @Autowired
    public PersonReferenceController(PersonReferenceService personReferenceService) {
        this.personReferenceService = personReferenceService;
    }

    @GetMapping
    public ResponseEntity<List<PersonReference>> getAllPersonReferences() {
        List<PersonReference> references = personReferenceService.getAllPersonReferences();
        return ResponseEntity.ok(references);
    }
    @PostMapping()
    public ResponseEntity<Void> addPersonReference(@Valid @RequestBody UUID personId) {
        personReferenceService.addPersonReference(personId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @GetMapping("/{id}")
    public void getPersonReferenceById(@PathVariable UUID id) {
        personReferenceService.deletePersonReference(id);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePersonReference(@PathVariable UUID id) {
        personReferenceService.deleteReferencesByPersonId(id);
        return ResponseEntity.noContent().build();
    }
}
