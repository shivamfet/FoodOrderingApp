package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.awt.event.WindowAdapter;
import java.util.List;

@Repository
public class CategoryDao {

    @PersistenceContext
    private EntityManager entityManager;

    public CategoryEntity getCategoryByUUID(final String uuid) {
            try {
                return entityManager.createNamedQuery("getCategoryByUUID" , CategoryEntity.class).setParameter("uuid" , uuid).getSingleResult();
            } catch (NoResultException e) {
                return null;
            }
    }

    public List<CategoryEntity> getAllCategories() {
        try {
            return entityManager.createNamedQuery("getAllCategories" , CategoryEntity.class).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

}
