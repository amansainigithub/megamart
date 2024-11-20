package com.coder.springjwt.services.adminServices.catalogBreathService.imple;

import com.coder.springjwt.constants.adminConstants.adminMessageConstants.AdminMessageResponse;
import com.coder.springjwt.constants.sellerConstants.sellerMessageConstants.SellerMessageResponse;
import com.coder.springjwt.dtos.adminDtos.catalogDtos.CatalogBreathDto;
import com.coder.springjwt.models.adminModels.catalog.catalogBreath.CatalogBreathModel;
import com.coder.springjwt.repository.adminRepository.catalogRepos.CatalogBreathRepo;
import com.coder.springjwt.services.adminServices.catalogBreathService.CatalogBreathService;
import com.coder.springjwt.services.sellerServices.sellerStoreService.userService.userServiceImple.UserServiceImple;
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
public class CatalogBreathServiceImple implements CatalogBreathService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CatalogBreathRepo catalogBreathRepo;
    @Override
    public ResponseEntity<?> saveCatalogBreath(CatalogBreathDto catalogBreathDto) {
        MessageResponse response =new MessageResponse();
        try {
            CatalogBreathModel catalogBreathModel=  modelMapper.map(catalogBreathDto , CatalogBreathModel.class);
            log.info("Object Mapper Convert Success");

            this.catalogBreathRepo.save(catalogBreathModel);
            log.info("Catalog Breath Success");

            response.setMessage("Catalog Breath Success");
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
    public ResponseEntity<?> getCatalogBreath(Integer page, Integer size) {
        MessageResponse response = new MessageResponse();
        try {
            Page<CatalogBreathModel> catalogMaterials = this.catalogBreathRepo.findAll(PageRequest.of(page , size, Sort.by("id").descending()));
            if(catalogMaterials.isEmpty())
            {
                log.info("Data Not found :::: {} " + CatalogBreathServiceImple.class.getName());
                response.setStatus(HttpStatus.BAD_GATEWAY);
                response.setMessage(AdminMessageResponse.DATA_NOT_FOUND);
                return ResponseGenerator.generateBadRequestResponse(response, AdminMessageResponse.DATA_NOT_FOUND);
            }else{
                log.info("Data fetch Success :::: {}" + UserServiceImple.class.getName());
                response.setStatus(HttpStatus.OK);
                return ResponseGenerator.generateSuccessResponse(catalogMaterials, AdminMessageResponse.SUCCESS);
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
