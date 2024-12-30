package com.coder.springjwt.controllers.admin.catalog_CBI_Controller;

import com.coder.springjwt.constants.adminConstants.adminUrlMappings.AdminUrlMappings;
import com.coder.springjwt.constants.sellerConstants.sellerUrlMappings.SellerUrlMappings;
import com.coder.springjwt.payload.adminPayloads.catalogPaylods.CatalogPayloadInvestigation;
import com.coder.springjwt.services.adminServices.catalogCbiService.CatalogCbiService;
import com.coder.springjwt.services.sellerServices.sellerStoreService.SellerProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;


@RestController
@RequestMapping(AdminUrlMappings.CATALOG_CBI_CONTROLLER)
public class CatalogCbiController {

    @Autowired
    SellerProductService sellerProductService;

    @Autowired
    private CatalogCbiService catalogCbiService;

//    @GetMapping(AdminUrlMappings.GET_CATALOG_PROGRESS_LIST)
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<?> getCatalogInProgressList(@RequestParam Integer page ,
//                                                      @RequestParam  Integer size) {
//        //return this.catalogCbiService.getCatalogInProgressListService(page,size);
//    }

//    @GetMapping(SellerUrlMappings.GET_CATALOG_MASTERS)
//    @PreAuthorize("hasRole('ADMIN')"  )
//    public ResponseEntity<?> getCatalogMasters() {
//        return catalogCbiService.getCatalogMasters();
//    }
//

//    @PostMapping(AdminUrlMappings.CATALOG_INVESTIGATION)
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<?> catalogInvestigation(@PathVariable Long catalogId ,
//                                                  @RequestBody CatalogPayloadInvestigation catalogInvestigationPayload) {
//        return catalogCbiService.catalogInvestigationService(catalogId , catalogInvestigationPayload );
//    }

//    @GetMapping(AdminUrlMappings.SEARCH_CATALOGS_BY_DATES)
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<?> searchCatalogByDate(
//            @RequestParam int page,
//            @RequestParam int size,
//            @RequestParam @DateTimeFormat(pattern = "MM/dd/yyyy") LocalDate startDate,
//            @RequestParam @DateTimeFormat(pattern = "MM/dd/yyyy") LocalDate endDate) {
//
//        // Adjust endDate to include the entire day
//        LocalDate adjustedEndDate = endDate.plusDays(1);
//
//        return catalogCbiService.searchCatalogByDateService(page, size, startDate, adjustedEndDate);
//    }


}
