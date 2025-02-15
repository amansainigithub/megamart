package com.coder.springjwt.exception.services.sellerServices.catalogTypeService;

import com.coder.springjwt.dtos.sellerDtos.productMetaDtos.ProductTypeDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface ProductTypeService {
    ResponseEntity<?> saveType(ProductTypeDto productTypeDto);

    ResponseEntity<?> deleteType(long typeId);

    ResponseEntity<?> getTypeById(long typeId);

    ResponseEntity<?> updateType(ProductTypeDto productTypeDto);

    ResponseEntity<?> getType(Integer page, Integer size);
}
