package com.coder.springjwt.services.adminServices.categories.babyCategoryImple;

import com.coder.springjwt.bucket.bucketService.BucketService;
import com.coder.springjwt.dtos.adminDtos.categoriesDtos.babyDto.BabyCategoryDto;
import com.coder.springjwt.dtos.adminDtos.categoriesDtos.childDtos.ChildCategoryDto;
import com.coder.springjwt.exception.adminException.CategoryNotFoundException;
import com.coder.springjwt.models.adminModels.categories.BabyCategoryModel;
import com.coder.springjwt.models.adminModels.categories.ChildCategoryModel;
import com.coder.springjwt.models.adminModels.categories.ParentCategoryModel;
import com.coder.springjwt.repository.adminRepository.categories.BabyCategoryRepo;
import com.coder.springjwt.repository.adminRepository.categories.ChildCategoryRepo;
import com.coder.springjwt.repository.adminRepository.categories.ParentCategoryRepo;
import com.coder.springjwt.services.adminServices.categories.BabyCategoryService;
import com.coder.springjwt.services.adminServices.categories.ParentCategory.ParentCategoryServiceImple;
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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BabyCategoryServiceImple implements BabyCategoryService {


    @Autowired
    BabyCategoryRepo babyCategoryRepo;

    @Autowired
    ChildCategoryRepo childCategoryRepo;

    @Autowired
    BucketService bucketService;

    @Autowired
    private ModelMapper modelMapper;

    Logger logger =  LoggerFactory.getLogger(ParentCategoryServiceImple.class);


    @Override
    public ResponseEntity<?> saveBabyCategory(BabyCategoryDto babyCategoryDto) {
        MessageResponse response =new MessageResponse();
        try {
            BabyCategoryModel babyCategoryModel =  modelMapper.map(babyCategoryDto , BabyCategoryModel.class);
            logger.info("Mapper Convert Success");

            Optional<ChildCategoryModel> childOptional =
                    this.childCategoryRepo.findById(Long.parseLong(babyCategoryDto.getChildCategoryId()));

            if(childOptional.isPresent()) {
                logger.info("Data Present Success");
                babyCategoryModel.setChildCategoryModel(childOptional.get());

                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                babyCategoryModel.setUser(auth.getName());
                //save Category
                this.babyCategoryRepo.save(babyCategoryModel);

                logger.info("Child-Category Saved Success");
                response.setMessage("Child-Category Saved Success");
                response.setStatus(HttpStatus.OK);
                return ResponseGenerator.generateSuccessResponse(response, "Success");
            }
            else{
                logger.error("child Category Not Found Via Id : "+ babyCategoryDto.getChildCategoryId());
                response.setMessage("child Category Not Found Via Id : "+ babyCategoryDto.getChildCategoryId());
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

    @Override
    public ResponseEntity<?> getBabyCategoryList() {
        try {
            List<BabyCategoryModel> babyList =  this.babyCategoryRepo.findAll();
            List<BabyCategoryDto> babyCategoryDtos =   babyList
                    .stream()
                    .map(category -> modelMapper.map(category, BabyCategoryDto.class))
                    .collect(Collectors.toList());
            return ResponseGenerator.generateSuccessResponse(babyCategoryDtos,"Success");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse("Failed");
        }
    }

    @Override
    public ResponseEntity<?> deleteBabyCategoryById(long categoryId) {
        try {
            BabyCategoryModel data = this.babyCategoryRepo.findById(categoryId).orElseThrow(
                    () -> new CategoryNotFoundException("Category id not Found"));

            this.babyCategoryRepo.deleteById(data.getId());
            logger.info("Delete Success => Category id :: " + categoryId );
            return ResponseGenerator.generateSuccessResponse("Delete Success" , "Success");

        }
        catch (Exception e)
        {
            e.printStackTrace();
            logger.error("Category Could Not deleted");
            return ResponseGenerator.generateBadRequestResponse
                    ("Category Could not deleted :: " + e.getMessage() , "Error");
        }
    }
}
