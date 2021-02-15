package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.CategoryDao;
import com.upgrad.FoodOrderingApp.service.dao.RestaurantCategoryDao;
import com.upgrad.FoodOrderingApp.service.dao.RestaurantDao;
import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantCategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import com.upgrad.FoodOrderingApp.service.exception.CategoryNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.InvalidRatingException;
import com.upgrad.FoodOrderingApp.service.exception.RestaurantNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantDao restaurantDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private RestaurantCategoryDao restaurantCategoryDao;

    public List<RestaurantEntity> restaurantsByRating() {
        return restaurantDao.getRestaurantsByRating();
    }

    public RestaurantEntity restaurantByUUID(final String uuid) throws RestaurantNotFoundException {
        if (uuid.isEmpty()) {
            throw new RestaurantNotFoundException("RNF-002" , "Restaurant id field should not be empty");
        }
        RestaurantEntity restaurantEntity = restaurantDao.getRestaurantByUUID(uuid);
        if (restaurantEntity == null) {
            throw new RestaurantNotFoundException("RNF-001" , "No restaurant by this id");
        }
        return restaurantEntity;
    }

    public List<RestaurantEntity> restaurantsByName(final String restaurantName) throws RestaurantNotFoundException {
        if (restaurantName.isEmpty()) {
            throw new RestaurantNotFoundException("RNF-003" , "Restaurant name field should not be empty");
        }
        return restaurantDao.getRestaurantsByName(restaurantName.toLowerCase());
    }

    public List<RestaurantEntity> restaurantByCategory(final String categoryUUID) throws CategoryNotFoundException {
        if (categoryUUID.isEmpty()) {
            throw new CategoryNotFoundException("CNF-001" , "Category id field should not be empty");
        }

        CategoryEntity categoryEntity = categoryDao.getCategoryByUUID(categoryUUID);
        if (categoryEntity == null) {
            throw new CategoryNotFoundException("CNF-002" , "No category by this id");
        }
        return restaurantCategoryDao.getRestaurantsByCategory(categoryUUID);
    }

    public RestaurantEntity updateRestaurantRating(RestaurantEntity restaurantEntity , Double rating) throws RestaurantNotFoundException, InvalidRatingException {

        if (rating < 1 || rating < 5) {
            throw new InvalidRatingException("IRE-001" , "Restaurant should be in the range of 1 to 5");
        }
        restaurantEntity.setCustomerRating(rating);
        return restaurantDao.updateRating(restaurantEntity);
    }

}
