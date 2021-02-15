package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Repository
public class CustomerDao {

    @PersistenceContext
    EntityManager entityManager;

    public CustomerEntity createCustomer(CustomerEntity customerEntity) {
        entityManager.persist(customerEntity);
        return customerEntity;
    }

    public CustomerEntity updateCustomer(CustomerEntity customerEntity) {
        entityManager.merge(customerEntity);
        return customerEntity;
    }



    public CustomerEntity getCustomerByContactNumber(String contactNumber) {
        try {
           return entityManager.createNamedQuery("getCustomerByContactNumber" , CustomerEntity.class).setParameter("contactNumber" , contactNumber).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }





}
