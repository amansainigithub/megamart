package com.coder.springjwt.controllers.seller.categoryController;

import com.coder.springjwt.constants.sellerConstants.sellerUrlMappings.SellerUrlMappings;
import com.coder.springjwt.dtos.sellerDtos.categoriesDtos.babyDto.BabyCategoryDto;
import com.coder.springjwt.services.sellerServices.categories.BabyCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(SellerUrlMappings.BABY_CONTROLLER)
public class BabyCategoryController {

    @Autowired
    private BabyCategoryService babyCategoryService;

    @PostMapping(SellerUrlMappings.SAVE_BABY_CATEGORY)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> saveBabyCategory(@Validated @RequestBody BabyCategoryDto babyCategoryDto) {
        return this.babyCategoryService.saveBabyCategory(babyCategoryDto);
    }


    @GetMapping(SellerUrlMappings.GET_BABY_CATEGORY_LIST)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> getBabyCategoryList() {
        return this.babyCategoryService.getBabyCategoryList();
    }


    @GetMapping(SellerUrlMappings.DELETE_BABY_CATEGORY_BY_ID)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> deleteBabyCategoryById(@PathVariable long categoryId ) {
        return this.babyCategoryService.deleteBabyCategoryById(categoryId);
    }


    @PostMapping(SellerUrlMappings.UPDATE_BABY_CATEGORY)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> updateBabyCategory(@Validated @RequestBody BabyCategoryDto babyCategoryDto ) {
        return this.babyCategoryService.updateBabyCategory(babyCategoryDto);
    }



    @GetMapping(SellerUrlMappings.GET_BABY_CATEGORY_BY_ID)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> getBabyCategoryById(@PathVariable long categoryId ) {
        return this.babyCategoryService.getBabyCategoryById(categoryId);
    }

    @PostMapping(SellerUrlMappings.UPDATE_BABY_CATEGORY_FILE)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> updateBabyCategoryFile(@RequestParam(value = "file") MultipartFile file , @PathVariable Long babyCategoryId)
    {
        return babyCategoryService.updateBabyCategoryFile(file,babyCategoryId);
    }


}
