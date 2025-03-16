package com.coder.springjwt.services.publicService.productService;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface PublicService {

    ResponseEntity<?> getProductCategoryService();

    ResponseEntity<?> getProductListByCategoryId(long categoryId, String categoryName, Integer page, Integer size);

    ResponseEntity<?> getProductListByBornCategoryId(long cI, String cN, Integer page, Integer size);

    ResponseEntity<?> getProductListDeal99(long cI, String cN, Integer page, Integer size);

    ResponseEntity<?> productWatching(String pI, String pN);


}
