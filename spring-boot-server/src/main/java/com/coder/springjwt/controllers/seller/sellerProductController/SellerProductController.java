package com.coder.springjwt.controllers.seller.sellerProductController;

import com.coder.springjwt.constants.sellerConstants.sellerUrlMappings.SellerUrlMappings;
import com.coder.springjwt.formBuilderTools.formVariableKeys.ProductRootBuilder;
import com.coder.springjwt.services.sellerServices.sellerStoreService.SellerProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping(SellerUrlMappings.SELLER_PRODUCT_CONTROLLER)
public class SellerProductController {

    @Autowired
    SellerProductService sellerProductService;

    @GetMapping(SellerUrlMappings.GET_GST_LIST)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> getGstList(@PathVariable Long catalogId) {
        return sellerProductService.getGstList(catalogId);
    }

    @GetMapping(SellerUrlMappings.GET_PRODUCT_MASTERS)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> getProductMasters() {
        return sellerProductService.getProductMasters();
    }

    @GetMapping(SellerUrlMappings.FORM_BUILDER_FLYING)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> formBuilderFlying(@PathVariable String categoryId){
            return this.sellerProductService.formBuilderFlying(categoryId);
    }

    @PostMapping(SellerUrlMappings.SAVE_SELLER_PRODUCT)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> saveSellerProduct(@RequestBody ProductRootBuilder productRootBuilder , @PathVariable Long bornCategoryId) {
            return sellerProductService.saveSellerProduct(productRootBuilder , bornCategoryId);
    }

    @PostMapping(SellerUrlMappings.UPLOAD_PRODUCT_FILES)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> uploadProductFiles(@RequestParam Map<String, MultipartFile> files , @PathVariable String productLockerNumber) {
        return sellerProductService.uploadProductFiles(files, productLockerNumber);
    }

    @PostMapping(SellerUrlMappings.UPDATE_SELLER_PRODUCT)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> updateSellerProduct(@RequestBody ProductRootBuilder productRootBuilder , @PathVariable Long productId) {
        try {
            Thread.sleep(3000);
        return sellerProductService.updateSellerProduct(productRootBuilder , productId);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/getProductById/{productId}")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> getProductById(@PathVariable String productId) {
        return sellerProductService.getProductBYId(productId);
    }

}
