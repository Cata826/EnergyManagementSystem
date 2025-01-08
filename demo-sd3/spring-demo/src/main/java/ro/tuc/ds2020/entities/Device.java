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
    private UUID id;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "mhec", nullable = false)
    private double mhec;

    @Column(name = "person_id", nullable = false)
    @Type(type = "uuid-binary")
    private UUID personId;

    public Device() {
    }
    public Device(UUID id,String address, String description, double mhec, UUID personId) {
        this.id=id;
        this.address = address;
        this.description = description;
        this.mhec = mhec;
        this.personId = personId;
    }
    public Device(String address, String description, double mhec, UUID personId) {
        this.address = address;
        this.description = description;
        this.mhec = mhec;
        this.personId = personId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getMhec() {
        return mhec;
    }

    public void setMhec(double mhec) {
        this.mhec = mhec;
    }

    public UUID getPersonId() {
        return personId;
    }

    public void setPersonId(UUID personId) {
        this.personId = personId;
    }
}

