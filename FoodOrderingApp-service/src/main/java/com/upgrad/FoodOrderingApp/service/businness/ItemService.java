package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.CategoryItemDao;
import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.CategoryItemEntity;
import com.upgrad.FoodOrderingApp.service.entity.ItemEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryItemDao categoryItemDao;


    public List<ItemEntity> getItemsByCategoryAndRestaurant(final String restaurantUUID , final String categoryUUID) {
        List<CategoryEntity> categoryEntities = categoryService.getCategoriesByRestaurant(restaurantUUID);
        List<CategoryEntity> categoryEntities1 = categoryEntities.stream().filter(categoryEntity -> categoryEntity.getUuid().equals(categoryUUID)).collect(Collectors.toList());
        return categoryItemDao.getItemsByCategory(categoryEntities1.get(0).getUuid());
    }

}
