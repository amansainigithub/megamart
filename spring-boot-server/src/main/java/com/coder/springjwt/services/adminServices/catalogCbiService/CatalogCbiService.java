package com.coder.springjwt.services.adminServices.catalogCbiService;

import com.coder.springjwt.payload.sellerPayloads.sellerPayload.SellerCatalogPayload;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
public interface CatalogCbiService  {
    ResponseEntity<?> getCatalogInProgressListService(int page , int size);

    ResponseEntity<?> catalogInvestigationService(Long catalogId, SellerCatalogPayload sellerCatalogPayload, List<MultipartFile> files);
}
