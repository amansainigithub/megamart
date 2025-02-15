package com.coder.springjwt.controllers.seller.catalogMetaData;

import com.coder.springjwt.constants.sellerConstants.sellerUrlMappings.SellerUrlMappings;
import com.coder.springjwt.dtos.sellerDtos.productMetaDtos.ProductMaterialDto;
import com.coder.springjwt.exception.services.sellerServices.catalogMaterialService.ProductMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(SellerUrlMappings.PRODUCT_MATERIAL_CONTROLLER)
public class ProductMaterialController {

    @Autowired
    private ProductMaterialService productMaterialService;

    @PostMapping(SellerUrlMappings.SAVE_MATERIAL)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> saveMaterial(@Validated @RequestBody ProductMaterialDto productMaterialDto) {
        return this.productMaterialService.saveMaterial(productMaterialDto);
    }


    @GetMapping(SellerUrlMappings.DELETE_MATERIAL)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> deleteMaterial(@PathVariable long materialId ) {
        return this.productMaterialService.deleteCatalogMaterial(materialId);
    }

    @GetMapping(SellerUrlMappings.GET_MATERIAL_BY_ID)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> getMaterialById(@PathVariable long materialId ) {
        return this.productMaterialService.getCatalogMaterialById(materialId);
    }

    @PostMapping(SellerUrlMappings.UPDATE_MATERIAL)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> updateMaterial(@Validated @RequestBody ProductMaterialDto productMaterialDto) {
        return this.productMaterialService.updateCatalogMaterial(productMaterialDto);
    }

    @PostMapping(SellerUrlMappings.GET_MATERIAL)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> getMaterial(@RequestParam Integer page , @RequestParam  Integer size) {
        return this.productMaterialService.getCatalogMaterial(page,size);
    }
}
