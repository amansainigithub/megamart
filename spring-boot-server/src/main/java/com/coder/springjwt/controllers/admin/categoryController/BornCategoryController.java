package com.coder.springjwt.controllers.admin.categoryController;

import com.coder.springjwt.constants.adminConstants.adminUrlMappings.AdminUrlMappings;
import com.coder.springjwt.dtos.adminDtos.categoriesDtos.babyDto.BabyCategoryDto;
import com.coder.springjwt.dtos.adminDtos.categoriesDtos.bornDtos.BornCategoryDto;
import com.coder.springjwt.services.adminServices.categories.BornCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(
        origins = {"http://localhost:8080","http://localhost:4200"},
        maxAge = 3600,allowCredentials="true")
@RestController
@RequestMapping(AdminUrlMappings.BASE_PROTECTED_URL)
public class BornCategoryController {


    @Autowired
    private BornCategoryService bornCategoryService;

    @PostMapping(AdminUrlMappings.SAVE_BORN_CATEGORY)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> saveBornCategory(@Validated @RequestBody BornCategoryDto bornCategoryDto) {
        return this.bornCategoryService.saveBornCategory(bornCategoryDto);
    }


    @GetMapping(AdminUrlMappings.GET_BORN_CATEGORY_LIST)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getBornCategoryList() {
        return this.bornCategoryService.getBornCategoryList();
    }

    @GetMapping(AdminUrlMappings.DELETE_BORN_CATEGORY_BY_ID)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteBornCategoryById(@PathVariable long categoryId ) {
        return this.bornCategoryService.deleteBornCategoryById(categoryId);
    }

    @PostMapping(AdminUrlMappings.UPDATE_BORN_CATEGORY)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateBornCategory(@Validated @RequestBody BornCategoryDto bornCategoryDto ) {
        return this.bornCategoryService.updateBornCategory(bornCategoryDto);
    }

    @GetMapping(AdminUrlMappings.GET_BORN_CATEGORY_BY_ID)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getBornCategoryById(@PathVariable long categoryId ) {
        return this.bornCategoryService.getBornCategoryById(categoryId);
    }


}
