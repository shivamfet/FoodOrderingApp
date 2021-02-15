package com.upgrad.FoodOrderingApp.service.entity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "state" , schema = "public")
@NamedQueries (
        {
                @NamedQuery(name = "getAllStates" , query = "select s from StateEntity s"),
                @NamedQuery(name = "getStateByUuid" , query = "select s from StateEntity s where s.uuid = :uuid")
        }
)

public class StateEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "uuid")
    @Size(max = 200)
    String uuid;

    @Column(name = "state_name")
    @Size(max = 30)
    String stateName;

    @OneToMany(mappedBy = "stateEntity" , cascade = CascadeType.REMOVE , fetch = FetchType.LAZY)
    List<AddressEntity> addressEntityList;

    public StateEntity() {

    }

    public StateEntity(String uuid , String stateName) {
        this.uuid = uuid;
        this.stateName = stateName;
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

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public List<AddressEntity> getAddressEntityList() {
        return addressEntityList;
    }

    public void setAddressEntityList(List<AddressEntity> addressEntityList) {
        this.addressEntityList = addressEntityList;
    }
}
