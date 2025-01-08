package ro.tuc.ds2020.dtos;
import ro.tuc.ds2020.dtos.validators.annotation.AgeLimit;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.Objects;
import java.util.UUID;
public class PersonDetailsDTO {

    private UUID id;
    @NotNull
    private String name;

    @NotNull
    private String username;

    @NotNull
    @Email
    private String email;

    @NotNull
    @Size(min = 6, message = "Password must have at least 6 characters")
    private String password;

    @NotNull
    private boolean isAdmin;


    public PersonDetailsDTO() {
    }

    public PersonDetailsDTO(String name, String username, String email, String password, boolean isAdmin) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public PersonDetailsDTO(UUID id, String name, String username, String email, String password, boolean isAdmin) {
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

    public String getEmail() {
        return email;
    }

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
        PersonDetailsDTO that = (PersonDetailsDTO) o;
        return isAdmin == that.isAdmin &&
                Objects.equals(name, that.name) &&
                Objects.equals(username, that.username) &&
                Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, username, email, isAdmin);
    }
}
