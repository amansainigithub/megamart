package com.coder.springjwt.services.adminServices.catalogMaterialService;

import com.coder.springjwt.dtos.adminDtos.catalogDtos.CatalogMaterialDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface CatalogMaterialService {
    ResponseEntity<?> saveCatalogMaterial(CatalogMaterialDto catalogMaterialDto);

    ResponseEntity<?> deleteCatalogMaterial(long materialId);

    ResponseEntity<?> getCatalogMaterialById(long materialId);

    ResponseEntity<?> updateCatalogMaterial(CatalogMaterialDto catalogMaterialDto);

    ResponseEntity<?> getCatalogMaterial(Integer page, Integer size);
}
