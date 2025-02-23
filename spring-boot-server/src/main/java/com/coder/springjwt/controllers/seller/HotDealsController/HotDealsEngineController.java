package com.coder.springjwt.controllers.seller.HotDealsController;

import com.coder.springjwt.constants.sellerConstants.sellerUrlMappings.SellerUrlMappings;
import com.coder.springjwt.dtos.sellerDtos.hotDealsDtos.HotDealsEngineDto;
import com.coder.springjwt.services.hotDealsEngineService.HotDealsEngineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(SellerUrlMappings.HOT_DEALS_ENGINE_CONTROLLER)
public class HotDealsEngineController {

    @Autowired
    private HotDealsEngineService hotDealsEngineService;

    @PostMapping(SellerUrlMappings.SAVE_HOT_DEALS_ENGINE)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> saveHotDealsEngine(@Validated @RequestBody HotDealsEngineDto hotDealsEngineDto) {
        return this.hotDealsEngineService.saveHotDealsEngine(hotDealsEngineDto);
    }

    @GetMapping(SellerUrlMappings.GET_HOT_DEALS_ENGINE)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> getHotDealsEngine(@PathVariable Long engineId) {
        return this.hotDealsEngineService.getHotDealsEngine(engineId);
    }

    @GetMapping(SellerUrlMappings.GET_HOT_DEALS_ENGINES)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> getHotDealsEngines(@RequestParam Integer page , @RequestParam  Integer size) {
        return this.hotDealsEngineService.getHotDealsEngines(page,size);
    }

    @GetMapping(SellerUrlMappings.DELETE_HOT_DEALS_ENGINES)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> deleteHotDealsEngine( @PathVariable Long engineId) {
        return this.hotDealsEngineService.deleteHotDealsEngine(engineId);
    }

    @PostMapping(SellerUrlMappings.UPDATE_HOT_DEALS_ENGINES)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> updateHotDealsEngine(@Validated @RequestBody HotDealsEngineDto hotDealsEngineDto ) {
        return this.hotDealsEngineService.updateHotDealsEngine(hotDealsEngineDto);
    }


}
