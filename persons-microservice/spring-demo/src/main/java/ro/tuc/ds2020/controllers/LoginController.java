package ro.tuc.ds2020.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.dtos.LoginRequestDTO;
import ro.tuc.ds2020.dtos.LoginResponseDTO;
import ro.tuc.ds2020.dtos.PersonDetailsDTO;
import ro.tuc.ds2020.entities.Person;
import ro.tuc.ds2020.services.PersonService;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/auth")
public class LoginController {
    private final PersonService personService;
    @Autowired
    public LoginController(PersonService personService) {
        this.personService = personService;
    }

//    @PostMapping("/login")
//    public ResponseEntity<PersonDetailsDTO> login(@RequestBody LoginRequestDTO loginRequest) {
//        PersonDetailsDTO personDetails = personService.login(loginRequest.getEmail(), loginRequest.getPassword());
//        return ResponseEntity.ok(personDetails);
//    }
        @PostMapping("/login")
        public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequest) {
            try {
                // Authenticate user and generate token
                LoginResponseDTO responseDTO = personService.login(loginRequest.getEmail(), loginRequest.getPassword());
                return ResponseEntity.ok(responseDTO);
            } catch (RuntimeException e) {
                return ResponseEntity.status(401).body(e.getMessage());
            }
        }

    @GetMapping("/isAdmin/{id}")
    public ResponseEntity<Boolean> isAdmin(@PathVariable("id") UUID id) {
        boolean isAdmin = personService.isAdmin(id);
        return ResponseEntity.ok(isAdmin);
    }
}
