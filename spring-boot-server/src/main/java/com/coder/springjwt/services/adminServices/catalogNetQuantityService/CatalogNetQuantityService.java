package com.coder.springjwt.services.adminServices.catalogNetQuantityService;

import com.coder.springjwt.dtos.adminDtos.catalogDtos.CatalogNetQuantityDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface CatalogNetQuantityService {
    ResponseEntity<?> saveCatalogNetQuantity(CatalogNetQuantityDto catalogNetQuantityDto);

    ResponseEntity<?> deleteNetQuantity(long netQuantityId);

    ResponseEntity<?> getNetQuantityById(long netQuantityId);

    ResponseEntity<?> updateNetQuantity(CatalogNetQuantityDto catalogNetQuantityDto);

    ResponseEntity<?> getNetQuantity(Integer page, Integer size);
}
