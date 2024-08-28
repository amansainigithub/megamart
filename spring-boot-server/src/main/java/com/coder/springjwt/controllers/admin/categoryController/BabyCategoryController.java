package com.coder.springjwt.controllers.admin.categoryController;

import com.coder.springjwt.constants.adminConstants.adminUrlMappings.AdminUrlMappings;
import com.coder.springjwt.dtos.adminDtos.categoriesDtos.babyDto.BabyCategoryDto;
import com.coder.springjwt.dtos.adminDtos.categoriesDtos.childDtos.ChildCategoryDto;
import com.coder.springjwt.dtos.adminDtos.categoriesDtos.parentDtos.ParentCategoryDto;
import com.coder.springjwt.services.adminServices.categories.BabyCategoryService;
import com.coder.springjwt.services.adminServices.categories.ChildCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(
        origins = {"http://localhost:8080","http://localhost:4200"},
        maxAge = 3600,allowCredentials="true")
@RestController
@RequestMapping(AdminUrlMappings.BASE_PROTECTED_URL)
public class BabyCategoryController {

    @Autowired
    private BabyCategoryService babyCategoryService;

    @PostMapping(AdminUrlMappings.SAVE_BABY_CATEGORY)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> saveBabyCategory(@Validated @RequestBody BabyCategoryDto babyCategoryDto) {
        return this.babyCategoryService.saveBabyCategory(babyCategoryDto);
    }


    @GetMapping(AdminUrlMappings.GET_BABY_CATEGORY_LIST)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getBabyCategoryList() {
        return this.babyCategoryService.getBabyCategoryList();
    }


    @GetMapping(AdminUrlMappings.DELETE_BABY_CATEGORY_BY_ID)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteBabyCategoryById(@PathVariable long categoryId ) {
        return this.babyCategoryService.deleteBabyCategoryById(categoryId);
    }


    @PostMapping(AdminUrlMappings.UPDATE_BABY_CATEGORY)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateBabyCategory(@Validated @RequestBody BabyCategoryDto babyCategoryDto ) {
        return this.babyCategoryService.updateBabyCategory(babyCategoryDto);
    }



    @GetMapping(AdminUrlMappings.GET_BABY_CATEGORY_BY_ID)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getBabyCategoryById(@PathVariable long categoryId ) {
        return this.babyCategoryService.getBabyCategoryById(categoryId);
    }

    @PostMapping(AdminUrlMappings.UPDATE_BABY_CATEGORY_FILE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateBabyCategoryFile(@RequestParam(value = "file") MultipartFile file , @PathVariable Long babyCategoryId)
    {
        return babyCategoryService.updateBabyCategoryFile(file,babyCategoryId);
    }


}
