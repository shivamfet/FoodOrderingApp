package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.*;
import com.upgrad.FoodOrderingApp.service.businness.*;
import com.upgrad.FoodOrderingApp.service.entity.AddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.entity.StateEntity;
import com.upgrad.FoodOrderingApp.service.exception.AddressNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.SaveAddressException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class AddressController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private StateService stateService;

    @Autowired
    private ValidationUtils validationUtils;

    @RequestMapping(method = RequestMethod.POST , path = "/address" , consumes = MediaType.APPLICATION_JSON_UTF8_VALUE , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<SaveAddressResponse> saveAddress(@RequestHeader("authorization") final String authorization, @RequestBody(required = false) SaveAddressRequest saveAddressRequest) throws AuthorizationFailedException, SaveAddressException, AddressNotFoundException {

        String accessToken = authorization.split("Bearer ")[1];
        customerService.getCustomer(accessToken);
        StateEntity stateEntity = addressService.getStateByUUID(saveAddressRequest.getStateUuid());


        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setUuid(UUID.randomUUID().toString());
        addressEntity.setIsActive(0);
        addressEntity.setCity(saveAddressRequest.getCity());
        addressEntity.setFlatBuilNumber(saveAddressRequest.getFlatBuildingName());
        addressEntity.setLocality(saveAddressRequest.getLocality());
        addressEntity.setPincode(saveAddressRequest.getPincode());

        AddressEntity savedAddressEntity = addressService.saveAddress(addressEntity , stateEntity);

        SaveAddressResponse saveAddressResponse = new SaveAddressResponse().id(savedAddressEntity.getUuid()).status("ADDRESS SUCCESSFULLY REGISTERED");
        return new ResponseEntity<SaveAddressResponse>(saveAddressResponse , HttpStatus.CREATED);

    }

    @RequestMapping(method = RequestMethod.GET , path = "/address/customer" , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<AddressListResponse> getSavedAddresses(@RequestHeader final String authorization) throws AuthorizationFailedException {
        String accessToken = authorization.split("Bearer ")[1];

        CustomerEntity customerEntity = customerService.getCustomer(accessToken);
        List<AddressEntity> addressEntities = addressService.getAllAddress(customerEntity);

        List<AddressList> addressLists = addressEntities.stream().map(address ->
                new AddressList().city(address.getCity())
                        .flatBuildingName(address.getFlatBuilNumber())
                        .locality(address.getLocality())
                        .state(new AddressListState().stateName(address.getState().getStateName())
                                                    .id(UUID.fromString(address.getState().getUuid())))
                        .pincode(address.getPincode())
                        .id(UUID.fromString(address.getUuid()))
                        .city(address.getCity())).collect(Collectors.toList());


        AddressListResponse addressListResponse =new AddressListResponse().addresses(addressLists);
        return new ResponseEntity<AddressListResponse>(addressListResponse , HttpStatus.OK);

    }

    @RequestMapping(method = RequestMethod.DELETE , path = "/address/{address_id}" , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<DeleteAddressResponse> deleteAddress(@RequestHeader("authorization") final String authorization , @PathVariable(value = "address_id") final String addressUUID) throws AuthorizationFailedException, AddressNotFoundException {
        String accessToken = authorization.split("Bearer ")[1];

        CustomerEntity customerEntity = customerService.getCustomer(accessToken);

        AddressEntity addressEntity = addressService.getAddressByUUID(addressUUID , customerEntity);

        AddressEntity deleteAddressEntity = addressService.deleteAddress(addressEntity);

        DeleteAddressResponse deleteAddressResponse = new DeleteAddressResponse()
                .id(UUID.fromString(deleteAddressEntity.getUuid()))
                .status("ADDRESS DELETED SUCCESSFULLY");

        return new ResponseEntity<DeleteAddressResponse>(deleteAddressResponse,HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET , path = "/states" , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<StatesListResponse> getAllStates() throws AuthorizationFailedException {
        List<StateEntity> stateEntities = addressService.getAllStates();

        StatesListResponse statesListResponse;
        if (stateEntities.isEmpty()) {
            statesListResponse = new StatesListResponse().states(null);
        } else {
            List<StatesList> statesLists = stateEntities.stream().map(state ->
                    new StatesList().stateName(state.getStateName())
                            .id(UUID.fromString(state.getUuid()))).collect(Collectors.toList());
            statesListResponse = new StatesListResponse().states(statesLists);
        }


        return new ResponseEntity<StatesListResponse>(statesListResponse , HttpStatus.OK);
    }
}
