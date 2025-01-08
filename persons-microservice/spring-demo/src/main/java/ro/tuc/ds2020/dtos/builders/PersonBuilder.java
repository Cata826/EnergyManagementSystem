package ro.tuc.ds2020.dtos.builders;
import ro.tuc.ds2020.dtos.PersonDTO;
import ro.tuc.ds2020.dtos.PersonDetailsDTO;
import ro.tuc.ds2020.entities.Person;

public class PersonBuilder {

    private PersonBuilder() {
    }

    public static PersonDTO toPersonDTO(Person person) {
        return new PersonDTO(
                person.getId(),
                person.getName(),
                person.getUsername(),
                person.getEmail(),
                person.getPassword(),
                person.isAdmin()
        );
    }
    public static PersonDetailsDTO toPersonDetailsDTO(Person person) {
        return new PersonDetailsDTO(
                person.getId(),
                person.getName(),
                person.getUsername(),
                person.getEmail(),
                person.getPassword(),
                person.isAdmin()
        );
    }

    public static Person toEntity(PersonDetailsDTO personDetailsDTO) {
        return new Person(
                personDetailsDTO.getName(),
                personDetailsDTO.getUsername(),
                personDetailsDTO.getEmail(),
                personDetailsDTO.getPassword(),
                personDetailsDTO.isAdmin()
        );
    }
}
