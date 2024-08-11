package com.coder.springjwt.services.adminServices.categories;


import com.coder.springjwt.dtos.adminDtos.categoriesDtos.ChildCategoryDto;
import com.coder.springjwt.models.adminModels.categories.ChildCategoryModel;
import com.coder.springjwt.models.adminModels.categories.ParentCategoryModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface ChildCategoryimple {

    ResponseEntity<?> saveChildCategory(ChildCategoryDto childCategoryDto);
}
