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
                                               String index,
                                               String catalogData,
                                               List<MultipartFile> files);
}
