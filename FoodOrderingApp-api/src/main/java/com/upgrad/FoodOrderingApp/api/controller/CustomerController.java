package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.*;

import com.upgrad.FoodOrderingApp.service.businness.CustomerService;
import com.upgrad.FoodOrderingApp.service.businness.ValidationUtils;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthenticationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.SignUpRestrictedException;
import com.upgrad.FoodOrderingApp.service.exception.UpdateCustomerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.instrument.classloading.jboss.JBossLoadTimeWeaver;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.Base64;
import java.util.UUID;

@RestController
@RequestMapping("/")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ValidationUtils validationUtils;

    @RequestMapping(method = RequestMethod.POST , path = "/customer/signup" , consumes = MediaType.APPLICATION_JSON_UTF8_VALUE , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<SignupCustomerResponse> signUp(@RequestBody(required = false) final SignupCustomerRequest signupCustomerRequest) throws SignUpRestrictedException {

        if (signupCustomerRequest.getFirstName().isEmpty() || signupCustomerRequest.getContactNumber().isEmpty()  ||
                signupCustomerRequest.getEmailAddress().isEmpty() || signupCustomerRequest.getPassword().isEmpty()) {
            throw new SignUpRestrictedException("SGR-005" , "Except last name all fields should be filled");
        }

        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setFirstName(signupCustomerRequest.getFirstName());
        customerEntity.setLastName(signupCustomerRequest.getLastName());
        customerEntity.setEmail(signupCustomerRequest.getEmailAddress());
        customerEntity.setContactNumber(signupCustomerRequest.getContactNumber());
        customerEntity.setPassword(signupCustomerRequest.getPassword());
        customerEntity.setUuid(UUID.randomUUID().toString());
        final CustomerEntity createdCustomerEntity = customerService.saveCustomer(customerEntity);
        final SignupCustomerResponse signupCustomerResponse = new SignupCustomerResponse().id(createdCustomerEntity.getUuid()).status("CUSTOMER SUCCESSFULLY REGISTERED");
        return new ResponseEntity<SignupCustomerResponse>(signupCustomerResponse , HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/customer/login", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<LoginResponse> login(@RequestHeader("authorization") final String authorization) throws AuthenticationFailedException {
        if (!authorization.contains("Basic ")) {
            throw new AuthenticationFailedException("ATH-003" , "Incorrect format of decoded customer name and password");
        }
        byte[] decode = Base64.getDecoder().decode(authorization.split("Basic ")[1]);
        String decodedText = new String(decode);
        String[] decodedArray = decodedText.split(":");

        if (decodedArray.length < 2) {
            throw new AuthenticationFailedException("ATH-003" , "Incorrect format of decoded customer name and password");
        }

        CustomerAuthEntity customerAuth = customerService.authenticate(decodedArray[0] , decodedArray[1]);
        CustomerEntity customer = customerAuth.getCustomer();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("access-token" , customerAuth.getAccessToken());

        LoginResponse loginResponse = new LoginResponse().id(customer.getUuid()).firstName(customer.getFirstName()).lastName(customer.getLastName()).emailAddress(customer.getEmail()).message("LOGGED IN SUCCESSFULLY");
        return new ResponseEntity<LoginResponse>(loginResponse , httpHeaders , HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/customer/logout", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<LogoutResponse> logout(@RequestHeader("authorization") final String authorization) throws AuthenticationFailedException, AuthorizationFailedException {
        String accessToken = authorization.split("Bearer ")[1];
        CustomerAuthEntity customerAuthEntity = customerService.logout(accessToken);
        CustomerEntity customerEntity = customerAuthEntity.getCustomer();
        LogoutResponse logoutResponse = new LogoutResponse().id((customerEntity.getUuid())).message("LOGGED OUT SUCCESSFULLY");
        return  new ResponseEntity<LogoutResponse>(logoutResponse , HttpStatus.OK);

    }

    @RequestMapping(method = RequestMethod.PUT , path = "/customer" , consumes = MediaType.APPLICATION_JSON_UTF8_VALUE , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UpdateCustomerResponse> updateCustomer(@RequestHeader("authorization") final String authorization ,  @RequestBody(required = false) UpdateCustomerRequest updateCustomerRequest) throws AuthorizationFailedException, UpdateCustomerException {
        if (updateCustomerRequest.getFirstName().isEmpty()) {
            throw new UpdateCustomerException("UCR-002" , "First name field should not be empty");
        }

        String accessToken = authorization.split("Bearer ")[1];
        CustomerAuthEntity customerAuthEntity = customerService.authenticate(accessToken);
        CustomerEntity customerEntity = customerService.getCustomer(accessToken);

        customerEntity.setFirstName(updateCustomerRequest.getFirstName());
        customerEntity.setLastName(updateCustomerRequest.getLastName());

        CustomerEntity updatedCustomerEntity = customerService.updateCustomer(customerEntity);
        UpdateCustomerResponse updateCustomerResponse = new UpdateCustomerResponse().id(updatedCustomerEntity.getUuid()).
                firstName(updatedCustomerEntity.getFirstName()).
                lastName(updateCustomerRequest.getLastName()).
                status("CUSTOMER DETAILS UPDATED SUCCESSFULLY");;

        return new ResponseEntity<UpdateCustomerResponse>(updateCustomerResponse , HttpStatus.OK);

    }

    @RequestMapping(method = RequestMethod.PUT , path = "/customer/password" , consumes = MediaType.APPLICATION_JSON_UTF8_VALUE ,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UpdatePasswordResponse> updatePassword(@RequestHeader("authorization") final String authorization ,
                                                                 @RequestBody(required = false) UpdatePasswordRequest updatePasswordRequest) throws AuthorizationFailedException, UpdateCustomerException {

        if (updatePasswordRequest.getOldPassword().isEmpty() || updatePasswordRequest.getNewPassword().isEmpty()) {
            throw new UpdateCustomerException("UCR-003" , "No field should be empty");
        }

        String accessToken = authorization.split("Bearer ")[1];
        CustomerAuthEntity customerAuthEntity = customerService.authenticate(accessToken);
        CustomerEntity customerEntity = customerService.getCustomer(accessToken);
        customerService.updateCustomerPassword(updatePasswordRequest.getOldPassword() , updatePasswordRequest.getNewPassword() , customerEntity);
        UpdatePasswordResponse updatePasswordResponse = new UpdatePasswordResponse().id(customerEntity.getUuid()).status("CUSTOMER PASSWORD UPDATED SUCCESSFULLY");
        return new ResponseEntity<UpdatePasswordResponse>(updatePasswordResponse , HttpStatus.OK);
    }

}
