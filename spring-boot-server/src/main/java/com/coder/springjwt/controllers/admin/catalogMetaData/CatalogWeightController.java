package com.coder.springjwt.controllers.admin.catalogMetaData;

import com.coder.springjwt.constants.adminConstants.adminUrlMappings.AdminUrlMappings;
import com.coder.springjwt.dtos.adminDtos.catalogDtos.CatalogWeightDto;
import com.coder.springjwt.services.adminServices.catalogWeightService.CatalogWeightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(
        origins = {"http://localhost:8080","http://localhost:4200"},
        maxAge = 3600,allowCredentials="true")
@RestController
@RequestMapping(AdminUrlMappings.CATALOG_WEIGHT_CONTROLLER)
public class CatalogWeightController {

    @Autowired
    private CatalogWeightService catalogWeightService;

    @PostMapping(AdminUrlMappings.SAVE_CATALOG_WEIGHT)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> saveCatalogWeight(@Validated @RequestBody CatalogWeightDto catalogWeightDto) {
        return this.catalogWeightService.saveCatalogWeight(catalogWeightDto);
    }

    @PostMapping(AdminUrlMappings.GET_CATALOG_WEIGHT)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getCatalogWeight(@RequestParam Integer page , @RequestParam  Integer size) {
        return this.catalogWeightService.getCatalogWeight(page,size);
    }
}
