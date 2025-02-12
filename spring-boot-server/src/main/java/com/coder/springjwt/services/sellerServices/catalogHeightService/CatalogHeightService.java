package com.coder.springjwt.services.sellerServices.catalogHeightService;

import com.coder.springjwt.dtos.sellerDtos.productMetaDtos.ProductHeightDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface CatalogHeightService {
    ResponseEntity<?> saveHeight(ProductHeightDto saveCatalogHeight);

    ResponseEntity<?> getHeight(Integer page, Integer size);
}
