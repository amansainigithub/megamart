package com.coder.springjwt.services.adminServices.catalogBrandService;

import com.coder.springjwt.dtos.adminDtos.catalogDtos.CatalogBrandDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface CatalogBrandService {
    ResponseEntity<?> saveCatalogBrand(CatalogBrandDto catalogBrandDto);

    ResponseEntity<?> deleteBrand(long brandId);

    ResponseEntity<?> getCatalogBrandById(long brandId);

    ResponseEntity<?> updateCatalogBrand(CatalogBrandDto catalogBrandDto);

    ResponseEntity<?> getCatalogBrand(Integer page, Integer size);
}
