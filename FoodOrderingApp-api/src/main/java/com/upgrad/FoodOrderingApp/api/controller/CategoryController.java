package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.*;
import com.upgrad.FoodOrderingApp.service.businness.CategoryService;
import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.exception.CategoryNotFoundException;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(method = RequestMethod.GET , path = "/category" , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CategoriesListResponse> getAllCategories() {
        List<CategoryEntity> categoryEntityList = categoryService.getAllCategoriesOrderedByName();
        List<CategoryListResponse> categoryListResponses = categoryEntityList.stream().map(categoryEntity -> new CategoryListResponse().id(UUID.fromString(categoryEntity.getUuid()))
                                                            .categoryName(categoryEntity.getCategoryName())).collect(Collectors.toList());
        if (categoryListResponses.isEmpty()) {
            categoryListResponses = null;
        }

        CategoriesListResponse categoriesListResponse = new CategoriesListResponse().categories(categoryListResponses);
        return new ResponseEntity<CategoriesListResponse>(categoriesListResponse , HttpStatus.OK);

    }

    @RequestMapping(method = RequestMethod.GET , path = "/category/{category_id}" , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CategoryDetailsResponse> getCategoryById(@PathVariable(value = "category_id") final String categoryUUID) throws CategoryNotFoundException {
        CategoryEntity categoryEntity = categoryService.getCategoryById(categoryUUID);
        CategoryDetailsResponse categoryDetailsResponse = new CategoryDetailsResponse().categoryName(categoryEntity.getCategoryName()).id(UUID.fromString(categoryEntity.getUuid()))
                .itemList(categoryEntity.getItems().stream().map(itemEntity -> new ItemList().id(UUID.fromString(itemEntity.getUuid()))
                                                                                             .itemName(itemEntity.getItemName())
                                                                                             .itemType(ItemList.ItemTypeEnum.valueOf(itemEntity.getType().getValue()))
                                                                                             .price(itemEntity.getPrice())).collect(Collectors.toList()));
        return new ResponseEntity<CategoryDetailsResponse>(categoryDetailsResponse , HttpStatus.OK);
    }

}
