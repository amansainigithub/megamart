package com.coder.springjwt.services.adminServices.categories;


import com.coder.springjwt.dtos.adminDtos.categoriesDtos.childDtos.ChildCategoryDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface ChildCategoryService {

    ResponseEntity<?> saveChildCategory(ChildCategoryDto childCategoryDto);
}
