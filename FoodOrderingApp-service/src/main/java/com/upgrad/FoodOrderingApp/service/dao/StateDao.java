package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.StateEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class StateDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<StateEntity> getAllStates() {
        List<StateEntity> stateEntities = entityManager.createNamedQuery("getAllStates" , StateEntity.class).getResultList();
        return stateEntities;
    }

    public StateEntity getStateByUuid(final String uuid) {
        try {
            return entityManager.createNamedQuery("getStateByUuid" , StateEntity.class).setParameter("uuid" , uuid).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
