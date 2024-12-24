package com.coder.springjwt.services.sellerServices.sellerStoreService.imple;

import com.coder.springjwt.bucket.bucketModels.BucketModel;
import com.coder.springjwt.bucket.bucketService.BucketService;
import com.coder.springjwt.constants.sellerConstants.sellerMessageConstants.SellerMessageResponse;
import com.coder.springjwt.exception.adminException.DataNotFoundException;
import com.coder.springjwt.formBuilderTools.FormBuilderModel.FormBuilderTool;
import com.coder.springjwt.formBuilderTools.formVariableKeys.FormBuilderRoot;
import com.coder.springjwt.formBuilderTools.formVariableKeys.ProductRootBuilder;
import com.coder.springjwt.formBuilderTools.formVariableKeys.ProductRows;
import com.coder.springjwt.helpers.generateRandomNumbers.GenerateRandomNumber;
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
import org.json.JSONObject;
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
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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

        List<HsnCodes> hsnCodes = hsnRepository.findAll();

        FormBuilderTool productIdentity = new FormBuilderTool();
        productIdentity.setIdentifier("productName");
        productIdentity.setName("Product Name");
        productIdentity.setType("TEXT");
        productIdentity.setRequired(true);
        productIdentity.setDescription("Please Enter Product Name");
        productIdentity.setMinLength("10");
        productIdentity.setMaxLength("200");
        productIdentity.setExclamationDesc("Please Enter Product Name");
        productIdentity.setIsFiledDisabled("");

        FormBuilderTool gstField = new FormBuilderTool();
        gstField.setIdentifier("gst");
        gstField.setName("GST");
        gstField.setType("DROPDOWN");
        gstField.setRequired(true);
        gstField.setDescription("Gst Mandatory");
        gstField.setExclamationDesc("Gst Mandatory");
        gstField.setIsFiledDisabled("");
        gstField.setValues(List.of("5 %","10","12 %","15 %","18 %"));


        FormBuilderTool hsnField = new FormBuilderTool();
        hsnField.setIdentifier("hsn");
        hsnField.setName("HSN");
        hsnField.setType("DROPDOWN");
        hsnField.setRequired(true);
        hsnField.setDescription("hsn");
        hsnField.setValues(hsnCodes.stream().map(HsnCodes::getHsn).collect(Collectors.toList()));

        FormBuilderTool productWeight = new FormBuilderTool();
        productWeight.setIdentifier("productWeight");
        productWeight.setName("Weight(g)");
        productWeight.setType("TEXT");
        productWeight.setRequired(true);
        productWeight.setDescription("Please Enter Net Weight");
        productWeight.setMinLength("1");
        productWeight.setMaxLength("5");
        productWeight.setExclamationDesc("Please Enter Net Weight");
        productWeight.setIsFiledDisabled("");

        List<FormBuilderTool> productIdentityList = new ArrayList<>();
        productIdentityList.add(productIdentity);
        productIdentityList.add(gstField);
        productIdentityList.add(hsnField);
        productIdentityList.add(productWeight);

//        =========================================

        //Sizes
        FormBuilderTool sizeField = new FormBuilderTool();
        sizeField.setIdentifier("productSize");
        sizeField.setName("Product Size");
        sizeField.setType("MULTISELECT");
        sizeField.setRequired(true);
        sizeField.setDescription("Product Size Mandatory");
        sizeField.setExclamationDesc("Product Size");
        sizeField.setIsFiledDisabled("");
        sizeField.setValues(List.of("S","M","L","XL","XXL","3XL","4XL","5XL","6XL"));

        List<FormBuilderTool> productSizeList = new ArrayList<>();
        productSizeList.add(sizeField);


