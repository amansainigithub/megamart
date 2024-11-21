package com.coder.springjwt.controllers.admin.catalogMetaData;

import com.coder.springjwt.constants.adminConstants.adminUrlMappings.AdminUrlMappings;
import com.coder.springjwt.dtos.adminDtos.catalogDtos.CatalogBreathDto;
import com.coder.springjwt.services.adminServices.catalogBreathService.CatalogBreathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(AdminUrlMappings.CATALOG_BREATH_CONTROLLER)
public class CatalogBreathController {

    @Autowired
    private CatalogBreathService catalogBreathService;

    @PostMapping(AdminUrlMappings.SAVE_CATALOG_BREATH)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> saveCatalogBreath(@Validated @RequestBody CatalogBreathDto catalogBreathDto) {
        return this.catalogBreathService.saveCatalogBreath(catalogBreathDto);
    }

    @PostMapping(AdminUrlMappings.GET_CATALOG_BREATH)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getCatalogBreath(@RequestParam Integer page , @RequestParam  Integer size) {
        return this.catalogBreathService.getCatalogBreath(page,size);
    }
}
