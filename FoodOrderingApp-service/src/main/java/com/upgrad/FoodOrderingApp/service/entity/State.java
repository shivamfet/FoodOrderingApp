package com.upgrad.FoodOrderingApp.service.entity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "state" , schema = "public")
public class State {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "uuid")
    @Size(max = 200)
    String uuid;

    @Column(name = "state_name")
    @Size(max = 30)
    String stateNam;

    @OneToMany(mappedBy = "state" , cascade = CascadeType.REMOVE , fetch = FetchType.LAZY)
    List<Address> addressList;
}
