package com.coder.springjwt.services.adminServices.catalogNetQuantityService.imple;

import com.coder.springjwt.constants.adminConstants.adminMessageConstants.AdminMessageResponse;
import com.coder.springjwt.constants.sellerConstants.sellerMessageConstants.SellerMessageResponse;
import com.coder.springjwt.dtos.adminDtos.catalogDtos.CatalogNetQuantityDto;
import com.coder.springjwt.dtos.adminDtos.hsn.HsnCodesDto;
import com.coder.springjwt.exception.adminException.CategoryNotFoundException;
import com.coder.springjwt.exception.adminException.DataNotFoundException;
import com.coder.springjwt.models.adminModels.catalog.catalogNetQuantity.CatalogNetQuantityModel;
import com.coder.springjwt.models.adminModels.catalog.hsn.HsnCodes;
import com.coder.springjwt.repository.adminRepository.catalogRepos.CatalogNetQuantityRepo;
import com.coder.springjwt.services.adminServices.catalogNetQuantityService.CatalogNetQuantityService;
import com.coder.springjwt.services.adminServices.userService.userServiceImple.UserServiceImple;
import com.coder.springjwt.util.MessageResponse;
import com.coder.springjwt.util.ResponseGenerator;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
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
public class CatalogNetQuantityServiceImple implements CatalogNetQuantityService {

    @Autowired
    private CatalogNetQuantityRepo catalogNetQuantityRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ResponseEntity<?> saveCatalogNetQuantity(CatalogNetQuantityDto catalogNetQuantityDto) {
        MessageResponse response =new MessageResponse();
        try {
            CatalogNetQuantityModel catalogNetQuantityModel=  modelMapper.map(catalogNetQuantityDto , CatalogNetQuantityModel.class);
            log.info("Object Mapper Convert Success");

            this.catalogNetQuantityRepo.save(catalogNetQuantityModel);
            log.info("NetQuantity Saved Success");

            response.setMessage("NetQuantity Saved Success");
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
    public ResponseEntity<?> deleteNetQuantity(long netQuantityId) {
        try {
            CatalogNetQuantityModel catalogNetQuantityModel = this.catalogNetQuantityRepo.findById(netQuantityId).orElseThrow(
                    () -> new CategoryNotFoundException("Net Quantity id not Found"));

            this.catalogNetQuantityRepo.deleteById(catalogNetQuantityModel.getId());
            log.info("Delete Success => netQuantity id :: " + netQuantityId );
            return ResponseGenerator.generateSuccessResponse("Delete Success" , AdminMessageResponse.SUCCESS);

        }
        catch (Exception e)
        {
            e.printStackTrace();
            log.error("netQuantityId Could Not be deleted or not found");
            return ResponseGenerator.generateBadRequestResponse
                    ("netQuantityId Could not deleted :: " + e.getMessage() , AdminMessageResponse.FAILED);
        }
    }

    @Override
    public ResponseEntity<?> getNetQuantityById(long netQuantityId) {
        try {
            CatalogNetQuantityModel catalogNetQuantityModel= this.catalogNetQuantityRepo.findById(netQuantityId).orElseThrow(
                    () -> new RuntimeException("Data not Found ! Error"));
            CatalogNetQuantityDto catalogNetQuantityDto= modelMapper.map(catalogNetQuantityModel, CatalogNetQuantityDto.class);
            log.info("Hsn Code Fetch Success !");
            return ResponseGenerator.generateSuccessResponse(catalogNetQuantityDto , AdminMessageResponse.SUCCESS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            log.error("Failed To fetch netQuantity By Id");
            return ResponseGenerator.generateBadRequestResponse(e.getMessage() , AdminMessageResponse.FAILED);
        }
    }

    @Override
    public ResponseEntity<?> updateNetQuantity(CatalogNetQuantityDto catalogNetQuantityDto) {
        try {
            log.info("Update NetQuantity Process Starting....");
            this.catalogNetQuantityRepo.findById(catalogNetQuantityDto.getId())
                    .orElseThrow(()->new DataNotFoundException("Data not Found"));

            CatalogNetQuantityModel catalogNetQuantityModel =  modelMapper.map(catalogNetQuantityDto , CatalogNetQuantityModel.class);
            this.catalogNetQuantityRepo.save(catalogNetQuantityModel);

            log.info("Data Updated Success");
            return ResponseGenerator.generateSuccessResponse(AdminMessageResponse.SUCCESS ,
                    AdminMessageResponse.DATA_UPDATE_SUCCESS);
        }
        catch (Exception e)
        {
            log.info("Data Update Failed");
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(AdminMessageResponse.FAILED ,AdminMessageResponse.DATA_UPDATE_FAILED);
        }
    }

    @Override
    public ResponseEntity<?> getNetQuantity(Integer page, Integer size) {
        MessageResponse response = new MessageResponse();
        try {
            Page<CatalogNetQuantityModel> catalogNetQuantityModels = this.catalogNetQuantityRepo.findAll(PageRequest.of(page , size, Sort.by("id").descending()));
            if(catalogNetQuantityModels.isEmpty())
            {
                log.info("Data Not found :::: {} " + CatalogNetQuantityServiceImple.class.getName());

                response.setStatus(HttpStatus.BAD_GATEWAY);
                response.setMessage(AdminMessageResponse.DATA_NOT_FOUND);
                return ResponseGenerator.generateBadRequestResponse(response, AdminMessageResponse.DATA_NOT_FOUND);
            }else{
                log.info("Data fetch Success :::: {}" + CatalogNetQuantityServiceImple.class.getName());

                response.setStatus(HttpStatus.OK);
                return ResponseGenerator.generateSuccessResponse(catalogNetQuantityModels, AdminMessageResponse.SUCCESS);
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
