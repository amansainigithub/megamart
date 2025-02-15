package com.coder.springjwt.exception.services.sellerServices.categories.babyCategoryImple;

import com.coder.springjwt.bucket.bucketModels.BucketModel;
import com.coder.springjwt.bucket.bucketService.BucketService;
import com.coder.springjwt.dtos.sellerDtos.categoriesDtos.babyDto.BabyCategoryDto;
import com.coder.springjwt.exception.customerException.CategoryNotFoundException;
import com.coder.springjwt.exception.customerException.DataNotFoundException;
import com.coder.springjwt.exception.services.sellerServices.categories.ParentCategory.ParentCategoryServiceImple;
import com.coder.springjwt.models.sellerModels.categories.BabyCategoryModel;
import com.coder.springjwt.models.sellerModels.categories.ChildCategoryModel;
import com.coder.springjwt.repository.sellerRepository.categories.BabyCategoryRepo;
import com.coder.springjwt.repository.sellerRepository.categories.ChildCategoryRepo;
import com.coder.springjwt.exception.services.sellerServices.categories.BabyCategoryService;
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

    @Override
    public ResponseEntity<?> updateBabyCategory(BabyCategoryDto babyCategoryDto) {
        MessageResponse response = new MessageResponse();
        try {

           logger.info(babyCategoryDto.toString());

           this.babyCategoryRepo.findById(babyCategoryDto.getId()).orElseThrow(()->new DataNotFoundException("Data not Found"));
           BabyCategoryModel babyCategoryModel =  modelMapper.map(babyCategoryDto , BabyCategoryModel.class);

           //Get Child Data
           ChildCategoryModel childCategoryModel =
                   this.childCategoryRepo.findById(Long.valueOf(babyCategoryDto.getChildCategoryId())).get();
           babyCategoryModel.setChildCategoryModel(childCategoryModel);

           this.babyCategoryRepo.save(babyCategoryModel);

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
    public ResponseEntity<?> getBabyCategoryById(long categoryId) {
        try {
            BabyCategoryModel babyCategoryModel = this.babyCategoryRepo.findById(categoryId).orElseThrow(
                    () -> new RuntimeException("Data not Found ! Error"));
            BabyCategoryDto babyCategoryDto = modelMapper.map(babyCategoryModel, BabyCategoryDto.class);
            logger.info("Child Category Fetch Success !");
            return ResponseGenerator.generateSuccessResponse(babyCategoryDto , "Success");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            logger.error("");
            return ResponseGenerator.generateBadRequestResponse(e.getMessage() , "Error");
        }
    }

    @Override
    public ResponseEntity<?> updateBabyCategoryFile(MultipartFile file, Long babyCategoryId) {
        try {
            BabyCategoryModel babyCategoryModel = this.babyCategoryRepo.findById(babyCategoryId).orElseThrow(()-> new DataNotFoundException("DATA_NOT_FOUND"));

            //Delete old File
            try {
                bucketService.deleteFile(babyCategoryModel.getCategoryFile());
            }catch (Exception e)
            {
                logger.error("File Not deleted Id:: " + babyCategoryId);
            }
            //upload New File
            BucketModel bucketModel = bucketService.uploadFile(file);
            if(bucketModel != null)
            {
                babyCategoryModel.setCategoryFile(bucketModel.getBucketUrl());
                this.babyCategoryRepo.save(babyCategoryModel);
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
