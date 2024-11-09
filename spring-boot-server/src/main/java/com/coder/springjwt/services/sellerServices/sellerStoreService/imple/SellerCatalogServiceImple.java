package com.coder.springjwt.services.sellerServices.sellerStoreService.imple;

import com.coder.springjwt.bucket.bucketService.BucketService;
import com.coder.springjwt.constants.sellerConstants.sellerMessageConstants.SellerMessageResponse;
import com.coder.springjwt.helpers.userHelper.UserHelper;
import com.coder.springjwt.models.CatalogRole;
import com.coder.springjwt.models.adminModels.catalog.catalogMaterial.CatalogMaterial;
import com.coder.springjwt.models.adminModels.catalog.catalogNetQuantity.CatalogNetQuantityModel;
import com.coder.springjwt.models.adminModels.catalog.catalogSize.CatalogSizeModel;
import com.coder.springjwt.models.adminModels.catalog.catalogType.CatalogTypeModel;
import com.coder.springjwt.models.adminModels.catalog.hsn.HsnCodes;
import com.coder.springjwt.models.adminModels.categories.BornCategoryModel;
import com.coder.springjwt.models.sellerModels.sellerStore.SellerCatalog;
import com.coder.springjwt.models.sellerModels.sellerStore.SellerStore;
import com.coder.springjwt.payload.sellerPayloads.sellerPayload.SellerCatalogPayload;
import com.coder.springjwt.repository.UserRepository;
import com.coder.springjwt.repository.adminRepository.catalogRepos.*;
import com.coder.springjwt.repository.adminRepository.categories.BornCategoryRepo;
import com.coder.springjwt.repository.sellerRepository.sellerStoreRepository.SellerCatalogRepository;
import com.coder.springjwt.repository.sellerRepository.sellerStoreRepository.SellerStoreRepository;
import com.coder.springjwt.services.sellerServices.sellerStoreService.SellerCatalogService;
import com.coder.springjwt.util.MessageResponse;
import com.coder.springjwt.util.ResponseGenerator;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SellerCatalogServiceImple implements SellerCatalogService {

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
    private CatalogNetQuantityRepo catalogNetQuantityRepo;

    @Autowired
    private CatalogMaterialRepo catalogMaterialRepo;

    @Autowired
    private CatalogSizeRepo catalogSizeRepo;

    @Autowired
    private CatalogTypeRepo catalogTypeRepo;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SellerStoreRepository sellerStoreRepository;


    @Autowired
    private BucketService bucketService;





    @Override
    public ResponseEntity<?> getSellerCatalog(Long catalogId) {
        try {

            Optional<SellerCatalog> catalog = this.sellerCatalogRepository.findById(catalogId);

            if(catalog.isPresent())
            {
                SellerCatalog sellerCatalog = catalog.get();

                SellerCatalogPayload catalogNode = modelMapper.map(sellerCatalog, SellerCatalogPayload.class);

                log.info("Data Fetched Success :: Seller Catalog by Id" +SellerCatalogServiceImple.class.getName());

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
    public ResponseEntity<?> getCatalogMasters() {

        MessageResponse mRes = new MessageResponse();

        //Response Data
        HashMap<String ,Object> dataResponse =new HashMap<>();

        try {

            // HSN Codes
            List<HsnCodes> hsnCodes = this.getHsnList();
            dataResponse.put("hsn" , hsnCodes);

            //Size List
            List<CatalogSizeModel> sizeList = this.getSizeList();
            dataResponse.put("catalogSize" , sizeList);

            //Size List
            List<CatalogNetQuantityModel> netQuantityList = this.getNetQuantityList();
            dataResponse.put("netQuantityList" , netQuantityList);

            //material List
            List<CatalogMaterial> materialList = this.getMaterialList();
            dataResponse.put("materialList" , materialList);

            //material List
            List<CatalogTypeModel> typeList = this.getTypeList();
            dataResponse.put("typeList" , typeList);

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

    public List<CatalogSizeModel> getSizeList()
    {
        try {
            List<CatalogSizeModel> catalogSizeList = this.catalogSizeRepo.findAll();
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

    public List<CatalogNetQuantityModel> getNetQuantityList()
    {
        try {
            List<CatalogNetQuantityModel> netQuantityList = this.catalogNetQuantityRepo.findAll();
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

    public List<CatalogMaterial> getMaterialList()
    {
        try {
            List<CatalogMaterial> catalogMaterialList = this.catalogMaterialRepo.findAll();
            if(catalogMaterialList.isEmpty())
            {
                log.info("Data Not found :::: {} ");
                return null;
            }else{
                log.info("Data fetch Success");
                return catalogMaterialList;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public List<CatalogTypeModel> getTypeList()
    {
        try {
            List<CatalogTypeModel> catalogTypeList = this.catalogTypeRepo.findAll();
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
    public ResponseEntity<?> sellerSaveCatalogService(Long categoryId,
                                                      SellerCatalogPayload sellerCatalogPayload,
                                                      List<MultipartFile> files) {


        try {
            BornCategoryModel bornData = bornCategoryRepo.findById(categoryId).orElseThrow(
                                                ()->new RuntimeException("Category Id not Found"));

            // Check for minimum and maximum file count
            if (files.size() < MIN_FILE_COUNT || files.size() > MAX_FILE_COUNT) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(SellerMessageResponse.YOU_MUST_UPLOAD_1_TO_5_FILES);
            }

            // File upload To Check each file for type and size validity
            List<String> invalidFiles = files.stream()
                    .filter(file -> !isValidImageFormat(file.getContentType()) || !isValidFileSize(file.getSize()))
                    .map(MultipartFile::getOriginalFilename)
                    .collect(Collectors.toList());

            if (!invalidFiles.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                        .body(SellerMessageResponse.THE_FOLLOWING_FILES_ARE_UNSUPPORTED_OR_EXCEED_3MB
                                + String.join(", ", invalidFiles));
            }
            else if  (invalidFiles.isEmpty()){

                // GET Current Username
                Map<String, String> currentUser = UserHelper.getCurrentUser();
                //Get Seller Store Data
                Optional<SellerStore> optional = sellerStoreRepository.findByUsername(currentUser.get("username"));

                System.out.println("CatalogJsonData :: " + sellerCatalogPayload);

                if (optional.isPresent()) {
                    //Get seller Store Data
                    SellerStore sellerStore = optional.get();

                    //convert Payload To Modal class
                    SellerCatalog sellerCatalog = modelMapper.map(sellerCatalogPayload, SellerCatalog.class);

                    //file Upload Progress Starting
                    catalogFileStore(files, sellerCatalog);

                    //Discount Catalog
                    String discountPercentage = calculateDiscount(Double.valueOf(sellerCatalog.getMrp()), Double.valueOf(sellerCatalog.getSellActualPrice()));
                    sellerCatalog.setDiscount(discountPercentage);

                    //Set sub-title
                    sellerCatalog.setCatalogSubTitle(sellerCatalog.getCatalogName());

                    //Set CategoryName and Category Id's
                    sellerCatalog.setCategoryName(bornData.getCategoryName());
                    sellerCatalog.setCategoryId(String.valueOf(bornData.getId()));

                    //set Status
                    sellerCatalog.setCatalogStatus(String.valueOf(CatalogRole.DRAFT));

                    //set Current User
                    sellerCatalog.setUsername(sellerStore.getUsername());

                    //set Store Name
                    sellerCatalog.setSellerStoreName(sellerStore.getStoreName());

                    sellerCatalog.setSellerStore(sellerStore);

                    this.sellerCatalogRepository.save(sellerCatalog);

                    return ResponseGenerator.generateSuccessResponse(SellerMessageResponse.DATA_SAVED_SUCCESS,
                                                                     SellerMessageResponse.SUCCESS);

                } else {
                    return ResponseGenerator.generateBadRequestResponse(SellerMessageResponse.DATA_NOT_SAVED,
                                                                        SellerMessageResponse.FAILED);
                }
            }else {
                return ResponseGenerator.generateBadRequestResponse(SellerMessageResponse.SOMETHING_WENT_WRONG,
                        SellerMessageResponse.FAILED);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(SellerMessageResponse.DATA_NOT_SAVED);
        }

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
    public ResponseEntity<?> getAllCatalogByUsernameService() {
        try {
            //Get Current Username
            Map<String, String> currentUser = UserHelper.getCurrentUser();

            Optional<SellerStore> optional = sellerStoreRepository.findByUsername(currentUser.get("username"));

            if(optional.isPresent())
            {
                List<SellerCatalog> catalogList =
                        this.sellerCatalogRepository.findAllByUsername(optional.get().getUsername());
                return ResponseGenerator.generateSuccessResponse(catalogList,SellerMessageResponse.SUCCESS);
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
    public ResponseEntity<?> getAllCatalogByQcProgressService() {
        try {
            //Get Current Username
            Map<String, String> currentUser = UserHelper.getCurrentUser();

            Optional<SellerStore> optional = sellerStoreRepository.findByUsername(currentUser.get("username"));

            if(optional.isPresent())
            {
                //Fetch the Progress Catalog List
                List<SellerCatalog> catalogList =
                        this.sellerCatalogRepository.findAllByCatalogStatusAndCatalogStatus(optional.get().getUsername() , "PROGRESS");
                return ResponseGenerator.generateSuccessResponse(catalogList,SellerMessageResponse.SUCCESS);
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
    public ResponseEntity<?> getAllCatalogByDraft() {
        try {
            //Get Current Username
            Map<String, String> currentUser = UserHelper.getCurrentUser();

            Optional<SellerStore> optional = sellerStoreRepository.findByUsername(currentUser.get("username"));

            if(optional.isPresent())
            {
                //Fetch the Progress Catalog List
                List<SellerCatalog> catalogList =
                        this.sellerCatalogRepository.findAllByCatalogStatusAndCatalogStatus(optional.get().getUsername() , "DRAFT");
                return ResponseGenerator.generateSuccessResponse(catalogList,SellerMessageResponse.SUCCESS);
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


}
