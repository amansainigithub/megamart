package com.coder.springjwt.controllers.admin.catalogMetaData;

import com.coder.springjwt.constants.adminConstants.adminUrlMappings.AdminUrlMappings;
import com.coder.springjwt.dtos.adminDtos.catalogDtos.CatalogTypeDto;
import com.coder.springjwt.services.adminServices.catalogTypeService.CatalogTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(AdminUrlMappings.CATALOG_TYPE_CONTROLLER)
public class CatalogTypeController {

    @Autowired
    private CatalogTypeService catalogTypeService;

    @PostMapping(AdminUrlMappings.SAVE_CATALOG_TYPE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> saveCatalogType(@Validated @RequestBody CatalogTypeDto catalogTypeDto) {
        return this.catalogTypeService.saveCatalogType(catalogTypeDto);
    }

    @GetMapping(AdminUrlMappings.DELETE_CATALOG_TYPE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteCatalogType(@PathVariable long typeId ) {
        return this.catalogTypeService.deleteCatalogType(typeId);
    }

    @GetMapping(AdminUrlMappings.GET_CATALOG_TYPE_BY_ID)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getCatalogTypeById(@PathVariable long typeId ) {
        return this.catalogTypeService.getCatalogTypeById(typeId);
    }

    @PostMapping(AdminUrlMappings.UPDATE_CATALOG_TYPE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateCatalogType(@Validated @RequestBody CatalogTypeDto catalogTypeDto ) {
        return this.catalogTypeService.updateCatalogType(catalogTypeDto);
    }


    @PostMapping(AdminUrlMappings.GET_CATALOG_TYPE)
    public ResponseEntity<?> getCatalogType(@RequestParam Integer page , @RequestParam  Integer size) {
        return this.catalogTypeService.getCatalogType(page,size);
    }


}
