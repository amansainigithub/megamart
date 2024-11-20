package com.coder.springjwt.controllers.admin.catalogMetaData;

import com.coder.springjwt.constants.adminConstants.adminUrlMappings.AdminUrlMappings;
import com.coder.springjwt.dtos.adminDtos.catalogDtos.CatalogLengthDto;
import com.coder.springjwt.dtos.adminDtos.catalogDtos.CatalogWeightDto;
import com.coder.springjwt.services.adminServices.catalogLengthService.CatalogLengthService;
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
@RequestMapping(AdminUrlMappings.CATALOG_LENGTH_CONTROLLER)
public class CatalogLengthController {

    @Autowired
    private CatalogLengthService catalogLengthService;

    @PostMapping(AdminUrlMappings.SAVE_CATALOG_LENGTH)
    @PreAuthorize("hasRole('ADMIN') OR hasRole('SELLER')")
    public ResponseEntity<?> saveCatalogLength(@Validated @RequestBody CatalogLengthDto catalogLengthDto) {
        return this.catalogLengthService.saveCatalogLength(catalogLengthDto);
    }

    @PostMapping(AdminUrlMappings.GET_CATALOG_LENGTH)
    public ResponseEntity<?> getCatalogLength(@RequestParam Integer page , @RequestParam  Integer size) {
        return this.catalogLengthService.getCatalogLength(page,size);
    }
}
