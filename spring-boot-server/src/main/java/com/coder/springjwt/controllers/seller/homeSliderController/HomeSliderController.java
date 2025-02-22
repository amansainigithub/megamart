package com.coder.springjwt.controllers.seller.homeSliderController;

import com.coder.springjwt.constants.sellerConstants.sellerUrlMappings.SellerUrlMappings;
import com.coder.springjwt.dtos.sellerDtos.homeSliderDtos.HomeSliderDto;
import com.coder.springjwt.services.homeSliderService.HomeSliderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(SellerUrlMappings.HOME_SLIDER_CONTROLLER)
public class HomeSliderController {

    @Autowired
    private HomeSliderService homeSliderService;


    @PostMapping(SellerUrlMappings.SAVE_HOME_SLIDER)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> saveHomeSlider(@Validated @RequestBody HomeSliderDto homeSliderDto) {
        return this.homeSliderService.saveHomeSlider(homeSliderDto);
    }


    @PostMapping(SellerUrlMappings.UPDATE_HOME_SLIDER_FILE)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> updateHomeSliderFile(@RequestParam(value = "file") MultipartFile file , @PathVariable Long homeSliderId)
    {
        return this.homeSliderService.updateHomeSliderFile(file,homeSliderId);
    }

    @GetMapping(SellerUrlMappings.DELETE_HOME_SLIDER)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> deleteHomeSlider( @PathVariable Long homeSliderId) {
        return this.homeSliderService.deleteHomeSlider(homeSliderId);
    }

    @GetMapping(SellerUrlMappings.GET_HOME_SLIDER_BY_ID)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> getHomeSliderById(@PathVariable long homeSliderId ) {
        return this.homeSliderService.getHomeSliderById(homeSliderId);
    }


    @GetMapping(SellerUrlMappings.GET_HOME_SLIDER_LIST)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> getHomeSliderList() {
        return this.homeSliderService.getHomeSliderList();
    }


    @PostMapping(SellerUrlMappings.UPDATE_HOME_SLIDER)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> updateHomeSlider(@Validated @RequestBody HomeSliderDto homeSliderDto ) {
        return this.homeSliderService.updateHomeSlider(homeSliderDto);
    }


}
