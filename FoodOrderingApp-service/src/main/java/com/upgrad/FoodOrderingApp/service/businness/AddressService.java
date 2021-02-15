package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.AddressDao;
import com.upgrad.FoodOrderingApp.service.dao.CustomerAddressDao;
import com.upgrad.FoodOrderingApp.service.dao.StateDao;
import com.upgrad.FoodOrderingApp.service.entity.*;
import com.upgrad.FoodOrderingApp.service.exception.AddressNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.SaveAddressException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class AddressService {


    @Autowired
    private AddressDao addressDao;

    @Autowired
    private CustomerAddressDao customerAddressDao;

    @Autowired
    private StateDao stateDao;

    @Autowired
    private ValidationUtils validationUtils;

    @Transactional(propagation = Propagation.REQUIRED)
    public AddressEntity saveAddress(AddressEntity addressEntity , StateEntity stateEntity) throws SaveAddressException {

        if (addressEntity.getCity().isEmpty() || addressEntity.getFlatBuilNumber().isEmpty()
                || addressEntity.getLocality().isEmpty() ||
                addressEntity.getUuid().isEmpty()) {
            throw new SaveAddressException("SAR-001" , "No field can be empty");
        }

        boolean isPincodeValid = validationUtils.isPincodeValid(addressEntity.getPincode());
        if (!isPincodeValid) {
            throw new SaveAddressException("SAR-002" , "Invalid pincode");
        }

        addressEntity.setState(stateEntity);
        return addressDao.saveAddress(addressEntity);
    }


    public AddressEntity getAddressByUUID(final String uuid , CustomerEntity customerEntity) throws AddressNotFoundException, AuthorizationFailedException {
        AddressEntity addressEntity =  addressDao.getAddressByUUID(uuid);
        if (addressEntity == null) {
            throw new AddressNotFoundException("ANF-003", "No address by this id");
        }

        if (!uuid.equals(customerEntity.getUuid())) {
            throw new AuthorizationFailedException("ATHR-004", "You are not authorized to view/update/delete any one else's address");
        }
        return addressEntity;
    }

    public List<StateEntity> getAllStates() {
        return stateDao.getAllStates();
    }

    public  AddressEntity deleteAddress(AddressEntity addressEntity) {
        return addressDao.deleteAddress(addressEntity);
    }

    public List<AddressEntity> getAllAddress(CustomerEntity customerEntity) {
        List<CustomerAddressEntity> customerAddressEntities = customerAddressDao.getCustomerAddresses(customerEntity);
        List<AddressEntity> addressEntities = new ArrayList<AddressEntity>();
        if (customerAddressEntities != null) {
            customerAddressEntities.forEach(customerAddressEntity -> {
                addressEntities.add(customerAddressEntity.getAddressEntity());
            });
        }
        return addressEntities;
    }

    public StateEntity getStateByUUID(final String uuid) throws AddressNotFoundException {
        StateEntity stateEntity =  stateDao.getStateByUuid(uuid);
        if (stateEntity == null) {
            throw new AddressNotFoundException("ANF-002" , "No State by this id");
        }
        return stateEntity;
    }


}
