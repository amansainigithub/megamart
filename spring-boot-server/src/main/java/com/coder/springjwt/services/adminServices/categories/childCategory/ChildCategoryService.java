package com.coder.springjwt.services.adminServices.categories.childCategory;

import com.coder.springjwt.dtos.adminDtos.categoriesDtos.childDtos.ChildCategoryDto;
import com.coder.springjwt.models.adminModels.categories.ChildCategoryModel;
import com.coder.springjwt.models.adminModels.categories.ParentCategoryModel;
import com.coder.springjwt.repository.adminRepository.categories.ChildCategoryRepo;
import com.coder.springjwt.repository.adminRepository.categories.ParentCategoryRepo;
import com.coder.springjwt.util.MessageResponse;
import com.coder.springjwt.util.ResponseGenerator;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChildCategoryService implements com.coder.springjwt.services.adminServices.categories.ChildCategoryService {

    @Autowired
    ChildCategoryRepo childCategoryRepo;

    @Autowired
    ParentCategoryRepo parentCategoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    Logger logger  = LoggerFactory.getLogger(ChildCategoryService.class);

    @Override
    public ResponseEntity<?> saveChildCategory(ChildCategoryDto childCategoryDto) {
        MessageResponse response =new MessageResponse();
        try {
            ChildCategoryModel childCategoryModel =  modelMapper.map(childCategoryDto , ChildCategoryModel.class);
            logger.info("Mapper Convert Success");

            Optional <ParentCategoryModel>  parentOptional=
                    this.parentCategoryRepo.findById(Long.parseLong(childCategoryDto.getParentCategoryId()));

            if(parentOptional.isPresent()) {
                logger.info("Data Present Success");
                childCategoryModel.setParentCategory(parentOptional.get());

                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                childCategoryModel.setUser(auth.getName());
                //save Category
                this.childCategoryRepo.save(childCategoryModel);

                logger.info("Child-Category Saved Success");
                response.setMessage("Child-Category Saved Success");
                response.setStatus(HttpStatus.OK);
                return ResponseGenerator.generateSuccessResponse(response, "Success");
            }
            else{
                logger.error("parent Category Not Found Via Id : "+ childCategoryDto.getParentCategoryId());
                response.setMessage("parent Category Not Found Via Id : "+ childCategoryDto.getParentCategoryId());
                response.setStatus(HttpStatus.BAD_REQUEST);
                return ResponseGenerator.generateBadRequestResponse(response);
            }
        }
        catch (DataIntegrityViolationException ex) {
            // Handle exception here
            logger.error("Duplicate entry error: ");
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
