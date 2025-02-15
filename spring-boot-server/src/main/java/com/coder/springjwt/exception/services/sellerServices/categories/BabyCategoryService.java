package com.coder.springjwt.exception.services.sellerServices.categories;

import com.coder.springjwt.dtos.sellerDtos.categoriesDtos.babyDto.BabyCategoryDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public interface BabyCategoryService {

    ResponseEntity<?> saveBabyCategory(BabyCategoryDto babyCategoryDto);

    ResponseEntity<?> getBabyCategoryList();

    ResponseEntity<?> deleteBabyCategoryById(long categoryId);

    ResponseEntity<?> updateBabyCategory(BabyCategoryDto babyCategoryDto);

    ResponseEntity<?> getBabyCategoryById(long categoryId);

    ResponseEntity<?> updateBabyCategoryFile(MultipartFile file, Long babyCategoryId);


}
