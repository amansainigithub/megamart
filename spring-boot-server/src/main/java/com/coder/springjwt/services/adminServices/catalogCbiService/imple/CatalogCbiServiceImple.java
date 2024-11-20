package com.coder.springjwt.services.adminServices.catalogCbiService.imple;

import com.coder.springjwt.constants.sellerConstants.sellerMessageConstants.SellerMessageResponse;
import com.coder.springjwt.helpers.userHelper.UserHelper;
import com.coder.springjwt.models.CatalogRole;
import com.coder.springjwt.models.adminModels.categories.BornCategoryModel;
import com.coder.springjwt.models.sellerModels.sellerStore.SellerCatalog;
import com.coder.springjwt.models.sellerModels.sellerStore.SellerStore;
import com.coder.springjwt.payload.sellerPayloads.sellerPayload.SellerCatalogPayload;
import com.coder.springjwt.repository.sellerRepository.sellerStoreRepository.SellerCatalogRepository;
import com.coder.springjwt.repository.sellerRepository.sellerStoreRepository.SellerStoreRepository;
import com.coder.springjwt.services.adminServices.catalogCbiService.CatalogCbiService;
import com.coder.springjwt.util.ResponseGenerator;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CatalogCbiServiceImple implements CatalogCbiService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SellerCatalogRepository sellerCatalogRepository;

    @Autowired
    private SellerStoreRepository sellerStoreRepository;


    @Override
    public ResponseEntity<?> getCatalogInProgressListService(int page , int size) {
        try {
            //Fetch the Progress Catalog List
                Page<SellerCatalog> catalogProgressList =
                        this.sellerCatalogRepository.findAllByCatalogStatusAndPageable(
                                String.valueOf(CatalogRole.QC_PROGRESS) , PageRequest.of(page, size));

                if(!catalogProgressList.isEmpty())
                {
                    log.info("Catalog Progress List Fetch Success");

                    return ResponseGenerator.generateSuccessResponse(catalogProgressList, SellerMessageResponse.SUCCESS);
                }else{
                    return ResponseGenerator.generateSuccessResponse(SellerMessageResponse.FAILED,
                            SellerMessageResponse.DATA_NOT_FOUND);
                }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateSuccessResponse(SellerMessageResponse.ERROR,
                    SellerMessageResponse.SOMETHING_WENT_WRONG);
        }
    }

    @Override
    public ResponseEntity<?> catalogInvestigationService(Long catalogId, SellerCatalogPayload sellerCatalogPayload, List<MultipartFile> files) {

        try {

            log.info("Catalog Id :: " + catalogId);

                // GET Current Username
                Map<String, String> currentUser = UserHelper.getCurrentUser();

                System.out.println("CatalogJsonData :: " + sellerCatalogPayload);

                Optional<SellerCatalog> currentCatalog = sellerCatalogRepository.findById(catalogId);

                if ( currentCatalog.isPresent()) {

                    //Get seller Store Data
                    SellerCatalog catalogNode = currentCatalog.get();
                    //convert Payload To Modal class
                    SellerCatalog sellerCatalog = modelMapper.map(sellerCatalogPayload, SellerCatalog.class);
                    sellerCatalog.setId(currentCatalog.get().getId());

                    //Set Seller Store
                    sellerCatalog.setSellerStore(catalogNode.getSellerStore());

                    //Discount Catalog
                    String discountPercentage = calculateDiscount(Double.valueOf(sellerCatalog.getMrp()), Double.valueOf(sellerCatalog.getSellActualPrice()));
                    sellerCatalog.setDiscount(discountPercentage);

                    this.sellerCatalogRepository.save(sellerCatalog);

                    return ResponseGenerator.generateSuccessResponse(SellerMessageResponse.DATA_SAVED_SUCCESS,
                            SellerMessageResponse.SUCCESS);

                } else {
                    return ResponseGenerator.generateBadRequestResponse(SellerMessageResponse.DATA_NOT_SAVED,
                            SellerMessageResponse.FAILED);
                }

        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(SellerMessageResponse.DATA_NOT_SAVED);
        }

    }

    public String calculateDiscount(double mrp, double sellingPrice) {
        if (mrp <= 0) {
            return "MRP should be greater than 0";
        }
        // Calculate discount percentage
        double discountPercentage = ((mrp - sellingPrice) / mrp) * 100;

        // Round to 2 decimal places
        BigDecimal roundedDiscount = new BigDecimal(discountPercentage).setScale(2, RoundingMode.HALF_UP);

        log.info("Discount Percentage: " + roundedDiscount + "%");

        return String.valueOf(roundedDiscount);
    }



}
