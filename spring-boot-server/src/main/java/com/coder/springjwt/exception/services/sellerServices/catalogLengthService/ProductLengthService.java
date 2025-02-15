package com.coder.springjwt.exception.services.sellerServices.catalogLengthService;

import com.coder.springjwt.dtos.sellerDtos.productMetaDtos.ProductLengthDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface ProductLengthService {
    ResponseEntity<?> saveLength(ProductLengthDto productLengthDto);

    ResponseEntity<?> getLength(Integer page, Integer size);
}
