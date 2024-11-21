package com.coder.springjwt.controllers.admin.catalogMetaData;

import com.coder.springjwt.constants.adminConstants.adminUrlMappings.AdminUrlMappings;
import com.coder.springjwt.dtos.adminDtos.catalogDtos.CatalogSizeDto;
import com.coder.springjwt.services.adminServices.catalogSizeService.CatalogSizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(AdminUrlMappings.CATALOG_SIZE_CONTROLLER)
public class CatalogSizeController {


    @Autowired
    private CatalogSizeService catalogSizeService;

    @PostMapping(AdminUrlMappings.SAVE_CATALOG_SIZE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> saveCatalogSize(@Validated @RequestBody CatalogSizeDto catalogSizeDto) {
        return this.catalogSizeService.saveCatalogSize(catalogSizeDto);
    }


    @GetMapping(AdminUrlMappings.DELETE_CATALOG_SIZE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteCatalogSize(@PathVariable long sizeId ) {
        return this.catalogSizeService.deleteCatalogSize(sizeId);
    }

    @GetMapping(AdminUrlMappings.GET_CATALOG_SIZE_BY_ID)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getCatalogSizeById(@PathVariable long sizeId ) {
        return this.catalogSizeService.getCatalogSizeById(sizeId);
    }


    @PostMapping(AdminUrlMappings.UPDATE_CATALOG_SIZE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateCatalogSize(@Validated @RequestBody CatalogSizeDto catalogSizeDto ) {
        return this.catalogSizeService.updateCatalogSize(catalogSizeDto);
    }

    @PostMapping(AdminUrlMappings.GET_CATALOG_SIZE)
    public ResponseEntity<?> getCatalogSize(@RequestParam Integer page , @RequestParam  Integer size) {
        return this.catalogSizeService.getCatalogSize(page,size);
    }

}
