package com.coder.springjwt.services.adminServices.catalogTypeService;

import com.coder.springjwt.dtos.adminDtos.catalogSizeDto.CatalogTypeDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface CatalogTypeService {
    ResponseEntity<?> saveCatalogType(CatalogTypeDto catalogTypeDto);

    ResponseEntity<?> deleteCatalogType(long typeId);

    ResponseEntity<?> getCatalogTypeById(long typeId);

    ResponseEntity<?> updateCatalogType(CatalogTypeDto catalogTypeDto);

    ResponseEntity<?> getCatalogType(Integer page, Integer size);
}
