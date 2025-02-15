package com.coder.springjwt.controllers.seller.catalogMetaData;

import com.coder.springjwt.constants.sellerConstants.sellerUrlMappings.SellerUrlMappings;
import com.coder.springjwt.dtos.sellerDtos.productMetaDtos.ProductSizeVariantDto;
import com.coder.springjwt.exception.services.sellerServices.catalogSizeService.ProductSizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(SellerUrlMappings.PRODUCT_SIZE_CONTROLLER)
public class ProductSizeController {


    @Autowired
    private ProductSizeService productSizeService;

    @PostMapping(SellerUrlMappings.SAVE_SIZE)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> saveSize(@Validated @RequestBody ProductSizeVariantDto productSizeVariantDto) {
        return this.productSizeService.saveSize(productSizeVariantDto);
    }


    @GetMapping(SellerUrlMappings.DELETE_SIZE)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> deleteSize(@PathVariable long sizeId ) {
        return this.productSizeService.deleteSize(sizeId);
    }

    @GetMapping(SellerUrlMappings.GET_SIZE_BY_ID)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> getSizeById(@PathVariable long sizeId ) {
        return this.productSizeService.getSizeById(sizeId);
    }


    @PostMapping(SellerUrlMappings.UPDATE_SIZE)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> updateSize(@Validated @RequestBody ProductSizeVariantDto productSizeVariantDto) {
        return this.productSizeService.updateSize(productSizeVariantDto);
    }

    @PostMapping(SellerUrlMappings.GET_SIZE)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> getSize(@RequestParam Integer page , @RequestParam  Integer size) {
        return this.productSizeService.getSize(page,size);
    }

}
