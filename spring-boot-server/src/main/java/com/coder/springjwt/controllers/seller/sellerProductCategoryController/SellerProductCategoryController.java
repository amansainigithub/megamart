package com.coder.springjwt.controllers.seller.sellerProductCategoryController;


import com.coder.springjwt.constants.sellerConstants.sellerUrlMappings.SellerUrlMappings;
import com.coder.springjwt.services.sellerServices.sellerProductCategoryService.SellerProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(SellerUrlMappings.SELLER_PRODUCT_CATEGORY_CONTROLLER)
public class SellerProductCategoryController {

    @Autowired
    private SellerProductCategoryService sellerProductCategoryService;

    @GetMapping(SellerUrlMappings.GET_PARENT_CATEGORY_LIST)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> getParentCategory() {
        return sellerProductCategoryService.getParentCategory();
    }

    @PostMapping(SellerUrlMappings.GET_CHILD_CATEGORY_LIST_BY_ID)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> getChildCategoryListById(@PathVariable Long parentId) {
        return sellerProductCategoryService.getChildCategoryListById(parentId);
    }

    @PostMapping(SellerUrlMappings.GET_BABY_CATEGORY_LIST_BY_CHILD_ID)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> getBabyCategoryListChildById(@PathVariable Long childId) {
        return sellerProductCategoryService.getBabyCategoryListChildById(childId);
    }

    @PostMapping(SellerUrlMappings.GET_BORN_CATEGORY_LIST_BY_BABY_ID)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> getBornCategoryListByBabyId(@PathVariable Long babyId) {
        return sellerProductCategoryService.getBornCategoryListByBabyId(babyId);
    }



}
