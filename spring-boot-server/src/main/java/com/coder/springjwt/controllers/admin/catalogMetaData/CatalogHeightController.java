package com.coder.springjwt.controllers.admin.catalogMetaData;

import com.coder.springjwt.constants.adminConstants.adminUrlMappings.AdminUrlMappings;
import com.coder.springjwt.dtos.adminDtos.catalogDtos.CatalogHeightDto;
import com.coder.springjwt.services.adminServices.catalogHeightService.CatalogHeightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(
        origins = {"http://localhost:8080","http://localhost:4200"},
        maxAge = 3600,allowCredentials="true")
@RestController
@RequestMapping(AdminUrlMappings.CATALOG_HEIGHT_CONTROLLER)
public class CatalogHeightController {

    @Autowired
    private CatalogHeightService catalogHeightService;

    @PostMapping(AdminUrlMappings.SAVE_CATALOG_HEIGHT)
    @PreAuthorize("hasRole('ADMIN') OR hasRole('SELLER')")
    public ResponseEntity<?> saveCatalogHeight(@Validated @RequestBody CatalogHeightDto catalogHeightDto) {
        return this.catalogHeightService.saveCatalogHeight(catalogHeightDto);
    }

    @PostMapping(AdminUrlMappings.GET_CATALOG_HEIGHT)
    @PreAuthorize("hasRole('ADMIN') OR hasRole('SELLER')")
    public ResponseEntity<?> getCatalogHeight(@RequestParam Integer page , @RequestParam  Integer size) {
        return this.catalogHeightService.getCatalogHeight(page,size);
    }
}
