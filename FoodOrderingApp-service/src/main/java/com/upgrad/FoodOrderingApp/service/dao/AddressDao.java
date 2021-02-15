package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.AddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class AddressDao {

    @PersistenceContext
    EntityManager entityManager;

    public AddressEntity saveAddress(AddressEntity addressEntity) {
        entityManager.persist(addressEntity);
        return addressEntity;
    }

    public AddressEntity getAddressByUUID(final String uuid) {
        try {
            return entityManager.createNamedQuery("getAddressByUUID" , AddressEntity.class).setParameter("uuid" , uuid).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }

    }

    public AddressEntity deleteAddress(AddressEntity addressEntity) {
        int count = ((Number)entityManager.createNamedQuery("getOrdersDeliveredRoAnAddress").getSingleResult()).intValue();
        if (count == 0) {
            entityManager.remove(addressEntity);
        } else {
            addressEntity.setIsActive(1);
            entityManager.merge(addressEntity);
        }
        return addressEntity;
    }

}
