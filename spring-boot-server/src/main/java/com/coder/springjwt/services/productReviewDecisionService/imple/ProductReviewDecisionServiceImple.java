package com.coder.springjwt.services.productReviewDecisionService.imple;

import com.coder.springjwt.constants.sellerConstants.sellerMessageConstants.SellerMessageResponse;
import com.coder.springjwt.emuns.ProductStatus;
import com.coder.springjwt.exception.customerException.DataNotFoundException;
import com.coder.springjwt.helpers.generateDateandTime.GenerateDateAndTime;
import com.coder.springjwt.models.sellerModels.sellerProductModels.SellerProduct;
import com.coder.springjwt.payload.sellerPayloads.ProductReviewPayload;
import com.coder.springjwt.repository.UserRepository;
import com.coder.springjwt.repository.sellerRepository.productStatusRepo.ProductReviewStatusRepository;
import com.coder.springjwt.repository.sellerRepository.sellerStoreRepository.SellerProductRepository;
import com.coder.springjwt.repository.sellerRepository.sellerStoreRepository.SellerStoreRepository;
import com.coder.springjwt.services.productReviewDecisionService.ProductReviewDecisionService;
import com.coder.springjwt.util.ResponseGenerator;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@Slf4j
public class ProductReviewDecisionServiceImple implements ProductReviewDecisionService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SellerStoreRepository sellerStoreRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private SellerProductRepository sellerProductRepository;

    @Autowired
    private ProductReviewStatusRepository productReviewStatusRepository;

    @Override
    public ResponseEntity<?> saveProductReviewDecisionService(ProductReviewPayload productReviewPayload) {
        try {
            boolean reviewDecision = productReviewStatusRepository.existsById(Long.parseLong(productReviewPayload.getReviewDecisionId()));

            SellerProduct sellerProduct = sellerProductRepository.findByProductWithProductStatus
                                                (Long.parseLong(productReviewPayload.getId()),
                                                 ProductStatus.PV_UNDER_REVIEW.toString())
                                                .orElseThrow(() -> new DataNotFoundException(SellerMessageResponse.DATA_NOT_FOUND));

            if(reviewDecision && sellerProduct != null) {
                userRepository.findByUsername(sellerProduct.getSellerUserName()).orElseThrow(() ->
                        new UsernameNotFoundException("USERNAME_NOT_FOUND"));

                //Generate Product Launcher Id
                String productLauncherId;
                do {
                    productLauncherId = generateLiveId(String.valueOf(sellerProduct.getId()));
                } while (sellerProductRepository.existsByProductLauncherId(productLauncherId));

                sellerProduct.setProductLauncherId(productLauncherId);
                sellerProduct.setProductLauncherDate(GenerateDateAndTime.getTodayDate());
                sellerProduct.setProductLauncherTime(GenerateDateAndTime.getCurrentTime());

                //set Product Status Approved
                sellerProduct.setProductStatus(ProductStatus.PV_APPROVED.toString());

                this.sellerProductRepository.save(sellerProduct);

                log.info("Seller Product Id :: " +     sellerProduct.getId());
                log.info("Product Launcher Id :: " +   sellerProduct.getProductLauncherId());
                log.info("Product Launcher Date :: " + sellerProduct.getProductLauncherDate());
                log.info("Product Launcher Time :: " + sellerProduct.getProductLauncherTime());
                log.info("Saved Success");

                HashMap<String,String> hashMap = new HashMap();
                hashMap.put("id",String.valueOf(sellerProduct.getId()));
                hashMap.put("productLauncherId",sellerProduct.getProductLauncherId());
                hashMap.put("productLauncherDate",sellerProduct.getProductLauncherDate());
                hashMap.put("productLauncherTime",sellerProduct.getProductLauncherTime());
                hashMap.put("productStatus",sellerProduct.getProductStatus());

                return ResponseGenerator.generateSuccessResponse(hashMap,SellerMessageResponse.SUCCESS);
            }
            else{
                throw new RuntimeException(SellerMessageResponse.SOMETHING_WENT_WRONG);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(SellerMessageResponse.SOMETHING_WENT_WRONG);
        }
    }

    public static String generateLiveId(String productId)
    {
        long milliSeconds = System.currentTimeMillis();
        String productLauncherId= "PL-" + milliSeconds +"-"+productId;
        return productLauncherId;
    }
}
