package com.coder.springjwt.controllers.admin.categoryController;

import com.coder.springjwt.constants.adminConstants.adminUrlMappings.AdminUrlMappings;
import com.coder.springjwt.dtos.adminDtos.categoriesDtos.childDtos.ChildCategoryDto;
import com.coder.springjwt.dtos.adminDtos.categoriesDtos.parentDtos.ParentCategoryDto;
import com.coder.springjwt.services.adminServices.categories.ChildCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(AdminUrlMappings.CHILD_CONTROLLER)
public class ChildCategoryController {


    @Autowired
    private ChildCategoryService childCategoryimple;

    @PostMapping(AdminUrlMappings.SAVE_CHILD_CATEGORY)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> saveChildCategory(@Validated @RequestBody ChildCategoryDto childCategoryDto) {
        return this.childCategoryimple.saveChildCategory(childCategoryDto);
    }


    @GetMapping(AdminUrlMappings.GET_CHILD_CATEGORY_LIST)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getChildCategoryList() {
        return this.childCategoryimple.getChildCategoryList();
    }

    @GetMapping(AdminUrlMappings.DELETE_CHILD_CATEGORY_BY_ID)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteChildCategoryById(@PathVariable long categoryId ) {
        return this.childCategoryimple.deleteChildCategoryById(categoryId);
    }

    @GetMapping(AdminUrlMappings.GET_CHILD_CATEGORY_BY_ID)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getChildCategoryById(@PathVariable long categoryId ) {
        return this.childCategoryimple.getChildCategoryById(categoryId);
    }

    @PostMapping(AdminUrlMappings.UPDATE_CHILD_CATEGORY)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateChildCategory(@Validated @RequestBody ChildCategoryDto childCategoryDto ) {
        return this.childCategoryimple.updateChildCategory(childCategoryDto);
    }

    @PostMapping(AdminUrlMappings.UPDATE_CHILD_CATEGORY_FILE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateChildCategoryFile(@RequestParam(value = "file") MultipartFile file , @PathVariable Long childCategoryId)
    {
        return childCategoryimple.updateParentCategoryFile(file,childCategoryId);
    }






}
