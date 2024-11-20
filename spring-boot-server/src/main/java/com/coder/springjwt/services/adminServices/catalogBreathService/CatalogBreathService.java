package com.coder.springjwt.services.adminServices.catalogBreathService;

import com.coder.springjwt.dtos.adminDtos.catalogDtos.CatalogBreathDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface CatalogBreathService {
    ResponseEntity<?> saveCatalogBreath(CatalogBreathDto catalogBreathDto);

    ResponseEntity<?> getCatalogBreath(Integer page, Integer size);
}
