package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.CategoryItemDao;
import com.upgrad.FoodOrderingApp.service.dao.ItemDao;
import com.upgrad.FoodOrderingApp.service.dao.OrderItemDao;
import com.upgrad.FoodOrderingApp.service.dao.OrdersDao;
import com.upgrad.FoodOrderingApp.service.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ItemService {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private OrdersDao ordersDao;

    @Autowired
    private OrderItemDao orderItemDao;

    @Autowired
    private ItemDao itemDao;

    @Autowired
    private CategoryItemDao categoryItemDao;


    public List<ItemEntity> getItemsByCategoryAndRestaurant(final String restaurantUUID , final String categoryUUID) {
        List<CategoryEntity> categoryEntities = categoryService.getCategoriesByRestaurant(restaurantUUID);
        List<CategoryEntity> categoryEntities1 = categoryEntities.stream().filter(categoryEntity -> categoryEntity.getUuid().equals(categoryUUID)).collect(Collectors.toList());
        return categoryItemDao.getItemsByCategory(categoryEntities1.get(0).getUuid());
    }

    public List<ItemEntity> getItemsByPopularity(RestaurantEntity restaurantEntity) {
        String restaurantUUID = restaurantEntity.getUuid();
        List<OrderEntity> orderEntities = ordersDao.getOrdersByRestaurant(restaurantUUID);
        List<ItemEntity> itemEntities  = orderEntities.stream().map(orderEntity -> orderItemDao.getItemsByOrder(orderEntity.getUuid())).flatMap(List::stream).collect(Collectors.toList());
        Map<String , Long> itemCount = itemEntities.stream().collect(Collectors.groupingBy(ItemEntity::getUuid , Collectors.counting()));
        Map<String , Long> sortedItemCount = itemCount.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        List<ItemEntity> sortedItemEntities = sortedItemCount.keySet().stream().map(key -> itemDao.getItemByUUID(key)).collect(Collectors.toList());
        if (sortedItemEntities.size() >= 5) {
            return sortedItemEntities.stream().limit(5).collect(Collectors.toList());
        } else {
            return sortedItemEntities;
        }
    }

}
