package com.coder.springjwt.services.adminServices.catalogSizeService.imple;

import com.coder.springjwt.constants.adminConstants.adminMessageConstants.AdminMessageResponse;
import com.coder.springjwt.constants.sellerConstants.sellerMessageConstants.SellerMessageResponse;
import com.coder.springjwt.dtos.adminDtos.catalogSizeDto.CatalogSizeDto;
import com.coder.springjwt.exception.adminException.CategoryNotFoundException;
import com.coder.springjwt.exception.adminException.DataNotFoundException;
import com.coder.springjwt.models.adminModels.catalog.catalogSize.CatalogSizeModel;
import com.coder.springjwt.repository.adminRepository.catalogRepos.CatalogSizeRepo;
import com.coder.springjwt.services.adminServices.catalogSizeService.CatalogSizeService;
import com.coder.springjwt.services.adminServices.hsnService.hsnServiceImple.HsnServiceImple;
import com.coder.springjwt.services.adminServices.userService.userServiceImple.UserServiceImple;
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
public class CatalogSizeServiceImple implements CatalogSizeService {

    @Autowired
    private CatalogSizeRepo catalogSizeRepo;

    @Autowired
    private ModelMapper modelMapper;

    Logger logger  = LoggerFactory.getLogger(CatalogSizeServiceImple.class);


    @Override
    public ResponseEntity<?> saveCatalogSize(CatalogSizeDto catalogSizeDto) {
        MessageResponse response =new MessageResponse();
        try {
            CatalogSizeModel catalogSizeModel=  modelMapper.map(catalogSizeDto , CatalogSizeModel.class);
            logger.info("Object Mapper Convert Success");

            this.catalogSizeRepo.save(catalogSizeModel);
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
    public ResponseEntity<?> deleteCatalogSize(long sizeId) {
        try {
            CatalogSizeModel catalogSizeModel = this.catalogSizeRepo.findById(sizeId).orElseThrow(
                    () -> new CategoryNotFoundException("HSN Code id not Found"));

            this.catalogSizeRepo.deleteById(catalogSizeModel.getId());
            logger.info("Delete Success => Catalog id :: " + sizeId );
            return ResponseGenerator.generateSuccessResponse("Delete Success" , AdminMessageResponse.SUCCESS);

        }
        catch (Exception e)
        {
            e.printStackTrace();
            logger.error("Catlog Size Could Not be deleted");
            return ResponseGenerator.generateBadRequestResponse
                    ("HSN Could not deleted :: " + e.getMessage() , AdminMessageResponse.FAILED);
        }
    }

    @Override
    public ResponseEntity<?> getCatalogSizeById(long sizeId) {
        try {
            CatalogSizeModel catalogSizeModel= this.catalogSizeRepo.findById(sizeId).orElseThrow(
                    () -> new RuntimeException("Data not Found ! Error"));
            CatalogSizeDto catalogSizeDto = modelMapper.map(catalogSizeModel, CatalogSizeDto.class);
            logger.info("Catalog Size Fetch Success !");
            return ResponseGenerator.generateSuccessResponse(catalogSizeDto , AdminMessageResponse.SUCCESS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            logger.error("Failed To fetch Catalog size By Id");
            return ResponseGenerator.generateBadRequestResponse(e.getMessage() , AdminMessageResponse.FAILED);
        }
    }

    @Override
    public ResponseEntity<?> updateCatalogSize(CatalogSizeDto catalogSizeDto) {
        try {
            logger.info("Update Catalog Size Process Starting....");
            this.catalogSizeRepo.findById(catalogSizeDto.getId())
                    .orElseThrow(()->new DataNotFoundException("Data not Found"));

            CatalogSizeModel catalogSizeModel =  modelMapper.map(catalogSizeDto , CatalogSizeModel.class);
            this.catalogSizeRepo.save(catalogSizeModel);

            logger.info("Data Updated Success");
            return ResponseGenerator.generateSuccessResponse(AdminMessageResponse.SUCCESS ,
                    AdminMessageResponse.DATA_UPDATE_SUCCESS);
        }
        catch (Exception e)
        {
            logger.info("Data Update Failed");
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(AdminMessageResponse.FAILED ,AdminMessageResponse.DATA_UPDATE_FAILED);
        }
    }

    @Override
    public ResponseEntity<?> getCatalogSize(Integer page, Integer size) {
        MessageResponse response = new MessageResponse();
        try {
            Page<CatalogSizeModel> catalogSizeData = this.catalogSizeRepo.findAll(PageRequest.of(page , size, Sort.by("id").descending()));
            if(catalogSizeData.isEmpty())
            {
                log.info("Data Not found :::: {} " + UserServiceImple.class.getName());
                response.setStatus(HttpStatus.BAD_GATEWAY);
                response.setMessage(AdminMessageResponse.DATA_NOT_FOUND);
                return ResponseGenerator.generateBadRequestResponse(response, AdminMessageResponse.DATA_NOT_FOUND);
            }else{
                log.info("Data fetch Success :::: {}" + CatalogSizeServiceImple.class.getName());

                response.setStatus(HttpStatus.OK);
                return ResponseGenerator.generateSuccessResponse(catalogSizeData, AdminMessageResponse.SUCCESS);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            response.setStatus(HttpStatus.BAD_REQUEST);
            response.setMessage(e.getMessage());
            return ResponseGenerator.generateBadRequestResponse(response, AdminMessageResponse.FAILED);
        }
    }
}
