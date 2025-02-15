package com.coder.springjwt.services.sellerServices.categories;


import com.coder.springjwt.dtos.sellerDtos.categoriesDtos.childDtos.ChildCategoryDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public interface ChildCategoryService {

    ResponseEntity<?> saveChildCategory(ChildCategoryDto childCategoryDto);

    ResponseEntity<?> getChildCategoryList();


    ResponseEntity<?> deleteChildCategoryById(long categoryId);

    ResponseEntity<?> getChildCategoryById(long categoryId);

    ResponseEntity<?> updateChildCategory(ChildCategoryDto childCategoryDto );

    ResponseEntity<?> updateParentCategoryFile(MultipartFile file, Long childCategoryId);
}
