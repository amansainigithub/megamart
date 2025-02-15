package com.coder.springjwt.services.sellerServices.catalogWeightService;

import com.coder.springjwt.dtos.sellerDtos.productMetaDtos.ProductWeightDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface ProductWeightService {

    ResponseEntity<?> saveWeight(ProductWeightDto productWeightDto);

    ResponseEntity<?> getWeight(Integer page, Integer size);
}
