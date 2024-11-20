package com.coder.springjwt.services.adminServices.catalogWeightService;

import com.coder.springjwt.dtos.adminDtos.catalogDtos.CatalogWeightDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface CatalogWeightService {

    ResponseEntity<?> saveCatalogWeight(CatalogWeightDto catalogWeightDto);

    ResponseEntity<?> getCatalogWeight(Integer page, Integer size);
}
