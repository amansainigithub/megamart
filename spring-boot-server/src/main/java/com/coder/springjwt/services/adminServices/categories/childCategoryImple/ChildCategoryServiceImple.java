package com.coder.springjwt.services.adminServices.categories.childCategoryImple;

import com.coder.springjwt.bucket.bucketModels.BucketModel;
import com.coder.springjwt.bucket.bucketService.BucketService;
import com.coder.springjwt.dtos.adminDtos.categoriesDtos.childDtos.ChildCategoryDto;
import com.coder.springjwt.dtos.adminDtos.categoriesDtos.parentDtos.ParentCategoryDto;
import com.coder.springjwt.exception.adminException.CategoryNotFoundException;
import com.coder.springjwt.exception.adminException.DataNotFoundException;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChildCategoryServiceImple implements com.coder.springjwt.services.adminServices.categories.ChildCategoryService {

    @Autowired
    ChildCategoryRepo childCategoryRepo;

    @Autowired
    ParentCategoryRepo parentCategoryRepo;

    @Autowired
    BucketService bucketService;


    @Autowired
    private ModelMapper modelMapper;

    Logger logger  = LoggerFactory.getLogger(ChildCategoryServiceImple.class);

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

    @Override
    public ResponseEntity<?> getChildCategoryList() {
      try {
          List<ChildCategoryModel> childList =  this.childCategoryRepo.findAll();
          List<ChildCategoryDto> childCategoryDtos =   childList
                  .stream()
                  .map(category -> modelMapper.map(category, ChildCategoryDto.class))
                  .collect(Collectors.toList());
          return ResponseGenerator.generateSuccessResponse(childCategoryDtos,"Success");
      }
      catch (Exception e)
      {
          e.printStackTrace();
          return ResponseGenerator.generateBadRequestResponse("Failed");
      }
    }

    @Override
    public ResponseEntity<?> deleteChildCategoryById(long categoryId) {
        try {
            ChildCategoryModel data = this.childCategoryRepo.findById(categoryId).orElseThrow(
                    () -> new CategoryNotFoundException("Category id not Found"));

            this.childCategoryRepo.deleteById(data.getId());
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

    @Override
    public ResponseEntity<?> getChildCategoryById(long categoryId) {
        try {
            ChildCategoryModel childCategoryModel = this.childCategoryRepo.findById(categoryId).orElseThrow(
                    () -> new RuntimeException("Data not Found ! Error"));
            ChildCategoryDto childCategoryDto = modelMapper.map(childCategoryModel, ChildCategoryDto.class);
            logger.info("Child Category Fetch Success !");
            return ResponseGenerator.generateSuccessResponse(childCategoryDto , "Success");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            logger.error("");
            return ResponseGenerator.generateBadRequestResponse(e.getMessage() , "Error");
        }
    }

    @Override
    public ResponseEntity<?> updateChildCategory(ChildCategoryDto childCategoryDto ) {
        MessageResponse response = new MessageResponse();
        try {
            logger.info(childCategoryDto.toString());
            logger.info("Update Child Process Starting....");
            this.childCategoryRepo.findById(childCategoryDto.getId()).orElseThrow(()->new DataNotFoundException("Dara not Found"));

            ChildCategoryModel childCategorymodel =  modelMapper.map(childCategoryDto , ChildCategoryModel.class);
            this.childCategoryRepo.save(childCategorymodel);

            logger.info("Data Update Success");
            return ResponseGenerator.generateSuccessResponse("Success" , "Data update Success");

        }
        catch (Exception e)
        {
            logger.info("Data Update Failed");
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse("failed" ," Data Update Failed");
        }
    }

    @Override
    public ResponseEntity<?> updateParentCategoryFile(MultipartFile file, Long childCategoryId) {
        try {
            ChildCategoryModel childCategoryModel = this.childCategoryRepo.findById(childCategoryId).orElseThrow(()-> new DataNotFoundException("DATA_NOT_FOUND"));

            //Delete old File
            bucketService.deleteFile(childCategoryModel.getCategoryFile());

            //upload New File
            BucketModel bucketModel = bucketService.uploadFile(file);
            if(bucketModel != null)
            {
                childCategoryModel.setCategoryFile(bucketModel.getBucketUrl());
                this.childCategoryRepo.save(childCategoryModel);
                return ResponseGenerator.generateSuccessResponse("Success","File Update Success");
            }
            else {
                logger.error("Bucket Model is null | please check AWS bucket configuration");
                throw new Exception("Bucket AWS is Empty");
            }
        }
        catch (Exception e)
        {
            logger.info("Exception" , e.getMessage());
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse("Error" ,"File Not Update");
        }
    }

}
