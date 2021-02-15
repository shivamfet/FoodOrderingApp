package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.*;
import com.upgrad.FoodOrderingApp.service.businness.CategoryService;
import com.upgrad.FoodOrderingApp.service.businness.CustomerService;
import com.upgrad.FoodOrderingApp.service.businness.ItemService;
import com.upgrad.FoodOrderingApp.service.businness.RestaurantService;
import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.CategoryNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.InvalidRatingException;
import com.upgrad.FoodOrderingApp.service.exception.RestaurantNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ItemService itemService;

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
                                        .id(UUID.fromString(restaurantEntity.getAddress().getState().getUuid()))
                                        .stateName(restaurantEntity.getAddress().getState().getStateName())))).collect(Collectors.toList());



        RestaurantListResponse restaurantListResponse = new RestaurantListResponse().restaurants(restaurantList);
        return new ResponseEntity<RestaurantListResponse>(restaurantListResponse , HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET , path = "/restaurant/name/{restaurant_name}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
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
                                        .id(UUID.fromString(restaurantEntity.getAddress().getState().getUuid()))
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
                                        .id(UUID.fromString(restaurantEntity.getAddress().getState().getUuid()))
                                        .stateName(restaurantEntity.getAddress().getState().getStateName())))).collect(Collectors.toList());

        RestaurantListResponse restaurantListResponse = new RestaurantListResponse().restaurants(restaurantList);
        return new ResponseEntity<RestaurantListResponse>(restaurantListResponse , HttpStatus.OK);
    }

//    @RequestMapping(method = RequestMethod.GET , path = "/restaurant/{restaurant_id}" , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    public ResponseEntity<RestaurantDetailsResponse> getRestaurantByUUID(@PathVariable(value = "restaurant_id") final String restaurantUUID ) throws RestaurantNotFoundException {
//        RestaurantEntity restaurantEntity = restaurantService.restaurantByUUID(restaurantUUID);
//        List<CategoryEntity> categoryEntities = categoryService.getCategoriesByRestaurant(restaurantUUID);
//
//        RestaurantDetailsResponse restaurantDetailsResponse = new RestaurantDetailsResponse()
//                                                            .id(UUID.fromString(restaurantEntity.getUuid()))
//                                                            .restaurantName(restaurantEntity.getRestaurantName())
//                                                            .photoURL(restaurantEntity.getPhotoUrl())
//                                                            .customerRating(BigDecimal.valueOf(restaurantEntity.getCustomerRating()))
//                                                            .averagePrice(restaurantEntity.getAvgPrice())
//                                                            .numberCustomersRated(restaurantEntity.getNumberCustomersRated())
//                                                            .categories(categoryEntities.stream().map(categoryEntity ->
//                                                                    new CategoryDetailsResponse()
//                                                                            .categoryName(categoryEntity.getCategoryName())
//                                                                            .itemList((itemService.getItemsByCategoryAndRestaurant(categoryEntity.getUuid() , restaurantUUID))
//                                                                                    .stream().map(itemEntity ->
//                                                                                            new ItemList().id(UUID.fromString(itemEntity.getUuid()))
//                                                                                                          .itemName(itemEntity.getItemName())
//                                                                                                          .itemType(ItemList.ItemTypeEnum.valueOf(itemEntity.getType().getValue()))
//                                                                                                          .price(itemEntity.getPrice())).collect(Collectors.toList()))).collect(Collectors.toList())
//
//
//        return null;
//    }

    @RequestMapping(method = RequestMethod.PUT , path = "/restaurant/{restaurant_id}" , params = "customer_rating" , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RestaurantUpdatedResponse> updateRestaurant(@RequestHeader("authorization") final String authorization , @PathVariable(value = "restaurant_id") final String restaurantUUID , @RequestParam(value = "customer_rating")final Double customerRating) throws AuthorizationFailedException, RestaurantNotFoundException, InvalidRatingException {
        final String accessToken = authorization.split("Bearer ")[1];
        CustomerEntity customerEntity = customerService.getCustomer(accessToken);
        RestaurantEntity restaurantEntity = restaurantService.restaurantByUUID(restaurantUUID);
        RestaurantEntity restaurantEntity1 = restaurantService.updateRestaurantRating(restaurantEntity , customerRating);

        RestaurantUpdatedResponse restaurantUpdatedResponse = new RestaurantUpdatedResponse().id(UUID.fromString(restaurantUUID)).status("RESTAURANT RATING UPDATED SUCCESSFULLY");
        return new ResponseEntity<RestaurantUpdatedResponse>(restaurantUpdatedResponse , HttpStatus.OK);
    }



}
