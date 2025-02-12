package com.coder.springjwt.controllers.seller.catalogMetaData;

import com.coder.springjwt.constants.sellerConstants.sellerUrlMappings.SellerUrlMappings;
import com.coder.springjwt.dtos.sellerDtos.productMetaDtos.ProductTypeDto;
import com.coder.springjwt.services.sellerServices.catalogTypeService.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(SellerUrlMappings.PRODUCT_TYPE_CONTROLLER)
public class ProductTypeController {

    @Autowired
    private ProductTypeService productTypeService;

    @PostMapping(SellerUrlMappings.SAVE_TYPE)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> saveType(@Validated @RequestBody ProductTypeDto productTypeDto) {
        return this.productTypeService.saveType(productTypeDto);
    }

    @GetMapping(SellerUrlMappings.DELETE_TYPE)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> deleteType(@PathVariable long typeId ) {
        return this.productTypeService.deleteType(typeId);
    }

    @GetMapping(SellerUrlMappings.GET_TYPE_BY_ID)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> getTypeById(@PathVariable long typeId ) {
        return this.productTypeService.getTypeById(typeId);
    }

    @PostMapping(SellerUrlMappings.UPDATE_TYPE)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> updateType(@Validated @RequestBody ProductTypeDto productTypeDto) {
        return this.productTypeService.updateType(productTypeDto);
    }


    @PostMapping(SellerUrlMappings.GET_TYPE)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> getType(@RequestParam Integer page , @RequestParam  Integer size) {
        return this.productTypeService.getType(page,size);
    }


}
