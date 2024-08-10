package com.coder.springjwt.controllers.admin.categoryController;

import com.coder.springjwt.constants.adminConstants.adminUrlMappings.AdminUrlMappings;
import com.coder.springjwt.models.adminModels.categories.ParentCategoryModel;
import com.coder.springjwt.services.adminServices.categories.ParentCategoryimple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(AdminUrlMappings.AUTH_BASE_URL)
public class CategoryController {


   @Autowired
    private ParentCategoryimple parentCategoryimple;


    @PostMapping(AdminUrlMappings.PARAENT_CATEGORY)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createParentCategory(@Validated @RequestBody ParentCategoryModel parentCategoryModel) {
        return this.parentCategoryimple.saveParentCategory(parentCategoryModel);
    }


}
