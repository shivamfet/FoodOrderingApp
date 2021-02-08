package com.upgrad.FoodOrderingApp.service.entity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.ZonedDateTime;

@Entity
@Table(name = "customer_auth" , schema = "public")
public class CustomerAuth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "uuid")
    @Size(max = 200)
    private String uuid;

    @Column(name = "access_token" )
    @Size(max = 500)
    private String access_token;

    @Column(name = "login_at")
    ZonedDateTime loginAt;

    @Column(name = "logout_at")
    ZonedDateTime logoutAt;

    @Column(name = "expires_at")
    ZonedDateTime expiresAt;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    Customer customer;

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

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public ZonedDateTime getLoginAt() {
        return loginAt;
    }

    public void setLoginAt(ZonedDateTime loginAt) {
        this.loginAt = loginAt;
    }

    public ZonedDateTime getLogoutAt() {
        return logoutAt;
    }

    public void setLogoutAt(ZonedDateTime logoutAt) {
        this.logoutAt = logoutAt;
    }

    public ZonedDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(ZonedDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
