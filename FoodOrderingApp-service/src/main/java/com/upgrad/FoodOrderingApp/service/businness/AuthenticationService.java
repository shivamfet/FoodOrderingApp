//package com.upgrad.FoodOrderingApp.service.businness;
//;import com.upgrad.FoodOrderingApp.service.dao.CustomerAuthDao;
//import com.upgrad.FoodOrderingApp.service.dao.CustomerDao;
//import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
//import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
//import com.upgrad.FoodOrderingApp.service.exception.AuthenticationFailedException;
//import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.ZonedDateTime;
//
//@Service
//public class AuthenticationService {
//
//    @Autowired
//    CustomerDao customerDao;
//
//    @Autowired
//    CustomerAuthDao customerAuthDao;
//
//    @Autowired
//    private PasswordCryptographyProvider passwordCryptographyProvider;
//
//    @Transactional(propagation = Propagation.REQUIRED)
//    public CustomerAuthEntity authenticate(final String username , final  String password) throws AuthenticationFailedException {
//        CustomerEntity customerEntity = customerDao.getCustomerByContactNumber(username);
//        CustomerAuthEntity customerAuthEntity = new CustomerAuthEntity();
//        if (customerEntity == null) {
//            throw new AuthenticationFailedException("ATH-001" , "This contact number has not been registered!");
//        }
//        String encryptedPassword = PasswordCryptographyProvider.encrypt(password , customerEntity.getSalt());
//        if (encryptedPassword.equals(customerEntity.getPassword())) {
//            JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(encryptedPassword);
//
//            final ZonedDateTime now = ZonedDateTime.now();
//            final ZonedDateTime expiresAt = now.plusHours(8);
//
//            customerAuthEntity.setCustomerEntity(customerEntity);
//            String token = jwtTokenProvider.generateToken(customerEntity.getUuid() , now , expiresAt);
//            customerAuthEntity.setAccessToken(token);
//            customerAuthEntity.setUuid(customerEntity.getUuid());
//            customerAuthEntity.setLoginAt(now);
//            customerAuthEntity.setExpiresAt(expiresAt);
//            customerAuthDao.createAuthToken(customerAuthEntity);
//        } else {
//            throw new AuthenticationFailedException("ATH-002" , "Invalid Credentials");
//        }
//        return customerAuthEntity;
//    }
//
//    public CustomerAuthEntity authenticate (final String accessToken) throws AuthorizationFailedException {
//        CustomerAuthEntity customerAuthEntity = customerAuthDao.getCustomerAuthByAccessToken(accessToken);
//        if (customerAuthEntity == null) {
//            throw new AuthorizationFailedException("ATHR-001" , "Customer is not Logged in");
//        }
//        if (customerAuthEntity.getLogoutAt() != null) {
//            throw new AuthorizationFailedException("ATHR-002" , "Customer is logged out. Log in again to access this endpoint.");
//        }
//        ZonedDateTime tokenExpiryTime = customerAuthEntity.getExpiresAt();
//        ZonedDateTime currentTime = ZonedDateTime.now();
//        long diff = tokenExpiryTime.compareTo(currentTime);
//        if (diff <= 0) {
//            throw new AuthorizationFailedException("ATHR-003" , "Your session is expired. Log in again to access this endpoint.");
//        }
//        return customerAuthEntity;
//    }
//}
