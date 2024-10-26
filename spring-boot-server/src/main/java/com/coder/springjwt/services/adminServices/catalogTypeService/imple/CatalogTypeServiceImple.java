package com.coder.springjwt.services.adminServices.catalogTypeService.imple;

import com.coder.springjwt.constants.adminConstants.adminMessageConstants.AdminMessageResponse;
import com.coder.springjwt.constants.sellerConstants.sellerMessageConstants.SellerMessageResponse;
import com.coder.springjwt.dtos.adminDtos.catalogDtos.CatalogTypeDto;
import com.coder.springjwt.exception.adminException.CategoryNotFoundException;
import com.coder.springjwt.exception.adminException.DataNotFoundException;
import com.coder.springjwt.models.adminModels.catalog.catalogType.CatalogTypeModel;
import com.coder.springjwt.repository.adminRepository.catalogRepos.CatalogTypeRepo;
import com.coder.springjwt.services.adminServices.catalogTypeService.CatalogTypeService;
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
public class CatalogTypeServiceImple implements CatalogTypeService {

    @Autowired
    private CatalogTypeRepo catalogTypeRepo;

    @Autowired
    private ModelMapper modelMapper;

    Logger logger  = LoggerFactory.getLogger(CatalogTypeServiceImple.class);


    @Override
    public ResponseEntity<?> saveCatalogType(CatalogTypeDto catalogTypeDto) {

        MessageResponse response =new MessageResponse();
        try {
            CatalogTypeModel catalogTypeModel=  modelMapper.map(catalogTypeDto , CatalogTypeModel.class);
            logger.info("Object Mapper Convert Success");

            this.catalogTypeRepo.save(catalogTypeModel);
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
    public ResponseEntity<?> deleteCatalogType(long typeId) {
        try {
            CatalogTypeModel catalogTypeModel = this.catalogTypeRepo.findById(typeId).orElseThrow(
                    () -> new CategoryNotFoundException("Catalog Type id not Found"));

            this.catalogTypeRepo.deleteById(catalogTypeModel.getId());
            logger.info("Delete Success => Type id :: " + typeId );
            return ResponseGenerator.generateSuccessResponse("Delete Success" , AdminMessageResponse.SUCCESS);

        }
        catch (Exception e)
        {
            e.printStackTrace();
            logger.error("Catalog Type Could Not be deleted");
            return ResponseGenerator.generateBadRequestResponse
                    ("Catalog Type Could not deleted :: " + e.getMessage() , AdminMessageResponse.FAILED);
        }
    }

    @Override
    public ResponseEntity<?> getCatalogTypeById(long typeId) {
        try {
            CatalogTypeModel  catalogTypeModel= this.catalogTypeRepo.findById(typeId).orElseThrow(
                    () -> new RuntimeException("Data not Found ! Error"));
            CatalogTypeDto catalogTypeDto = modelMapper.map(catalogTypeModel, CatalogTypeDto.class);
            logger.info("Catalog Type Fetch Success !");
            return ResponseGenerator.generateSuccessResponse(catalogTypeDto , AdminMessageResponse.SUCCESS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            logger.error("Failed To fetch Catalog Type By Id");
            return ResponseGenerator.generateBadRequestResponse(e.getMessage() , AdminMessageResponse.FAILED);
        }
    }

    @Override
    public ResponseEntity<?> updateCatalogType(CatalogTypeDto catalogTypeDto) {
        try {
            logger.info("Update Child Process Starting....");
            this.catalogTypeRepo.findById(catalogTypeDto.getId())
                    .orElseThrow(()->new DataNotFoundException("Data not Found"));

            CatalogTypeModel catalogTypeModel =  modelMapper.map(catalogTypeDto , CatalogTypeModel.class);
            this.catalogTypeRepo.save(catalogTypeModel);

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
    public ResponseEntity<?> getCatalogType(Integer page, Integer size) {
        MessageResponse response = new MessageResponse();
        try {
            Page<CatalogTypeModel> catalogTypeList = this.catalogTypeRepo.findAll(PageRequest.of(page , size, Sort.by("id").descending()));
            if(catalogTypeList.isEmpty())
            {
                log.info("Data Not found :::: {} " + CatalogTypeServiceImple.class.getName());

                response.setStatus(HttpStatus.BAD_GATEWAY);
                response.setMessage(AdminMessageResponse.DATA_NOT_FOUND);
                return ResponseGenerator.generateBadRequestResponse(response, AdminMessageResponse.DATA_NOT_FOUND);
            }else{
                log.info("Data fetch Success :::: {}" + CatalogTypeServiceImple.class.getName());

                response.setStatus(HttpStatus.OK);
                return ResponseGenerator.generateSuccessResponse(catalogTypeList, AdminMessageResponse.SUCCESS);
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
