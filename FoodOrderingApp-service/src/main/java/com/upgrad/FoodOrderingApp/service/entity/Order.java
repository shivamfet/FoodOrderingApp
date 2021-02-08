package com.upgrad.FoodOrderingApp.service.entity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.ZonedDateTime;

@Entity
@Table(name = "orders" , schema = "public")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "uuid")
    @Size(max = 200)
    private String uuid;

    @Column(name = "bill")
    private Double bill;

    @ManyToOne
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @Column(name = "discount")
    private Double discount;

    @Column(name = "date")
    ZonedDateTime date;

    @OneToOne
    @JoinColumn(name = "payment_id")
    Payment payment;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    Customer customer;

    @ManyToOne
    @JoinColumn(name = "address_id")
    Address address;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    Restaurant restaurant;

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

    public Double getBill() {
        return bill;
    }

    public void setBill(Double bill) {
        this.bill = bill;
    }

    public Coupon getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
