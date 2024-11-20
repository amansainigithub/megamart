package com.coder.springjwt.services.adminServices.catalogHeightService;

import com.coder.springjwt.dtos.adminDtos.catalogDtos.CatalogHeightDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface CatalogHeightService {
    ResponseEntity<?> saveCatalogHeight(CatalogHeightDto saveCatalogHeight);

    ResponseEntity<?> getCatalogHeight(Integer page, Integer size);
}
