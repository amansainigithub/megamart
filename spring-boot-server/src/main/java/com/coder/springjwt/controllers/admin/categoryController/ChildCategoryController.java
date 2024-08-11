package com.coder.springjwt.controllers.admin.categoryController;

import com.coder.springjwt.constants.adminConstants.adminUrlMappings.AdminUrlMappings;
import com.coder.springjwt.dtos.adminDtos.categoriesDtos.ChildCategoryDto;
import com.coder.springjwt.services.adminServices.categories.ChildCategoryimple;
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
public class ChildCategoryController {


    @Autowired
    private ChildCategoryimple childCategoryimple;

    @PostMapping(AdminUrlMappings.SAVE_CHILD_CATEGORY)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> saveChildCategory(@Validated @RequestBody ChildCategoryDto childCategoryDto) {
        return this.childCategoryimple.saveChildCategory(childCategoryDto);
    }


}
