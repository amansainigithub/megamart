package com.coder.springjwt.controllers.seller.catalogMetaData;

import com.coder.springjwt.constants.sellerConstants.sellerUrlMappings.SellerUrlMappings;
import com.coder.springjwt.dtos.sellerDtos.productMetaDtos.ProductLengthDto;
import com.coder.springjwt.exception.services.sellerServices.catalogLengthService.ProductLengthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(SellerUrlMappings.PRODUCT_LENGTH_CONTROLLER)
public class ProductLengthController {

    @Autowired
    private ProductLengthService productLengthService;

    @PostMapping(SellerUrlMappings.SAVE_LENGTH)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> saveLength(@Validated @RequestBody ProductLengthDto productLengthDto) {
        return this.productLengthService.saveLength(productLengthDto);
    }

    @PostMapping(SellerUrlMappings.GET_LENGTH)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> getLength(@RequestParam Integer page , @RequestParam  Integer size) {
        return this.productLengthService.getLength(page,size);
    }
}
