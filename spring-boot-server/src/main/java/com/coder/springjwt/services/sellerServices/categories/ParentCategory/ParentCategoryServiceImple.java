package com.coder.springjwt.services.sellerServices.categories.ParentCategory;

import com.coder.springjwt.bucket.bucketModels.BucketModel;
import com.coder.springjwt.bucket.bucketService.BucketService;
import com.coder.springjwt.constants.sellerConstants.sellerMessageConstants.SellerMessageResponse;
import com.coder.springjwt.dtos.sellerDtos.categoriesDtos.parentDtos.ParentCategoryDto;
import com.coder.springjwt.exception.customerPanelException.CategoryNotFoundException;
import com.coder.springjwt.exception.customerPanelException.DataNotFoundException;
import com.coder.springjwt.models.sellerModels.categories.ParentCategoryModel;
import com.coder.springjwt.repository.sellerRepository.categories.ParentCategoryRepo;
import com.coder.springjwt.services.sellerServices.categories.ParentCategoryService;
import com.coder.springjwt.util.MessageResponse;
import com.coder.springjwt.util.ResponseGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Slf4j
public class ParentCategoryServiceImple implements ParentCategoryService {

    @Autowired
    ParentCategoryRepo parentCategoryRepo;
    @Autowired
    BucketService bucketService;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper objectMapper;



    @Override
    public ResponseEntity<?> saveParentCategory(ParentCategoryDto parentCategoryDto) {

        MessageResponse response =new MessageResponse();
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            parentCategoryDto.setUser(auth.getName());

            //Convert DTO TO Model Class...
            ParentCategoryModel parentCategoryModel =  modelMapper.map(parentCategoryDto , ParentCategoryModel.class);
            log.info("model mapper Conversion Success");

            //save Category
            this.parentCategoryRepo.save(parentCategoryModel);

            response.setMessage(SellerMessageResponse.PARENT_CATEGORY_NOT_FOUND);
            response.setStatus(HttpStatus.OK);
            return ResponseGenerator.generateSuccessResponse(response ,SellerMessageResponse.PARENT_CATEGORY_SAVED);
        }
        catch (DataIntegrityViolationException ex) {
            // Handle exception here
            response.setMessage(SellerMessageResponse.DUPLICATE_ENTRY_ERROR);
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
    public ResponseEntity<?> getParentCategoryList() {
        try {
               List<ParentCategoryModel> parentList = this.parentCategoryRepo.findAll();
                return ResponseGenerator.generateSuccessResponse(parentList, SellerMessageResponse.SUCCESS);
       }
       catch (Exception e)
       {
           e.printStackTrace();
           return ResponseGenerator.generateBadRequestResponse(SellerMessageResponse.SOMETHING_WENT_WRONG);
       }
    }



    @Override
    public ResponseEntity<?> deleteCategoryById(long categoryId) {
        try {
            ParentCategoryModel data = this.parentCategoryRepo.findById(categoryId).orElseThrow(
                    () -> new CategoryNotFoundException(SellerMessageResponse.ID_NOT_FOUND));

        this.parentCategoryRepo.deleteById(data.getId());
            log.info("Delete Success => Category id :: " + categoryId );
        return ResponseGenerator.generateSuccessResponse(SellerMessageResponse.DELETE_SUCCESS , SellerMessageResponse.SUCCESS);

    }
        catch (Exception e)
        {
                e.printStackTrace();
            log.error("Category Could Not deleted");
                return ResponseGenerator.generateBadRequestResponse
                        (SellerMessageResponse.CATEGORY_COULD_NOT_DELETE + e.getMessage()
                                , SellerMessageResponse.ERROR);
        }
    }

    @Override
    public ResponseEntity<?> getParentCategoryById(long categoryId) {

        try {
            ParentCategoryModel data = this.parentCategoryRepo.findById(categoryId).orElseThrow(
                    () -> new RuntimeException(SellerMessageResponse.DATA_NOT_FOUND));
            return ResponseGenerator.generateSuccessResponse(data , SellerMessageResponse.SUCCESS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(e.getMessage() , SellerMessageResponse.ERROR);
        }
    }

    @Override
    public ResponseEntity<?> updateParentCategory(ParentCategoryDto parentCategoryDto ) {

        MessageResponse response = new MessageResponse();
        try {
            ParentCategoryModel parentData =  this.parentCategoryRepo.findById(parentCategoryDto.getId()).orElseThrow(()->
                    new DataNotFoundException(SellerMessageResponse.DATA_NOT_FOUND));

            //Delete Bucket -->AWS
            this.bucketService.deleteFile(parentData.getCategoryFile());

           ParentCategoryModel parentCategoryModel =  modelMapper.map(parentCategoryDto , ParentCategoryModel.class);
            this.parentCategoryRepo.save(parentCategoryModel);

            log.info("Data Update Success");
            return ResponseGenerator.generateSuccessResponse(SellerMessageResponse.SUCCESS , SellerMessageResponse.DATA_UPDATE_SUCCESS);

        }
        catch (Exception e)
        {
            log.info("Data Update Failed");
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(SellerMessageResponse.FAILED ,SellerMessageResponse.DATA_UPDATE_FAILED);
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
                return ResponseGenerator.generateSuccessResponse(SellerMessageResponse.SUCCESS,SellerMessageResponse.FILE_UPDATE_SUCCESS);
            }
            else {
                log.error("Bucket Model is null | please check AWS bucket configuration");
                throw new Exception(SellerMessageResponse.AWS_BUCKET_IS_EMPTY);
            }
        }
        catch (Exception e)
        {
            log.info("Exception" , e.getMessage());
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(SellerMessageResponse.ERROR ,SellerMessageResponse.FILE_NOT_UPDATE);
        }
    }


}
