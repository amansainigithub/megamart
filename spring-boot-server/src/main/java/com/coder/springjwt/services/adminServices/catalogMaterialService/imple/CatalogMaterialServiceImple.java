package com.coder.springjwt.services.adminServices.catalogMaterialService.imple;

import com.coder.springjwt.constants.adminConstants.adminMessageConstants.AdminMessageResponse;
import com.coder.springjwt.constants.sellerConstants.sellerMessageConstants.SellerMessageResponse;
import com.coder.springjwt.dtos.adminDtos.catalogDtos.CatalogMaterialDto;
import com.coder.springjwt.exception.adminException.CategoryNotFoundException;
import com.coder.springjwt.exception.adminException.DataNotFoundException;
import com.coder.springjwt.models.adminModels.catalog.catalogMaterial.CatalogMaterial;
import com.coder.springjwt.repository.adminRepository.catalogRepos.CatalogMaterialRepo;
import com.coder.springjwt.services.adminServices.catalogMaterialService.CatalogMaterialService;
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
public class CatalogMaterialServiceImple implements CatalogMaterialService {

    @Autowired
    private CatalogMaterialRepo catalogMaterialRepo;

    @Autowired
    private ModelMapper modelMapper;

    Logger logger  = LoggerFactory.getLogger(CatalogMaterialServiceImple.class);

    @Override
    public ResponseEntity<?> saveCatalogMaterial(CatalogMaterialDto catalogMaterialDto) {
        MessageResponse response =new MessageResponse();
        try {
            CatalogMaterial catalogMaterial=  modelMapper.map(catalogMaterialDto , CatalogMaterial.class);
            logger.info("Object Mapper Convert Success");

            this.catalogMaterialRepo.save(catalogMaterial);
            logger.info("Material Saved Success");

            response.setMessage("Material Saved Success");
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
    public ResponseEntity<?> deleteCatalogMaterial(long materialId) {
        try {
            CatalogMaterial catalogMaterial = this.catalogMaterialRepo.findById(materialId).orElseThrow(
                    () -> new CategoryNotFoundException("Material id not Found"));

            this.catalogMaterialRepo.deleteById(catalogMaterial.getId());
            logger.info("Delete Success => Material id :: " + materialId );
            return ResponseGenerator.generateSuccessResponse("Delete Success" , AdminMessageResponse.SUCCESS);

        }
        catch (Exception e)
        {
            e.printStackTrace();
            logger.error("Material Could Not be deleted");
            return ResponseGenerator.generateBadRequestResponse
                    ("Material not deleted :: " + e.getMessage() , AdminMessageResponse.FAILED);
        }
    }

    @Override
    public ResponseEntity<?> getCatalogMaterialById(long materialId) {
        try {
            CatalogMaterial  catalogMaterial= this.catalogMaterialRepo.findById(materialId).orElseThrow(
                    () -> new RuntimeException("Data not Found ! Error"));
            CatalogMaterialDto catalogMaterialDto = modelMapper.map(catalogMaterial, CatalogMaterialDto.class);
            logger.info("Material Fetch Success !");
            return ResponseGenerator.generateSuccessResponse(catalogMaterialDto , AdminMessageResponse.SUCCESS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            logger.error("Failed To fetch Material By Id");
            return ResponseGenerator.generateBadRequestResponse(e.getMessage() , AdminMessageResponse.FAILED);
        }
    }

    @Override
    public ResponseEntity<?> updateCatalogMaterial(CatalogMaterialDto catalogMaterialDto) {
        try {
            logger.info("Update Child Process Starting....");
            this.catalogMaterialRepo.findById(catalogMaterialDto.getId())
                    .orElseThrow(()->new DataNotFoundException("Data not Found"));

            CatalogMaterial catalogMaterial =  modelMapper.map(catalogMaterialDto , CatalogMaterial.class);
            this.catalogMaterialRepo.save(catalogMaterial);

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
    public ResponseEntity<?> getCatalogMaterial(Integer page, Integer size) {
        MessageResponse response = new MessageResponse();
        try {
            Page<CatalogMaterial> catalogMaterials = this.catalogMaterialRepo.findAll(PageRequest.of(page , size, Sort.by("id").descending()));
            if(catalogMaterials.isEmpty())
            {
                log.info("Data Not found :::: {} " + CatalogMaterialServiceImple.class.getName());
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
