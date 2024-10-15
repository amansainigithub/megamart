package com.coder.springjwt.services.sellerServices.sellerProductCategoryService;

import com.coder.springjwt.payload.sellerPayloads.sellerPayload.SellerPickUpPayload;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface SellerProductCategoryService {

    public ResponseEntity<?> getParentCategory();

    ResponseEntity<?> getChildCategoryListById(Long parentId);

    ResponseEntity<?> getBabyCategoryListChildById(Long childId);

    ResponseEntity<?> getBornCategoryListByBabyId(Long babyId);
}
