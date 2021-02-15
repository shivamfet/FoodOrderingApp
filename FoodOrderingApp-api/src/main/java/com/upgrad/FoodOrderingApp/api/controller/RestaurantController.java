package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.RestaurantDetailsResponseAddress;
import com.upgrad.FoodOrderingApp.api.model.RestaurantDetailsResponseAddressState;
import com.upgrad.FoodOrderingApp.api.model.RestaurantList;
import com.upgrad.FoodOrderingApp.api.model.RestaurantListResponse;
import com.upgrad.FoodOrderingApp.service.businness.CategoryService;
import com.upgrad.FoodOrderingApp.service.businness.RestaurantService;
import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import com.upgrad.FoodOrderingApp.service.exception.CategoryNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.RestaurantNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(method = RequestMethod.GET , path = "/restaurant" , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RestaurantListResponse> getRestaurants() {
        List<RestaurantEntity> restaurantEntities = restaurantService.restaurantsByRating();

        List<RestaurantList> restaurantList  =   restaurantEntities.stream().map(restaurantEntity ->
            new RestaurantList().id(UUID.fromString(restaurantEntity.getUuid()))
                    .customerRating(BigDecimal.valueOf(restaurantEntity.getCustomerRating()))
                    .averagePrice(restaurantEntity.getAvgPrice())
                    .numberCustomersRated(restaurantEntity.getNumberCustomersRated())
                    .photoURL(restaurantEntity.getPhotoUrl())
                    .restaurantName(restaurantEntity.getRestaurantName())
                    .categories((categoryService.
                            getCategoriesByRestaurant(restaurantEntity.getUuid())
                            ).stream().map(CategoryEntity::getCategoryName).
                            collect(Collectors.joining(",")))
                    .address(new RestaurantDetailsResponseAddress()
                                .id(UUID.fromString(restaurantEntity.getAddress().getUuid()))
                                .city(restaurantEntity.getAddress().getCity())
                                .flatBuildingName(restaurantEntity.getAddress().getFlatBuilNumber())
                                .locality(restaurantEntity.getAddress().getFlatBuilNumber())
                                .pincode(restaurantEntity.getAddress().getPincode())
                                .state(new RestaurantDetailsResponseAddressState()
                                        .id(UUID.fromString(restaurantEntity.getAddress().getUuid()))
                                        .stateName(restaurantEntity.getAddress().getState().getStateName())))).collect(Collectors.toList());



        RestaurantListResponse restaurantListResponse = new RestaurantListResponse().restaurants(restaurantList);
        return new ResponseEntity<RestaurantListResponse>(restaurantListResponse , HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET , path = "/restaurant/{restaurant_name}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RestaurantListResponse> getRestaurantByName(@PathVariable(value = "restaurant_name") final String restaurantName) throws RestaurantNotFoundException {

        List<RestaurantEntity> restaurantEntities = restaurantService.restaurantsByName(restaurantName);
        List<RestaurantList> restaurantList  =   restaurantEntities.stream().map(restaurantEntity ->
                new RestaurantList().id(UUID.fromString(restaurantEntity.getUuid()))
                        .customerRating(BigDecimal.valueOf(restaurantEntity.getCustomerRating()))
                        .averagePrice(restaurantEntity.getAvgPrice())
                        .numberCustomersRated(restaurantEntity.getNumberCustomersRated())
                        .photoURL(restaurantEntity.getPhotoUrl())
                        .restaurantName(restaurantEntity.getRestaurantName())
                        .categories((categoryService.
                                getCategoriesByRestaurant(restaurantEntity.getUuid())
                        ).stream().map(CategoryEntity::getCategoryName).
                                collect(Collectors.joining(",")))
                        .address(new RestaurantDetailsResponseAddress()
                                .id(UUID.fromString(restaurantEntity.getAddress().getUuid()))
                                .city(restaurantEntity.getAddress().getCity())
                                .flatBuildingName(restaurantEntity.getAddress().getFlatBuilNumber())
                                .locality(restaurantEntity.getAddress().getFlatBuilNumber())
                                .pincode(restaurantEntity.getAddress().getPincode())
                                .state(new RestaurantDetailsResponseAddressState()
                                        .id(UUID.fromString(restaurantEntity.getAddress().getUuid()))
                                        .stateName(restaurantEntity.getAddress().getState().getStateName())))).collect(Collectors.toList());



        RestaurantListResponse restaurantListResponse = new RestaurantListResponse().restaurants(restaurantList);
        return new ResponseEntity<RestaurantListResponse>(restaurantListResponse , HttpStatus.OK);

    }

    @RequestMapping(method = RequestMethod.GET , path = "/restaurant/category/{category_id}" , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RestaurantListResponse> getRestaurantByCategoryId(@PathVariable(value = "category_id") final String categoryUUID) throws CategoryNotFoundException {
            List<RestaurantEntity> restaurantEntities = restaurantService.restaurantByCategory(categoryUUID);
        List<RestaurantList> restaurantList  =   restaurantEntities.stream().map(restaurantEntity ->
                new RestaurantList().id(UUID.fromString(restaurantEntity.getUuid()))
                        .customerRating(BigDecimal.valueOf(restaurantEntity.getCustomerRating()))
                        .averagePrice(restaurantEntity.getAvgPrice())
                        .numberCustomersRated(restaurantEntity.getNumberCustomersRated())
                        .photoURL(restaurantEntity.getPhotoUrl())
                        .restaurantName(restaurantEntity.getRestaurantName())
                        .categories((categoryService.
                                getCategoriesByRestaurant(restaurantEntity.getUuid())
                        ).stream().map(CategoryEntity::getCategoryName).
                                collect(Collectors.joining(",")))
                        .address(new RestaurantDetailsResponseAddress()
                                .id(UUID.fromString(restaurantEntity.getAddress().getUuid()))
                                .city(restaurantEntity.getAddress().getCity())
                                .flatBuildingName(restaurantEntity.getAddress().getFlatBuilNumber())
                                .locality(restaurantEntity.getAddress().getFlatBuilNumber())
                                .pincode(restaurantEntity.getAddress().getPincode())
                                .state(new RestaurantDetailsResponseAddressState()
                                        .id(UUID.fromString(restaurantEntity.getAddress().getUuid()))
                                        .stateName(restaurantEntity.getAddress().getState().getStateName())))).collect(Collectors.toList());

        RestaurantListResponse restaurantListResponse = new RestaurantListResponse().restaurants(restaurantList);
        return new ResponseEntity<RestaurantListResponse>(restaurantListResponse , HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET , path = "/restaurant/category/{restaurant_id}" , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RestaurantList> getRestaurantByUUID(@PathVariable(value = "restaurant_id") final String restaurantUUID ) throws RestaurantNotFoundException {
        RestaurantEntity restaurantEntity = restaurantService.restaurantByUUID(restaurantUUID);
        List<CategoryEntity> categoryEntities = categoryService.getCategoriesByRestaurant(restaurantUUID);


    }




}
