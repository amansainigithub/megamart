package com.coder.springjwt.services.sellerServices.sellerProductVerifierService.imple;

import com.coder.springjwt.constants.sellerConstants.sellerMessageConstants.SellerMessageResponse;
import com.coder.springjwt.emuns.ProductStatus;
import com.coder.springjwt.exception.customerException.DataNotFoundException;
import com.coder.springjwt.formBuilderTools.formVariableKeys.FormBuilderRoot;
import com.coder.springjwt.models.sellerModels.categories.BornCategoryModel;
import com.coder.springjwt.models.sellerModels.sellerProductModels.SellerProduct;
import com.coder.springjwt.payload.sellerPayloads.sellerProducts.SellerProductPayloads;
import com.coder.springjwt.payload.sellerPayloads.sellerProducts.SellerProductVariantsPayloads;
import com.coder.springjwt.repository.sellerRepository.categories.BornCategoryRepo;
import com.coder.springjwt.repository.sellerRepository.sellerStoreRepository.SellerProductRepository;
import com.coder.springjwt.repository.sellerRepository.sellerStoreRepository.SellerStoreRepository;
import com.coder.springjwt.services.sellerServices.sellerProductVerifierService.SellerProductVerifierService;
import com.coder.springjwt.services.sellerServices.sellerStoreService.imple.FormBuilderService;
import com.coder.springjwt.util.ResponseGenerator;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class SellerProductVerifierServiceImple implements SellerProductVerifierService {

    @Autowired
    private SellerStoreRepository sellerStoreRepository;

    @Autowired
    private SellerProductRepository sellerProductRepository;

    @Autowired
    private BornCategoryRepo bornCategoryRepo;

    @Autowired
    private FormBuilderService formBuilderService;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public ResponseEntity<?> getFormBuilderFlyingByAdmin(String categoryId) {
        try {
            BornCategoryModel bornData = this.bornCategoryRepo.findById(Long.parseLong(categoryId))
                    .orElseThrow(() -> new DataNotFoundException(SellerMessageResponse.DATA_NOT_FOUND));
            return ResponseGenerator.generateSuccessResponse(bornData, SellerMessageResponse.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        FormBuilderRoot formBuilder = this.formBuilderService.getFormBuilder();
        return ResponseEntity.ok(formBuilder);
    }

    @Override
    public ResponseEntity<?> getSellerProductVerifierList(String username , int page, int size) {
        try{
            // Fetch pending products
            Page<SellerProduct> sellerPendingProductList = this.sellerProductRepository.findByProductUnderReviewByAdmin(
                                            List.of( ProductStatus.PV_UNDER_REVIEW.toString(),  ProductStatus.PV_APPROVED.toString()),
                                            List.of("YES"),
                                            PageRequest.of(page, size));

            List<SellerProduct> productList = sellerPendingProductList.getContent();
            System.out.println(productList.size());
            ArrayList<SellerProductPayloads> sellerProductPayloads = new ArrayList<>();
            for(SellerProduct sellerProduct : productList ) {

                    boolean productUnderReview = Boolean.FALSE;

                    SellerProductPayloads productPayloads = new SellerProductPayloads();
                    productPayloads.setId(sellerProduct.getId());
                    productPayloads.setProductName(sellerProduct.getProductName());
                    productPayloads.setProductStatus(sellerProduct.getProductStatus());
                    productPayloads.setCreationDate(sellerProduct.getProductCreationDate());
                    productPayloads.setCreationTime(sellerProduct.getProductCreationTime());
                    productPayloads.setProductColor(sellerProduct.getProductColor());
                    productPayloads.setFileName(sellerProduct.getProductFiles().get(0).getFileName());
                    productPayloads.setFileUrl(sellerProduct.getProductFiles().get(0).getFileUrl());

                    if(productPayloads.getProductStatus().equals(ProductStatus.PV_UNDER_REVIEW.toString())){
                        productUnderReview = Boolean.TRUE;
                    }

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

                        if(variantData.getProductStatus().equals(ProductStatus.PV_UNDER_REVIEW.toString())){
                            productUnderReview = Boolean.TRUE;
                        }
                    }
                    if(productUnderReview) {
                        productPayloads.setSellerProductVariantPayloads(sellerProductVariantPayloads);
                        sellerProductPayloads.add(productPayloads);
                    }
            }
            // Paginate the custom payload
            Page<SellerProductPayloads> paginatedPayloads = new PageImpl<>(
                    sellerProductPayloads,
                    PageRequest.of(page, size),
                    sellerPendingProductList.getTotalElements()
            );
            return ResponseGenerator.generateSuccessResponse(paginatedPayloads, SellerMessageResponse.SUCCESS);
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(SellerMessageResponse.FAILED);
        }
    }

    @Override
    public ResponseEntity<?> getSellerProductByIdAdmin(String productId) {
        try {
            SellerProduct  sellerData= this.sellerProductRepository
                    .findByProductWithProductStatus(Long.parseLong(productId), ProductStatus.PV_UNDER_REVIEW.toString()).orElseThrow(()-> new DataNotFoundException(SellerMessageResponse.DATA_NOT_FOUND));

            if (sellerData.getProductStatus().equals(ProductStatus.PV_UNDER_REVIEW.toString())) {
                log.info("Product Data By Id Successfully Fetch PRODUCT-ID ->:: " + sellerData.getId() +
                            " :: STATUS -> " + sellerData.getProductStatus());
                return ResponseGenerator.generateSuccessResponse(sellerData, SellerMessageResponse.SUCCESS);
            }
            throw new UsernameNotFoundException(SellerMessageResponse.USER_NOT_FOUND + " : OR : " + SellerMessageResponse.SOMETHING_WENT_WRONG);
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(SellerMessageResponse.FAILED);
        }
    }


    @Override
    public ResponseEntity<?> getSellerProductUnderReviewList(String username , int page, int size) {

        try {
            Page<SellerProduct> productPendingList = this.sellerProductRepository.findByProductUnderReviewByAdmin(ProductStatus.PV_UNDER_REVIEW.toString(),
                                                         List.of("YES","NO") ,PageRequest.of(page, size));

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
                productData.put("howManyVariants",product.getHowManyVariants());
                return productData;
            });
            return ResponseGenerator.generateSuccessResponse(dataCrunching,SellerMessageResponse.SUCCESS);
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(HttpStatus.BAD_REQUEST);
        }
    }


    @Override
    public ResponseEntity<?> getSellerProductUnderReviewNoVariantList(String username, int page, int size) {

        try {
            Page<SellerProduct> productPendingList = this.sellerProductRepository.findByProductUnderReviewByAdmin(ProductStatus.PV_UNDER_REVIEW.toString(),
                    List.of("NO") ,PageRequest.of(page, size));

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
                productData.put("howManyVariants",product.getHowManyVariants());
                return productData;
            });
            return ResponseGenerator.generateSuccessResponse(dataCrunching,SellerMessageResponse.SUCCESS);
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> getSellerProductApprovedList(String username, int page, int size) {
        try {
            Page<SellerProduct> productApprovedList = this.sellerProductRepository.findByProductUnderReviewByAdmin(ProductStatus.PV_APPROVED.toString(),
                    List.of("NO") ,PageRequest.of(page, size));

            // Map the SellerProduct list to a custom DTO with id and productName
            Page<Map<String, Object>> dataCrunching = productApprovedList.map(product -> {
                Map<String, Object> productData = new HashMap<>();
                productData.put("id", product.getId());
                productData.put("productName", product.getProductName());
                productData.put("productCode", product.getProductCode());
                productData.put("creationDate", product.getProductCreationDate());
                productData.put("creationTime", product.getProductCreationTime());
                productData.put("fileName", product.getProductFiles().get(0).getFileName());
                productData.put("fileUrl", product.getProductFiles().get(0).getFileUrl());
                productData.put("productStatus", product.getProductStatus());
                productData.put("howManyVariants",product.getHowManyVariants());
                return productData;
            });
            return ResponseGenerator.generateSuccessResponse(dataCrunching,SellerMessageResponse.SUCCESS);
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> getSellerVariantProductApprovedList(String username, int page, int size) {
        try {
            Page<SellerProduct> productApprovedList = this.sellerProductRepository.findByProductUnderReviewByAdmin(ProductStatus.PV_APPROVED.toString(),
                                                       List.of("YES") ,PageRequest.of(page, size));

            // Map the SellerProduct list to a custom DTO with id and productName
            Page<Map<String, Object>> dataCrunching = productApprovedList.map(product -> {
                Map<String, Object> productData = new HashMap<>();
                productData.put("id", product.getId());
                productData.put("productName", product.getProductName());
                productData.put("productCode", product.getProductCode());
                productData.put("creationDate", product.getProductCreationDate());
                productData.put("creationTime", product.getProductCreationTime());
                productData.put("fileName", product.getProductFiles().get(0).getFileName());
                productData.put("fileUrl", product.getProductFiles().get(0).getFileUrl());
                productData.put("productStatus", product.getProductStatus());
                productData.put("howManyVariants",product.getHowManyVariants());

                List<SellerProduct> sellerProductList =  this.sellerProductRepository.findByVariant(product.getId());

                int variantSize = sellerProductList.size();
                int variantCounter = 0;
                for(SellerProduct sellerProduct : sellerProductList){
                    if(sellerProduct.getProductStatus().equals(ProductStatus.PV_APPROVED.toString())){
                        variantCounter++;
                    }
                }
                if(variantSize == variantCounter)
                {
                    return productData;
                }else{
                    return null;
                }
            });
            return ResponseGenerator.generateSuccessResponse(dataCrunching,SellerMessageResponse.SUCCESS);
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(HttpStatus.BAD_REQUEST);
        }
    }


}
