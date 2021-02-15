package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantCategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RestaurantCategoryDao {

    @PersistenceContext
    EntityManager entityManager;

    public List<CategoryEntity> getCategoriesByRestaurant(final String restaurantUUID) {
        try {
            List<RestaurantCategoryEntity> restaurantCategoryEntities = entityManager.createNamedQuery("getCategoriesByRestaurant" , RestaurantCategoryEntity.class).setParameter("restaurantUUID" , restaurantUUID).getResultList();
            List<CategoryEntity> categoryEntities = new ArrayList<CategoryEntity>();
            restaurantCategoryEntities.forEach(restaurantCategoryEntity -> {
                categoryEntities.add(restaurantCategoryEntity.getCategoryEntity());
            });
            return categoryEntities;
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<RestaurantEntity> getRestaurantsByCategory(final String categoryUUID) {
        try {
            List<RestaurantCategoryEntity> restaurantCategoryEntities = entityManager.createNamedQuery("getRestaurantsByCategory" , RestaurantCategoryEntity.class ).setParameter("categoryUUID" , categoryUUID).getResultList();
            List<RestaurantEntity> restaurantEntities = new ArrayList<RestaurantEntity>();
            restaurantCategoryEntities.forEach(restaurantCategoryEntity -> {
                restaurantEntities.add(restaurantCategoryEntity.getRestaurantEntity());
            });
            return restaurantEntities;
        } catch (NoResultException e) {
            return null;
        }
    }

}
