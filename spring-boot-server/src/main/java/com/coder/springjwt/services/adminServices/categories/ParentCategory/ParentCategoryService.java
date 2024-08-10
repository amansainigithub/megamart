package com.coder.springjwt.services.adminServices.categories.ParentCategory;

import com.coder.springjwt.models.adminModels.categories.ParentCategoryModel;
import com.coder.springjwt.repository.adminRepository.categories.ParentCategoryRepo;
import com.coder.springjwt.services.adminServices.categories.ParentCategoryimple;
import com.coder.springjwt.util.ResponseGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ParentCategoryService implements ParentCategoryimple {

    @Autowired
    ParentCategoryRepo parentCategoryRepo;

    @Override
    public ResponseEntity<?> saveParentCategory(ParentCategoryModel parentCategoryModel) {

        this.parentCategoryRepo.save(parentCategoryModel);
        return ResponseGenerator.generateSuccessResponse(parentCategoryModel ,"Success");

    }
}
