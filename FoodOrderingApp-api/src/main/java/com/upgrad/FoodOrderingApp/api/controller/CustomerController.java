package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.LoginResponse;
import com.upgrad.FoodOrderingApp.api.model.SignupCustomerRequest;
import com.upgrad.FoodOrderingApp.api.model.SignupCustomerResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class CustomerController {

    @RequestMapping(method = RequestMethod.POST , path = "/customer/signup" , consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<SignupCustomerResponse> signUp(SignupCustomerRequest signupCustomerRequest) {
        Customer customer
        SignupCustomerRequest signupCustomerRequest = new SignupCustomerRequest();
        return null;
    }

    public ResponseEntity<LoginResponse> login(@RequestHeader("authorization") final String authorization) {
        return null;
    }




}
