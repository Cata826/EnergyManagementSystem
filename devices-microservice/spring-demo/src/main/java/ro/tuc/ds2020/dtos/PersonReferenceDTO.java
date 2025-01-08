package ro.tuc.ds2020.dtos;
import ro.tuc.ds2020.entities.PersonReference;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.UUID;

public class PersonReferenceDTO {
    private UUID id;
    private UUID personId;

    public PersonReferenceDTO() {
    }
    @NotNull
    private PersonReference personReference;

    public PersonReference getPersonReference() {
        return personReference;
    }

    public void setPersonReference(PersonReference personReference) {
        this.personReference = personReference;
    }

    public PersonReferenceDTO(UUID personId) {
        this.personId = personId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getPersonId() {
        return personId;
    }

    public void setPersonId(UUID personId) {
        this.personId = personId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonReferenceDTO that = (PersonReferenceDTO) o;
        return Objects.equals(personId, that.personId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personId);
    }
}
