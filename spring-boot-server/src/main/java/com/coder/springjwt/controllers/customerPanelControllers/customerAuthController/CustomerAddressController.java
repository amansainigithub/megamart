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
}
