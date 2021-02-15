package com.upgrad.FoodOrderingApp.service.entity;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "restaurant" , schema = "public")
@NamedQueries(
        {
                @NamedQuery(name = "getRestaurantsByRating" , query = "select restaurant from RestaurantEntity restaurant order by restaurant.customerRating desc"),
                @NamedQuery(name = "getRestaurantByUUID" , query = "select restaurant from RestaurantEntity restaurant where restaurant.uuid = :uuid"),
                @NamedQuery(name = "getRestaurantByUUID" , query = "select restaurant from RestaurantEntity restaurant where lower(restaurant.restaurantName) like :restaurantName")
        }
)
public class RestaurantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "uuid")
    @Size(max = 200)
    private String uuid;

    @Column(name = "restaurant_name")
    private String restaurantName;

    @Column(name = "photo_url")
    private String photo_url;

    @Column(name = "customer_rating")
    private Double customerRating;

    @Column(name = "average_price_for_two")
    private Integer averagePriceForTwo;

    @Column(name = "number_of_customers_rated")
    private Integer numberOfCustomersRated;

    @OneToOne(fetch = FetchType.EAGER , cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private AddressEntity addressEntity;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getPhotoUrl() {
        return photo_url;
    }

    public void setPhotoUrl(String photo_url) {
        this.photo_url = photo_url;
    }

    public Double getCustomerRating() {
        return customerRating;
    }

    public void setCustomerRating(Double customerRating) {
        this.customerRating = customerRating;
    }

    public Integer getAvgPrice() {
        return averagePriceForTwo;
    }

    public void setAvgPrice(Integer averagePriceForTwo) {
        this.averagePriceForTwo = averagePriceForTwo;
    }

    public Integer getNumberCustomersRated() {
        return numberOfCustomersRated;
    }

    public void setNumberCustomersRated(Integer numberOfCustomersRated) {
        this.numberOfCustomersRated = numberOfCustomersRated;
    }

    public AddressEntity getAddress() {
        return addressEntity;
    }

    public void setAddress(AddressEntity addressEntity) {
        this.addressEntity = addressEntity;
    }
}
