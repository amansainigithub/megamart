package com.coder.springjwt.services.adminServices.categories;

import com.coder.springjwt.dtos.adminDtos.categoriesDtos.babyDto.BabyCategoryDto;
import com.coder.springjwt.dtos.adminDtos.categoriesDtos.childDtos.ChildCategoryDto;
import com.coder.springjwt.dtos.adminDtos.categoriesDtos.parentDtos.ParentCategoryDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface BabyCategoryService {

    ResponseEntity<?> saveBabyCategory(BabyCategoryDto babyCategoryDto);

    ResponseEntity<?> getBabyCategoryList();

    ResponseEntity<?> deleteBabyCategoryById(long categoryId);

}
