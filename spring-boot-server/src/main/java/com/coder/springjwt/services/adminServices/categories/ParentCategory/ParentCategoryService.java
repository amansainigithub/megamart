package com.coder.springjwt.services.adminServices.categories.ParentCategory;

import com.coder.springjwt.models.adminModels.categories.ParentCategoryModel;
import com.coder.springjwt.repository.adminRepository.categories.ParentCategoryRepo;
import com.coder.springjwt.services.adminServices.categories.ParentCategoryimple;
import com.coder.springjwt.util.MessageResponse;
import com.coder.springjwt.util.ResponseGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ParentCategoryService implements ParentCategoryimple {

    @Autowired
    ParentCategoryRepo parentCategoryRepo;

    @Override
    public ResponseEntity<?> saveParentCategory(ParentCategoryModel parentCategoryModel) {
        MessageResponse response =new MessageResponse();
        try {

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            parentCategoryModel.setUser(auth.getName());
            //save Categoru
            this.parentCategoryRepo.save(parentCategoryModel);

            response.setMessage("Category Saved Success");
            response.setStatus(HttpStatus.OK);
            return ResponseGenerator.generateSuccessResponse(response ,"Success");
        }
        catch (DataIntegrityViolationException ex) {
            // Handle exception here
            response.setMessage("Duplicate entry error: ");
            response.setStatus(HttpStatus.BAD_REQUEST);
            return ResponseGenerator.generateBadRequestResponse(response);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            response.setMessage(e.getMessage());
            response.setStatus(HttpStatus.BAD_REQUEST);
            return ResponseGenerator.generateBadRequestResponse(response);
        }

    }
}
