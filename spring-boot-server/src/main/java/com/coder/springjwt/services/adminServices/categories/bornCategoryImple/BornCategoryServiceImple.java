package com.coder.springjwt.services.adminServices.categories.bornCategoryImple;

import com.coder.springjwt.bucket.bucketService.BucketService;
import com.coder.springjwt.dtos.adminDtos.categoriesDtos.babyDto.BabyCategoryDto;
import com.coder.springjwt.dtos.adminDtos.categoriesDtos.bornDtos.BornCategoryDto;
import com.coder.springjwt.exception.adminException.CategoryNotFoundException;
import com.coder.springjwt.exception.adminException.DataNotFoundException;
import com.coder.springjwt.models.adminModels.categories.BabyCategoryModel;
import com.coder.springjwt.models.adminModels.categories.BornCategoryModel;
import com.coder.springjwt.repository.adminRepository.categories.BabyCategoryRepo;
import com.coder.springjwt.repository.adminRepository.categories.BornCategoryRepo;
import com.coder.springjwt.services.adminServices.categories.BornCategoryService;
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
public class BornCategoryServiceImple implements BornCategoryService {

    @Autowired
    BabyCategoryRepo babyCategoryRepo;

    @Autowired
    BornCategoryRepo bornCategoryRepo;

    @Autowired
    BucketService bucketService;

    @Autowired
    private ModelMapper modelMapper;

    Logger logger =  LoggerFactory.getLogger(BornCategoryServiceImple.class);

    @Override
    public ResponseEntity<?> saveBornCategory(BornCategoryDto bornCategoryDto) {
        MessageResponse response =new MessageResponse();
        try {
            BornCategoryModel bornCategoryModel=  modelMapper.map(bornCategoryDto , BornCategoryModel.class);
            logger.info("Mapper Convert Success");

            System.out.println("Born Model =>  " + bornCategoryModel);

            System.out.println("Born DTO =>  " + bornCategoryDto);

            Optional<BabyCategoryModel> babyOptional =
                    this.babyCategoryRepo.findById(Long.parseLong(bornCategoryDto.getBabyCategoryId()));

            logger.info("bornOptional Running 1.....");
            logger.info("IS pResent :: "+String.valueOf(babyOptional.isPresent()));

            if(babyOptional.isPresent()) {
                logger.info("Data Present Success");
                bornCategoryModel.setBabyCategoryModel(babyOptional.get());

                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                bornCategoryModel.setUser(auth.getName());
                //save Category
                this.bornCategoryRepo.save(bornCategoryModel);

                logger.info("Born-Category Saved Success");
                response.setMessage("Born-Category Saved Success");
                response.setStatus(HttpStatus.OK);
                return ResponseGenerator.generateSuccessResponse(response, "Success");
            }
            else{
                logger.error("Baby Category Not Found Via Id : "+ bornCategoryDto.getBabyCategoryId());
                response.setMessage("Baby Category Not Found Via Id : "+ bornCategoryDto.getBabyCategoryId());
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
    public ResponseEntity<?> getBornCategoryList() {
        try {
            List<BornCategoryModel> bornList =  this.bornCategoryRepo.findAll();
            List<BornCategoryDto> bornCategoryDtos =   bornList
                    .stream()
                    .map(category -> modelMapper.map(category, BornCategoryDto.class))
                    .collect(Collectors.toList());
            return ResponseGenerator.generateSuccessResponse(bornCategoryDtos,"Success");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse("Failed");
        }
    }

    @Override
    public ResponseEntity<?> deleteBornCategoryById(long categoryId) {
        try {
            BornCategoryModel data = this.bornCategoryRepo.findById(categoryId).orElseThrow(
                    () -> new CategoryNotFoundException("Category id not Found"));

            this.bornCategoryRepo.deleteById(data.getId());
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
    public ResponseEntity<?> updateBornCategory(BornCategoryDto bornCategoryDto) {
        MessageResponse response = new MessageResponse();
        try {
            System.out.println("bornCategoryDto Category DTO :: " + bornCategoryDto);
            logger.info(bornCategoryDto.toString());
            BornCategoryModel bornData =   this.bornCategoryRepo.findById(bornCategoryDto.getId()).
                    orElseThrow(()->new DataNotFoundException("Data not Found"));

            BornCategoryModel bornCategoryModel =  modelMapper.map(bornCategoryDto , BornCategoryModel.class);

            //Set baby Data in Modal
            bornCategoryModel.setBabyCategoryModel(bornData.getBabyCategoryModel());

            this.bornCategoryRepo.save(bornCategoryModel);

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
    public ResponseEntity<?> getBornCategoryById(long categoryId) {
        try {
            BornCategoryModel bornCategoryModel = this.bornCategoryRepo.findById(categoryId).orElseThrow(
                    () -> new RuntimeException("Data not Found ! Error"));
            BornCategoryDto bornCategoryDto = modelMapper.map(bornCategoryModel, BornCategoryDto.class);
            logger.info("Born Category Fetch Success !");
            return ResponseGenerator.generateSuccessResponse(bornCategoryDto , "Success");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            logger.error("");
            return ResponseGenerator.generateBadRequestResponse(e.getMessage() , "Error");
        }
    }
}
