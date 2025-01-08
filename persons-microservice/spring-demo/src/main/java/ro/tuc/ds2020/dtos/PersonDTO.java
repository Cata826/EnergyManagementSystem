package ro.tuc.ds2020.dtos;
import org.springframework.hateoas.RepresentationModel;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.UUID;

public class PersonDTO extends RepresentationModel<PersonDTO> {
    private UUID id;

    @NotNull
    private String name;
    @NotNull
    private String username;
    @NotNull
    private String email;
    @NotNull
    private String password;
    @NotNull
    private boolean isAdmin;

    public PersonDTO() {
    }
    public PersonDTO(UUID id, String name, String username, String email, String password, boolean isAdmin) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {return email;}
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonDTO personDTO = (PersonDTO) o;
        return isAdmin == personDTO.isAdmin &&
                Objects.equals(name, personDTO.name) &&
                Objects.equals(username, personDTO.username) &&
                Objects.equals(email, personDTO.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, username, email, isAdmin);
    }
}
