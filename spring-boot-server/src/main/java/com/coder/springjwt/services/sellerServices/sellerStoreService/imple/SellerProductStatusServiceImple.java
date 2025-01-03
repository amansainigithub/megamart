package com.coder.springjwt.services.sellerServices.sellerStoreService.imple;

import com.coder.springjwt.constants.sellerConstants.sellerMessageConstants.SellerMessageResponse;
import com.coder.springjwt.exception.adminException.DataNotFoundException;
import com.coder.springjwt.helpers.userHelper.UserHelper;
import com.coder.springjwt.models.sellerModels.ProductStatus;
import com.coder.springjwt.models.sellerModels.sellerProductModels.SellerProduct;
import com.coder.springjwt.models.sellerModels.sellerStore.SellerStore;
import com.coder.springjwt.payload.sellerPayloads.sellerProducts.SellerProductPayloads;
import com.coder.springjwt.payload.sellerPayloads.sellerProducts.SellerProductVariantsPayloads;
import com.coder.springjwt.repository.UserRepository;
import com.coder.springjwt.repository.sellerRepository.sellerStoreRepository.SellerProductRepository;
import com.coder.springjwt.repository.sellerRepository.sellerStoreRepository.SellerStoreRepository;
import com.coder.springjwt.services.sellerServices.sellerStoreService.SellerProductStatusService;
import com.coder.springjwt.util.ResponseGenerator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SellerProductStatusServiceImple implements SellerProductStatusService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SellerStoreRepository sellerStoreRepository;

    @Autowired
    private SellerProductRepository sellerProductRepository;


    @Override
    public ResponseEntity<?> getAllIncompleteProduct() {
        try {

            //Get Current Username
            String currentUser = UserHelper.getOnlyCurrentUser();

            SellerStore sellerStore = this.sellerStoreRepository.findByUsername(currentUser)
                    .orElseThrow(() -> new UsernameNotFoundException(SellerMessageResponse.USER_NOT_FOUND));

            // Fetch pending products
            Page<SellerProduct> sellerPendingProductList = this.sellerProductRepository.findAllByProductWithPendingStatus(
                                                            sellerStore.getStoreName(),
                                                            ProductStatus.COMPLETE.toString(),
                                                            ProductStatus.IN_COMPLETE.toString(),
                                                            PageRequest.of(0, 100));

            List<SellerProduct> productList = sellerPendingProductList.getContent();

            ArrayList<SellerProductPayloads> sellerProductPayloads = new ArrayList<>();
            for(SellerProduct sellerProduct : productList ) {

                if (sellerProduct.getVariant().equals("YES")) {
                    SellerProductPayloads productPayloads = new SellerProductPayloads();
                    productPayloads.setId(sellerProduct.getId());
                    productPayloads.setProductName(sellerProduct.getProductName());
                    productPayloads.setProductStatus(sellerProduct.getProductStatus());
                    productPayloads.setCreationDate(sellerProduct.getProductCreationDate());
                    productPayloads.setCreationTime(sellerProduct.getProductCreationTime());
                    productPayloads.setProductColor(sellerProduct.getProductColor());
                    productPayloads.setFileName(sellerProduct.getProductFiles().get(0).getFileName());
                    productPayloads.setFileUrl(sellerProduct.getProductFiles().get(0).getFileUrl());

                    List<SellerProductVariantsPayloads> sellerProductVariantPayloads = new ArrayList<>();


                    List<SellerProduct> variantList = this.sellerProductRepository.findByVariant(sellerProduct.getId());
                    for (SellerProduct variantData : variantList) {
                        SellerProductVariantsPayloads sellerProductVariantsPayloads = new SellerProductVariantsPayloads();
                        sellerProductVariantsPayloads.setId(variantData.getId());
                        sellerProductVariantsPayloads.setProductName(variantData.getProductName());
                        sellerProductVariantsPayloads.setProductStatus(variantData.getProductStatus());
                        sellerProductVariantsPayloads.setCreationDate(variantData.getProductCreationDate());
                        sellerProductVariantsPayloads.setCreationTime(variantData.getProductCreationTime());
                        sellerProductVariantsPayloads.setProductColor(variantData.getProductColor());

                        if(variantData.getProductFiles().size() > 0 ){
                            sellerProductVariantsPayloads.setFileName(variantData.getProductFiles().get(0).getFileName());
                            sellerProductVariantsPayloads.setFileUrl(variantData.getProductFiles().get(0).getFileUrl());
                        }

                        sellerProductVariantPayloads.add(sellerProductVariantsPayloads);
                    }
                    productPayloads.setSellerProductVariantPayloads(sellerProductVariantPayloads);
                    sellerProductPayloads.add(productPayloads);
                }
            }
            return ResponseGenerator.generateSuccessResponse(sellerProductPayloads, SellerMessageResponse.SUCCESS);
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(SellerMessageResponse.FAILED);
        }
    }

    @Override
    public ResponseEntity<?> getProductVariantByVariantId(Long productId) {
            try {
                //Get Current Username
                String currentUser = UserHelper.getOnlyCurrentUser();

                Boolean userExists = this.userRepository.existsByUsername(currentUser);

                 SellerProduct  sellerData= this.sellerProductRepository
                                                      .findByProductWithProductStatus(productId, ProductStatus.IN_COMPLETE.toString()).orElseThrow(()-> new DataNotFoundException(SellerMessageResponse.DATA_NOT_FOUND));

                 if (userExists && sellerData.getProductStatus().equals(ProductStatus.IN_COMPLETE.toString())) {
                     System.out.println("Product Verified Success :: " + productId);
                     return ResponseGenerator.generateSuccessResponse(sellerData, SellerMessageResponse.SUCCESS);
                 }
                throw new UsernameNotFoundException(SellerMessageResponse.USER_NOT_FOUND + " : OR : " + SellerMessageResponse.SOMETHING_WENT_WRONG);
            }
            catch (Exception e){
                e.printStackTrace();
                return ResponseGenerator.generateBadRequestResponse(SellerMessageResponse.FAILED);
            }
    }





}
