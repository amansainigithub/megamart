package com.coder.springjwt.controllers.seller.categoryController;

import com.coder.springjwt.constants.sellerConstants.sellerUrlMappings.SellerUrlMappings;
import com.coder.springjwt.dtos.sellerDtos.categoriesDtos.parentDtos.ParentCategoryDto;
import com.coder.springjwt.services.sellerServices.categories.ParentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping(SellerUrlMappings.PARENT_CONTROLLER)
public class ParentCategoryController {
    @Autowired
    private ParentCategoryService parentCategoryimple;

    @PostMapping(SellerUrlMappings.CREATE_PARENT_CATEGORY)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> createParentCategory(@Validated @RequestBody ParentCategoryDto parentCategoryDto) {
        return this.parentCategoryimple.saveParentCategory(parentCategoryDto);
    }


    @GetMapping(SellerUrlMappings.GET_PARENT_CATEGORY_LIST1)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> getParentCategoryList() {
        return this.parentCategoryimple.getParentCategoryList();
    }

    @GetMapping(SellerUrlMappings.DELETE_CATEGORY_BY_ID)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> deleteCategoryById(@PathVariable long categoryId ) {
        return this.parentCategoryimple.deleteCategoryById(categoryId);
    }

    @GetMapping(SellerUrlMappings.GET_PARENT_CATEGORY_BY_ID)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> getParentCategoryById(@PathVariable long categoryId ) {
        return this.parentCategoryimple.getParentCategoryById(categoryId);
    }

    @PostMapping(SellerUrlMappings.UPDATE_PARENT_CATEGORY)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> updateParentCategory(@Validated @RequestBody ParentCategoryDto parentCategoryDto ) {
        return this.parentCategoryimple.updateParentCategory(parentCategoryDto);
    }


    @PostMapping(SellerUrlMappings.UPDATE_PARENT_CATEGORY_FILE)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> updateCategoryFile(@RequestParam(value = "file") MultipartFile file , @PathVariable Long parentCategoryId)
    {
        return parentCategoryimple.updateParentCategoryFile(file,parentCategoryId);
    }




}
