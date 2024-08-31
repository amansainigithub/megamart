package com.coder.springjwt.services.adminServices.categories;

import com.coder.springjwt.dtos.adminDtos.categoriesDtos.babyDto.BabyCategoryDto;
import com.coder.springjwt.dtos.adminDtos.categoriesDtos.bornDtos.BornCategoryDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public interface BornCategoryService {

    ResponseEntity<?> saveBornCategory(BornCategoryDto bornCategoryDto);

    ResponseEntity<?> getBornCategoryList();

    ResponseEntity<?> deleteBornCategoryById(long categoryId);

    ResponseEntity<?> updateBornCategory(BornCategoryDto bornCategoryDto);

    ResponseEntity<?> getBornCategoryById(long categoryId);

    ResponseEntity<?> updateBornCategoryFile(MultipartFile file, Long bornCategoryId);



}
