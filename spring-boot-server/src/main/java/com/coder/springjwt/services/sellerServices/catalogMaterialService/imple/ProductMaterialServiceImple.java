package com.coder.springjwt.services.sellerServices.catalogMaterialService.imple;

import com.coder.springjwt.constants.sellerConstants.sellerMessageConstants.SellerMessageResponse;
import com.coder.springjwt.dtos.sellerDtos.productMetaDtos.ProductMaterialDto;
import com.coder.springjwt.exception.customerPanelException.CategoryNotFoundException;
import com.coder.springjwt.exception.customerPanelException.DataNotFoundException;
import com.coder.springjwt.models.sellerModels.catalog.catalogMaterial.ProductMaterial;
import com.coder.springjwt.repository.sellerRepository.catalogRepos.ProductMaterialRepo;
import com.coder.springjwt.services.sellerServices.catalogMaterialService.ProductMaterialService;
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
public class ProductMaterialServiceImple implements ProductMaterialService {

    @Autowired
    private ProductMaterialRepo productMaterialRepo;

    @Autowired
    private ModelMapper modelMapper;

    Logger logger  = LoggerFactory.getLogger(ProductMaterialServiceImple.class);

    @Override
    public ResponseEntity<?> saveMaterial(ProductMaterialDto productMaterialDto) {
        MessageResponse response =new MessageResponse();
        try {
            ProductMaterial productMaterial =  modelMapper.map(productMaterialDto, ProductMaterial.class);
            logger.info("Object Mapper Convert Success");

            this.productMaterialRepo.save(productMaterial);
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
            ProductMaterial productMaterial = this.productMaterialRepo.findById(materialId).orElseThrow(
                    () -> new CategoryNotFoundException("Material id not Found"));

            this.productMaterialRepo.deleteById(productMaterial.getId());
            logger.info("Delete Success => Material id :: " + materialId );
            return ResponseGenerator.generateSuccessResponse("Delete Success" , SellerMessageResponse.SUCCESS);

        }
        catch (Exception e)
        {
            e.printStackTrace();
            logger.error("Material Could Not be deleted");
            return ResponseGenerator.generateBadRequestResponse
                    ("Material not deleted :: " + e.getMessage() , SellerMessageResponse.FAILED);
        }
    }

    @Override
    public ResponseEntity<?> getCatalogMaterialById(long materialId) {
        try {
            ProductMaterial productMaterial = this.productMaterialRepo.findById(materialId).orElseThrow(
                    () -> new RuntimeException("Data not Found ! Error"));
            ProductMaterialDto productMaterialDto = modelMapper.map(productMaterial, ProductMaterialDto.class);
            logger.info("Material Fetch Success !");
            return ResponseGenerator.generateSuccessResponse(productMaterialDto, SellerMessageResponse.SUCCESS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            logger.error("Failed To fetch Material By Id");
            return ResponseGenerator.generateBadRequestResponse(e.getMessage() , SellerMessageResponse.FAILED);
        }
    }

    @Override
    public ResponseEntity<?> updateCatalogMaterial(ProductMaterialDto productMaterialDto) {
        try {
            logger.info("Update Child Process Starting....");
            this.productMaterialRepo.findById(productMaterialDto.getId())
                    .orElseThrow(()->new DataNotFoundException("Data not Found"));

            ProductMaterial productMaterial =  modelMapper.map(productMaterialDto, ProductMaterial.class);
            this.productMaterialRepo.save(productMaterial);

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
    public ResponseEntity<?> getCatalogMaterial(Integer page, Integer size) {
        MessageResponse response = new MessageResponse();
        try {
            Page<ProductMaterial> catalogMaterials = this.productMaterialRepo.findAll(PageRequest.of(page , size, Sort.by("id").descending()));
            if(catalogMaterials.isEmpty())
            {
                log.info("Data Not found :::: {} " + ProductMaterialServiceImple.class.getName());
                response.setStatus(HttpStatus.BAD_GATEWAY);
                response.setMessage(SellerMessageResponse.DATA_NOT_FOUND);
                return ResponseGenerator.generateBadRequestResponse(response, SellerMessageResponse.DATA_NOT_FOUND);
            }else{
                log.info("Data fetch Success :::: {}" + UserServiceImple.class.getName());
                response.setStatus(HttpStatus.OK);
                return ResponseGenerator.generateSuccessResponse(catalogMaterials, SellerMessageResponse.SUCCESS);
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
