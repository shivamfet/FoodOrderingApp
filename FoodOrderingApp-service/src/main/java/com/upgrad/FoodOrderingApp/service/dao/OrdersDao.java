package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.OrderEntity;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class OrdersDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<OrderEntity> getOrdersByRestaurant(final String restaurantUUID) {
        try {
            return entityManager.createNamedQuery("getOrdersByRestaurant" , OrderEntity.class).setParameter("restaurantUUID" , restaurantUUID).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

}
