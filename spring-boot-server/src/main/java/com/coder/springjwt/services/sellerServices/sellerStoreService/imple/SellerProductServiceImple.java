package com.coder.springjwt.services.sellerServices.sellerStoreService.imple;

import com.amazonaws.Response;
import com.coder.springjwt.bucket.bucketService.BucketService;
import com.coder.springjwt.constants.sellerConstants.sellerMessageConstants.SellerMessageResponse;
import com.coder.springjwt.exception.adminException.DataNotFoundException;
import com.coder.springjwt.formBuilderTools.FormBuilderModel.FormBuilderTool;
import com.coder.springjwt.formBuilderTools.formVariableKeys.FormBuilderRoot;
import com.coder.springjwt.formBuilderTools.FormBuilderModel.ProductDataBuilder;
import com.coder.springjwt.formBuilderTools.FormBuilderModel.SizeDataBuilder;
import com.coder.springjwt.formBuilderTools.FormBuilderModel.TableDataBuilder;
import com.coder.springjwt.formBuilderTools.formVariableKeys.ProductRootData;
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
    public ResponseEntity<?> productDataFormBuilder(String categoryId) {
//        System.out.println("BornCategoryId :: " +  categoryId);
//
//        Optional<BornCategoryModel> bornCategory = this.bornCategoryRepo.findById(Long.valueOf(categoryId));
//        if(bornCategory.isPresent())
//        {
//            System.out.println("Form JSON Present in Database:: " +categoryId);
//            System.out.println(bornCategory.get().getFormBuilderModel().getFormBuilder());
//            return ResponseEntity.ok(bornCategory.get().getFormBuilderModel().getFormBuilder());
//        }
//        System.out.println("JSON Not Present in Database regards Category ID::  " +categoryId);


        List<HsnCodes> hsnCodes = hsnRepository.findAll();
        if(categoryId.equals("2"))
        {
            SizeDataBuilder sizeField = new SizeDataBuilder();
            sizeField.setIdentifier("productSize");
            sizeField.setName("productSize");
            sizeField.setType("multi-select");
            sizeField.setRequired(true);
            sizeField.setDescription("Size");
            sizeField.setMin("");
            sizeField.setMax("");
            sizeField.setValues(List.of("Free Size" , "S" , "M" , "L" , "XXL" ,"3XL","SM","2M", "SSL", "PXL","5XLS"));
            List<SizeDataBuilder> sizeDataBuilderList = new ArrayList<>();
            sizeDataBuilderList.add(sizeField);



            //Table Data
            TableDataBuilder variantSize = new TableDataBuilder();
            variantSize.setIdentifier("variantSize");
            variantSize.setName("Variant Size");
            variantSize.setType("text");
            variantSize.setRequired(false);
            variantSize.setDescription("Variant Size");
            variantSize.setMin("");
            variantSize.setMax("");
            variantSize.setValues(null);

            TableDataBuilder priceField = new TableDataBuilder();
            priceField.setIdentifier("price");
            priceField.setName("price");
            priceField.setType("text");
            priceField.setRequired(true);
            priceField.setDescription("Please Fill Price");
            priceField.setMin("");
            priceField.setMax("");

            TableDataBuilder productMrp = new TableDataBuilder();
            productMrp.setIdentifier("mrp");
            productMrp.setName("Mrp Price");
            productMrp.setType("text");
            productMrp.setRequired(true);
            productMrp.setDescription("Please Fill Mrp Price");
            productMrp.setMin("10");
            productMrp.setMax("10000");

            TableDataBuilder skuId = new TableDataBuilder();
            skuId.setIdentifier("skuId");
            skuId.setName("SKU ID");
            skuId.setType("text");
            skuId.setRequired(true);
            skuId.setDescription("Please Fill Sku Id (optional)");
            skuId.setMin("0");
            skuId.setMax("500");

            TableDataBuilder lengthField = new TableDataBuilder();
            lengthField.setIdentifier("productLength");
            lengthField.setName("product length (inch)");
            lengthField.setType("dropdown");
            lengthField.setRequired(true);
            lengthField.setDescription("Please Fill Product Length");
            lengthField.setMin("2");
            lengthField.setMax("12");
            lengthField.setValues(List.of("5","15","25","300","100"));

            TableDataBuilder  breathField= new TableDataBuilder();
            breathField.setIdentifier("productBreath");
            breathField.setName("product Breath");
            breathField.setType("dropdown");
            breathField.setRequired(true);
            breathField.setDescription("Please Fill Breath");
            breathField.setMin("");
            breathField.setMax("");
            breathField.setValues(List.of("45","50","55","60","65","70","75","80"));

            TableDataBuilder  heightField= new TableDataBuilder();
            heightField.setIdentifier("productHeight");
            heightField.setName("product height");
            heightField.setType("dropdown");
            heightField.setRequired(true);
            heightField.setDescription("Please Fill height");
            heightField.setMin("");
            heightField.setMax("");
            heightField.setValues(List.of("100","101","102","103","106","109","145","1520"));

            List<TableDataBuilder> tableDataBuilders = new ArrayList<>();
            tableDataBuilders.add(variantSize);
            tableDataBuilders.add(priceField);
            tableDataBuilders.add(productMrp);
            tableDataBuilders.add(skuId);
            tableDataBuilders.add(lengthField);
            tableDataBuilders.add(breathField);
            tableDataBuilders.add(heightField);



//            Product Data Form
            ProductDataBuilder productNameField = new ProductDataBuilder();
            productNameField.setIdentifier("productName");
            productNameField.setName("Product Name");
            productNameField.setType("text");
            productNameField.setRequired(true);
            productNameField.setDescription("Please EnterProduct Name");
            productNameField.setMin("10");
            productNameField.setMax("300");

            ProductDataBuilder gstField = new ProductDataBuilder();
            gstField.setIdentifier("gst");
            gstField.setName("Gst %");
            gstField.setType("dropdown");
            gstField.setRequired(true);
            gstField.setDescription("Gst Mandatory");
            gstField.setMin("");
            gstField.setMax("");
            gstField.setValues(List.of("5 %","10 %","12 %","15 %","18 %"));

            ProductDataBuilder hsnField = new ProductDataBuilder();
            hsnField.setIdentifier("hsn");
            hsnField.setName("hsn");
            hsnField.setType("dropdown");
            hsnField.setRequired(true);
            hsnField.setDescription("hsn");
            hsnField.setMin("");
            hsnField.setMax("");
            hsnField.setValues(hsnCodes.stream().map(HsnCodes::getHsn).collect(Collectors.toList()));

            ProductDataBuilder productCode = new ProductDataBuilder();
            productCode.setIdentifier("productCode");
            productCode.setName("Product Code(Optional)");
            productCode.setType("text");
            productCode.setRequired(false);
            productCode.setDescription("product Code");
            productCode.setMin("");
            productCode.setMax("");
            productCode.setValues(null);

            List<ProductDataBuilder> productDataBuilderList = new ArrayList<>();
            productDataBuilderList.add(productNameField);
            productDataBuilderList.add(gstField);
            productDataBuilderList.add(hsnField);
            productDataBuilderList.add(productCode);


            //Product Details
            ProductDataBuilder productStyleField = new ProductDataBuilder();
            productStyleField.setIdentifier("styleName");
            productStyleField.setName("style Name");
            productStyleField.setType("text");
            productStyleField.setRequired(true);
            productStyleField.setDescription("product Style");
            productStyleField.setMin("");
            productStyleField.setMax("");
            productStyleField.setValues(null);

            ProductDataBuilder sleeveTypeField = new ProductDataBuilder();
            sleeveTypeField.setIdentifier("sleeveType");
            sleeveTypeField.setName("Sleeve Type");
            sleeveTypeField.setType("dropdown");
            sleeveTypeField.setRequired(true);
            sleeveTypeField.setDescription("Sleeve");
            sleeveTypeField.setMin("");
            sleeveTypeField.setMax("");
            sleeveTypeField.setValues(List.of("Half Sleeve %","full Sleeve"));

            ProductDataBuilder fitTypeField = new ProductDataBuilder();
            fitTypeField.setIdentifier("fitType");
            fitTypeField.setName("fitType Name");
            fitTypeField.setType("dropdown");
            fitTypeField.setRequired(true);
            fitTypeField.setDescription("fitType please select");
            fitTypeField.setMin("");
            fitTypeField.setMax("");
            fitTypeField.setValues(List.of("Regular Fit","Skin Fit"));

            ProductDataBuilder genderField = new ProductDataBuilder();
            genderField.setIdentifier("gender");
            genderField.setName("gender Name");
            genderField.setType("dropdown");
            genderField.setRequired(true);
            genderField.setDescription("Gender please select");
            genderField.setMin("");
            genderField.setMax("");
            genderField.setValues(List.of("Male","Female","Other"));

            ProductDataBuilder materialTypeField = new ProductDataBuilder();
            materialTypeField.setIdentifier("materialType");
            materialTypeField.setName("material Type");
            materialTypeField.setType("dropdown");
            materialTypeField.setRequired(true);
            materialTypeField.setDescription("material Type please select");
            materialTypeField.setMin("");
            materialTypeField.setMax("");
            materialTypeField.setValues(List.of("Cotton","Satin","Leather","Linen","Denim","Velvet","wool"));

            ProductDataBuilder colorField = new ProductDataBuilder();
            colorField.setIdentifier("productColor");
            colorField.setName("productColor");
            colorField.setType("dropdown");
            colorField.setRequired(true);
            colorField.setDescription("productColor please select");
            colorField.setMin("");
            colorField.setMax("");
            colorField.setValues(List.of("Yellow","Green","Blue","Green","Orange","Velvet","Brown"));

            ProductDataBuilder countryOriginField = new ProductDataBuilder();
            countryOriginField.setIdentifier("country");
            countryOriginField.setName("country");
            countryOriginField.setType("dropdown");
            countryOriginField.setRequired(true);
            countryOriginField.setDescription("country please select");
            countryOriginField.setMin("");
            countryOriginField.setMax("");
            countryOriginField.setValues(List.of("India"));

            ProductDataBuilder patternField = new ProductDataBuilder();
            patternField.setIdentifier("pattern");
            patternField.setName("pattern");
            patternField.setType("dropdown");
            patternField.setRequired(true);
            patternField.setDescription("pattern please select");
            patternField.setMin("");
            patternField.setMax("");
            patternField.setValues(List.of("Line","Circle"));


            ProductDataBuilder manufactureField = new ProductDataBuilder();
            manufactureField.setIdentifier("manufactureName");
            manufactureField.setName("manufactureName");
            manufactureField.setType("text");
            manufactureField.setRequired(false);
            manufactureField.setDescription("country please select");
            manufactureField.setMin("");
            manufactureField.setMax("");
            manufactureField.setValues(null);

            List<ProductDataBuilder> productDetails = new ArrayList<>();
            productDetails.add(productStyleField);
            productDetails.add(sleeveTypeField);
            productDetails.add(sleeveTypeField);
            productDetails.add(fitTypeField);
            productDetails.add(genderField);
            productDetails.add(materialTypeField);
            productDetails.add(colorField);
            productDetails.add(countryOriginField);
            productDetails.add(patternField);
            productDetails.add(manufactureField);


            //Product Description and Other Details
            ProductDataBuilder numberOfItemsField = new ProductDataBuilder();
            numberOfItemsField.setIdentifier("numberOfItems");
            numberOfItemsField.setName("number Of Items");
            numberOfItemsField.setType("dropdown");
            numberOfItemsField.setRequired(true);
            numberOfItemsField.setDescription("numberOfItems please select");
            numberOfItemsField.setMin("");
            numberOfItemsField.setMax("");
            numberOfItemsField.setValues(List.of("1","2","3","4","5","6","7","8","9","10"));

            ProductDataBuilder finishingType = new ProductDataBuilder();
            finishingType.setIdentifier("finishingType");
            finishingType.setName("finishing Type ");
            finishingType.setType("dropdown");
            finishingType.setRequired(true);
            finishingType.setDescription("finishingType please select");
            finishingType.setMin("");
            finishingType.setMax("");
            finishingType.setValues(List.of("Liner","Rarer","Printing","blur shade","shades","multiShades"));

            ProductDataBuilder brandField = new ProductDataBuilder();
            brandField.setIdentifier("brandField");
            brandField.setName("brandField Type ");
            brandField.setType("dropdown");
            brandField.setRequired(false);
            brandField.setDescription("brandField please select");
            brandField.setMin("");
            brandField.setMax("");
            brandField.setValues(List.of("Jack & jones","Microman","Puma","Generic","lux cozi","spyker"));

            ProductDataBuilder descriptionFiled = new ProductDataBuilder();
            descriptionFiled.setIdentifier("description");
            descriptionFiled.setName("description");
            descriptionFiled.setType("textbox");
            descriptionFiled.setRequired(true);
            descriptionFiled.setDescription("please fill description");
            descriptionFiled.setMin("10");
            descriptionFiled.setMax("5000");
            descriptionFiled.setValues(null);

            List<ProductDataBuilder> productDescAndOtherDetails = new ArrayList<>();
            productDescAndOtherDetails.add(numberOfItemsField);
            productDescAndOtherDetails.add(finishingType);
            productDescAndOtherDetails.add(brandField);
            productDescAndOtherDetails.add(descriptionFiled);



            FormBuilderRoot formBuilderRoot = new FormBuilderRoot();
            formBuilderRoot.setSizeDataBuilderList(sizeDataBuilderList);
            formBuilderRoot.setTableDataBuilderList(tableDataBuilders);
            formBuilderRoot.setProductDataBuilderList(productDataBuilderList);
            formBuilderRoot.setProductDetailsBuilderList(productDetails);
            formBuilderRoot.setProductDescAndOtherBuilderList(productDescAndOtherDetails);

            JSONObject jsonObject = new JSONObject(formBuilderRoot);
            System.out.println(jsonObject);

            return  ResponseEntity.ok(formBuilderRoot);
        }
        return null;
    }

    @Autowired
    private SellerProductRepository sellerProductRepository;
    @Autowired
    private ProductVariantsRepository productVariantsRepository;

    @Override
    public ResponseEntity<?> saveSellerProduct(ProductRootData productRootData) {

        try {
            System.out.println(productRootData);
            System.out.println("==================================");

            // Map incoming data to SellerProduct
            SellerProduct sellerProduct = modelMapper.map(productRootData, SellerProduct.class);

            // Explicitly set the relationship for ProductVariants
            if (sellerProduct.getProductRows() != null) {
                for (ProductVariants variant : sellerProduct.getProductRows()) {
                    variant.setSellerProduct(sellerProduct);
                }
            }


            // Save SellerProduct along with its ProductVariants
            SellerProduct savedSellerProduct = this.sellerProductRepository.save(sellerProduct);

            System.out.println("Save Success");
            System.out.println(savedSellerProduct);
            return ResponseGenerator.generateSuccessResponse(Map.of("productId",savedSellerProduct.getId()),
                                                             SellerMessageResponse.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse("Something Went Wrong",
                    SellerMessageResponse.DATA_NOT_SAVED);
        }

    }

    @Override
    public ResponseEntity<?> uploadProductFiles(Map<String, MultipartFile> files , String productLockerNumber) {

        try {
            SellerProduct sellerProduct = this.sellerProductRepository.findById(Long.parseLong(productLockerNumber)).orElseThrow(()-> new DataNotFoundException(SellerMessageResponse.DATA_NOT_FOUND));

            System.out.println("Seller product Received" + sellerProduct.getId());

            System.out.println(files.entrySet().size());
            // Iterate over the received files
            List<ProductFiles> productFilesList = new ArrayList<>();
            for (Map.Entry<String, MultipartFile> entry : files.entrySet()) {

                String key = entry.getKey();  // The key (e.g., file0, file1, etc.)
                MultipartFile file = entry.getValue();

                // You can now process each file
                // For example, save the file or perform any operation
                System.out.println("Received file: " + key);
                System.out.println("File name: " + file.getOriginalFilename());
                System.out.println("======================");
                // Example of saving the file
                // Path path = Paths.get("some/directory/" + file.getOriginalFilename());
                // Files.copy(file.getInputStream(), path);

                ProductFiles productFiles =new ProductFiles();
                productFiles.setFileKey(entry.getKey());
                productFiles.setFileName("File Url");
                productFiles.setFileSize(String.valueOf(file.getSize()));
                productFiles.setFileType(file.getContentType());
                productFiles.setSellerProduct(sellerProduct);

                productFilesList.add(productFiles);

            }

            sellerProduct.setProductFiles(productFilesList);

            this.sellerProductRepository.save(sellerProduct);

            System.out.println("Seller product Saved Success with Images");

            return ResponseGenerator.generateSuccessResponse("Success",SellerMessageResponse.SUCCESS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateDataNotFound(SellerMessageResponse.DATA_NOT_FOUND);
        }


           }

    @Override
    public ResponseEntity<?> formBuilderFlying(String categoryId) {

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
        sizeField.setIdentifier("gst");
        sizeField.setName("Gst %");
        sizeField.setType("MULTISELECT");
        sizeField.setRequired(false);
        sizeField.setDescription("Gst Mandatory");
        sizeField.setExclamationDesc("exclamation Gst");
        sizeField.setIsFiledDisabled("");
        sizeField.setValues(List.of("S","M","L","XL","XXL","3XL","4XL","5XL","6XL"));

        List<FormBuilderTool> productSizeList = new ArrayList<>();
        productSizeList.add(sizeField);


//        ======================================
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

        FormBuilderTool sizeLabel = new FormBuilderTool();
        sizeLabel.setIdentifier("productLabel");
        sizeLabel.setName("size");
        sizeLabel.setType("LABEL");
        sizeLabel.setRequired(true);
        sizeLabel.setDescription("product Size");
        sizeLabel.setExclamationDesc("product Size");

        List<FormBuilderTool> productVariants = new ArrayList<>();
        productVariants.add(sizeLabel);
        productVariants.add(productPrice);



        FormBuilderRoot formBuilderRoot = new FormBuilderRoot();
        formBuilderRoot.setProductIdentityList(productIdentityList);
        formBuilderRoot.setProductSizes(productSizeList);
        formBuilderRoot.setProductVariants(productVariants);

        JSONObject jsonObject = new JSONObject(formBuilderRoot);
        System.out.println(jsonObject);

        return  ResponseEntity.ok(formBuilderRoot);
    }


}
