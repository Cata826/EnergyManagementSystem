package ro.tuc.ds2020.dtos;
import org.springframework.hateoas.RepresentationModel;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.UUID;

public class DeviceDTO extends RepresentationModel<DeviceDTO> {
    private UUID id;

    @NotNull
    private String description;

    @NotNull
    private String address;

    @NotNull
    private double mhec;

    @NotNull
    private PersonReferenceDTO  personReference;

    public DeviceDTO() {
    }

    public DeviceDTO(String description, String address, double mhec, PersonReferenceDTO  personReference) {
        this.description = description;
        this.address = address;
        this.mhec = mhec;
        this.personReference = personReference;
    }

    public DeviceDTO(UUID id, String description, String address, double mhec, PersonReferenceDTO  personReference) {
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

    public PersonReferenceDTO  getPersonReference() { // Ensure this method exists
        return personReference;
    }

    public void setPersonReference(PersonReferenceDTO  personReference) { // Ensure this method exists
        this.personReference = personReference;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceDTO that = (DeviceDTO) o;
        return Double.compare(that.mhec, mhec) == 0 &&
                Objects.equals(description, that.description) &&
                Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, address, mhec);
    }
}
