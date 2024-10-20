package com.coder.springjwt.services.sellerServices.sellerStoreService.imple;

import com.coder.springjwt.constants.sellerConstants.sellerMessageConstants.SellerMessageResponse;
import com.coder.springjwt.models.sellerModels.sellerStore.SellerCatalog;
import com.coder.springjwt.payload.sellerPayloads.sellerPayload.SellerCatalogPayload;
import com.coder.springjwt.repository.sellerRepository.sellerStoreRepository.SellerCatalogRepository;
import com.coder.springjwt.services.sellerServices.sellerStoreService.SellerCatalogService;
import com.coder.springjwt.services.sellerServices.sellerStoreService.SellerStoreService;
import com.coder.springjwt.util.ResponseGenerator;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class SellerCatalogServiceImple implements SellerCatalogService {



    @Autowired
    private SellerCatalogRepository sellerCatalogRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ResponseEntity<?> sellerSaveCatalogService(SellerCatalogPayload sellerCatalogPayload) {
        try {
            sellerCatalogPayload.setCatalogStatus("DRAFT");
            SellerCatalog sellerCatalog = modelMapper.map(sellerCatalogPayload, SellerCatalog.class);
            this.sellerCatalogRepository.save(sellerCatalog);

            SellerCatalogPayload catalogPayload = new SellerCatalogPayload();
            catalogPayload.setId(String.valueOf(sellerCatalog.getId()));
            return ResponseGenerator.generateSuccessResponse(catalogPayload ,SellerMessageResponse.SUCCESS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(SellerMessageResponse.DATA_NOT_SAVED);
        }
    }

    @Override
    public ResponseEntity<?> getSellerCatalog(Long catalogId) {
        try {

            Optional<SellerCatalog> catalog = this.sellerCatalogRepository.findById(catalogId);

            if(catalog.isPresent())
            {
                SellerCatalog sellerCatalog = catalog.get();

                SellerCatalogPayload catalogNode = modelMapper.map(sellerCatalog, SellerCatalogPayload.class);

                log.info("Data Fetched Success :: Seller Catalog by Id" +SellerCatalogServiceImple.class.getName());

                return ResponseGenerator.generateSuccessResponse(catalogNode , SellerMessageResponse.SUCCESS);

            }else {

                return ResponseGenerator.generateBadRequestResponse(SellerMessageResponse.DATA_NOT_FOUND);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(SellerMessageResponse.FAILED);
        }
    }
}
