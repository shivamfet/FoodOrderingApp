package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.AddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAddressEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomerAddressDao {

    @PersistenceContext
    EntityManager entityManager;

    public List<CustomerAddressEntity> getCustomerAddresses(CustomerEntity customerEntity) {
        try{
            List<CustomerAddressEntity> customerAddressEntities = entityManager.createNamedQuery("getListOfAddress" , CustomerAddressEntity.class).setParameter("customer" , customerEntity).getResultList();
            return customerAddressEntities;
        } catch (NoResultException e) {
            return null;
        }

    }
}
