package com.coder.springjwt.controllers.seller.catalogMetaData;

import com.coder.springjwt.constants.sellerConstants.sellerUrlMappings.SellerUrlMappings;
import com.coder.springjwt.dtos.sellerDtos.productMetaDtos.ProductNetQuantityDto;
import com.coder.springjwt.exception.services.sellerServices.catalogNetQuantityService.ProductNetQuantityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(SellerUrlMappings.PRODUCT_NET_QUANTITY_CONTROLLER)
public class ProductNetQuantityController {

    @Autowired
    private ProductNetQuantityService productNetQuantityService;

    @PostMapping(SellerUrlMappings.SAVE_NET_QUANTITY)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> saveNetQuantity(@Validated @RequestBody ProductNetQuantityDto productNetQuantityDto) {
        return this.productNetQuantityService.saveNetQuantity(productNetQuantityDto);
    }

    @GetMapping(SellerUrlMappings.DELETE_NET_QUANTITY)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> deleteNetQuantity(@PathVariable long netQuantityId ) {
        return this.productNetQuantityService.deleteNetQuantity(netQuantityId);
    }


    @GetMapping(SellerUrlMappings.GET_NET_QUANTITY_ID)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> getNetQuantityById(@PathVariable long netQuantityId ) {
        return this.productNetQuantityService.getNetQuantityById(netQuantityId);
    }

    @PostMapping(SellerUrlMappings.UPDATE_NET_QUANTITY)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> updateNetQuantity(@Validated @RequestBody ProductNetQuantityDto productNetQuantityDto) {
        return this.productNetQuantityService.updateNetQuantity(productNetQuantityDto);
    }

    @PostMapping(SellerUrlMappings.GET_NET_QUANTITY)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> getNetQuantity(@RequestParam Integer page , @RequestParam  Integer size) {
        return this.productNetQuantityService.getNetQuantity(page,size);
    }

}
