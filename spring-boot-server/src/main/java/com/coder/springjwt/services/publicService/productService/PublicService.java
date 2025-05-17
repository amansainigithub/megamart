package com.coder.springjwt.services.publicService.productService;

import com.coder.springjwt.dtos.customerPanelDtos.filterDto.ProductFilterDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface PublicService {

    ResponseEntity<?> getProductCategoryService();

    ResponseEntity<?> getBabyCategoryFooter();


    ResponseEntity<?> getProductListByCategoryId(long categoryId, String categoryName, Integer page, Integer size);

    ResponseEntity<?> getProductListByBornCategoryId(long cI, String cN, Integer page, Integer size);


    ResponseEntity<?> productWatching(String pI, String pN);


    ResponseEntity<?> productSearching(String searchKey);

    ResponseEntity<?> productFilter(ProductFilterDto productFilterDto, Integer page, Integer size);

    ResponseEntity<?> getProductByIdCustomer(String pI, String pN);

}
