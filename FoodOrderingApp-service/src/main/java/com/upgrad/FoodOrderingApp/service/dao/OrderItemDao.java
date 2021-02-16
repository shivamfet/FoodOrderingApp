package com.upgrad.FoodOrderingApp.service.dao;

import com.sun.org.apache.xpath.internal.operations.Or;
import com.upgrad.FoodOrderingApp.service.entity.ItemEntity;
import com.upgrad.FoodOrderingApp.service.entity.OrderItemEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class OrderItemDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<ItemEntity> getItemsByOrder(final String orderUUID) {
        try {
            List<OrderItemEntity> orderItemEntities = entityManager.createNamedQuery("getItemsByOrder" , OrderItemEntity.class).setParameter("orderUUID" , orderUUID).getResultList();
            List<ItemEntity> itemEntities = orderItemEntities.stream().map(orderItemEntity -> orderItemEntity.getItemEntity()).collect(Collectors.toList());
            return itemEntities;
        } catch (NoResultException e) {
            return null;
        }
    }

}
