package com.coder.springjwt.services.sellerServices.catalogTypeService.imple;

import com.coder.springjwt.constants.sellerConstants.sellerMessageConstants.SellerMessageResponse;
import com.coder.springjwt.dtos.sellerDtos.productMetaDtos.ProductTypeDto;
import com.coder.springjwt.exception.customerException.CategoryNotFoundException;
import com.coder.springjwt.exception.customerException.DataNotFoundException;
import com.coder.springjwt.services.sellerServices.catalogTypeService.ProductTypeService;
import com.coder.springjwt.models.sellerModels.catalog.catalogType.ProductTypeModel;
import com.coder.springjwt.repository.sellerRepository.catalogRepos.ProductTypeRepo;
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
public class ProductTypeServiceImple implements ProductTypeService {

    @Autowired
    private ProductTypeRepo productTypeRepo;

    @Autowired
    private ModelMapper modelMapper;

    Logger logger  = LoggerFactory.getLogger(ProductTypeServiceImple.class);


    @Override
    public ResponseEntity<?> saveType(ProductTypeDto productTypeDto) {

        MessageResponse response =new MessageResponse();
        try {
            ProductTypeModel productTypeModel =  modelMapper.map(productTypeDto, ProductTypeModel.class);
            logger.info("Object Mapper Convert Success");

            this.productTypeRepo.save(productTypeModel);
            logger.info("Catalog-Type Saved Success");

            response.setMessage("Catalog-Type Saved Success");
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
    public ResponseEntity<?> deleteType(long typeId) {
        try {
            ProductTypeModel productTypeModel = this.productTypeRepo.findById(typeId).orElseThrow(
                    () -> new CategoryNotFoundException("Catalog Type id not Found"));

            this.productTypeRepo.deleteById(productTypeModel.getId());
            logger.info("Delete Success => Type id :: " + typeId );
            return ResponseGenerator.generateSuccessResponse("Delete Success" , SellerMessageResponse.SUCCESS);

        }
        catch (Exception e)
        {
            e.printStackTrace();
            logger.error("Catalog Type Could Not be deleted");
            return ResponseGenerator.generateBadRequestResponse
                    ("Catalog Type Could not deleted :: " + e.getMessage() , SellerMessageResponse.FAILED);
        }
    }

    @Override
    public ResponseEntity<?> getTypeById(long typeId) {
        try {
            ProductTypeModel productTypeModel = this.productTypeRepo.findById(typeId).orElseThrow(
                    () -> new RuntimeException("Data not Found ! Error"));
            ProductTypeDto productTypeDto = modelMapper.map(productTypeModel, ProductTypeDto.class);
            logger.info("Catalog Type Fetch Success !");
            return ResponseGenerator.generateSuccessResponse(productTypeDto, SellerMessageResponse.SUCCESS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            logger.error("Failed To fetch Catalog Type By Id");
            return ResponseGenerator.generateBadRequestResponse(e.getMessage() , SellerMessageResponse.FAILED);
        }
    }

    @Override
    public ResponseEntity<?> updateType(ProductTypeDto productTypeDto) {
        try {
            logger.info("Update Child Process Starting....");
            this.productTypeRepo.findById(productTypeDto.getId())
                    .orElseThrow(()->new DataNotFoundException("Data not Found"));

            ProductTypeModel productTypeModel =  modelMapper.map(productTypeDto, ProductTypeModel.class);
            this.productTypeRepo.save(productTypeModel);

            logger.info("Data Updated Success");
            return ResponseGenerator.generateSuccessResponse(SellerMessageResponse.SUCCESS ,
                    SellerMessageResponse.DATA_UPDATE_SUCCESS);
        }
        catch (Exception e)
        {
            logger.info("Data Update Failed");
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(SellerMessageResponse.FAILED ,"DATA_UPDATE_FAILED");
        }
    }

    @Override
    public ResponseEntity<?> getType(Integer page, Integer size) {
        MessageResponse response = new MessageResponse();
        try {
            Page<ProductTypeModel> catalogTypeList = this.productTypeRepo.findAll(PageRequest.of(page , size, Sort.by("id").descending()));
            if(catalogTypeList.isEmpty())
            {
                log.info("Data Not found :::: {} " + ProductTypeServiceImple.class.getName());

                response.setStatus(HttpStatus.BAD_GATEWAY);
                response.setMessage(SellerMessageResponse.DATA_NOT_FOUND);
                return ResponseGenerator.generateBadRequestResponse(response, SellerMessageResponse.DATA_NOT_FOUND);
            }else{
                log.info("Data fetch Success :::: {}" + ProductTypeServiceImple.class.getName());

                response.setStatus(HttpStatus.OK);
                return ResponseGenerator.generateSuccessResponse(catalogTypeList, SellerMessageResponse.SUCCESS);
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
