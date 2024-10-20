package com.coder.springjwt.services.sellerServices.sellerStoreService;

import com.coder.springjwt.payload.sellerPayloads.sellerPayload.SellerCatalogPayload;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface SellerCatalogService {

    ResponseEntity<?> sellerSaveCatalogService(SellerCatalogPayload sellerCatalogPayload);

    ResponseEntity<?> getSellerCatalog(Long catalogId);
}
