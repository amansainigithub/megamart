package com.coder.springjwt.services.sellerServices.sellerStoreService.imple;

import com.coder.springjwt.bucket.bucketModels.BucketModel;
import com.coder.springjwt.bucket.bucketService.BucketService;
import com.coder.springjwt.constants.sellerConstants.sellerMessageConstants.SellerMessageResponse;
import com.coder.springjwt.exception.adminException.DataNotFoundException;
import com.coder.springjwt.formBuilderTools.formVariableKeys.*;
import com.coder.springjwt.helpers.userHelper.UserHelper;
import com.coder.springjwt.models.CatalogRole;
import com.coder.springjwt.models.adminModels.catalog.catalogBreath.BreathModel;
import com.coder.springjwt.models.adminModels.catalog.catalogHeight.ProductHeightModel;
import com.coder.springjwt.models.adminModels.catalog.catalogLength.ProductLengthModel;
import com.coder.springjwt.models.adminModels.catalog.catalogMaterial.ProductMaterial;
import com.coder.springjwt.models.adminModels.catalog.catalogNetQuantity.ProductNetQuantityModel;
import com.coder.springjwt.models.adminModels.catalog.catalogSize.ProductSizeVariantModel;
import com.coder.springjwt.models.adminModels.catalog.catalogType.ProductTypeModel;
import com.coder.springjwt.models.adminModels.catalog.catalogWeight.ProductWeightModel;
import com.coder.springjwt.models.adminModels.catalog.gstPercentage.GstPercentageModel;
import com.coder.springjwt.models.adminModels.catalog.hsn.HsnCodes;
import com.coder.springjwt.models.adminModels.categories.BornCategoryModel;
import com.coder.springjwt.models.sellerModels.ProductStatus;
import com.coder.springjwt.models.sellerModels.sellerProductModels.ProductFiles;
import com.coder.springjwt.models.sellerModels.sellerProductModels.ProductVariants;
import com.coder.springjwt.models.sellerModels.sellerProductModels.SellerProduct;
import com.coder.springjwt.models.sellerModels.sellerStore.*;
import com.coder.springjwt.repository.UserRepository;
import com.coder.springjwt.repository.adminRepository.catalogRepos.*;
import com.coder.springjwt.repository.adminRepository.categories.BornCategoryRepo;
import com.coder.springjwt.repository.sellerRepository.sellerStoreRepository.ProductVariantsRepository;
import com.coder.springjwt.repository.sellerRepository.sellerStoreRepository.SellerCatalogRepository;
import com.coder.springjwt.repository.sellerRepository.sellerStoreRepository.SellerProductRepository;
import com.coder.springjwt.repository.sellerRepository.sellerStoreRepository.SellerStoreRepository;
import com.coder.springjwt.services.sellerServices.sellerStoreService.SellerProductService;
import com.coder.springjwt.util.MessageResponse;
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

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SellerProductServiceImple implements SellerProductService {

    private static final long MAX_FILE_SIZE = 3 * 1024 * 1024; // 3MB in bytes
    private static final int MAX_FILE_COUNT = 5; // Maximum number of files
    private static final int MIN_FILE_COUNT = 1; // Minimum number of files

    @Autowired
    private BornCategoryRepo bornCategoryRepo;

    @Autowired
    private HsnRepository hsnRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SellerCatalogRepository sellerCatalogRepository;

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
    private SellerStoreRepository sellerStoreRepository;


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
    public ResponseEntity<?> getAllCatalogByUsernameService(int page , int size) {
        try {
            //Get Current Username
            Map<String, String> currentUser = UserHelper.getCurrentUser();

            Optional<SellerStore> optional = sellerStoreRepository.findByUsername(currentUser.get("username"));

            if(optional.isPresent())
            {
                Page<SellerCatalog> catalogPage =
                        this.sellerCatalogRepository.findAllByUsername(optional.get().getUsername(), PageRequest.of(page, size));

                //Catalog's Divided Counts
                List<SellerCatalog> catalogDivided = this.sellerCatalogRepository.findAllByUsername(optional.get().getUsername());
                Map<String, Long> catalogCounts = catalogDivided.stream()
                        .collect(Collectors.groupingBy(SellerCatalog::getCatalogStatus, Collectors.counting()));

                // Retrieve counts for each status individually
                long errorCount = catalogCounts.getOrDefault("QC_ERROR", 0L);
                long qcPassCount = catalogCounts.getOrDefault("QC_PASS", 0L);
                long progressCount = catalogCounts.getOrDefault("QC_PROGRESS", 0L);
                long draftCount = catalogCounts.getOrDefault("QC_DRAFT", 0L);

                // Print or store the counts as needed
                System.out.println("Error Count: " + errorCount);
                System.out.println("QC Pass Count: " + qcPassCount);
                System.out.println("Progress Count: " + progressCount);
                System.out.println("Draft Count: " + draftCount);

                HashMap<String,Object> catalogData =new HashMap<>();
                catalogData.put("catalogPage",catalogPage);
                catalogData.put("errorCount",errorCount);
                catalogData.put("qcPassCount",qcPassCount);
                catalogData.put("progressCount",progressCount);
                catalogData.put("draftCount",draftCount);


                return ResponseGenerator.generateSuccessResponse(catalogData,SellerMessageResponse.SUCCESS);
            }else{
                return ResponseGenerator.generateSuccessResponse(SellerMessageResponse.FAILED,
                                                                 SellerMessageResponse.USER_NOT_FOUND);
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
    public ResponseEntity<?> getAllCatalogByQcProgressService(int page , int size) {
        try {
            //Get Current Username
            Map<String, String> currentUser = UserHelper.getCurrentUser();

            Optional<SellerStore> optional = sellerStoreRepository.findByUsername(currentUser.get("username"));

            if(optional.isPresent())
            {
                //Fetch the Progress Catalog List
                Page<SellerCatalog> catalogProgressList =
                        this.sellerCatalogRepository.findAllByCatalogStatusAndCatalogStatus(optional.get().getUsername() ,
                                    String.valueOf(CatalogRole.QC_PROGRESS) , PageRequest.of(page, size));

                log.info("Catalog Progress List Fetch Success");

                return ResponseGenerator.generateSuccessResponse(catalogProgressList,SellerMessageResponse.SUCCESS);
            }else{
                return ResponseGenerator.generateSuccessResponse(SellerMessageResponse.FAILED,
                        SellerMessageResponse.USER_NOT_FOUND);
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
    public ResponseEntity<?> getAllCatalogByDraft(int page , int size) {
        try {
            //Get Current Username
            Map<String, String> currentUser = UserHelper.getCurrentUser();

            Optional<SellerStore> optional = sellerStoreRepository.findByUsername(currentUser.get("username"));

            if(optional.isPresent())
            {
                //Fetch the Progress Catalog List
                Page<SellerCatalog> catalogDraftList =
                        this.sellerCatalogRepository.findAllByCatalogStatusAndCatalogStatus(optional.get().getUsername() ,
                                String.valueOf(CatalogRole.QC_DRAFT) , PageRequest.of(page, size));

                log.info("Catalog Draft List Fetch Success");

                return ResponseGenerator.generateSuccessResponse(catalogDraftList,SellerMessageResponse.SUCCESS);
            }else{
                return ResponseGenerator.generateSuccessResponse(SellerMessageResponse.FAILED,
                        SellerMessageResponse.USER_NOT_FOUND);
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
    public ResponseEntity<?> getAllCatalogByError(int page , int size) {
        try {
            //Get Current Username
            Map<String, String> currentUser = UserHelper.getCurrentUser();

            Optional<SellerStore> optional = sellerStoreRepository.findByUsername(currentUser.get("username"));

            if(optional.isPresent())
            {
                //Fetch the Progress Catalog List
                Page<SellerCatalog> catalogSellerList =
                        this.sellerCatalogRepository.findAllByCatalogStatusAndCatalogStatus(optional.get().getUsername()
                                , String.valueOf(CatalogRole.QC_ERROR) , PageRequest.of(page, size));

                log.info("Catalog Error List Fetch Success");

                return ResponseGenerator.generateSuccessResponse(catalogSellerList,SellerMessageResponse.SUCCESS);
            }else{
                return ResponseGenerator.generateSuccessResponse(SellerMessageResponse.FAILED,
                        SellerMessageResponse.USER_NOT_FOUND);
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
    public ResponseEntity<?> getAllCatalogByQcPass(int page , int size) {
        try {
            //Get Current Username
            Map<String, String> currentUser = UserHelper.getCurrentUser();

            Optional<SellerStore> optional = sellerStoreRepository.findByUsername(currentUser.get("username"));

            if(optional.isPresent())
            {
                //Fetch the Progress Catalog List
                Page<SellerCatalog> catalogPassList =
                        this.sellerCatalogRepository.findAllByCatalogStatusAndCatalogStatus(optional.get().getUsername() ,
                                                            String.valueOf(CatalogRole.QC_PASS) , PageRequest.of(page, size));

                log.info("Catalog Pass List Fetch Success");

                return ResponseGenerator.generateSuccessResponse(catalogPassList,SellerMessageResponse.SUCCESS);
            }else{
                return ResponseGenerator.generateSuccessResponse(SellerMessageResponse.FAILED,
                        SellerMessageResponse.USER_NOT_FOUND);
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
    public ResponseEntity<?> formBuilderFlying(String categoryId) {

        try {
            BornCategoryModel bornData = this.bornCategoryRepo.findById(Long.parseLong(categoryId))
                    .orElseThrow(() -> new DataNotFoundException("Data Not Found"));
            System.out.println("Data present Success");
          return ResponseGenerator.generateSuccessResponse(bornData,SellerMessageResponse.SUCCESS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        FormBuilderRoot formBuilder = this.formBuilderService.getFormBuilder();
        return   ResponseEntity.ok(formBuilder);
    }

    @Override
    public ResponseEntity<?> saveSellerProductNew(ProductRootBuilder productRootBuilder, Long bornCategoryId) {
        try {
            if(productRootBuilder != null){

               BornCategoryModel bornCategoryModel = this.bornCategoryRepo.findById(bornCategoryId)
                                                     .orElseThrow(()-> new DataNotFoundException("Born Category not Found"));


                // Map incoming data to SellerProduct
            SellerProduct sellerProduct = modelMapper.map(productRootBuilder, SellerProduct.class);

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

            //Set Product Status
            if(productRootBuilder.getProductVariants().isEmpty()){
                sellerProduct.setProductStatus(ProductStatus.PV_PROGRESS.toString());
            }else{
                sellerProduct.setProductStatus(ProductStatus.IN_COMPLETE.toString());
            }

            // Explicitly set the relationship for ProductVariants
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

            if(productResponse.getId() > 0 && productRootBuilder.getProductVariants().size() > 0){
                this.saveProductVariants(productResponse, productRootBuilder, bornCategoryModel);
            }

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


    public void saveProductVariants(SellerProduct sellerProduct, ProductRootBuilder productRootBuilder, BornCategoryModel bornCategoryModel){
        int rowsCounter = 2;
        try {

            Map<String, List<FormProductVariantBuilder>> managedVariants =
                    productCalculationService.groupingProductVariats(productRootBuilder);

            for (Map.Entry<String, List<FormProductVariantBuilder>> entry : managedVariants.entrySet()) {
                String color = entry.getKey(); // The color variant
                List<FormProductVariantBuilder> variants = entry.getValue(); // The list of variants for this color

                ArrayList<ProductRows> variantRowList = new ArrayList<>();

                for (FormProductVariantBuilder variant : variants) {
                    ProductRows variantRow = modelMapper.map(variant, ProductRows.class);
                    //For New Creation Entry that is 0
                    variantRow.setId(0);
                    variantRowList.add(variantRow);
                }
                productRootBuilder.setTableRows(variantRowList);

                // Map incoming data to SellerProduct
                SellerProduct sellerProductVariant = modelMapper.map(productRootBuilder, SellerProduct.class);

                //Set Rows Counter
                sellerProductVariant.setRowsCounter(rowsCounter);

                //Calculated GST
                productCalculationService.calculateTaxes(color, sellerProductVariant.getProductRows(),sellerProductVariant.getGst(),bornCategoryModel);

                //Set Color for Root Table
                sellerProductVariant.setProductColor(color);

                //set current Date
                sellerProductVariant.setProductCreationDate(productCalculationService.getCurrentDate());

                //set Current Time
                sellerProductVariant.setProductCreationTime(productCalculationService.getCurrentTime());

                //Set MapperId
                sellerProductVariant.setParentKey(String.valueOf(sellerProduct.getId()));

                //Set Product Id
                sellerProductVariant.setProductId(productCalculationService.generateProductId());

                //Set Product Status
                sellerProductVariant.setProductStatus(ProductStatus.IN_COMPLETE.toString());

                //Set Shipping Charges
                sellerProductVariant.setShippingCharges(bornCategoryModel.getShippingCharge());

                // Explicitly set the relationship for ProductVariants
                if (sellerProductVariant.getProductRows() != null) {
                    for (ProductVariants variant : sellerProductVariant.getProductRows()) {
                        if(variant.getSkuId() == "" || variant.getSkuId() == null)
                        {
                            variant.setSkuId(productCalculationService.getSkuCode());
                        }
                        variant.setSellerProduct(sellerProductVariant);
                    }
                }
                // Save SellerProduct along with its ProductVariants
                this.sellerProductRepository.save(sellerProductVariant);

                //Increment Rows-Count
                rowsCounter++;
            }
            log.info("Variant Saved Success");
            ResponseGenerator.generateSuccessResponse("Success",SellerMessageResponse.SUCCESS);
        }
        catch (Exception e)
        {
            log.error("Variant Not Saved");
            e.printStackTrace();
            ResponseGenerator.generateBadRequestResponse("Failed",SellerMessageResponse.SOMETHING_WENT_WRONG);
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

                    BucketService bucketService = new BucketService();
                    BucketModel bucketModel = bucketService.uploadFile(file);
                    System.out.println("File name: " + file.getOriginalFilename());
                    System.out.println("========FILE UPLOAD SUCCESS==============");

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
                System.out.println("Seller product Saved Success with Images");
            }
            return ResponseGenerator.generateSuccessResponse("Success",SellerMessageResponse.SUCCESS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateDataNotFound(SellerMessageResponse.DATA_NOT_FOUND);
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
            List<ProductRows> productData = productRows.stream()
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



}
