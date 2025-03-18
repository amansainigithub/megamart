package com.coder.springjwt.services.publicService.addressService;

import com.coder.springjwt.dtos.customerPanelDtos.addressDto.AddressDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface AddressService {
    ResponseEntity<?> saveAddress(AddressDto addressDto);

    ResponseEntity<?> getAddress();
}
