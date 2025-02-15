package com.coder.springjwt.controllers.seller.categoryController;

import com.coder.springjwt.constants.sellerConstants.sellerUrlMappings.SellerUrlMappings;
import com.coder.springjwt.dtos.sellerDtos.categoriesDtos.childDtos.ChildCategoryDto;
import com.coder.springjwt.services.sellerServices.categories.ChildCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(SellerUrlMappings.CHILD_CONTROLLER)
public class ChildCategoryController {


    @Autowired
    private ChildCategoryService childCategoryimple;

    @PostMapping(SellerUrlMappings.SAVE_CHILD_CATEGORY)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> saveChildCategory(@Validated @RequestBody ChildCategoryDto childCategoryDto) {
        return this.childCategoryimple.saveChildCategory(childCategoryDto);
    }


    @GetMapping(SellerUrlMappings.GET_CHILD_CATEGORY_LIST)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> getChildCategoryList() {
        return this.childCategoryimple.getChildCategoryList();
    }

    @GetMapping(SellerUrlMappings.DELETE_CHILD_CATEGORY_BY_ID)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> deleteChildCategoryById(@PathVariable long categoryId ) {
        return this.childCategoryimple.deleteChildCategoryById(categoryId);
    }

    @GetMapping(SellerUrlMappings.GET_CHILD_CATEGORY_BY_ID)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> getChildCategoryById(@PathVariable long categoryId ) {
        return this.childCategoryimple.getChildCategoryById(categoryId);
    }

    @PostMapping(SellerUrlMappings.UPDATE_CHILD_CATEGORY)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> updateChildCategory(@Validated @RequestBody ChildCategoryDto childCategoryDto ) {
        return this.childCategoryimple.updateChildCategory(childCategoryDto);
    }

    @PostMapping(SellerUrlMappings.UPDATE_CHILD_CATEGORY_FILE)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> updateChildCategoryFile(@RequestParam(value = "file") MultipartFile file , @PathVariable Long childCategoryId)
    {
        return childCategoryimple.updateParentCategoryFile(file,childCategoryId);
    }






}
