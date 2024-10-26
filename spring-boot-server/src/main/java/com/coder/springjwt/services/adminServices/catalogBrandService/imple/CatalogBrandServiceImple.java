package com.coder.springjwt.services.adminServices.catalogBrandService.imple;

import com.coder.springjwt.constants.adminConstants.adminMessageConstants.AdminMessageResponse;
import com.coder.springjwt.constants.sellerConstants.sellerMessageConstants.SellerMessageResponse;
import com.coder.springjwt.dtos.adminDtos.catalogSizeDto.CatalogBrandDto;
import com.coder.springjwt.dtos.adminDtos.hsn.HsnCodesDto;
import com.coder.springjwt.exception.adminException.CategoryNotFoundException;
import com.coder.springjwt.exception.adminException.DataNotFoundException;
import com.coder.springjwt.models.adminModels.catalog.catalogBrand.CatalogBrandModel;
import com.coder.springjwt.models.adminModels.hsn.HsnCodes;
import com.coder.springjwt.repository.adminRepository.catalogRepos.CatalogBrandRepo;
import com.coder.springjwt.repository.adminRepository.catalogRepos.CatalogMaterialRepo;
import com.coder.springjwt.services.adminServices.catalogBrandService.CatalogBrandService;
import com.coder.springjwt.services.adminServices.catalogMaterialService.imple.CatalogMaterialServiceImple;
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
public class CatalogBrandServiceImple implements CatalogBrandService {

    @Autowired
    private CatalogBrandRepo catalogBrandRepo;

    @Autowired
    private ModelMapper modelMapper;

    Logger logger  = LoggerFactory.getLogger(CatalogBrandServiceImple.class);
    @Override
    public ResponseEntity<?> saveCatalogBrand(CatalogBrandDto catalogBrandDto) {
        MessageResponse response =new MessageResponse();
        try {
            CatalogBrandModel catalogBrandModel=  modelMapper.map(catalogBrandDto , CatalogBrandModel.class);
            logger.info("Object Mapper Convert Success");

            this.catalogBrandRepo.save(catalogBrandModel);
            logger.info("Brand Saved Success");

            response.setMessage("Brand Saved Success");
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
    public ResponseEntity<?> deleteBrand(long brandId) {
        try {
            CatalogBrandModel catalogBrandModel = this.catalogBrandRepo.findById(brandId).orElseThrow(
                    () -> new CategoryNotFoundException("HSN Code id not Found"));

            this.catalogBrandRepo.deleteById(catalogBrandModel.getId());
            logger.info("Delete Success => BRAND id :: " + brandId );
            return ResponseGenerator.generateSuccessResponse("Delete Success" , AdminMessageResponse.SUCCESS);

        }
        catch (Exception e)
        {
            e.printStackTrace();
            logger.error("BRAND Could Not be deleted");
            return ResponseGenerator.generateBadRequestResponse
                    ("BRAND Could not deleted :: " + e.getMessage() , AdminMessageResponse.FAILED);
        }
    }

    @Override
    public ResponseEntity<?> getCatalogBrandById(long brandId) {
        try {
            CatalogBrandModel  catalogBrandModel= this.catalogBrandRepo.findById(brandId).orElseThrow(
                    () -> new RuntimeException("Data not Found ! Error"));
            CatalogBrandDto catalogBrandDto = modelMapper.map(catalogBrandModel, CatalogBrandDto.class);
            logger.info("Brand Code Fetch Success !");
            return ResponseGenerator.generateSuccessResponse(catalogBrandDto , AdminMessageResponse.SUCCESS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            logger.error("Failed To fetch Brand By Id");
            return ResponseGenerator.generateBadRequestResponse(e.getMessage() , AdminMessageResponse.FAILED);
        }
    }

    @Override
    public ResponseEntity<?> updateCatalogBrand(CatalogBrandDto catalogBrandDto) {
        try {
            logger.info("Update Brand Process Starting....");
            this.catalogBrandRepo.findById(catalogBrandDto.getId())
                    .orElseThrow(()->new DataNotFoundException("Data not Found"));

            CatalogBrandModel catalogBrandModel =  modelMapper.map(catalogBrandDto , CatalogBrandModel.class);
            this.catalogBrandRepo.save(catalogBrandModel);

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
    public ResponseEntity<?> getCatalogBrand(Integer page, Integer size) {
        MessageResponse response = new MessageResponse();
        try {
            Page<CatalogBrandModel> catalogBrandModels = this.catalogBrandRepo.findAll(PageRequest.of(page , size, Sort.by("id").descending()));
            if(catalogBrandModels.isEmpty())
            {
                log.info("Data Not found :::: {} " + CatalogBrandServiceImple.class.getName());

                response.setStatus(HttpStatus.BAD_GATEWAY);
                response.setMessage(AdminMessageResponse.DATA_NOT_FOUND);
                return ResponseGenerator.generateBadRequestResponse(response, AdminMessageResponse.DATA_NOT_FOUND);
            }else{
                log.info("Data fetch Success :::: {}" + CatalogBrandServiceImple.class.getName());

                response.setStatus(HttpStatus.OK);
                return ResponseGenerator.generateSuccessResponse(catalogBrandModels, AdminMessageResponse.SUCCESS);
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
