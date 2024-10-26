package com.coder.springjwt.controllers.admin.catalogMetaData;

import com.coder.springjwt.constants.adminConstants.adminUrlMappings.AdminUrlMappings;
import com.coder.springjwt.dtos.adminDtos.catalogDtos.CatalogBrandDto;
import com.coder.springjwt.services.adminServices.catalogBrandService.CatalogBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(
        origins = {"http://localhost:8080","http://localhost:4200"},
        maxAge = 3600,allowCredentials="true")
@RestController
@RequestMapping(AdminUrlMappings.CATALOG_BRAND_CONTROLLER)
public class CatalogBrandController {

    @Autowired
    private CatalogBrandService catalogBrandService;

    @PostMapping(AdminUrlMappings.SAVE_CATALOG_BRANDE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> saveCatalogBrand(@Validated @RequestBody CatalogBrandDto catalogBrandDto) {
        return this.catalogBrandService.saveCatalogBrand(catalogBrandDto);
    }

    @GetMapping(AdminUrlMappings.DELETE_BRAND)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteBrand(@PathVariable long brandId ) {
        return this.catalogBrandService.deleteBrand(brandId);
    }

    @GetMapping(AdminUrlMappings.GET_CATALOG_BRAND_ID)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getCatalogBrandById(@PathVariable long brandId ) {
        return this.catalogBrandService.getCatalogBrandById(brandId);
    }

    @PostMapping(AdminUrlMappings.UPDATE_CATALOG_BRAND)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateCatalogBrand(@Validated @RequestBody CatalogBrandDto catalogBrandDto ) {
        return this.catalogBrandService.updateCatalogBrand(catalogBrandDto);
    }

    @PostMapping(AdminUrlMappings.GET_CATALOG_BRAND)
    public ResponseEntity<?> getCatalogBrand(@RequestParam Integer page , @RequestParam  Integer size) {
        return this.catalogBrandService.getCatalogBrand(page,size);
    }


}
