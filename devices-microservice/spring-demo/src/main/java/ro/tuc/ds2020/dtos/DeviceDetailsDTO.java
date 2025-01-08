package ro.tuc.ds2020.dtos;
import ro.tuc.ds2020.entities.PersonReference;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.UUID;

public class DeviceDetailsDTO {
    private UUID id;

    @NotNull
    private String description;

    @NotNull
    private String address;

    @NotNull
    private double mhec;

    @NotNull
    private PersonReference personReference;

    public DeviceDetailsDTO() {
    }

    public DeviceDetailsDTO(String description, String address, double mhec, PersonReference personReference) {
        this.description = description;
        this.address = address;
        this.mhec = mhec;
        this.personReference = personReference;
    }

    public DeviceDetailsDTO(UUID id, String description, String address, double mhec, PersonReference personReference) {
        this.id = id;
        this.description = description;
        this.address = address;
        this.mhec = mhec;
        this.personReference = personReference;
    }
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getMhec() {
        return mhec;
    }

    public void setMhec(double mhec) {
        this.mhec = mhec;
    }

    public PersonReference getPersonReference() { // Ensure this method exists
        return personReference;
    }

    public void setPersonReference(PersonReference personReference) { // Ensure this method exists
        this.personReference = personReference;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceDetailsDTO that = (DeviceDetailsDTO) o;
        return Double.compare(that.mhec, mhec) == 0 &&
                Objects.equals(description, that.description) &&
                Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, address, mhec);
    }
}
