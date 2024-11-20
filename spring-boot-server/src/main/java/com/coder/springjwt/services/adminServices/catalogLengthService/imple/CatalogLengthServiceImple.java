package com.coder.springjwt.services.adminServices.catalogLengthService.imple;

import com.coder.springjwt.constants.adminConstants.adminMessageConstants.AdminMessageResponse;
import com.coder.springjwt.constants.sellerConstants.sellerMessageConstants.SellerMessageResponse;
import com.coder.springjwt.dtos.adminDtos.catalogDtos.CatalogLengthDto;
import com.coder.springjwt.models.adminModels.catalog.catalogLength.CatalogLengthModel;
import com.coder.springjwt.models.adminModels.catalog.catalogMaterial.CatalogMaterial;
import com.coder.springjwt.models.adminModels.catalog.catalogWeight.CatalogWeightModel;
import com.coder.springjwt.repository.adminRepository.catalogRepos.CatalogLengthRepo;
import com.coder.springjwt.repository.adminRepository.catalogRepos.CatalogWeightRepo;
import com.coder.springjwt.services.adminServices.catalogLengthService.CatalogLengthService;
import com.coder.springjwt.services.adminServices.catalogMaterialService.imple.CatalogMaterialServiceImple;
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
public class CatalogLengthServiceImple implements CatalogLengthService {

    @Autowired
    private CatalogLengthRepo catalogLengthRepo;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public ResponseEntity<?> saveCatalogLength(CatalogLengthDto catalogLengthDto) {
        MessageResponse response =new MessageResponse();
        try {
            CatalogLengthModel catalogLengthMode =  modelMapper.map(catalogLengthDto , CatalogLengthModel.class);
            log.info("Object Mapper Convert Success");

            this.catalogLengthRepo.save(catalogLengthMode);
            log.info("Catalog Length Saved Success");

            response.setMessage("Catalog Length Saved Success");
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
    public ResponseEntity<?> getCatalogLength(Integer page, Integer size) {
        MessageResponse response = new MessageResponse();
        try {
            Page<CatalogLengthModel> catalogLengthModels = this.catalogLengthRepo.findAll(PageRequest.of(page , size, Sort.by("id").descending()));
            if(catalogLengthModels.isEmpty())
            {
                log.info("Data Not found :::: {} " + CatalogMaterialServiceImple.class.getName());
                response.setStatus(HttpStatus.BAD_GATEWAY);
                response.setMessage(AdminMessageResponse.DATA_NOT_FOUND);
                return ResponseGenerator.generateBadRequestResponse(response, AdminMessageResponse.DATA_NOT_FOUND);
            }else{
                log.info("Data fetch Success :::: {}" + UserServiceImple.class.getName());
                response.setStatus(HttpStatus.OK);
                return ResponseGenerator.generateSuccessResponse(catalogLengthModels, AdminMessageResponse.SUCCESS);
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
