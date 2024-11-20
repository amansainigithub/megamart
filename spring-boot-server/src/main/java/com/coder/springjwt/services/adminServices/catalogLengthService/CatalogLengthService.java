package com.coder.springjwt.services.adminServices.catalogLengthService;

import com.coder.springjwt.dtos.adminDtos.catalogDtos.CatalogLengthDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface CatalogLengthService {
    ResponseEntity<?> saveCatalogLength(CatalogLengthDto catalogLengthDto);

    ResponseEntity<?> getCatalogLength(Integer page, Integer size);
}
