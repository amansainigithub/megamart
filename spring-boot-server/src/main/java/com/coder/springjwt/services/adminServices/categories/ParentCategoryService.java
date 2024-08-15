package com.coder.springjwt.services.adminServices.categories;

import com.coder.springjwt.dtos.adminDtos.categoriesDtos.parentDtos.ParentCategoryDto;
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
