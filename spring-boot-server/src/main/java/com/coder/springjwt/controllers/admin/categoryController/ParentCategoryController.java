package com.coder.springjwt.controllers.admin.categoryController;

import com.coder.springjwt.constants.adminConstants.adminUrlMappings.AdminUrlMappings;
import com.coder.springjwt.dtos.adminDtos.categoriesDtos.parentDtos.ParentCategoryDto;
import com.coder.springjwt.services.adminServices.categories.ParentCategoryService;
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
public class ParentCategoryController {
    @Autowired
    private ParentCategoryService parentCategoryimple;

    @PostMapping(AdminUrlMappings.CREATE_PARENT_CATEGORY)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createParentCategory(@Validated @RequestBody ParentCategoryDto parentCategoryDto) {
        return this.parentCategoryimple.saveParentCategory(parentCategoryDto);
    }

    @GetMapping(AdminUrlMappings.GET_PARENT_CATEGORY_LIST)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getParentCategoryList() {
        return this.parentCategoryimple.getParentCategoryList();
    }

    @GetMapping(AdminUrlMappings.DELETE_CATEGORY_BY_ID)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteCategoryById(@PathVariable long categoryId ) {
        return this.parentCategoryimple.deleteCategoryById(categoryId);
    }

    @GetMapping(AdminUrlMappings.GET_PARENT_CATEGORY_BY_ID)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getParentCategoryById(@PathVariable long categoryId ) {
        return this.parentCategoryimple.getParentCategoryById(categoryId);
    }

    @PostMapping(AdminUrlMappings.UPDATE_PARENT_CATEGORY)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateParentCategory(@Validated @RequestBody ParentCategoryDto parentCategoryDto ) {
        return this.parentCategoryimple.updateParentCategory(parentCategoryDto);
    }


    @PostMapping(AdminUrlMappings.UPDATE_PARENT_CATEGORY_FILE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateCategoryFile(@RequestParam(value = "file") MultipartFile file , @PathVariable Long parentCategoryId)
    {
        return parentCategoryimple.updateParentCategoryFile(file,parentCategoryId);
    }

}
