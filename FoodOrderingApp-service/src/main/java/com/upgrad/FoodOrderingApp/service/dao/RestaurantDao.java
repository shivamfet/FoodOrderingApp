package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;


@Repository
public class RestaurantDao {

    @PersistenceContext
    EntityManager entityManager;

    public List<RestaurantEntity> getRestaurantsByRating() {
        try {
            List<RestaurantEntity> restaurantEntities = entityManager.createNamedQuery("getRestaurantsByRating" , RestaurantEntity.class).getResultList();
            return restaurantEntities;
        } catch (NoResultException e) {
            return null;
        }
    }

    public RestaurantEntity getRestaurantByUUID(final String uuid) {
        try {
            return entityManager.createNamedQuery("getRestaurantByUUID" , RestaurantEntity.class).setParameter("uuid" , uuid).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<RestaurantEntity> getRestaurantsByName(final String restaurantName) {
        return entityManager.createNamedQuery("getRestaurantsByName" , RestaurantEntity.class).setParameter("restaurantName" , restaurantName).getResultList();
    }

    public RestaurantEntity updateRating(RestaurantEntity restaurantEntity) {
        return entityManager.merge(restaurantEntity);
    }
}
