package com.coder.springjwt.services.adminServices.categories.ParentCategory;

import com.coder.springjwt.bucket.bucketModels.BucketModel;
import com.coder.springjwt.bucket.bucketService.BucketService;
import com.coder.springjwt.constants.sellerConstants.sellerMessageConstants.SellerMessageResponse;
import com.coder.springjwt.dtos.adminDtos.categoriesDtos.parentDtos.ParentCategoryDto;
import com.coder.springjwt.exception.adminException.CategoryNotFoundException;
import com.coder.springjwt.exception.adminException.DataNotFoundException;
import com.coder.springjwt.models.adminModels.categories.ParentCategoryModel;
import com.coder.springjwt.repository.adminRepository.categories.ParentCategoryRepo;
import com.coder.springjwt.util.MessageResponse;
import com.coder.springjwt.util.ResponseGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

@Service
public class ParentCategoryServiceImple implements com.coder.springjwt.services.adminServices.categories.ParentCategoryService {

    @Autowired
    ParentCategoryRepo parentCategoryRepo;

    @Autowired
    BucketService bucketService;

    @Autowired
    private ModelMapper modelMapper;


    Logger logger =  LoggerFactory.getLogger(ParentCategoryServiceImple.class);

    @Override
    public ResponseEntity<?> saveParentCategory(ParentCategoryDto parentCategoryDto) {
        MessageResponse response =new MessageResponse();
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            parentCategoryDto.setUser(auth.getName());

            //Convert DTO TO Model Class...
            ParentCategoryModel parentCategoryModel =  modelMapper.map(parentCategoryDto , ParentCategoryModel.class);
            logger.info("model mapper Conversion Success");

            //save Category
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



    @Autowired
    private ObjectMapper objectMapper;



    @Override
    public ResponseEntity<?> getParentCategoryList() {

        String listJson = (String) this.getValue("parentData");

        try {
           if (listJson == null) {
               System.out.println("First To Get From Database");
               // Fetch data from the database
               List<ParentCategoryModel> parentList = this.parentCategoryRepo.findAll();
               // Serialize the list to JSON and store it in Redis
               this.setValue("parentData", objectMapper.writeValueAsString(parentList));

               return ResponseGenerator.generateSuccessResponse(parentList, "Success");

           } else {
               System.out.print("====================Redis Server=================");
               // Deserialize the JSON string to a List<ParentCategoryModel>
               List<ParentCategoryModel> parentList = objectMapper.readValue(listJson, new TypeReference<List<ParentCategoryModel>>() {});
               return ResponseGenerator.generateSuccessResponse(parentList, "Success");

           }
       }
       catch (Exception e)
       {
           e.printStackTrace();
           return ResponseGenerator.generateBadRequestResponse(SellerMessageResponse.SOMETHING_WENT_WRONG);
       }
    }

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void setValue(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public String getValue(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

    public void deleteKey(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public ResponseEntity<?> deleteCategoryById(long categoryId) {
        try {
            ParentCategoryModel data = this.parentCategoryRepo.findById(categoryId).orElseThrow(
                    () -> new CategoryNotFoundException("Category id not Found"));

        this.parentCategoryRepo.deleteById(data.getId());
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
    public ResponseEntity<?> getParentCategoryById(long categoryId) {
        try {
            ParentCategoryModel data = this.parentCategoryRepo.findById(categoryId).orElseThrow(
                    () -> new RuntimeException("Data not Found ! Error"));
            return ResponseGenerator.generateSuccessResponse(data , "Success");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            logger.error("");
            return ResponseGenerator.generateBadRequestResponse(e.getMessage() , "Error");
        }
    }

    @Override
    public ResponseEntity<?> updateParentCategory(ParentCategoryDto parentCategoryDto ) {
        MessageResponse response = new MessageResponse();
        try {
            logger.info(parentCategoryDto.toString());
            logger.info("Update Process Starting....");
            ParentCategoryModel parentData =  this.parentCategoryRepo.findById(parentCategoryDto.getId()).orElseThrow(()->new DataNotFoundException("Dara not Found"));

            //Delete Bucket -->AWS
            logger.info("File Delete Success AWS");
            this.bucketService.deleteFile(parentData.getCategoryFile());

           ParentCategoryModel parentCategoryModel =  modelMapper.map(parentCategoryDto , ParentCategoryModel.class);
            this.parentCategoryRepo.save(parentCategoryModel);

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
    public ResponseEntity<?> updateParentCategoryFile(MultipartFile file, Long parentCategoryId) {
        try {
            ParentCategoryModel parentCategoryModel = this.parentCategoryRepo.findById(parentCategoryId).orElseThrow(()-> new DataNotFoundException("DATA_NOT_FOUND"));

            //Delete old File
            bucketService.deleteFile(parentCategoryModel.getCategoryFile());

            //upload New File
            BucketModel bucketModel = bucketService.uploadFile(file);
            if(bucketModel != null)
            {
                parentCategoryModel.setCategoryFile(bucketModel.getBucketUrl());
                this.parentCategoryRepo.save(parentCategoryModel);
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
