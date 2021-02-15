package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.CategoryItemEntity;
import com.upgrad.FoodOrderingApp.service.entity.ItemEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CategoryItemDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<ItemEntity> getItemsByCategory(final String categoryUUID) {
        try {
            List<CategoryItemEntity> categoryItemEntities = entityManager.createNamedQuery("getItemsByCategory" , CategoryItemEntity.class).setParameter("uuid" , categoryUUID).getResultList();
            List<ItemEntity> itemEntities = new ArrayList<>();
            categoryItemEntities.forEach(categoryItemEntity -> {
                itemEntities.add(categoryItemEntity.getItemEntity());
            });
            return itemEntities;
        } catch (NoResultException e) {
            return null;
        }
    }

}
