package com.coder.springjwt.services.sellerServices.categories.childCategoryImple;

import com.coder.springjwt.bucket.bucketModels.BucketModel;
import com.coder.springjwt.bucket.bucketService.BucketService;
import com.coder.springjwt.constants.sellerConstants.sellerMessageConstants.SellerMessageResponse;
import com.coder.springjwt.dtos.sellerDtos.categoriesDtos.childDtos.ChildCategoryDto;
import com.coder.springjwt.exception.customerPanelException.CategoryNotFoundException;
import com.coder.springjwt.exception.customerPanelException.DataNotFoundException;
import com.coder.springjwt.models.sellerModels.categories.ChildCategoryModel;
import com.coder.springjwt.models.sellerModels.categories.ParentCategoryModel;
import com.coder.springjwt.repository.sellerRepository.categories.ChildCategoryRepo;
import com.coder.springjwt.repository.sellerRepository.categories.ParentCategoryRepo;
import com.coder.springjwt.services.sellerServices.categories.ChildCategoryService;
import com.coder.springjwt.util.MessageResponse;
import com.coder.springjwt.util.ResponseGenerator;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ChildCategoryServiceImple implements ChildCategoryService {

    @Autowired
    ChildCategoryRepo childCategoryRepo;

    @Autowired
    ParentCategoryRepo parentCategoryRepo;

    @Autowired
    BucketService bucketService;


    @Autowired
    private ModelMapper modelMapper;


    @Override
    public ResponseEntity<?> saveChildCategory(ChildCategoryDto childCategoryDto) {
        MessageResponse response =new MessageResponse();
        try {
            ChildCategoryModel childCategoryModel =  modelMapper.map(childCategoryDto , ChildCategoryModel.class);
            log.info("Mapper Convert Success");

            Optional <ParentCategoryModel>  parentOptional=
                    this.parentCategoryRepo.findById(Long.parseLong(childCategoryDto.getParentCategoryId()));

            if(parentOptional.isPresent()) {
                log.info("Data Present Success");
                childCategoryModel.setParentCategory(parentOptional.get());

                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                childCategoryModel.setUser(auth.getName());
                //save Category
                this.childCategoryRepo.save(childCategoryModel);

                log.info("Child-Category Saved Success");
                response.setMessage(SellerMessageResponse.CHILD_CATEGORY_SAVED_SUCCESS);
                response.setStatus(HttpStatus.OK);
                return ResponseGenerator.generateSuccessResponse(response, SellerMessageResponse.SUCCESS);
            }
            else{
                log.error("parent Category Not Found Via Id : "+ childCategoryDto.getParentCategoryId());
                response.setMessage(SellerMessageResponse.PARENT_CATEGORY_NOT_FOUND + childCategoryDto.getParentCategoryId());
                response.setStatus(HttpStatus.BAD_REQUEST);
                return ResponseGenerator.generateBadRequestResponse(response);
            }
        }
        catch (DataIntegrityViolationException ex) {
            // Handle exception here
            log.error("Duplicate entry error: ");
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
    public ResponseEntity<?> getChildCategoryList() {

        try {
          List<ChildCategoryModel> childList =  this.childCategoryRepo.findAll();
          List<ChildCategoryDto> childCategoryDtos =   childList
                  .stream()
                  .map(category -> modelMapper.map(category, ChildCategoryDto.class))
                  .collect(Collectors.toList());
          return ResponseGenerator.generateSuccessResponse(childCategoryDtos,SellerMessageResponse.SUCCESS);
      }
      catch (Exception e)
      {
          e.printStackTrace();
          return ResponseGenerator.generateBadRequestResponse(SellerMessageResponse.FAILED);
      }
    }

    @Override
    public ResponseEntity<?> deleteChildCategoryById(long categoryId) {

        try {
            ChildCategoryModel data = this.childCategoryRepo.findById(categoryId).orElseThrow(
                    () -> new CategoryNotFoundException(SellerMessageResponse.ID_NOT_FOUND));

            this.childCategoryRepo.deleteById(data.getId());
            log.info("Delete Success => Category id :: " + categoryId );
            return ResponseGenerator.generateSuccessResponse(SellerMessageResponse.DELETE_SUCCESS , SellerMessageResponse.SUCCESS);

        }
        catch (Exception e)
        {
            e.printStackTrace();
            log.error("Category Could Not deleted");
            return ResponseGenerator.generateBadRequestResponse
                    (SellerMessageResponse.CATEGORY_COULD_NOT_DELETE + e.getMessage() , SellerMessageResponse.ERROR);
        }
    }

    @Override
    public ResponseEntity<?> getChildCategoryById(long categoryId) {

        try {
            ChildCategoryModel childCategoryModel = this.childCategoryRepo.findById(categoryId).orElseThrow(
                    () -> new RuntimeException(SellerMessageResponse.DATA_NOT_FOUND));
            ChildCategoryDto childCategoryDto = modelMapper.map(childCategoryModel, ChildCategoryDto.class);
            log.info("Child Category Fetch Success !");
            return ResponseGenerator.generateSuccessResponse(childCategoryDto , SellerMessageResponse.SUCCESS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(e.getMessage() , SellerMessageResponse.ERROR);
        }
    }

    @Override
    public ResponseEntity<?> updateChildCategory(ChildCategoryDto childCategoryDto ) {

        MessageResponse response = new MessageResponse();
        try {
            log.info("Update Child Process Starting....");
            this.childCategoryRepo.findById(childCategoryDto.getId()).orElseThrow(()->new DataNotFoundException("Dara not Found"));

            ChildCategoryModel childCategorymodel =  modelMapper.map(childCategoryDto , ChildCategoryModel.class);
            this.childCategoryRepo.save(childCategorymodel);

            log.info("Data Update Success");
            return ResponseGenerator.generateSuccessResponse(SellerMessageResponse.SUCCESS, SellerMessageResponse.DATA_UPDATE_SUCCESS);

        }
        catch (Exception e)
        {
            log.info("Data Update Failed");
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(SellerMessageResponse.SUCCESS ,SellerMessageResponse.DATA_UPDATE_FAILED);
        }
    }

    @Override
    public ResponseEntity<?> updateParentCategoryFile(MultipartFile file, Long childCategoryId) {
        try {
            ChildCategoryModel childCategoryModel = this.childCategoryRepo
                    .findById(childCategoryId).orElseThrow(()-> new DataNotFoundException(SellerMessageResponse.DATA_NOT_FOUND));

            //Delete old File
            bucketService.deleteFile(childCategoryModel.getCategoryFile());

            //upload New File
            BucketModel bucketModel = bucketService.uploadFile(file);
            if(bucketModel != null)
            {
                childCategoryModel.setCategoryFile(bucketModel.getBucketUrl());
                this.childCategoryRepo.save(childCategoryModel);
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
            return ResponseGenerator.generateBadRequestResponse(SellerMessageResponse.ERROR,SellerMessageResponse.FILE_NOT_UPDATE);
        }
    }

}
