package com.coder.springjwt.controllers.seller.catalogMetaData;

import com.coder.springjwt.constants.sellerConstants.sellerUrlMappings.SellerUrlMappings;
import com.coder.springjwt.dtos.sellerDtos.productMetaDtos.ProductBreathDto;
import com.coder.springjwt.exception.services.sellerServices.catalogBreathService.ProductBreathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(SellerUrlMappings.PRODUCT_BREATH_CONTROLLER)
public class ProductBreathController {

    @Autowired
    private ProductBreathService productBreathService;

    @PostMapping(SellerUrlMappings.SAVE_BREATH)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> saveBreath(@Validated @RequestBody ProductBreathDto productBreathDto) {
        return this.productBreathService.saveBreath(productBreathDto);
    }

    @PostMapping(SellerUrlMappings.GET_BREATH)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> getBreath(@RequestParam Integer page , @RequestParam  Integer size) {
        return this.productBreathService.getBreath(page,size);
    }
}
