package com.coder.springjwt.controllers.seller.catalogMetaData;

import com.coder.springjwt.constants.sellerConstants.sellerUrlMappings.SellerUrlMappings;
import com.coder.springjwt.dtos.sellerDtos.productMetaDtos.ProductHeightDto;
import com.coder.springjwt.services.sellerServices.catalogHeightService.CatalogHeightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(SellerUrlMappings.PRODUCT_HEIGHT_CONTROLLER)
public class productHeightController {

    @Autowired
    private CatalogHeightService catalogHeightService;

    @PostMapping(SellerUrlMappings.SAVE_HEIGHT)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> saveHeight(@Validated @RequestBody ProductHeightDto productHeightDto) {
        return this.catalogHeightService.saveHeight(productHeightDto);
    }

    @PostMapping(SellerUrlMappings.GET_HEIGHT)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> getHeight(@RequestParam Integer page , @RequestParam  Integer size) {
        return this.catalogHeightService.getHeight(page,size);
    }
}
