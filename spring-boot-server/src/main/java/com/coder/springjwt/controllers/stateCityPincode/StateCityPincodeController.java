package com.coder.springjwt.controllers.stateCityPincode;

import com.coder.springjwt.constants.sellerConstants.sellerUrlMappings.SellerUrlMappings;
import com.coder.springjwt.models.sellerModels.SellerMobile.SellerOtpRequest;
import com.coder.springjwt.services.stateCityPincodeServices.StateCityPincodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(SellerUrlMappings.STATE_CITY_PINCODE_CONTROLLER)
public class StateCityPincodeController {


    @Autowired
    private StateCityPincodeService stateCityPincodeService;


    @GetMapping(SellerUrlMappings.STATE_CITY_PINCODE)
    public ResponseEntity<?> stateCityPincode(@PathVariable int pincode) {
        return stateCityPincodeService.getCityStatePincode(pincode);
    }
}
