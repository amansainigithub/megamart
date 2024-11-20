package com.coder.springjwt.controllers.admin.catalog_CBI_Controller;

import com.coder.springjwt.constants.adminConstants.adminUrlMappings.AdminUrlMappings;
import com.coder.springjwt.constants.sellerConstants.sellerUrlMappings.SellerUrlMappings;
import com.coder.springjwt.payload.sellerPayloads.sellerPayload.SellerCatalogPayload;
import com.coder.springjwt.services.adminServices.catalogCbiService.CatalogCbiService;
import com.coder.springjwt.services.sellerServices.sellerStoreService.SellerCatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(
        origins = {"http://localhost:8080","http://localhost:4200"},
        maxAge = 3600,allowCredentials="true")
@RestController
@RequestMapping(AdminUrlMappings.CATALOG_CBI_CONTROLLER)
public class CatalogCbiController {

    @Autowired
    SellerCatalogService sellerCatalogService;


    @Autowired
    private CatalogCbiService catalogCbiService;

    @GetMapping(AdminUrlMappings.GET_CATALOG_PROGRESS_LIST)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getCatalogInProgressList(@RequestParam Integer page , @RequestParam  Integer size) {
        return this.catalogCbiService.getCatalogInProgressListService(page,size);
    }

    @GetMapping(SellerUrlMappings.GET_CATALOG_MASTERS)
    @PreAuthorize("hasRole('ADMIN')"  )
    public ResponseEntity<?> getCatalogMasters() {
        return sellerCatalogService.getCatalogMasters();
    }


    @PostMapping("/catalogInvestigation/{catalogId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> catalogInvestigation(@PathVariable Long catalogId ,
                                              @RequestPart("catalogData") SellerCatalogPayload sellerCatalogPayload,
                                              @RequestParam(value = "files" , required = false) List<MultipartFile> files) {
        System.out.println(sellerCatalogPayload);
        return catalogCbiService.catalogInvestigationService(catalogId , sellerCatalogPayload , files );

    }


}
