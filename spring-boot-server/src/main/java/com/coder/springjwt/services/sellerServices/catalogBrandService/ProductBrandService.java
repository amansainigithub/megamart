package com.coder.springjwt.services.sellerServices.catalogBrandService;

import com.coder.springjwt.dtos.sellerDtos.productMetaDtos.ProductBrandDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface ProductBrandService {
    ResponseEntity<?> saveBrand(ProductBrandDto productBrandDto);

    ResponseEntity<?> deleteBrand(long brandId);

    ResponseEntity<?> getBrandById(long brandId);

    ResponseEntity<?> updateBrand(ProductBrandDto productBrandDto);

    ResponseEntity<?> getBrand(Integer page, Integer size);
}
