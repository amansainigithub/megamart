package com.coder.springjwt.services.sellerServices.sellerStoreService;

import com.coder.springjwt.payload.sellerPayloads.sellerPayload.SellerCatalogPayload;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
public interface SellerCatalogService {
    ResponseEntity<?> getSellerCatalog(Long catalogId);


    ResponseEntity<?> saveCatalogFiles(MultipartFile file);

    ResponseEntity<?> getGstList(Long catalogId);

    ResponseEntity<?> getCatalogMasters();


    ResponseEntity<?> sellerSaveCatalogService(Long categoryId,
                                               SellerCatalogPayload sellerCatalogPayload,
                                               List<MultipartFile> files);

    ResponseEntity<?> getAllCatalogByUsernameService(int page , int size);

    ResponseEntity<?> getAllCatalogByQcProgressService(int page , int size);

    ResponseEntity<?> getAllCatalogByDraft(int page , int size);

    ResponseEntity<?> getAllCatalogByError(int page , int size);

    ResponseEntity<?> getAllCatalogByQcPass(int page , int size);
}
