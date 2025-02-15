package com.coder.springjwt.exception.services.sellerServices.categories;

import com.coder.springjwt.dtos.sellerDtos.categoriesDtos.bornDtos.BornCategoryDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
public interface BornCategoryService {

    ResponseEntity<?> saveBornCategory(BornCategoryDto bornCategoryDto);

    ResponseEntity<?> getBornCategoryList();

    ResponseEntity<?> deleteBornCategoryById(long categoryId);

    ResponseEntity<?> updateBornCategory(BornCategoryDto bornCategoryDto);

    ResponseEntity<?> getBornCategoryById(long categoryId);

    ResponseEntity<?> updateBornCategoryFile(MultipartFile file, Long bornCategoryId);

    ResponseEntity<?> getBornCategoryListByPagination(Integer page,Integer size);


    ResponseEntity<?> sampleFilesService(Long bornCategoryId, List<MultipartFile> files, List<String> metadataList);
}
