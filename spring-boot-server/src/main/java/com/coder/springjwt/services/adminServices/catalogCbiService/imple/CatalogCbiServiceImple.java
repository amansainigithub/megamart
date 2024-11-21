package com.coder.springjwt.services.adminServices.catalogCbiService.imple;

import com.coder.springjwt.bucket.bucketService.BucketService;
import com.coder.springjwt.constants.sellerConstants.sellerMessageConstants.SellerMessageResponse;
import com.coder.springjwt.helpers.userHelper.UserHelper;
import com.coder.springjwt.models.CatalogRole;
import com.coder.springjwt.models.adminModels.catalog.catalogBreath.CatalogBreathModel;
import com.coder.springjwt.models.adminModels.catalog.catalogHeight.CatalogHeightModel;
import com.coder.springjwt.models.adminModels.catalog.catalogLength.CatalogLengthModel;
import com.coder.springjwt.models.adminModels.catalog.catalogMaterial.CatalogMaterial;
import com.coder.springjwt.models.adminModels.catalog.catalogNetQuantity.CatalogNetQuantityModel;
import com.coder.springjwt.models.adminModels.catalog.catalogSize.CatalogSizeModel;
import com.coder.springjwt.models.adminModels.catalog.catalogType.CatalogTypeModel;
import com.coder.springjwt.models.adminModels.catalog.catalogWeight.CatalogWeightModel;
import com.coder.springjwt.models.adminModels.catalog.gstPercentage.GstPercentageModel;
import com.coder.springjwt.models.adminModels.catalog.hsn.HsnCodes;
import com.coder.springjwt.models.sellerModels.sellerStore.SellerCatalog;
import com.coder.springjwt.payload.adminPayloads.catalogPaylods.CatalogPayloadInvestigation;
import com.coder.springjwt.repository.UserRepository;
import com.coder.springjwt.repository.adminRepository.catalogRepos.*;
import com.coder.springjwt.repository.sellerRepository.sellerStoreRepository.SellerCatalogRepository;
import com.coder.springjwt.repository.sellerRepository.sellerStoreRepository.SellerStoreRepository;
import com.coder.springjwt.services.adminServices.catalogCbiService.CatalogCbiService;
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
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class CatalogCbiServiceImple implements CatalogCbiService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private HsnRepository hsnRepository;
    @Autowired
    private SellerCatalogRepository sellerCatalogRepository;
    @Autowired
    private SellerStoreRepository sellerStoreRepository;
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
    private BucketService bucketService;
    @Autowired
    private CatalogLengthRepo catalogLengthRepo;
    @Autowired
    private GstPercentageRepo gstPercentageRepo;
    @Autowired
    private CatalogWeightRepo catalogWeightRepo;
    @Autowired
    private CatalogBreathRepo catalogBreathRepo;
    @Autowired
    private CatalogHeightRepo catalogHeightRepo;


    @Override
    public ResponseEntity<?> getCatalogInProgressListService(int page , int size) {
        try {
            //Fetch the Progress Catalog List
                Page<SellerCatalog> catalogProgressList =
                        this.sellerCatalogRepository.findAllByCatalogStatusAndPageable(
                                String.valueOf(CatalogRole.QC_PROGRESS) , PageRequest.of(page, size));

                if(!catalogProgressList.isEmpty())
                {
                    log.info("Catalog Progress List Fetch Success");

                    return ResponseGenerator.generateSuccessResponse(catalogProgressList, SellerMessageResponse.SUCCESS);
                }else{
                    return ResponseGenerator.generateSuccessResponse(SellerMessageResponse.FAILED,
                            SellerMessageResponse.DATA_NOT_FOUND);
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
    public ResponseEntity<?> catalogInvestigationService(Long catalogId,
                                                         CatalogPayloadInvestigation catalogInvestigationPayload) {

        try {
            log.info("Catalog Id :: " + catalogId);

                // GET Current Username
                Map<String, String> currentUser = UserHelper.getCurrentUser();

                System.out.println("CatalogJsonData :: " + catalogInvestigationPayload);

                Optional<SellerCatalog> currentCatalog = sellerCatalogRepository.findById(catalogId);

                if ( currentCatalog.isPresent()) {

                    //Get seller Store Data
                    SellerCatalog catalogNode = currentCatalog.get();

                    //convert Payload To Modal class
                    SellerCatalog sellerCatalog = modelMapper.map(catalogInvestigationPayload, SellerCatalog.class);
                    sellerCatalog.setId(currentCatalog.get().getId());

                    //Set Seller Store
                    sellerCatalog.setSellerStore(catalogNode.getSellerStore());

                    //Discount Catalog
                    String discountPercentage = calculateDiscount(Double.valueOf(sellerCatalog.getMrp()), Double.valueOf(sellerCatalog.getSellActualPrice()));
                    sellerCatalog.setDiscount(discountPercentage);

                    //Set Catalog Id
                    sellerCatalog.setCatalogId(catalogNode.getCatalogId());

                    //Set SpaceId
                    sellerCatalog.setSpaceId(catalogNode.getSpaceId());

                    //Set Catalog Investigation Status
                    if(catalogInvestigationPayload.getActionStatus().equals(String.valueOf(CatalogRole.QC_PASS)))
                    {
                        sellerCatalog.setCatalogStatus(String.valueOf(CatalogRole.QC_PASS));
                    }else{
                        sellerCatalog.setCatalogStatus(String.valueOf(CatalogRole.QC_ERROR));
                    }

                    SellerCatalog save = this.sellerCatalogRepository.save(sellerCatalog);
                    log.info("========================================================");
                    log.info("Data Updated Success ID :: " + catalogId);

                    return ResponseGenerator.generateSuccessResponse(save,
                            SellerMessageResponse.SUCCESS);

                } else {
                    return ResponseGenerator.generateBadRequestResponse(SellerMessageResponse.DATA_NOT_SAVED,
                            SellerMessageResponse.FAILED);
                }

        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(SellerMessageResponse.DATA_NOT_SAVED);
        }

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

            //catalog Length List
            List<GstPercentageModel> gstPercentageList = this.getGstPercentageList();
            dataResponse.put("gstPercentageList" , gstPercentageList);

            //catalog Weight List
            List<CatalogWeightModel> catalogWeightList = this.getWeightList();
            dataResponse.put("catalogWeightList" , catalogWeightList);

            //catalog Weight List
            List<CatalogBreathModel> catalogBreathList = this.getBreathList();
            dataResponse.put("catalogBreathList" , catalogBreathList);

            //catalog Length List
            List<CatalogLengthModel> lengthList = this.getLengthList();
            dataResponse.put("lengthList" , lengthList);

            //catalog Length List
            List<CatalogHeightModel> heightLists = this.getHeightList();
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

    @Override
    public ResponseEntity<?> searchCatalogByDateService(int page, int size, LocalDate startDate, LocalDate endDate) {
        try {
            // Convert LocalDate to LocalDateTime for querying
            LocalDateTime startDateTime = startDate.atStartOfDay();
            LocalDateTime endDateTime = endDate.atStartOfDay();

            // Fetch the catalog list with pagination and date range filter
            Page<SellerCatalog> catalogProgressList = sellerCatalogRepository.findAllByCatalogStatusAndCreationDateBetween(
                    String.valueOf(CatalogRole.QC_PROGRESS),
                    startDateTime,
                    endDateTime,
                    PageRequest.of(page, size)
            );

            if (!catalogProgressList.isEmpty()) {
                return ResponseGenerator.generateSuccessResponse(catalogProgressList, SellerMessageResponse.SUCCESS);
            } else {
                return ResponseGenerator.generateSuccessResponse(SellerMessageResponse.FAILED,
                        SellerMessageResponse.DATA_NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseGenerator.generateSuccessResponse(SellerMessageResponse.ERROR,
                    SellerMessageResponse.SOMETHING_WENT_WRONG);
        }

    }


    public List<CatalogHeightModel> getHeightList()
    {
        try {
            List<CatalogHeightModel> catalogHeightLists = this.catalogHeightRepo.findAll();
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

    public List<CatalogBreathModel> getBreathList()
    {
        try {
            List<CatalogBreathModel> catalogBreathList = this.catalogBreathRepo.findAll();
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

    public List<CatalogWeightModel> getWeightList()
    {
        try {
            List<CatalogWeightModel> catalogWeightLists = this.catalogWeightRepo.findAll();
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


    public List<CatalogLengthModel> getLengthList()
    {
        try {
            List<CatalogLengthModel> lengthList = this.catalogLengthRepo.findAll();
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

}
