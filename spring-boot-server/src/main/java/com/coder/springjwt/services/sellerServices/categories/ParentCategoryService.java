package com.coder.springjwt.services.sellerServices.categories;

import com.coder.springjwt.dtos.sellerDtos.categoriesDtos.parentDtos.ParentCategoryDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public interface ParentCategoryService {

    ResponseEntity<?> saveParentCategory(ParentCategoryDto parentCategoryDto);

    ResponseEntity<?> getParentCategoryList();

    ResponseEntity<?> deleteCategoryById(long categoryId);

    ResponseEntity<?> getParentCategoryById(long categoryId);

    ResponseEntity<?> updateParentCategory(ParentCategoryDto parentCategoryDto );


    ResponseEntity<?> updateParentCategoryFile(MultipartFile file, Long parentCategoryId);
}
