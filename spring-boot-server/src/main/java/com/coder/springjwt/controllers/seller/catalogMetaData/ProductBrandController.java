package com.coder.springjwt.controllers.seller.catalogMetaData;

import com.coder.springjwt.constants.sellerConstants.sellerUrlMappings.SellerUrlMappings;
import com.coder.springjwt.dtos.sellerDtos.productMetaDtos.ProductBrandDto;
import com.coder.springjwt.exception.services.sellerServices.catalogBrandService.ProductBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(SellerUrlMappings.PRODUCT_BRAND_CONTROLLER)
public class ProductBrandController {

    @Autowired
    private ProductBrandService productBrandService;

    @PostMapping(SellerUrlMappings.SAVE_BRAND)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> saveBrand(@Validated @RequestBody ProductBrandDto productBrandDto) {
        return this.productBrandService.saveBrand(productBrandDto);
    }

    @GetMapping(SellerUrlMappings.DELETE_BRAND)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> deleteBrand(@PathVariable long brandId ) {
        return this.productBrandService.deleteBrand(brandId);
    }

    @GetMapping(SellerUrlMappings.GET_BRAND_BY_ID)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> getBrandById(@PathVariable long brandId ) {
        return this.productBrandService.getBrandById(brandId);
    }

    @PostMapping(SellerUrlMappings.UPDATE_BRAND)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> updateBrand(@Validated @RequestBody ProductBrandDto productBrandDto) {
        return this.productBrandService.updateBrand(productBrandDto);
    }

    @PostMapping(SellerUrlMappings.GET_BRAND)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> getBrand(@RequestParam Integer page , @RequestParam  Integer size) {
        return this.productBrandService.getBrand(page,size);
    }


}
