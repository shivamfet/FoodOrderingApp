package com.upgrad.FoodOrderingApp.service.entity;

import javax.persistence.*;

@Entity
@Table(name = "restaurant_category" , schema = "public")
@NamedQueries(
        {
                @NamedQuery(name = "getCategoriesByRestaurant" ,query = "select restaurant_category from " +
                        "RestaurantCategoryEntity restaurant_category where restaurant_category.restaurantEntity.uuid = :restaurantUUID order by  restaurant_category.categoryEntity.categoryName asc")
                @NamedQuery(name = "getRestaurantsByCategory" ,query = "select restaurant_category from " +
                        "RestaurantCategoryEntity restaurant_category where restaurant_category.categoryEntity.uuid= :categoryUUID order by  restaurant_category.restaurantEntity.restaurantName asc")
        }
)
public class RestaurantCategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    RestaurantEntity restaurantEntity;

    @ManyToOne
    @JoinColumn(name = "category_id")
    CategoryEntity categoryEntity;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RestaurantEntity getRestaurantEntity() {
        return restaurantEntity;
    }

    public void setRestaurantEntity(RestaurantEntity restaurantEntity) {
        this.restaurantEntity = restaurantEntity;
    }

    public CategoryEntity getCategoryEntity() {
        return categoryEntity;
    }

    public void setCategoryEntity(CategoryEntity categoryEntity) {
        this.categoryEntity = categoryEntity;
    }
}
