package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.ItemEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Repository
public class ItemDao {

    @PersistenceContext
    EntityManager entityManager;

    public ItemEntity getItemByUUID(final String itemUUID) {
        try {
            return entityManager.createNamedQuery("getItemByUUID" , ItemEntity.class).setParameter("itemUUID" , itemUUID).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

}
