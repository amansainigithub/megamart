package com.coder.springjwt.controllers.admin.catalogMetaData;

import com.coder.springjwt.constants.adminConstants.adminUrlMappings.AdminUrlMappings;
import com.coder.springjwt.dtos.adminDtos.catalogDtos.CatalogMaterialDto;
import com.coder.springjwt.services.adminServices.catalogMaterialService.CatalogMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(AdminUrlMappings.CATALOG_MATERIAL_CONTROLLER)
public class CatalogMaterialController {

    @Autowired
    private CatalogMaterialService catalogMaterialService;

    @PostMapping(AdminUrlMappings.SAVE_CATALOG_MATERIAL)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> saveCatalogMaterial(@Validated @RequestBody CatalogMaterialDto catalogMaterialDto) {
        return this.catalogMaterialService.saveCatalogMaterial(catalogMaterialDto);
    }


    @GetMapping(AdminUrlMappings.DELETE_CATALOG_MATERIAL)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteCatalogMaterial(@PathVariable long materialId ) {
        return this.catalogMaterialService.deleteCatalogMaterial(materialId);
    }

    @GetMapping(AdminUrlMappings.GET_CATALOG_MATERIAL_BY_ID)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getCatalogMaterialById(@PathVariable long materialId ) {
        return this.catalogMaterialService.getCatalogMaterialById(materialId);
    }

    @PostMapping(AdminUrlMappings.UPDATE_CATALOG_MATERIAL)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateCatalogMaterial(@Validated @RequestBody CatalogMaterialDto catalogMaterialDto ) {
        return this.catalogMaterialService.updateCatalogMaterial(catalogMaterialDto);
    }

    @PostMapping(AdminUrlMappings.GET_CATALOG_MATERIAL)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getCatalogMaterial(@RequestParam Integer page , @RequestParam  Integer size) {
        return this.catalogMaterialService.getCatalogMaterial(page,size);
    }
}
