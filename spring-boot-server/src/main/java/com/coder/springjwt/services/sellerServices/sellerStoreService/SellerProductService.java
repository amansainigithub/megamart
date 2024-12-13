package com.coder.springjwt.services.sellerServices.sellerStoreService;

import com.coder.springjwt.formBuilderTools.formVariableKeys.ProductRootData;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Component
public interface SellerProductService {
    ResponseEntity<?> getSellerCatalog(Long catalogId);

    ResponseEntity<?> saveCatalogFiles(MultipartFile file);

    ResponseEntity<?> getGstList(Long catalogId);

    ResponseEntity<?> getProductMasters();

    ResponseEntity<?> getAllCatalogByUsernameService(int page , int size);

    ResponseEntity<?> getAllCatalogByQcProgressService(int page , int size);

    ResponseEntity<?> getAllCatalogByDraft(int page , int size);

    ResponseEntity<?> getAllCatalogByError(int page , int size);

    ResponseEntity<?> getAllCatalogByQcPass(int page , int size);


    ResponseEntity<?> productDataFormBuilder(String categoryId);

    ResponseEntity<?> saveSellerProduct(ProductRootData productRootData);

    ResponseEntity<?> uploadProductFiles(Map<String, MultipartFile> files , String productLockerNumber);
}
