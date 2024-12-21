package com.coder.springjwt.services.sellerServices.sellerStoreService.imple;

import com.coder.springjwt.bucket.bucketModels.BucketModel;
import com.coder.springjwt.bucket.bucketService.BucketService;
import com.coder.springjwt.constants.sellerConstants.sellerMessageConstants.SellerMessageResponse;
import com.coder.springjwt.exception.adminException.DataNotFoundException;
import com.coder.springjwt.formBuilderTools.FormBuilderModel.FormBuilderTool;
import com.coder.springjwt.formBuilderTools.formVariableKeys.FormBuilderRoot;
import com.coder.springjwt.formBuilderTools.formVariableKeys.ProductRootBuilder;
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
import com.coder.springjwt.payload.sellerPayloads.sellerPayload.SellerCatalogPayload;
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
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.math.BigInteger;
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
    public ResponseEntity<?> getSellerCatalog(Long catalogId) {
        try {

            Optional<SellerCatalog> catalog = this.sellerCatalogRepository.findById(catalogId);

            if(catalog.isPresent())
            {
                SellerCatalog sellerCatalog = catalog.get();

                SellerCatalogPayload catalogNode = modelMapper.map(sellerCatalog, SellerCatalogPayload.class);

                log.info("Data Fetched Success :: Seller Catalog by Id" + SellerProductServiceImple.class.getName());

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

    @Override
    public ResponseEntity<?> saveCatalogFiles(MultipartFile file) {
        return null;
    }

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

        if(lastRow.getSpaceId() == "0" || lastRow.getSpaceId() == null){
            //Space id Starting
            String spaceId = "100000000000000000000000";
            sellerCatalog.setSpaceId(spaceId);
        }else{
            // Use BigInteger to handle the large spaceId
            BigInteger lastSpaceId = new BigInteger(lastRow.getSpaceId());
            BigInteger incrementedSpaceId = lastSpaceId.add(BigInteger.ONE);
            sellerCatalog.setSpaceId(incrementedSpaceId.toString());
        }


        //For catalogId
        if(lastRow.getCatalogId() == "0" || lastRow.getCatalogId() == null){
            //Space id Starting
            String catalogId = GenerateRandomNumber.generateRandomNumber(20)
                                + "-" + "1000000000000000";
            sellerCatalog.setCatalogId(catalogId);
        }else{
            // Extract last numeric portion
            String lastCatalogId = lastRow.getCatalogId();
            String[] parts = lastCatalogId.split("-");

            // Handle invalid format gracefully
            if (parts.length < 1) {
                throw new IllegalArgumentException("Invalid catalogId format: " + lastCatalogId);
            }

            // Increment the numeric part (last part)
            BigInteger lastNumber = new BigInteger(parts[1]);
            BigInteger incrementedNumber = lastNumber.add(BigInteger.ONE);

            // Reassemble the catalogId
            parts[0] = GenerateRandomNumber.generateRandomNumber(20);
            String newCatalogId = parts[0] + "-" + incrementedNumber.toString();
            sellerCatalog.setCatalogId(newCatalogId);
        }
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

    // Helper method to validate file size
    private boolean isValidFileSize(long size) {
        return size <= MAX_FILE_SIZE;
    }


    public boolean catalogFileStore(List<MultipartFile> files, SellerCatalog sellerCatalog)
    {
        int counter = 0;
        try {
            for (MultipartFile file : files) {
                System.out.println("File Name :: " + file.getOriginalFilename());
                //BucketModel bucketModel = bucketService.uploadFile(file);

                if(counter == 0)
                {
                    sellerCatalog.setCatalogFrontFile("https://images.unsplash.com/photo-1602810319250-a663f0af2f75?q=80&w=1887&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D");
                    sellerCatalog.setCatalogThumbnail("https://images.unsplash.com/photo-1602810319250-a663f0af2f75?q=80&w=1887&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D");
                    log.info("File 0 Upload Success");
                }
                else if(counter == 1)
                {
                    sellerCatalog.setFile_1("https://images.unsplash.com/photo-1602810320073-1230c46d89d4?q=80&w=1887&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D");
                    log.info("File 1 Upload Success");
                }
                else if(counter == 2 )
                {
                    sellerCatalog.setFile_2("https://images.unsplash.com/photo-1603251578711-3290ca1a0187?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D");
                    log.info("File 2 Upload Success");
                }
                else if(counter == 3 )
                {
                    sellerCatalog.setFile_3("https://images.unsplash.com/photo-1602810319250-a663f0af2f75?q=80&w=1887&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D");
                    log.info("File 3 Upload Success");
                }
                else if(counter == 4)
                {
                    sellerCatalog.setFile_4("https://images.unsplash.com/photo-1685883518316-355533810d68?q=80&w=1887&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D");
                    log.info("File 4 Upload Success");
                }
                else{
                    log.info("Else Executing===>");
                }

                counter++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Boolean.TRUE;
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
        productIdentity.setDescription("Please EnterProduct Name");
        productIdentity.setMinLength("2");
        productIdentity.setMaxLength("500");
        productIdentity.setExclamationDesc("exclamation Desc Generated");
        productIdentity.setIsFiledDisabled("");

        FormBuilderTool gstField = new FormBuilderTool();
        gstField.setIdentifier("gst");
        gstField.setName("Gst %");
        gstField.setType("DROPDOWN");
        gstField.setRequired(true);
        gstField.setDescription("Gst Mandatory");
        gstField.setExclamationDesc("exclamation Gst");
        gstField.setIsFiledDisabled("");
        gstField.setValues(List.of("5 %","10 %","12 %","15 %","18 %"));


        FormBuilderTool hsnField = new FormBuilderTool();
        hsnField.setIdentifier("hsn");
        hsnField.setName("hsn");
        hsnField.setType("DROPDOWN");
        hsnField.setRequired(true);
        hsnField.setDescription("hsn");
        hsnField.setValues(hsnCodes.stream().map(HsnCodes::getHsn).collect(Collectors.toList()));

        FormBuilderTool productCode = new FormBuilderTool();
        productCode.setIdentifier("productCode");
        productCode.setName("Product Code(Optional) *");
        productCode.setType("TEXT");
        productCode.setRequired(false);
        productCode.setMinLength("5");
        productCode.setMaxLength("20");
        productCode.setDescription("product Code");
        productCode.setValues(null);

        List<FormBuilderTool> productIdentityList = new ArrayList<>();
        productIdentityList.add(productIdentity);
        productIdentityList.add(gstField);
        productIdentityList.add(hsnField);
        productIdentityList.add(productCode);

//        =========================================

        //Sizes
        FormBuilderTool sizeField = new FormBuilderTool();
        sizeField.setIdentifier("productSize");
        sizeField.setName("Product Size");
        sizeField.setType("MULTISELECT");
        sizeField.setRequired(false);
        sizeField.setDescription("Product Size Mandatory");
        sizeField.setExclamationDesc("Product Size");
        sizeField.setIsFiledDisabled("");
        sizeField.setValues(List.of("S","M","L","XL","XXL","3XL","4XL","5XL","6XL"));

        List<FormBuilderTool> productSizeList = new ArrayList<>();
        productSizeList.add(sizeField);


//        ======================================
        FormBuilderTool sizeLabel = new FormBuilderTool();
        sizeLabel.setIdentifier("productLabel");
        sizeLabel.setName("size");
        sizeLabel.setType("LABEL");
        sizeLabel.setRequired(true);
        sizeLabel.setDescription("product Size");
        sizeLabel.setExclamationDesc("product Size");

        FormBuilderTool productPrice = new FormBuilderTool();
        productPrice.setIdentifier("productPrice");
        productPrice.setName("price");
        productPrice.setType("TEXT");
        productPrice.setRequired(true);
        productPrice.setDescription("Please Enter Price");
        productPrice.setMinLength("2");
        productPrice.setMaxLength("50");
        productPrice.setExclamationDesc("Price Alternatives");
        productPrice.setIsFiledDisabled("");

        FormBuilderTool productMrp = new FormBuilderTool();
        productMrp.setIdentifier("productMrp");
        productMrp.setName("productMrp");
        productMrp.setType("TEXT");
        productMrp.setRequired(true);
        productMrp.setDescription("product Mrp");
        productMrp.setMinLength("2");
        productMrp.setMaxLength("4");
        productMrp.setExclamationDesc("productMrp Alternatives");
        productMrp.setIsFiledDisabled("");

        FormBuilderTool productLength = new FormBuilderTool();
        productLength.setIdentifier("productLength");
        productLength.setName("product Length");
        productLength.setType("DROPDOWN");
        productLength.setRequired(true);
        productLength.setDescription("Product Length");
        productLength.setExclamationDesc("Product Length");
        productLength.setIsFiledDisabled("");
        productLength.setValues(List.of("10","20","40","80","200","300","400"));

        FormBuilderTool skuId = new FormBuilderTool();
        skuId.setIdentifier("skuId");
        skuId.setName("skuId");
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
        productVariants.add(productLength);
        productVariants.add(skuId);



        //////////////////Product Details///////////
        FormBuilderTool styleName = new FormBuilderTool();
        styleName.setIdentifier("styleName");
        styleName.setName("styleName");
        styleName.setType("TEXT");
        styleName.setRequired(true);
        styleName.setDescription("styleName");
        styleName.setMinLength("2");
        styleName.setMaxLength("100");
        styleName.setExclamationDesc("styleName");
        styleName.setIsFiledDisabled("");

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
        genderField.setName("gender Name");
        genderField.setType("DROPDOWN");
        genderField.setRequired(true);
        genderField.setDescription("Gender please select");
        genderField.setMinLength("");
        genderField.setMaxLength("");
        genderField.setValues(List.of("Male","Female","Other"));

        FormBuilderTool materialTypeField = new FormBuilderTool();
        materialTypeField.setIdentifier("materialType");
        materialTypeField.setName("material Type");
        materialTypeField.setType("DROPDOWN");
        materialTypeField.setRequired(true);
        materialTypeField.setDescription("material Type please select");
        materialTypeField.setMinLength("");
        materialTypeField.setMaxLength("");
        materialTypeField.setValues(List.of("Cotton","Satin","Leather","Linen","Denim","Velvet","wool"));

        FormBuilderTool colorField = new FormBuilderTool();
        colorField.setIdentifier("productColor");
        colorField.setName("productColor");
        colorField.setType("DROPDOWN");
        colorField.setRequired(true);
        colorField.setDescription("productColor please select");
        colorField.setMinLength("");
        colorField.setMaxLength("");
        colorField.setValues(List.of("Yellow","Green","Blue","Green","Orange","Velvet","Brown"));

        FormBuilderTool countryOriginField = new FormBuilderTool();
        countryOriginField.setIdentifier("country");
        countryOriginField.setName("country");
        countryOriginField.setType("DROPDOWN");
        countryOriginField.setRequired(true);
        countryOriginField.setDescription("country please select");
        countryOriginField.setMinLength("");
        countryOriginField.setMaxLength("");
        countryOriginField.setValues(List.of("India"));

        FormBuilderTool patternField = new FormBuilderTool();
        patternField.setIdentifier("pattern");
        patternField.setName("pattern");
        patternField.setType("DROPDOWN");
        patternField.setRequired(true);
        patternField.setDescription("pattern please select");
        patternField.setMinLength("");
        patternField.setMaxLength("");
        patternField.setValues(List.of("Line","Circle"));


        FormBuilderTool manufactureField = new FormBuilderTool();
        manufactureField.setIdentifier("manufactureName");
        manufactureField.setName("manufactureName");
        manufactureField.setType("TEXT");
        manufactureField.setRequired(false);
        manufactureField.setDescription("country please select");
        manufactureField.setMinLength("2");
        manufactureField.setMaxLength("100");
        manufactureField.setValues(null);


        List<FormBuilderTool> productDetails = new ArrayList<>();
        productDetails.add(styleName);
        productDetails.add(sleeveTypeField);
        productDetails.add(fitTypeField);
        productDetails.add(genderField);
        productDetails.add(materialTypeField);
        productDetails.add(colorField);
        productDetails.add(countryOriginField);
        productDetails.add(patternField);
        productDetails.add(manufactureField);


        //Product Description and Other Details
        FormBuilderTool numberOfItemsField = new FormBuilderTool();
        numberOfItemsField.setIdentifier("numberOfItems");
        numberOfItemsField.setName("number Of Items");
        numberOfItemsField.setType("DROPDOWN");
        numberOfItemsField.setRequired(true);
        numberOfItemsField.setDescription("numberOfItems please select");
        numberOfItemsField.setMinLength("");
        numberOfItemsField.setMaxLength("");
        numberOfItemsField.setValues(List.of("1","2","3","4","5","6","7","8","9","10"));

        FormBuilderTool finishingType = new FormBuilderTool();
        finishingType.setIdentifier("finishingType");
        finishingType.setName("finishing Type ");
        finishingType.setType("DROPDOWN");
        finishingType.setRequired(true);
        finishingType.setDescription("finishingType please select");
        finishingType.setMinLength("");
        finishingType.setMaxLength("");
        finishingType.setValues(List.of("Liner","Rarer","Printing","blur shade","shades","multiShades"));

        FormBuilderTool brandField = new FormBuilderTool();
        brandField.setIdentifier("brandField");
        brandField.setName("brandField Type ");
        brandField.setType("DROPDOWN");
        brandField.setRequired(false);
        brandField.setDescription("brandField please select");
        brandField.setMinLength("");
        brandField.setMaxLength("");
        brandField.setValues(List.of("Jack & jones","Microman","Puma","Generic","lux cozi","spyker"));

        FormBuilderTool descriptionFiled = new FormBuilderTool();
        descriptionFiled.setIdentifier("description");
        descriptionFiled.setName("description");
        descriptionFiled.setType("TEXTBOX");
        descriptionFiled.setRequired(true);
        descriptionFiled.setDescription("please fill description");
        descriptionFiled.setMinLength("10");
        descriptionFiled.setMaxLength("5000");
        descriptionFiled.setValues(null);

        List<FormBuilderTool> productOtherDetails = new ArrayList<>();
        productOtherDetails.add(numberOfItemsField);
        productOtherDetails.add(finishingType);
        productOtherDetails.add(brandField);
        productOtherDetails.add(descriptionFiled);

        FormBuilderRoot formBuilderRoot = new FormBuilderRoot();
        formBuilderRoot.setProductIdentityList(productIdentityList);
        formBuilderRoot.setProductSizes(productSizeList);
        formBuilderRoot.setProductVariants(productVariants);
        formBuilderRoot.setProductDetails(productDetails);
        formBuilderRoot.setProductOtherDetails(productOtherDetails);

        JSONObject jsonObject = new JSONObject(formBuilderRoot);
        System.out.println(jsonObject);
        return  ResponseEntity.ok(formBuilderRoot);
    }

    @Override
    public ResponseEntity<?> saveSellerProductNew(ProductRootBuilder productRootBuilder) {
        try {
            System.out.println(productRootBuilder);
            System.out.println("================---------------------------------------==================");

            // Map incoming data to SellerProduct
            SellerProduct sellerProduct = modelMapper.map(productRootBuilder, SellerProduct.class);

            // Explicitly set the relationship for ProductVariants
            if (sellerProduct.getProductRows() != null) {
                for (ProductVariants variant : sellerProduct.getProductRows()) {
                    variant.setSellerProduct(sellerProduct);
                }
            }
            // Save SellerProduct along with its ProductVariants
            SellerProduct productResponse = this.sellerProductRepository.save(sellerProduct);
            return ResponseGenerator.generateSuccessResponse(productResponse.getId(),SellerMessageResponse.SUCCESS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse("FAILED",SellerMessageResponse.SOMETHING_WENT_WRONG);
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

}
