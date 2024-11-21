package com.coder.springjwt.controllers.admin.catalogMetaData;

import com.coder.springjwt.constants.adminConstants.adminUrlMappings.AdminUrlMappings;
import com.coder.springjwt.dtos.adminDtos.catalogDtos.CatalogNetQuantityDto;
import com.coder.springjwt.services.adminServices.catalogNetQuantityService.CatalogNetQuantityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(AdminUrlMappings.CATALOG_NET_QUANTITY_CONTROLLER)
public class CatalogNetQuantityController {

    @Autowired
    private CatalogNetQuantityService catalogNetQuantityService;

    @PostMapping(AdminUrlMappings.SAVE_CATALOG_NET_QUANTITY)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> saveCatalogNetQuantity(@Validated @RequestBody CatalogNetQuantityDto catalogNetQuantityDto) {
        return this.catalogNetQuantityService.saveCatalogNetQuantity(catalogNetQuantityDto);
    }

    @GetMapping(AdminUrlMappings.DELETE_NET_QUANTITY)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteNetQuantity(@PathVariable long netQuantityId ) {
        return this.catalogNetQuantityService.deleteNetQuantity(netQuantityId);
    }


    @GetMapping(AdminUrlMappings.GET_CATALOG_NET_QUANTITY_ID)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getCatalogNetQuantityById(@PathVariable long netQuantityId ) {
        return this.catalogNetQuantityService.getNetQuantityById(netQuantityId);
    }

    @PostMapping(AdminUrlMappings.UPDATE_NET_QUANTITY)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateNetQuantity(@Validated @RequestBody CatalogNetQuantityDto catalogNetQuantityDto ) {
        return this.catalogNetQuantityService.updateNetQuantity(catalogNetQuantityDto);
    }

    @PostMapping(AdminUrlMappings.GET_NET_QUANTITY)
    public ResponseEntity<?> getNetQuantity(@RequestParam Integer page , @RequestParam  Integer size) {
        return this.catalogNetQuantityService.getNetQuantity(page,size);
    }

}
