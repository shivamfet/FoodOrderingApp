package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Repository
public class CustomerAuthDao {

    @PersistenceContext
    private EntityManager entityManager;

    public CustomerAuthEntity getCustomerAuthByAccessToken(final String accessToken) {
        try {
            return entityManager.createNamedQuery("customerAuthByAccessToken" , CustomerAuthEntity.class).setParameter("accessToken" , accessToken).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public CustomerAuthEntity createAuthToken(CustomerAuthEntity customerAuthEntity){
        entityManager.persist(customerAuthEntity);
        return customerAuthEntity;
    }

    public CustomerAuthEntity updateAuthToken(CustomerAuthEntity customerAuthEntity){
        entityManager.merge(customerAuthEntity);
        return customerAuthEntity;
    }
}
