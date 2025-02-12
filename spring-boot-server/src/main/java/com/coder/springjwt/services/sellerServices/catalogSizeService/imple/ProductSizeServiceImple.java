package com.coder.springjwt.services.sellerServices.catalogSizeService.imple;

import com.coder.springjwt.constants.sellerConstants.sellerMessageConstants.SellerMessageResponse;
import com.coder.springjwt.dtos.sellerDtos.productMetaDtos.ProductSizeVariantDto;
import com.coder.springjwt.exception.customerException.CategoryNotFoundException;
import com.coder.springjwt.exception.customerException.DataNotFoundException;
import com.coder.springjwt.models.sellerModels.catalog.catalogSize.ProductSizeVariantModel;
import com.coder.springjwt.repository.sellerRepository.catalogRepos.ProductSizeRepo;
import com.coder.springjwt.services.sellerServices.catalogSizeService.ProductSizeService;
import com.coder.springjwt.services.sellerServices.sellerStoreService.userService.userServiceImple.UserServiceImple;
import com.coder.springjwt.util.MessageResponse;
import com.coder.springjwt.util.ResponseGenerator;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProductSizeServiceImple implements ProductSizeService {

    @Autowired
    private ProductSizeRepo productSizeRepo;

    @Autowired
    private ModelMapper modelMapper;

    Logger logger  = LoggerFactory.getLogger(ProductSizeServiceImple.class);


    @Override
    public ResponseEntity<?> saveSize(ProductSizeVariantDto productSizeVariantDto) {
        MessageResponse response =new MessageResponse();
        try {
            ProductSizeVariantModel productSizeVariantModel =  modelMapper.map(productSizeVariantDto, ProductSizeVariantModel.class);
            logger.info("Object Mapper Convert Success");

            this.productSizeRepo.save(productSizeVariantModel);
            logger.info("CATALOG size Saved Success");

            response.setMessage("Catalog Size Saved Success");
            response.setStatus(HttpStatus.OK);
            return ResponseGenerator.generateSuccessResponse(response, SellerMessageResponse.SUCCESS);

        }
        catch (DataIntegrityViolationException ex) {
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
    public ResponseEntity<?> deleteSize(long sizeId) {
        try {
            ProductSizeVariantModel productSizeVariantModel = this.productSizeRepo.findById(sizeId).orElseThrow(
                    () -> new CategoryNotFoundException("HSN Code id not Found"));

            this.productSizeRepo.deleteById(productSizeVariantModel.getId());
            logger.info("Delete Success => Catalog id :: " + sizeId );
            return ResponseGenerator.generateSuccessResponse("Delete Success" , SellerMessageResponse.SUCCESS);

        }
        catch (Exception e)
        {
            e.printStackTrace();
            logger.error("Catlog Size Could Not be deleted");
            return ResponseGenerator.generateBadRequestResponse
                    ("HSN Could not deleted :: " + e.getMessage() , SellerMessageResponse.FAILED);
        }
    }

    @Override
    public ResponseEntity<?> getSizeById(long sizeId) {
        try {
            ProductSizeVariantModel productSizeVariantModel = this.productSizeRepo.findById(sizeId).orElseThrow(
                    () -> new RuntimeException("Data not Found ! Error"));
            ProductSizeVariantDto productSizeVariantDto = modelMapper.map(productSizeVariantModel, ProductSizeVariantDto.class);
            logger.info("Catalog Size Fetch Success !");
            return ResponseGenerator.generateSuccessResponse(productSizeVariantDto, SellerMessageResponse.SUCCESS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            logger.error("Failed To fetch Catalog size By Id");
            return ResponseGenerator.generateBadRequestResponse(e.getMessage() , SellerMessageResponse.FAILED);
        }
    }

    @Override
    public ResponseEntity<?> updateSize(ProductSizeVariantDto productSizeVariantDto) {
        try {
            logger.info("Update Catalog Size Process Starting....");
            this.productSizeRepo.findById(productSizeVariantDto.getId())
                    .orElseThrow(()->new DataNotFoundException("Data not Found"));

            ProductSizeVariantModel productSizeVariantModel =  modelMapper.map(productSizeVariantDto, ProductSizeVariantModel.class);
            this.productSizeRepo.save(productSizeVariantModel);

            logger.info("Data Updated Success");
            return ResponseGenerator.generateSuccessResponse(SellerMessageResponse.SUCCESS ,
                    SellerMessageResponse.DATA_UPDATE_SUCCESS);
        }
        catch (Exception e)
        {
            logger.info("Data Update Failed");
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(SellerMessageResponse.FAILED ,SellerMessageResponse.DATA_UPDATE_FAILED);
        }
    }

    @Override
    public ResponseEntity<?> getSize(Integer page, Integer size) {
        MessageResponse response = new MessageResponse();
        try {
            Page<ProductSizeVariantModel> catalogSizeData = this.productSizeRepo.findAll(PageRequest.of(page , size, Sort.by("id").descending()));
            if(catalogSizeData.isEmpty())
            {
                log.info("Data Not found :::: {} " + UserServiceImple.class.getName());
                response.setStatus(HttpStatus.BAD_GATEWAY);
                response.setMessage(SellerMessageResponse.DATA_NOT_FOUND);
                return ResponseGenerator.generateBadRequestResponse(response, SellerMessageResponse.DATA_NOT_FOUND);
            }else{
                log.info("Data fetch Success :::: {}" + ProductSizeServiceImple.class.getName());

                response.setStatus(HttpStatus.OK);
                return ResponseGenerator.generateSuccessResponse(catalogSizeData, SellerMessageResponse.SUCCESS);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            response.setStatus(HttpStatus.BAD_REQUEST);
            response.setMessage(e.getMessage());
            return ResponseGenerator.generateBadRequestResponse(response, SellerMessageResponse.FAILED);
        }
    }
}
