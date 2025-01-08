package ro.tuc.ds2020.entities;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
public class Device implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "uuid-binary")
    private UUID id;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "mhec", nullable = false)
    private double mhec;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "person_reference_id", nullable = false)
    private PersonReference personReference;

    public Device() {
    }

    public Device(String description, String address, double mhec, PersonReference personReference) {
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

    public PersonReference getPersonReference() {
        return personReference;
    }

    public void setPersonReference(PersonReference personReference) {
        this.personReference = personReference;
    }
}
