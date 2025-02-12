package com.coder.springjwt.controllers.seller.catalogMetaData;

import com.coder.springjwt.constants.sellerConstants.sellerUrlMappings.SellerUrlMappings;
import com.coder.springjwt.dtos.sellerDtos.productMetaDtos.ProductWeightDto;
import com.coder.springjwt.services.sellerServices.catalogWeightService.ProductWeightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(SellerUrlMappings.PRODUCT_WEIGHT_CONTROLLER)
public class ProductWeightController {

    @Autowired
    private ProductWeightService productWeightService;

    @PostMapping(SellerUrlMappings.SAVE_WEIGHT)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> saveWeight(@Validated @RequestBody ProductWeightDto productWeightDto) {
        return this.productWeightService.saveWeight(productWeightDto);
    }

    @PostMapping(SellerUrlMappings.GET_WEIGHT)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> getWeight(@RequestParam Integer page , @RequestParam  Integer size) {
        return this.productWeightService.getWeight(page,size);
    }
}
