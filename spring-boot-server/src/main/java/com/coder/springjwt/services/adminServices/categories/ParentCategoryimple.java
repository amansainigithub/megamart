package com.coder.springjwt.services.adminServices.categories;

import com.coder.springjwt.models.adminModels.categories.ParentCategoryModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface ParentCategoryimple {

    ResponseEntity<?> saveParentCategory(ParentCategoryModel parentCategoryModel);
}
