package com.upgrad.FoodOrderingApp.service.entity;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "restaurant" , schema = "public")
public class Restaurant {

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
    private Address address;

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

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    public Double getCustomerRating() {
        return customerRating;
    }

    public void setCustomerRating(Double customerRating) {
        this.customerRating = customerRating;
    }

    public Integer getAveragePriceForTwo() {
        return averagePriceForTwo;
    }

    public void setAveragePriceForTwo(Integer averagePriceForTwo) {
        this.averagePriceForTwo = averagePriceForTwo;
    }

    public Integer getNumberOfCustomersRated() {
        return numberOfCustomersRated;
    }

    public void setNumberOfCustomersRated(Integer numberOfCustomersRated) {
        this.numberOfCustomersRated = numberOfCustomersRated;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
