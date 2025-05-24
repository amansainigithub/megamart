package com.coder.springjwt.services.sellerServices.sellerStoreService.imple;

import com.coder.springjwt.bucket.bucketModels.BucketModel;
import com.coder.springjwt.bucket.bucketService.BucketService;
import com.coder.springjwt.constants.sellerConstants.sellerMessageConstants.SellerMessageResponse;
import com.coder.springjwt.emuns.DeliveryStatus;
import com.coder.springjwt.emuns.ProductStatus;
import com.coder.springjwt.exception.customerPanelException.DataNotFoundException;
import com.coder.springjwt.formBuilderTools.formVariableKeys.FormBuilderRoot;
import com.coder.springjwt.formBuilderTools.formVariableKeys.ProductRootBuilder;
import com.coder.springjwt.formBuilderTools.formVariableKeys.ProductRows;
import com.coder.springjwt.helpers.userHelper.UserHelper;
import com.coder.springjwt.models.sellerModels.catalog.catalogBreath.BreathModel;
import com.coder.springjwt.models.sellerModels.catalog.catalogHeight.ProductHeightModel;
import com.coder.springjwt.models.sellerModels.catalog.catalogLength.ProductLengthModel;
import com.coder.springjwt.models.sellerModels.catalog.catalogMaterial.ProductMaterial;
import com.coder.springjwt.models.sellerModels.catalog.catalogNetQuantity.ProductNetQuantityModel;
import com.coder.springjwt.models.sellerModels.catalog.catalogSize.ProductSizeVariantModel;
import com.coder.springjwt.models.sellerModels.catalog.catalogType.ProductTypeModel;
import com.coder.springjwt.models.sellerModels.catalog.catalogWeight.ProductWeightModel;
import com.coder.springjwt.models.sellerModels.catalog.gstPercentage.GstPercentageModel;
import com.coder.springjwt.models.sellerModels.catalog.hsn.HsnCodes;
import com.coder.springjwt.models.sellerModels.categories.BabyCategoryModel;
import com.coder.springjwt.models.sellerModels.categories.BornCategoryModel;
import com.coder.springjwt.models.sellerModels.sellerProductModels.ProductFiles;
import com.coder.springjwt.models.sellerModels.sellerProductModels.ProductVariants;
import com.coder.springjwt.models.sellerModels.sellerProductModels.SellerProduct;
import com.coder.springjwt.repository.UserRepository;
import com.coder.springjwt.repository.sellerRepository.catalogRepos.*;
import com.coder.springjwt.repository.sellerRepository.categories.BornCategoryRepo;
import com.coder.springjwt.repository.sellerRepository.sellerStoreRepository.ProductVariantsRepository;
import com.coder.springjwt.repository.sellerRepository.sellerStoreRepository.SellerProductRepository;
import com.coder.springjwt.services.sellerServices.sellerStoreService.SellerProductService;
import com.coder.springjwt.util.MessageResponse;
import com.coder.springjwt.util.ResponseGenerator;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SellerProductServiceImple implements SellerProductService {

    @Autowired
    private BornCategoryRepo bornCategoryRepo;
    @Autowired
    private HsnRepository hsnRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ProductNetQuantityRepo productNetQuantityRepo;
    @Autowired
    private ProductMaterialRepo productMaterialRepo;
    @Autowired
    private ProductSizeRepo productSizeRepo;
    @Autowired
    private ProductTypeRepo productTypeRepo;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BucketService bucketService;
    @Autowired
    private ProductLengthRepo productLengthRepo;
    @Autowired
    private GstPercentageRepo gstPercentageRepo;
    @Autowired
    private ProductWeightRepo productWeightRepo;
    @Autowired
    private ProductBreathRepo productBreathRepo;
    @Autowired
    private ProductHeightRepo productHeightRepo;
    @Autowired
    private SellerProductRepository sellerProductRepository;
    @Autowired
    private ProductVariantsRepository productVariantsRepository;
    @Autowired
    private ProductCalculationService productCalculationService;
    @Autowired
    private FormBuilderService formBuilderService;
    @Override
    public ResponseEntity<?> getGstList(Long catalogId) {
        return null;
    }

    @Override
    public ResponseEntity<?> getProductMasters() {

        MessageResponse mRes = new MessageResponse();

        //Response Data
        HashMap<String ,Object> dataResponse =new HashMap<>();

        try {

            // HSN Codes
            List<HsnCodes> hsnCodes = this.getHsnList();
            dataResponse.put("hsn" , hsnCodes);

            //Size List
            List<ProductSizeVariantModel> sizeList = this.getSizeList();
            dataResponse.put("catalogSize" , sizeList);

            //Size List
            List<ProductNetQuantityModel> netQuantityList = this.getNetQuantityList();
            dataResponse.put("netQuantityList" , netQuantityList);

            //material List
            List<ProductMaterial> materialList = this.getMaterialList();
            dataResponse.put("materialList" , materialList);

            //material List
            List<ProductTypeModel> typeList = this.getTypeList();
            dataResponse.put("typeList" , typeList);

            //catalog Length List
            List<GstPercentageModel> gstPercentageList = this.getGstPercentageList();
            dataResponse.put("gstPercentageList" , gstPercentageList);

            //catalog Weight List
            List<ProductWeightModel> catalogWeightList = this.getWeightList();
            dataResponse.put("catalogWeightList" , catalogWeightList);

            //catalog Weight List
            List<BreathModel> catalogBreathList = this.getBreathList();
            dataResponse.put("catalogBreathList" , catalogBreathList);

            //catalog Length List
            List<ProductLengthModel> lengthList = this.getLengthList();
            dataResponse.put("lengthList" , lengthList);

            //catalog Length List
            List<ProductHeightModel> heightLists = this.getHeightList();
            dataResponse.put("heightLists" , heightLists);

            return ResponseGenerator.generateSuccessResponse(dataResponse ,SellerMessageResponse.SUCCESS );
        }
        catch (Exception e)
        {
            e.printStackTrace();
            mRes.setStatus(HttpStatus.BAD_REQUEST);
            mRes.setMessage(e.getMessage());
            return ResponseGenerator.generateBadRequestResponse(mRes, SellerMessageResponse.FAILED);
        }
    }



    public List<ProductHeightModel> getHeightList()
    {
        try {
            List<ProductHeightModel> catalogHeightLists = this.productHeightRepo.findAll();
            if(catalogHeightLists.isEmpty())
            {
                log.info("Data Not found :::: {} ");
                return null;
            }else{
                log.info("Data fetch Success");
                return catalogHeightLists;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public List<BreathModel> getBreathList()
    {
        try {
            List<BreathModel> catalogBreathList = this.productBreathRepo.findAll();
            if(catalogBreathList.isEmpty())
            {
                log.info("Data Not found :::: {} ");
                return null;
            }else{
                log.info("Data fetch Success");
                return catalogBreathList;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public List<ProductWeightModel> getWeightList()
    {
        try {
            List<ProductWeightModel> catalogWeightLists = this.productWeightRepo.findAll();
            if(catalogWeightLists.isEmpty())
            {
                log.info("Data Not found :::: {} ");
                return null;
            }else{
                log.info("Data fetch Success");
                return catalogWeightLists;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public List<GstPercentageModel> getGstPercentageList()
    {
        try {
            List<GstPercentageModel> gstPercentageList = this.gstPercentageRepo.findAll();
            if(gstPercentageList.isEmpty())
            {
                log.info("Data Not found :::: {} ");
                return null;
            }else{
                log.info("Data fetch Success");
                return gstPercentageList;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }


    public List<ProductLengthModel> getLengthList()
    {
        try {
            List<ProductLengthModel> lengthList = this.productLengthRepo.findAll();
            if(lengthList.isEmpty())
            {
                log.info("Data Not found :::: {} ");
                return null;
            }else{
                log.info("Data fetch Success");
                return lengthList;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public List<HsnCodes> getHsnList()
    {
        try {
            List<HsnCodes> hsnCodes = this.hsnRepository.findAll();
            if(hsnCodes.isEmpty())
            {
                log.info("Data Not found :::: {} ");
                return null;
            }else{
                log.info("Data fetch Success");
                return hsnCodes;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public List<ProductSizeVariantModel> getSizeList()
    {
        try {
            List<ProductSizeVariantModel> catalogSizeList = this.productSizeRepo.findAll();
            if(catalogSizeList.isEmpty())
            {
                log.info("Data Not found :::: {} ");
                return null;
            }else{
                log.info("Data fetch Success");
                return catalogSizeList;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public List<ProductNetQuantityModel> getNetQuantityList()
    {
        try {
            List<ProductNetQuantityModel> netQuantityList = this.productNetQuantityRepo.findAll();
            if(netQuantityList.isEmpty())
            {
                log.info("Data Not found :::: {} ");
                return null;
            }else{
                log.info("Data fetch Success");
                return netQuantityList;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public List<ProductMaterial> getMaterialList()
    {
        try {
            List<ProductMaterial> productMaterialList = this.productMaterialRepo.findAll();
            if(productMaterialList.isEmpty())
            {
                log.info("Data Not found :::: {} ");
                return null;
            }else{
                log.info("Data fetch Success");
                return productMaterialList;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public List<ProductTypeModel> getTypeList()
    {
        try {
            List<ProductTypeModel> catalogTypeList = this.productTypeRepo.findAll();
            if(catalogTypeList.isEmpty())
            {
                log.info("Data Not found :::: {} ");
                return null;
            }else{
                log.info("Data fetch Success");
                return catalogTypeList;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ResponseEntity<?> formBuilderFlying(String categoryId) {

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
    public ResponseEntity<?> saveSellerProduct(ProductRootBuilder productRootBuilder, Long bornCategoryId) {
        try {
            if(productRootBuilder != null){

                String currentUser = UserHelper.getOnlyCurrentUser();


                BornCategoryModel bornCategoryModel = this.bornCategoryRepo.findById(bornCategoryId)
                                                  .orElseThrow(()-> new DataNotFoundException("Born Category not Found"));

                BabyCategoryModel babyCategoryModel = bornCategoryModel.getBabyCategoryModel();


                // Map incoming data to SellerProduct
            SellerProduct sellerProduct = modelMapper.map(productRootBuilder, SellerProduct.class);

//            //Set Seller Store
//            sellerProduct.setSellerStore(sellerStore);

            //Set Rows Counter
            sellerProduct.setRowsCounter(1l);

            //Calculated GST ,TCS,TDS PERCENTAGE%
            productCalculationService.calculateTaxes(productRootBuilder.getProductColor(), sellerProduct.getProductRows(),sellerProduct.getGst(),bornCategoryModel);

            //set current Date
            sellerProduct.setProductCreationDate(productCalculationService.getCurrentDate());

            //set Current Time
            sellerProduct.setProductCreationTime(productCalculationService.getCurrentTime());

            //Set Product Id
            sellerProduct.setProductId(productCalculationService.generateProductId());

            //Set Shipping Charges
            sellerProduct.setShippingCharges(bornCategoryModel.getShippingCharge());

            //save UserName and sellerUserId
            productCalculationService.setSellerUsernameAndUserId(sellerProduct);

            //set Born Category Name
            sellerProduct.setBornCategoryName(bornCategoryModel.getCategoryName());

            //set Born Category Name
            sellerProduct.setBornCategoryId(String.valueOf(bornCategoryModel.getId()));

            //set Baby Category Name
            sellerProduct.setBabyCategoryName(babyCategoryModel.getCategoryName());

            //set Baby Category Name
            sellerProduct.setBabyCategoryId(String.valueOf(babyCategoryModel.getId()));

            if(sellerProduct.getProductCode() == null)
            {
                sellerProduct.setProductCode(ProductCalculationService.generateProductCode(10));
            }

            //Set Product Status
            if(productRootBuilder.getProductVariants().isEmpty()){
                sellerProduct.setProductStatus(ProductStatus.PV_UNDER_REVIEW.toString());
                sellerProduct.setVariant("NO");
            }else{
                sellerProduct.setVariant("YES");
                sellerProduct.setProductStatus(ProductStatus.COMPLETE.toString());
            }

            //Set First Row Price To SellerProduct Table to Using Filter Product
            try {
                ProductVariants productVariants = sellerProduct.getProductRows().get(0);
                sellerProduct.setProductFPrice(Integer.valueOf(productVariants.getProductPrice()));
            }
            catch (Exception e){
                log.error("Product First Row Error | First Price Not Capturing | Error");
            }

                //if SKU-ID is null or blank then Generate otherwise set SKU-ID
            if (sellerProduct.getProductRows() != null) {
                for (ProductVariants variant : sellerProduct.getProductRows()) {
                    if(variant.getSkuId() == "" || variant.getSkuId() == null)
                    {
                        variant.setSkuId(productCalculationService.getSkuCode());
                    }
                    variant.setSellerProduct(sellerProduct);
                }
            }
            // Save SellerProduct along with its ProductVariants
            SellerProduct productResponse = this.sellerProductRepository.save(sellerProduct);
            productResponse.setParentKey(String.valueOf(productResponse.getId()));
            this.sellerProductRepository.save(productResponse);

            return ResponseGenerator.generateSuccessResponse(productResponse.getId(),SellerMessageResponse.SUCCESS);
            }else{
                return ResponseGenerator.generateBadRequestResponse("FAILED",SellerMessageResponse.SOMETHING_WENT_WRONG);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse("FAILED",HttpStatus.BAD_REQUEST.toString());
        }
    }




    @Override
    public ResponseEntity<?> uploadProductFiles(Map<String, MultipartFile> files , String productLockerNumber) {
        try {
            SellerProduct sellerProduct = this.sellerProductRepository.findById(Long.parseLong(productLockerNumber))
                                         .orElseThrow(()-> new DataNotFoundException(SellerMessageResponse.DATA_NOT_FOUND));

            if(sellerProduct != null) {
                List<ProductFiles> productFilesList = new ArrayList<>();
                for (Map.Entry<String, MultipartFile> entry : files.entrySet()) {
                    String key = entry.getKey();  // The key (e.g., file0, file1, etc.)
                    MultipartFile file = entry.getValue();

                    //File Upload AWS Bucket
//                    BucketService bucketService = new BucketService();
                    BucketModel bucketModel = bucketService.uploadFile(file);

                    ProductFiles productFiles =new ProductFiles();
                    productFiles.setFileUrl(bucketModel.getBucketUrl());
                    productFiles.setFileName(bucketModel.getFileName());
                    productFiles.setFileSize(String.valueOf(file.getSize()));
                    productFiles.setFileType(file.getContentType());
                    productFiles.setSellerProduct(sellerProduct);
                    productFilesList.add(productFiles);
                }
                sellerProduct.setProductFiles(productFilesList);
                this.sellerProductRepository.save(sellerProduct);
                log.info("Seller product Saved Success with Images");
            }
            return ResponseGenerator.generateSuccessResponse(SellerMessageResponse.SUCCESS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateDataNotFound(SellerMessageResponse.SOMETHING_WENT_WRONG);
        }
    }

    @Override
    public ResponseEntity<?> getProductBYId(String productId) {
        try {

            SellerProduct sellerProduct =  this.sellerProductRepository.findById(Long.parseLong(productId)).orElseThrow(()->
                                             new DataNotFoundException(SellerMessageResponse.DATA_NOT_FOUND));

            ProductRootBuilder productBuilder = modelMapper.map(sellerProduct, ProductRootBuilder.class);

            //Table Variant OR Sizes
            List<ProductVariants> productRows = sellerProduct.getProductRows();
            List<ProductRows> productData =  productRows.stream()
                                            .map(singleProductRow -> modelMapper.map(singleProductRow, ProductRows.class))
                                            .collect(Collectors.toList());
            productBuilder.setTableRows(productData);

            HashMap<Object , Object> dataNode = new HashMap<>();
            dataNode.put("productData",productBuilder);
            dataNode.put("productFiles",sellerProduct.getProductFiles());

              return ResponseGenerator.generateSuccessResponse(dataNode,SellerMessageResponse.SUCCESS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse("FAILED",SellerMessageResponse.FAILED);
        }
    }

    @Override
    public ResponseEntity<?> updateSellerProduct(ProductRootBuilder productRootBuilder, Long productId) {
        try {
            if(productRootBuilder != null && productId != null ){

            SellerProduct checkProductIsExists = this.sellerProductRepository.
                                                 findByProductWithProductStatus(productId,ProductStatus.IN_COMPLETE.toString())
                                                 .orElseThrow(() ->
                                                 new DataNotFoundException("Data not found Exception"));

              //Delete Product Variants Rows
              this.deleteProductRows(checkProductIsExists.getProductRows());

              BornCategoryModel bornCategoryModel = this.bornCategoryRepo.findById(Long.valueOf(checkProductIsExists.getBornCategoryId()))
                                                    .orElseThrow(()-> new DataNotFoundException("Born Category not Found"));

                // Map incoming data to SellerProduct
                SellerProduct sellerProduct = modelMapper.map(productRootBuilder, SellerProduct.class);

                sellerProduct.setId(productId);

                //Set Rows Counter
                sellerProduct.setRowsCounter(1l);

                //Calculated GST ,TCS,TDS PERCENTAGE %
                productCalculationService.calculateTaxes(productRootBuilder.getProductColor(), sellerProduct.getProductRows(),sellerProduct.getGst(),bornCategoryModel);

                //set current Date
                sellerProduct.setProductCreationDate(productCalculationService.getCurrentDate());

                //set Current Time
                sellerProduct.setProductCreationTime(productCalculationService.getCurrentTime());

                //Set Product Id's
                sellerProduct.setProductId(productCalculationService.generateProductId());

                //Set Shipping Charges
                sellerProduct.setShippingCharges(bornCategoryModel.getShippingCharge());

                //save UserName and sellerUserId
                productCalculationService.setSellerUsernameAndUserId(sellerProduct);


                //set Born Category Name
                sellerProduct.setBornCategoryName(bornCategoryModel.getCategoryName());

                //set Born Category Name
                sellerProduct.setBornCategoryId(String.valueOf(bornCategoryModel.getId()));

                sellerProduct.setProductStatus(ProductStatus.COMPLETE.toString());

                sellerProduct.setVariant(checkProductIsExists.getVariant());

                //Set SKU ID and Generate SKU ID
                if (sellerProduct.getProductRows() != null) {
                    for (ProductVariants variant : sellerProduct.getProductRows()) {
                        if(variant.getSkuId() == "" || variant.getSkuId() == null)
                        {
                            variant.setSkuId(productCalculationService.getSkuCode());
                        }
                        variant.setSellerProduct(sellerProduct);
                    }
                }
                //Set Parent Key
                sellerProduct.setParentKey(checkProductIsExists.getParentKey());

                // Save SellerProduct along with its ProductVariants
                SellerProduct productResponse = this.sellerProductRepository.save(sellerProduct);

                //Check all the Product Status (only for Variants Products)
                this.checkVariantStatusIsCompletedOrNot(productResponse);

                return ResponseGenerator.generateSuccessResponse(productResponse.getId(),SellerMessageResponse.SUCCESS);
            }else{
                return ResponseGenerator.generateBadRequestResponse("FAILED",SellerMessageResponse.SOMETHING_WENT_WRONG);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse("FAILED",HttpStatus.BAD_REQUEST.toString());
        }
    }

    @Override
    public ResponseEntity<?> getProductCounts() {
        log.info("getProductCounts Flying");
        Map<String,String> countData = new HashMap<>();
        try {
            long underReview = sellerProductRepository.countAllDeliveriesByStatus(ProductStatus.PV_UNDER_REVIEW.toString());
            long approved = sellerProductRepository.countAllDeliveriesByStatus(ProductStatus.PV_APPROVED.toString());
            countData.put("UNDER_REVIEW",String.valueOf(underReview));
            countData.put("APPROVED",String.valueOf(approved));
            countData.put("TOTAL" , String.valueOf( underReview + approved) );
            return ResponseGenerator.generateSuccessResponse(countData,SellerMessageResponse.SUCCESS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse("FAILED",HttpStatus.BAD_REQUEST.toString());
        }
    }


    public void deleteProductRows( List<ProductVariants>  productVariants) {
        List<Long> variantIds = productVariants.stream().map(ProductVariants::getId).collect(Collectors.toList());
        this.productVariantsRepository.deleteAllById(variantIds);
    }


    public Boolean checkVariantStatusIsCompletedOrNot(SellerProduct sellerProduct){
        boolean productStatus =true;
        List<SellerProduct> statusData = this.sellerProductRepository
                                                            .findByParentKey(sellerProduct.getVariant());
        for(SellerProduct data : statusData ){

            if(data.getProductStatus().equals(ProductStatus.IN_COMPLETE.toString())){
                productStatus  = Boolean.FALSE;
            }
        }
        if(productStatus){
            for(SellerProduct data : statusData ){
                data.setProductStatus(ProductStatus.PV_UNDER_REVIEW.toString());
                this.sellerProductRepository.save(data);
            }
        }
        return productStatus;
    }








}
