package com.upgrad.FoodOrderingApp.service.entity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "address" , schema = "public")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "uuid")
    @Size(max = 200)
    private String uuid;

    @Column(name = "flat_buil_number")
    @Size(max = 255)
    private String flatBuilNumber;

    @Column(name = "locality")
    @Size(max = 255)
    private String locality;

    @Column(name = "city")
    @Size(max = 30)
    private String city;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "state_id")
    private State state;

    @Column(name = "pincode")
    @Size(max = 30)
    String pincode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getFlatBuilNumber() {
        return flatBuilNumber;
    }

    public void setFlatBuilNumber(String flatBuilNumber) {
        this.flatBuilNumber = flatBuilNumber;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }
}
