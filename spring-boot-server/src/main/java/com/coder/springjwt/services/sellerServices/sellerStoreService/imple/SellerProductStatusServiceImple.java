package com.coder.springjwt.services.sellerServices.sellerStoreService.imple;

import com.coder.springjwt.constants.sellerConstants.sellerMessageConstants.SellerMessageResponse;
import com.coder.springjwt.emuns.ProductStatus;
import com.coder.springjwt.helpers.userHelper.UserHelper;
import com.coder.springjwt.models.sellerModels.sellerProductModels.SellerProduct;
import com.coder.springjwt.repository.UserRepository;
import com.coder.springjwt.repository.sellerRepository.catalogRepos.ProductBreathRepo;
import com.coder.springjwt.repository.sellerRepository.sellerStoreRepository.SellerProductRepository;
import com.coder.springjwt.services.sellerServices.sellerStoreService.SellerProductStatusService;
import com.coder.springjwt.util.ResponseGenerator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SellerProductStatusServiceImple implements SellerProductStatusService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SellerProductRepository sellerProductRepository;

    @Autowired
    private ProductBreathRepo productBreathRepo;

    @Override
    public ResponseEntity<?> getApprovedProductList(String username, int page, int size) {

        try {
            Page<SellerProduct> productPendingList = this.sellerProductRepository
                                                    .findByVariantIn(ProductStatus.PV_APPROVED.toString(),
                                                     UserHelper.getOnlyCurrentUser(), List.of("YES","NO") ,
                                                     PageRequest.of(page, size));

            // Map the SellerProduct list to a custom DTO with id and productName
            Page<Map<String, Object>> dataCrunching = productPendingList.map(product -> {
                Map<String, Object> productData = new HashMap<>();
                productData.put("id", product.getId());
                productData.put("productName", product.getProductName());
                productData.put("productCode", product.getProductCode());
                productData.put("creationDate", product.getProductCreationDate());
                productData.put("creationTime", product.getProductCreationTime());
                productData.put("fileName", product.getProductFiles().get(0).getFileName());
                productData.put("fileUrl", product.getProductFiles().get(0).getFileUrl());
                productData.put("productStatus", product.getProductStatus());
                return productData;
            });
            return ResponseGenerator.generateSuccessResponse(dataCrunching,SellerMessageResponse.SUCCESS);
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(HttpStatus.BAD_REQUEST);
        }
    }


}
