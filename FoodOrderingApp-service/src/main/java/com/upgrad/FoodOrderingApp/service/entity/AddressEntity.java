package com.upgrad.FoodOrderingApp.service.entity;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "address" , schema = "public")
@NamedQueries (
        {
                @NamedQuery(name = "getAddressByUUID" , query = "select a from AddressEntity a where a.uuid = :uuid"),
        }
)
public class AddressEntity {

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
    private StateEntity stateEntity;

    @Column(name = "pincode")
    @Size(max = 30)
    String pincode;

    @Column(name = "active")
    Integer isActive;

    public AddressEntity() {

    }

    public AddressEntity(String addressId, String s, String someLocality, String someCity, String s1, StateEntity stateEntity) {
        this.uuid = addressId;
        this.setLocality(someLocality);
        this.setCity(someCity);
        this.setFlatBuilNumber(s);
        this.setState(stateEntity);
    }

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

    public StateEntity getState() {
        return stateEntity;
    }

    public void setState(StateEntity stateEntity) {
        this.stateEntity = stateEntity;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }
}
