package com.coder.springjwt.controllers.customerPanelControllers.customerAuthController;

import com.coder.springjwt.constants.customerPanelConstants.customerUrlMappings.CustomerUrlMappings;
import com.coder.springjwt.dtos.customerPanelDtos.addressDto.AddressDto;
import com.coder.springjwt.services.publicService.addressService.AddressService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(CustomerUrlMappings.ADDRESS_CONTROLLER)
public class CustomerAddressController {

    @Autowired
    private AddressService addressService;

    @PostMapping(CustomerUrlMappings.SAVE_ADDRESS)
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> saveAddress(@Valid @RequestBody AddressDto addressDto) {
        return this.addressService.saveAddress(addressDto);
    }

    @GetMapping(CustomerUrlMappings.GET_ADDRESS)
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> getAddress() {
        return this.addressService.getAddress();
    }

    @GetMapping(CustomerUrlMappings.DELETE_ADDRESS)
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> deleteAddress(@PathVariable(required = true) long id) {
        return this.addressService.deleteAddress(id);
    }


    @GetMapping(CustomerUrlMappings.SET_DEFAULT_ADDRESS)
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> setDefaultAddress(@PathVariable(required = true) long id) {
        return this.addressService.setDefaultAddress(id);
    }

    @GetMapping(CustomerUrlMappings.GET_ADDRESS_BY_ID)
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> getAddressById(@PathVariable(required = true) long id) {
        return this.addressService.getAddressById(id);
    }

    @PostMapping(CustomerUrlMappings.UPDATE_ADDRESS)
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> updateAddress(@Valid @RequestBody AddressDto addressDto) {
        return this.addressService.updateAddress(addressDto);
    }









}
