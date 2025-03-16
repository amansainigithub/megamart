package com.coder.springjwt.controllers.seller.HotDealsController;

import com.coder.springjwt.constants.sellerConstants.sellerUrlMappings.SellerUrlMappings;
import com.coder.springjwt.dtos.sellerDtos.hotDealsDtos.HotDealsDto;
import com.coder.springjwt.services.sellerServices.hotDealsEngineService.HotDealsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(SellerUrlMappings.HOT_DEALS_CONTROLLER)
public class HotDealsController {

    @Autowired
    private HotDealsService hotDealsService;

    @PostMapping(SellerUrlMappings.SAVE_HOT_DEALS)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> saveHotDeals(@Validated @RequestBody HotDealsDto hotDealsDto) {
            return this.hotDealsService.saveHotDealsService(hotDealsDto);
    }

    @GetMapping(SellerUrlMappings.DELETE_HOT_DEAL)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> deleteHotDeal(@PathVariable Long id) {
        return this.hotDealsService.deleteHotDeal(id);
    }

    @GetMapping(SellerUrlMappings.DELETE_ALL_HOT_DEALS)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> deleteAllHotDeals() {
        return this.hotDealsService.deleteAllHotDeals();
    }

    @GetMapping(SellerUrlMappings.GET_HOT_DEALS)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> getHotDeals() {
        return this.hotDealsService.getHotDeals();
    }

    @GetMapping(SellerUrlMappings.GET_HOT_DEAL)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> getHotDeal(@PathVariable Long id) {
        return this.hotDealsService.getHotDeal(id);
    }

    @PostMapping(SellerUrlMappings.UPDATE_HOT_DEALS)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> updateHotDeals(@Validated @RequestBody HotDealsDto hotDealsDto ) {
        return this.hotDealsService.updateHotDeals(hotDealsDto);
    }

    @PostMapping(SellerUrlMappings.UPDATE_HOT_DEAL_FILE)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> updateHotDealFile(@RequestParam(value = "file") MultipartFile file , @PathVariable Long dealId)
    {
        return this.hotDealsService.updateHotDealFile(file,dealId);
    }
}
