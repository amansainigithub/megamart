package com.coder.springjwt.services.publicService.productService;

import com.coder.springjwt.dtos.customerPanelDtos.filterDto.ProductFilterDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface PublicService {

    ResponseEntity<?> getProductCategoryService();

    ResponseEntity<?> getProductListByCategoryId(long categoryId, String categoryName, Integer page, Integer size);

    ResponseEntity<?> getProductListByBornCategoryId(long cI, String cN, Integer page, Integer size);


    ResponseEntity<?> productWatching(String pI, String pN);


    ResponseEntity<?> productSearching(String searchKey);

    ResponseEntity<?> productFilter(ProductFilterDto productFilterDto, Integer page, Integer size);
}
