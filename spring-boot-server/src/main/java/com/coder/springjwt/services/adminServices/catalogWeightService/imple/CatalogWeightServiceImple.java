package com.coder.springjwt.services.adminServices.catalogWeightService.imple;

import com.coder.springjwt.constants.adminConstants.adminMessageConstants.AdminMessageResponse;
import com.coder.springjwt.constants.sellerConstants.sellerMessageConstants.SellerMessageResponse;
import com.coder.springjwt.dtos.adminDtos.catalogDtos.CatalogWeightDto;
import com.coder.springjwt.models.adminModels.catalog.catalogBrand.CatalogBrandModel;
import com.coder.springjwt.models.adminModels.catalog.catalogWeight.CatalogWeightModel;
import com.coder.springjwt.repository.adminRepository.catalogRepos.CatalogWeightRepo;
import com.coder.springjwt.services.adminServices.catalogBrandService.imple.CatalogBrandServiceImple;
import com.coder.springjwt.services.adminServices.catalogWeightService.CatalogWeightService;
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
public class CatalogWeightServiceImple implements CatalogWeightService {

    @Autowired
    private CatalogWeightRepo catalogWeightRepo;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public ResponseEntity<?> saveCatalogWeight(CatalogWeightDto catalogWeightDto) {
        MessageResponse response =new MessageResponse();
        try {
            CatalogWeightModel catalogWeightModel =  modelMapper.map(catalogWeightDto , CatalogWeightModel.class);
            log.info("Object Mapper Convert Success");

            this.catalogWeightRepo.save(catalogWeightModel);
            log.info("Catalog Weight Saved Success");

            response.setMessage("Catalog Weight Saved Success");
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
    public ResponseEntity<?> getCatalogWeight(Integer page, Integer size) {
        MessageResponse response = new MessageResponse();
        try {
            Page<CatalogWeightModel> catalogWeightModels = this.catalogWeightRepo.findAll(PageRequest.of(page , size, Sort.by("id").descending()));
            if(catalogWeightModels.isEmpty())
            {
                log.info("Data Not found :::: {} " + CatalogBrandServiceImple.class.getName());

                response.setStatus(HttpStatus.BAD_GATEWAY);
                response.setMessage(AdminMessageResponse.DATA_NOT_FOUND);
                return ResponseGenerator.generateBadRequestResponse(response, AdminMessageResponse.DATA_NOT_FOUND);
            }else{
                log.info("Data fetch Success :::: {}" + CatalogBrandServiceImple.class.getName());

                response.setStatus(HttpStatus.OK);
                return ResponseGenerator.generateSuccessResponse(catalogWeightModels, AdminMessageResponse.SUCCESS);
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
