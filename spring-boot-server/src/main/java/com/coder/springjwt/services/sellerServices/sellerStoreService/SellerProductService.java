package com.coder.springjwt.services.sellerServices.sellerStoreService;

import com.coder.springjwt.formBuilderTools.formVariableKeys.ProductRootBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Component
public interface SellerProductService {

    ResponseEntity<?> getGstList(Long catalogId);

    ResponseEntity<?> getProductMasters();

    ResponseEntity<?> formBuilderFlying(String categoryId);

    ResponseEntity<?> saveSellerProduct(ProductRootBuilder productRootBuilder, Long bornCategoryId);

    ResponseEntity<?> uploadProductFiles(Map<String, MultipartFile> files , String productLockerNumber);

    ResponseEntity<?> getProductBYId(String productId);

    ResponseEntity<?> updateSellerProduct(ProductRootBuilder productRootBuilder, Long productId);


}