//        =======================TABLE ROWS===============
        FormBuilderTool sizeLabel = new FormBuilderTool();
        sizeLabel.setIdentifier("productLabel");
        sizeLabel.setName("size");
        sizeLabel.setType("LABEL");
        sizeLabel.setRequired(true);
        sizeLabel.setDescription("product Size");
        sizeLabel.setExclamationDesc("product Size");


        FormBuilderTool productPrice = new FormBuilderTool();
        productPrice.setIdentifier("productPrice");
        productPrice.setName("Price");
        productPrice.setType("TEXT");
        productPrice.setRequired(true);
        productPrice.setDescription("Please Enter Price");
        productPrice.setMinLength("2");
        productPrice.setMaxLength("5");
        productPrice.setExclamationDesc("Price Alternatives");
        productPrice.setIsFiledDisabled("");

        FormBuilderTool productMrp = new FormBuilderTool();
        productMrp.setIdentifier("productMrp");
        productMrp.setName("Mrp");
        productMrp.setType("TEXT");
        productMrp.setRequired(true);
        productMrp.setDescription("product Mrp");
        productMrp.setMinLength("2");
        productMrp.setMaxLength("4");
        productMrp.setExclamationDesc("productMrp Alternatives");
        productMrp.setIsFiledDisabled("");

        FormBuilderTool productInventory = new FormBuilderTool();
        productInventory.setIdentifier("productInventory");
        productInventory.setName("Product Inventory");
        productInventory.setType("TEXT");
        productInventory.setRequired(true);
        productInventory.setDescription("Enter Product Inventory");
        productInventory.setMinLength("1");
        productInventory.setMaxLength("5");
        productInventory.setExclamationDesc("Product Inventory");
        productInventory.setIsFiledDisabled("");

        FormBuilderTool productLength = new FormBuilderTool();
        productLength.setIdentifier("productLength");
        productLength.setName("Product Length(cm)");
        productLength.setType("DROPDOWN");
        productLength.setRequired(true);
        productLength.setDescription("Enter Product Length");
        productLength.setExclamationDesc("Product Length");
        productLength.setIsFiledDisabled("");
        productLength.setValues(List.of("5","10","15","20","25","30","35","40","45","50"));

        FormBuilderTool waistSize = new FormBuilderTool();
        waistSize.setIdentifier("waistSize");
        waistSize.setName("Waist Size(cm)");
        waistSize.setType("DROPDOWN");
        waistSize.setRequired(true);
        waistSize.setDescription("Enter Waist Size");
        waistSize.setExclamationDesc("Waist Size");
        waistSize.setIsFiledDisabled("");
        waistSize.setValues(List.of("5","10","15","20","25","30","35","40","45","50"));

        FormBuilderTool shoulderWidth = new FormBuilderTool();
        shoulderWidth.setIdentifier("shoulderWidth");
        shoulderWidth.setName("Shoulder Width(cm)");
        shoulderWidth.setType("DROPDOWN");
        shoulderWidth.setRequired(true);
        shoulderWidth.setDescription("Enter Shoulder Width");
        shoulderWidth.setExclamationDesc("Shoulder Width");
        shoulderWidth.setIsFiledDisabled("");
        shoulderWidth.setValues(List.of("5","10","15","20","25","30","35","40","45","50"));

        FormBuilderTool chestBustSize = new FormBuilderTool();
        chestBustSize.setIdentifier("chestBustSize");
        chestBustSize.setName("Chest/Bust Size(cm)");
        chestBustSize.setType("DROPDOWN");
        chestBustSize.setRequired(true);
        chestBustSize.setDescription("Enter Chest/Bust Size");
        chestBustSize.setExclamationDesc("Chest/Bust Size");
        chestBustSize.setIsFiledDisabled("");
        chestBustSize.setValues(List.of("5","10","15","20","25","30","35","40","45","50"));

        FormBuilderTool skuId = new FormBuilderTool();
        skuId.setIdentifier("skuId");
        skuId.setName("SKU Code");
        skuId.setType("TEXT");
        skuId.setRequired(false);
        skuId.setDescription("skuId skuId");
        skuId.setMinLength("");
        skuId.setMaxLength("100");
        skuId.setExclamationDesc("skuId skuId");
        skuId.setIsFiledDisabled("");

        List<FormBuilderTool> productVariants = new ArrayList<>();
        productVariants.add(sizeLabel);
        productVariants.add(productPrice);
        productVariants.add(productMrp);
        productVariants.add(productInventory);
        productVariants.add(productLength);
        productVariants.add(waistSize);
        productVariants.add(shoulderWidth);
        productVariants.add(chestBustSize);
        productVariants.add(skuId);


        //////////////////Product Details///////////

        FormBuilderTool productCode = new FormBuilderTool();
        productCode.setIdentifier("productCode");
        productCode.setName("product code(optional)");
        productCode.setType("TEXT");
        productCode.setRequired(false);
        productCode.setDescription("Enter Product Code");
        productCode.setMinLength("10");
        productCode.setMaxLength("50");
        productCode.setExclamationDesc("productCode Verification");
        productCode.setIsFiledDisabled("");

        FormBuilderTool sleeveTypeField = new FormBuilderTool();
        sleeveTypeField.setIdentifier("sleeveType");
        sleeveTypeField.setName("Sleeve Type");
        sleeveTypeField.setType("DROPDOWN");
        sleeveTypeField.setRequired(true);
        sleeveTypeField.setDescription("Sleeve");
        sleeveTypeField.setMinLength("");
        sleeveTypeField.setMaxLength("");
        sleeveTypeField.setValues(List.of("Half Sleeve %","full Sleeve"));

        FormBuilderTool fitTypeField = new FormBuilderTool();
        fitTypeField.setIdentifier("fitType");
        fitTypeField.setName("fitType Name");
        fitTypeField.setType("DROPDOWN");
        fitTypeField.setRequired(true);
        fitTypeField.setDescription("fitType please select");
        fitTypeField.setMinLength("");
        fitTypeField.setMaxLength("");
        fitTypeField.setValues(List.of("Regular Fit","Skin Fit"));

        FormBuilderTool genderField = new FormBuilderTool();
        genderField.setIdentifier("gender");
        genderField.setName("gender");
        genderField.setType("DROPDOWN");
        genderField.setRequired(true);
        genderField.setDescription("Select Gender");
        genderField.setMinLength("");
        genderField.setMaxLength("");
        genderField.setValues(List.of("Male","Female","Other"));

        FormBuilderTool materialTypeField = new FormBuilderTool();
        materialTypeField.setIdentifier("materialType");
        materialTypeField.setName("material Type");
        materialTypeField.setType("DROPDOWN");
        materialTypeField.setRequired(true);
        materialTypeField.setDescription("Select Material Type");
        materialTypeField.setMinLength("");
        materialTypeField.setMaxLength("");
        materialTypeField.setValues(List.of("Cotton","Satin","Leather","Linen","Denim","Velvet","wool"));

        FormBuilderTool colorField = new FormBuilderTool();
        colorField.setIdentifier("productColor");
        colorField.setName("productColor");
        colorField.setType("DROPDOWN");
        colorField.setRequired(true);
        colorField.setDescription("Select Product Color");
        colorField.setMinLength("");
        colorField.setMaxLength("");
        colorField.setValues(List.of("Yellow","Green","Blue","Green","Orange","Velvet","Brown"));

        FormBuilderTool countryOriginField = new FormBuilderTool();
        countryOriginField.setIdentifier("country");
        countryOriginField.setName("country");
        countryOriginField.setType("DROPDOWN");
        countryOriginField.setRequired(true);
        countryOriginField.setDescription("Select Country");
        countryOriginField.setMinLength("");
        countryOriginField.setMaxLength("");
        countryOriginField.setValues(List.of("India"));

        FormBuilderTool patternField = new FormBuilderTool();
        patternField.setIdentifier("pattern");
        patternField.setName("pattern");
        patternField.setType("DROPDOWN");
        patternField.setRequired(true);
        patternField.setDescription("Select Pattern");
        patternField.setMinLength("");
        patternField.setMaxLength("");
        patternField.setValues(List.of("Line","Circle","shades","circle","herosim"));

        FormBuilderTool finishingType = new FormBuilderTool();
        finishingType.setIdentifier("finishingType");
        finishingType.setName("Finishing Type ");
        finishingType.setType("DROPDOWN");
        finishingType.setRequired(true);
        finishingType.setDescription("finishingType please select");
        finishingType.setMinLength("");
        finishingType.setMaxLength("");
        finishingType.setValues(List.of("Liner","Rarer","Printing","blur shade","shades","multiShades"));


        FormBuilderTool netQuantity = new FormBuilderTool();
        netQuantity.setIdentifier("netQuantity");
        netQuantity.setName("Net Quantity");
        netQuantity.setType("DROPDOWN");
        netQuantity.setRequired(true);
        netQuantity.setDescription("Select Net Quantity");
        netQuantity.setMinLength("");
        netQuantity.setMaxLength("");
        netQuantity.setValues(List.of("1","2","3","4","5","6","7","8","9","10"));


        List<FormBuilderTool> productDetails = new ArrayList<>();
        productDetails.add(productCode);
        productDetails.add(colorField);
        productDetails.add(sleeveTypeField);
        productDetails.add(fitTypeField);
        productDetails.add(genderField);
        productDetails.add(materialTypeField);
        productDetails.add(countryOriginField);
        productDetails.add(patternField);
        productDetails.add(finishingType);
        productDetails.add(netQuantity);


        //Product Description and Other Details
        FormBuilderTool manufactureField = new FormBuilderTool();
        manufactureField.setIdentifier("manufactureName");
        manufactureField.setName("manufactureName(Optional)");
        manufactureField.setType("TEXT");
        manufactureField.setRequired(false);
        manufactureField.setDescription("Select Manufacturer Name");
        manufactureField.setMinLength("10");
        manufactureField.setMaxLength("50");
        manufactureField.setValues(null);


        FormBuilderTool brandField = new FormBuilderTool();
        brandField.setIdentifier("brandField");
        brandField.setName("Brand(Optional)");
        brandField.setType("DROPDOWN");
        brandField.setRequired(false);
        brandField.setDescription("brandField please select");
        brandField.setMinLength("");
        brandField.setMaxLength("");
        brandField.setValues(List.of("Jack & jones","Microman","Puma","Generic","lux cozi","spyker"));

        FormBuilderTool descriptionFiled = new FormBuilderTool();
        descriptionFiled.setIdentifier("description");
        descriptionFiled.setName("Description");
        descriptionFiled.setType("TEXTBOX");
        descriptionFiled.setRequired(true);
        descriptionFiled.setDescription("please fill description");
        descriptionFiled.setMinLength("50");
        descriptionFiled.setMaxLength("5000");
        descriptionFiled.setValues(null);

        List<FormBuilderTool> productOtherDetails = new ArrayList<>();
        productOtherDetails.add(manufactureField);
        productOtherDetails.add(brandField);
        productOtherDetails.add(descriptionFiled);


        //AddVariant Creation Product Data (Model)
        List<FormBuilderTool> makerProductVariant = new ArrayList<>();
        makerProductVariant.add(colorField);
        makerProductVariant.add(sizeField);

        //Variant Creation Product Data (Model)
        FormBuilderTool makerColorVariant = new FormBuilderTool();
        makerColorVariant.setIdentifier("ColorVariant");
        makerColorVariant.setName("Color");
        makerColorVariant.setType("TEXT");
        makerColorVariant.setRequired(true);
        makerColorVariant.setDescription("Color Variant");
        makerColorVariant.setMinLength("");
        makerColorVariant.setMaxLength("0");
        makerColorVariant.setExclamationDesc("Color Variant");
        makerColorVariant.setIsFiledDisabled("");

        List<FormBuilderTool> makerAddVariantData = new ArrayList<>();
        makerAddVariantData.add(makerColorVariant);
        makerAddVariantData.add(sizeLabel);
        makerAddVariantData.add(productPrice);
        makerAddVariantData.add(productMrp);
        makerAddVariantData.add(productInventory);
        makerAddVariantData.add(productLength);
        makerAddVariantData.add(waistSize);
        makerAddVariantData.add(shoulderWidth);
        makerAddVariantData.add(chestBustSize);
        makerAddVariantData.add(skuId);

        FormBuilderRoot formBuilderRoot = new FormBuilderRoot();
        formBuilderRoot.setProductIdentityList(productIdentityList);
        formBuilderRoot.setProductSizes(productSizeList);
        formBuilderRoot.setProductVariants(productVariants);
        formBuilderRoot.setProductDetails(productDetails);
        formBuilderRoot.setProductOtherDetails(productOtherDetails);

        //Maker Product Variant
        formBuilderRoot.setMakerColorAndSize(makerProductVariant);
        formBuilderRoot.setMakerAddVariantData(makerAddVariantData);

        JSONObject jsonObject = new JSONObject(formBuilderRoot);
        System.out.println(jsonObject);
        return  ResponseEntity.ok(formBuilderRoot);
    }

    @Override
    public ResponseEntity<?> saveSellerProductNew(ProductRootBuilder productRootBuilder) {
        try {
            System.out.println(productRootBuilder);
            System.out.println("================---------------------------------------==================");

            if(productRootBuilder != null){

            // Map incoming data to SellerProduct
            SellerProduct sellerProduct = modelMapper.map(productRootBuilder, SellerProduct.class);

            //set current Date
            sellerProduct.setProductCreationDate(getCurrentDate());

            //set Current Time
            sellerProduct.setProductCreationTime(getCurrentTime());

            //Calculated GST
            this.calculateTaxes(sellerProduct.getProductRows(),sellerProduct.getGst());

            // Explicitly set the relationship for ProductVariants
            if (sellerProduct.getProductRows() != null) {
                for (ProductVariants variant : sellerProduct.getProductRows()) {
                    variant.setSellerProduct(sellerProduct);
                }
            }
                // Save SellerProduct along with its ProductVariants
                SellerProduct productResponse = this.sellerProductRepository.save(sellerProduct);
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


    public String calculateTaxes(List<ProductVariants> productVariants, String gstWithPercent){
            try {
                String gst = gstWithPercent.replace("%", "");
                for(ProductVariants pv : productVariants){

                    double grossServiceTax = this.calculateGST(Double.parseDouble(pv.getProductPrice()), Double.parseDouble(gst));
                    System.out.println("grossServiceTax:: " + grossServiceTax);

                    double tcs = this.calculateTCS(Double.parseDouble(pv.getProductPrice()), Double.parseDouble(gst));
                    System.out.println("tcs:: " + tcs);

                    double tds = this.calculateTDS(Double.parseDouble(pv.getProductPrice()));
                    System.out.println("tds:: " + tds);

                    double totalPrice = this.calculateTotalPrice(Double.parseDouble(pv.getProductPrice()), grossServiceTax , tcs);
                    System.out.println("totalPrice:: " + totalPrice);

                    System.out.println("-------------------------------------------------");
                    pv.setCalculatedGst(String.valueOf(roundToTwoDecimalPlaces(grossServiceTax)));
                    pv.setCalculatedTcs(String.valueOf(roundToTwoDecimalPlaces(tcs)));
                    pv.setCalculatedTds(String.valueOf(roundToTwoDecimalPlaces(tds)));
                    pv.setCalculatedTotalPrice(String.valueOf(roundToTwoDecimalPlaces(totalPrice)));

                    String calculatedDiscount = calculateDiscount(Double.parseDouble(pv.getProductPrice()),
                                                Double.parseDouble(pv.getProductMrp()));
                    pv.setCalculatedDiscount(calculatedDiscount);
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
            return null;
    }

    // Method to calculate GST,TCS,TDS,TOTAL PRICE
    public double calculateGST(double price, double gstRate) {
        return price * gstRate / 100;
    }

    // Method to calculate TCS (assuming TCS is 1% of the price including GST)
    public double calculateTCS(double price, double gst) {
        double totalPrice = price + gst;
        return totalPrice * 0.01; // 1% TCS
    }

    // Method to calculate TDS (assuming TDS is 1% of the product price excluding GST)
    public double calculateTDS(double price) {
        return price * 0.01; // 1% TDS
    }

    // Method to get the total price including GST, TCS, and TDS
    public double calculateTotalPrice(double price, double gst, double tcs) {
        return price + gst + tcs;
    }

    public Double roundToTwoDecimalPlaces(Double value) {
        if (value == null) {
            return null;
        }
        return new BigDecimal(value).setScale(2, RoundingMode.HALF_UP).doubleValue();
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
    public String getCurrentDate() // d MMM yyyy
    {
        //Set Catalog Date
        LocalDate currentDate = LocalDate.now();
        // Define the formatter
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM yyyy");
        //Set Catalog Time
        // Format the date
        return currentDate.format(formatter);
    }
    public String getCurrentTime()
    {
        //Set Currect Time
        LocalTime currentTime = LocalTime.now();
        // Define the formatter
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
        // Format the time
        return currentTime.format(formatter);
    }

    // Helper method to validate file type
    private boolean isValidImageFormat(String contentType) {
        return contentType != null &&
                (contentType.equals("image/png") || contentType.equals("image/jpeg"));
    }



    public void setSpaceId(SellerCatalog sellerCatalog){

        //For Space
        SellerCatalog lastRow = sellerCatalogRepository.findTopByOrderByIdDesc();

        if(lastRow == null)
        {
            String spaceId = "100000000000000000000000";
            sellerCatalog.setSpaceId(spaceId);

            String catalogId = GenerateRandomNumber.generateRandomNumber(20)
                    + "-" + "1000000000000000";
            sellerCatalog.setCatalogId(catalogId);
            return;
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
