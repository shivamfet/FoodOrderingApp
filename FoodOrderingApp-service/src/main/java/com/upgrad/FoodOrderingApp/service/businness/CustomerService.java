package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.CustomerAuthDao;
import com.upgrad.FoodOrderingApp.service.dao.CustomerDao;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthenticationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.SignUpRestrictedException;
import com.upgrad.FoodOrderingApp.service.exception.UpdateCustomerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;


@Service
public class CustomerService {

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private CustomerAuthDao customerAuthDao;

    @Autowired
    private PasswordCryptographyProvider passwordCryptographyProvider;

    @Autowired
    private ValidationUtils validationUtils;

    @Transactional(propagation = Propagation.REQUIRED)
    public CustomerEntity saveCustomer(CustomerEntity customerEntity) throws SignUpRestrictedException {
        boolean isEmailValid = validationUtils.isEmailValid(customerEntity.getEmail());
        if (!isEmailValid) {
            throw new SignUpRestrictedException("SGR-002" , "Invalid email-id format!");
        }

        boolean isContactNumberValid = validationUtils.isContactNumberValid(customerEntity.getContactNumber());
        if (!isContactNumberValid) {
            throw new SignUpRestrictedException("SGR-003" , "Invalid contact number");
        }

        boolean isPasswordValid = validationUtils.isPasswordValid(customerEntity.getPassword());
        if (!isPasswordValid) {
            throw new SignUpRestrictedException("SGR-004" , "Weak Password!");
        }

        CustomerEntity fetchedCustomerEntity = customerDao.getCustomerByContactNumber(customerEntity.getContactNumber());
        if (fetchedCustomerEntity != null ) {
            throw new SignUpRestrictedException("SGR-001" , "This contact number is already registered");
        } else {
            String encryptedText[] =passwordCryptographyProvider.encrypt(customerEntity.getPassword());
            customerEntity.setSalt(encryptedText[0]);
            customerEntity.setPassword(encryptedText[1]);
            customerDao.createCustomer(customerEntity);
            return customerEntity;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public CustomerAuthEntity authenticate(final String username , final  String password) throws AuthenticationFailedException {
        CustomerEntity customerEntity = customerDao.getCustomerByContactNumber(username);
        CustomerAuthEntity customerAuthEntity = new CustomerAuthEntity();
        if (customerEntity == null) {
            throw new AuthenticationFailedException("ATH-001" , "This contact number has not been registered!");
        }
        String encryptedPassword = PasswordCryptographyProvider.encrypt(password , customerEntity.getSalt());
        if (encryptedPassword.equals(customerEntity.getPassword())) {
            JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(encryptedPassword);

            final ZonedDateTime now = ZonedDateTime.now();
            final ZonedDateTime expiresAt = now.plusHours(8);

            customerAuthEntity.setCustomer(customerEntity);
            String token = jwtTokenProvider.generateToken(customerEntity.getUuid() , now , expiresAt);
            customerAuthEntity.setAccessToken(token);
            customerAuthEntity.setUuid(customerEntity.getUuid());
            customerAuthEntity.setLoginAt(now);
            customerAuthEntity.setExpiresAt(expiresAt);
            customerAuthDao.createAuthToken(customerAuthEntity);
        } else {
            throw new AuthenticationFailedException("ATH-002" , "Invalid Credentials");
        }
        return customerAuthEntity;
    }

    public CustomerAuthEntity authenticate (final String accessToken) throws AuthorizationFailedException {
        CustomerAuthEntity customerAuthEntity = customerAuthDao.getCustomerAuthByAccessToken(accessToken);
        if (customerAuthEntity == null) {
            throw new AuthorizationFailedException("ATHR-001" , "Customer is not Logged in");
        }
        if (customerAuthEntity.getLogoutAt() != null) {
            throw new AuthorizationFailedException("ATHR-002" , "Customer is logged out. Log in again to access this endpoint.");
        }
        ZonedDateTime tokenExpiryTime = customerAuthEntity.getExpiresAt();
        ZonedDateTime currentTime = ZonedDateTime.now();
        long diff = tokenExpiryTime.compareTo(currentTime);
        if (diff <= 0) {
            throw new AuthorizationFailedException("ATHR-003" , "Your session is expired. Log in again to access this endpoint.");
        }
        return customerAuthEntity;
    }

    public CustomerAuthEntity logout(String accessToken) throws AuthorizationFailedException {
        CustomerAuthEntity customerAuthEntity = authenticate(accessToken);
        customerAuthEntity.setLogoutAt(ZonedDateTime.now());
        customerAuthDao.updateAuthToken(customerAuthEntity);
        return customerAuthEntity;
    }

    public CustomerEntity getCustomer(String accessToken) throws AuthorizationFailedException {
        CustomerAuthEntity customerAuthEntity = authenticate(accessToken);
        return customerAuthEntity.getCustomer();
    }

    public CustomerEntity updateCustomer(CustomerEntity customerEntity) {
        CustomerEntity updatedCustomerEntity = customerDao.updateCustomer(customerEntity);
        return updatedCustomerEntity;
    }

    public CustomerEntity updateCustomerPassword(String oldPassword , String newPassword , CustomerEntity customerEntity ) throws UpdateCustomerException {

        // Validate if the new password meets the criteria

        boolean isPasswordValid = validationUtils.isPasswordValid(newPassword);

        if (!isPasswordValid) {
            throw new UpdateCustomerException("UCR-001" , "Weak Password!");
        }

        CustomerEntity fetchedCustomerEntity = customerDao.getCustomerByContactNumber(customerEntity.getContactNumber());
        String userSuppliedPassword = passwordCryptographyProvider.encrypt(oldPassword , customerEntity.getSalt());
        if (!userSuppliedPassword.equals(customerEntity.getPassword())) {
            throw new UpdateCustomerException("UCR-004" , "Incorrect old password!");
        }
        String encryptedText[] =passwordCryptographyProvider.encrypt(newPassword);
        fetchedCustomerEntity.setSalt(encryptedText[0]);
        fetchedCustomerEntity.setPassword(encryptedText[1]);
        CustomerEntity updatedCustomerEntity = customerDao.updateCustomer(customerEntity);
        return updatedCustomerEntity;
    }

}
