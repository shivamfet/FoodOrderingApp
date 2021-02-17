package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.CategoryDao;
import com.upgrad.FoodOrderingApp.service.dao.RestaurantCategoryDao;
import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.exception.CategoryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
public class CategoryService {

    @Autowired
    private RestaurantCategoryDao restaurantCategoryDao;

    @Autowired
    private CategoryDao categoryDao;

    public List<CategoryEntity> getAllCategoriesOrderedByName() {
        return categoryDao.getAllCategories();
    }

    public CategoryEntity getCategoryById(final String categoryUUID) throws CategoryNotFoundException {
        if (categoryUUID.isEmpty()) {
            throw new CategoryNotFoundException("CNF-001" , "Category id field should not be empty");
        }
        CategoryEntity categoryEntity = categoryDao.getCategoryByUUID(categoryUUID);
        if (categoryEntity == null) {
            throw new CategoryNotFoundException("CNF-002" , "No category by this id");
        }
        return categoryEntity;
    }

    public List<CategoryEntity> getCategoriesByRestaurant(final String uuid) {
        return restaurantCategoryDao.getCategoriesByRestaurant(uuid);
    }

}
