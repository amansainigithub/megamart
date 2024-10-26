package com.coder.springjwt.services.adminServices.catalogSizeService;

import com.coder.springjwt.dtos.adminDtos.catalogDtos.CatalogSizeDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface CatalogSizeService {

    ResponseEntity<?> saveCatalogSize(CatalogSizeDto catalogSizeDto);

    ResponseEntity<?> deleteCatalogSize(long sizeId);

    ResponseEntity<?> getCatalogSizeById(long sizeId);

    ResponseEntity<?> updateCatalogSize(CatalogSizeDto catalogSizeDto);

    ResponseEntity<?> getCatalogSize(Integer page, Integer size);
}
