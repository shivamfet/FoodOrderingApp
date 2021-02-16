package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.ItemList;
import com.upgrad.FoodOrderingApp.api.model.ItemListResponse;
import com.upgrad.FoodOrderingApp.service.businness.ItemService;
import com.upgrad.FoodOrderingApp.service.businness.RestaurantService;
import com.upgrad.FoodOrderingApp.service.common.ItemType;
import com.upgrad.FoodOrderingApp.service.entity.ItemEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import com.upgrad.FoodOrderingApp.service.exception.RestaurantNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private RestaurantService restaurantService;

    @RequestMapping(method = RequestMethod.GET , path = "/item/restaurant/{restaurant_id}" , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ItemListResponse> getTopFiveItems(@PathVariable(value = "restaurant_id") final String restaurantUUID) throws RestaurantNotFoundException {
        RestaurantEntity restaurantEntity = restaurantService.restaurantByUUID(restaurantUUID);
        List<ItemEntity> itemEntityList = itemService.getItemsByPopularity(restaurantEntity);

        List<ItemList> itemLists = itemEntityList.stream().map(itemEntity -> new ItemList().id(UUID.fromString(itemEntity.getUuid()))
                                                                .itemName(itemEntity.getItemName())
                                                                .price(itemEntity.getPrice())
                                                                .itemType(ItemList.ItemTypeEnum.valueOf(itemEntity.getType().getValue()))).collect(Collectors.toList());

        ItemListResponse itemListResponse = new ItemListResponse();
        itemLists.forEach(itemList -> itemListResponse.add(itemList));
        return new ResponseEntity<ItemListResponse>(itemListResponse , HttpStatus.OK);
    }

}
