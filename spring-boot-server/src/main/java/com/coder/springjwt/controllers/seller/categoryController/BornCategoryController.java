package com.coder.springjwt.controllers.seller.categoryController;

import com.coder.springjwt.constants.sellerConstants.sellerUrlMappings.SellerUrlMappings;
import com.coder.springjwt.dtos.sellerDtos.categoriesDtos.bornDtos.BornCategoryDto;
import com.coder.springjwt.exception.services.sellerServices.categories.BornCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping(SellerUrlMappings.BORN_CONTROLLER)
public class BornCategoryController {


    @Autowired
    private BornCategoryService bornCategoryService;

    @PostMapping(SellerUrlMappings.SAVE_BORN_CATEGORY)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> saveBornCategory(@Validated @RequestBody BornCategoryDto bornCategoryDto) {
        return this.bornCategoryService.saveBornCategory(bornCategoryDto);
    }


    @GetMapping(SellerUrlMappings.GET_BORN_CATEGORY_LIST)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> getBornCategoryList() {
        return this.bornCategoryService.getBornCategoryList();
    }

    @GetMapping(SellerUrlMappings.DELETE_BORN_CATEGORY_BY_ID)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> deleteBornCategoryById(@PathVariable long categoryId ) {
        return this.bornCategoryService.deleteBornCategoryById(categoryId);
    }

    @PostMapping(SellerUrlMappings.UPDATE_BORN_CATEGORY)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> updateBornCategory(@Validated  @RequestBody BornCategoryDto bornCategoryDto ) {
        return this.bornCategoryService.updateBornCategory(bornCategoryDto);
    }


    @GetMapping(SellerUrlMappings.GET_BORN_CATEGORY_BY_ID)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> getBornCategoryById(@PathVariable long categoryId ) {
        return this.bornCategoryService.getBornCategoryById(categoryId);
    }

    @PostMapping(SellerUrlMappings.UPDATE_BORN_CATEGORY_FILE)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> updateBornCategoryFile(@RequestParam(value = "file") MultipartFile file ,
                                                    @PathVariable Long bornCategoryId)
    {
        return bornCategoryService.updateBornCategoryFile(file,bornCategoryId);
    }



    @GetMapping(SellerUrlMappings.GET_BORN_CATEGORY_LIST_BY_PAGINATION)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> getBornCategoryListByPagination(@RequestParam  Integer page ,@RequestParam  Integer size)
    {
        return bornCategoryService.getBornCategoryListByPagination(page,size);
    }


    @PostMapping(SellerUrlMappings.PRODUCT_SAMPLE_FILES)
    public ResponseEntity<?> productSampleFiles(
            @PathVariable Long bornCategoryId ,
            @RequestParam("files") List<MultipartFile> files,
            @RequestParam("metadata") List<String> metadataList) {
        return bornCategoryService.sampleFilesService(bornCategoryId ,files ,metadataList);
    }

}
