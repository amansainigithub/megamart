package com.coder.springjwt.exception.services.sellerServices.catalogSizeService;

import com.coder.springjwt.dtos.sellerDtos.productMetaDtos.ProductSizeVariantDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface ProductSizeService {

    ResponseEntity<?> saveSize(ProductSizeVariantDto productSizeVariantDto);

    ResponseEntity<?> deleteSize(long sizeId);

    ResponseEntity<?> getSizeById(long sizeId);

    ResponseEntity<?> updateSize(ProductSizeVariantDto productSizeVariantDto);

    ResponseEntity<?> getSize(Integer page, Integer size);
}
