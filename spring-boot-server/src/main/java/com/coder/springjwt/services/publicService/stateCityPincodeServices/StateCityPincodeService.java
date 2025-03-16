package com.coder.springjwt.services.publicService.stateCityPincodeServices;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface StateCityPincodeService {


    public ResponseEntity<?> getCityStatePincode(int pincode);

}
