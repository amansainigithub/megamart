package com.coder.springjwt.services.sellerServices.outOfStockService.imple;

import com.coder.springjwt.constants.sellerConstants.sellerMessageConstants.SellerMessageResponse;
import com.coder.springjwt.models.sellerModels.sellerProductModels.ProductVariants;
import com.coder.springjwt.repository.sellerRepository.sellerStoreRepository.ProductVariantsRepository;
import com.coder.springjwt.services.sellerServices.outOfStockService.OutOfStockProductsService;
import com.coder.springjwt.util.ResponseGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OutOfStockProductsServiceImple implements OutOfStockProductsService {

    @Autowired
    private ProductVariantsRepository productVariantsRepository;

    @Override
    public ResponseEntity<?> getOutOfStockProductsService(Integer page, Integer size) {
        try {
            Page<ProductVariants> outOfStockData = this.productVariantsRepository.findByProductInventory("0",
                    PageRequest.of(page, size, Sort.by("id").descending()));
            return ResponseGenerator.generateSuccessResponse(outOfStockData , SellerMessageResponse.SUCCESS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            log.error("Failed To fetch Hsn Code By Id");
            return ResponseGenerator.generateBadRequestResponse(e.getMessage() , SellerMessageResponse.FAILED);
        }
    }

    @Override
    public ResponseEntity<?> updateOutOfStocksProducts(long id, String productInventory) {
        try {

            ProductVariants productVariants = this.productVariantsRepository.findById(id)
                                    .orElseThrow(() -> new RuntimeException(SellerMessageResponse.DATA_NOT_FOUND));

            productVariants.setProductInventory(productInventory);
            this.productVariantsRepository.save(productVariants);
            return ResponseGenerator.generateSuccessResponse(SellerMessageResponse.DATA_SAVED_SUCCESS , SellerMessageResponse.SUCCESS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(e.getMessage() , SellerMessageResponse.FAILED);

        }
    }
}
