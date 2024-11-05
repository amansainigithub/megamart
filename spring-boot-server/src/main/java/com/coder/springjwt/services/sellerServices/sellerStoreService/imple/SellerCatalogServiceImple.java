package com.coder.springjwt.services.sellerServices.sellerStoreService.imple;

import com.coder.springjwt.constants.sellerConstants.sellerMessageConstants.SellerMessageResponse;
import com.coder.springjwt.helpers.userHelper.UserHelper;
import com.coder.springjwt.models.CatalogRole;
import com.coder.springjwt.models.adminModels.catalog.catalogMaterial.CatalogMaterial;
import com.coder.springjwt.models.adminModels.catalog.catalogNetQuantity.CatalogNetQuantityModel;
import com.coder.springjwt.models.adminModels.catalog.catalogSize.CatalogSizeModel;
import com.coder.springjwt.models.adminModels.catalog.catalogType.CatalogTypeModel;
import com.coder.springjwt.models.adminModels.catalog.hsn.HsnCodes;
import com.coder.springjwt.models.sellerModels.sellerStore.SellerCatalog;
import com.coder.springjwt.models.sellerModels.sellerStore.SellerStore;
import com.coder.springjwt.payload.sellerPayloads.sellerPayload.SellerCatalogPayload;
import com.coder.springjwt.repository.UserRepository;
import com.coder.springjwt.repository.adminRepository.catalogRepos.*;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class SellerCatalogServiceImple implements SellerCatalogService {


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
                                                      String index,
                                                      String sellerCatalogPayload,
                                                      List<MultipartFile> files) {


        try {
            System.out.println("Catalog Data :: " + sellerCatalogPayload );
            SellerCatalogPayload catalogJsonNode = new Gson().fromJson(sellerCatalogPayload, SellerCatalogPayload.class);
            System.out.println("Catalog Data :: " + catalogJsonNode.getNetQuantity() );
            System.out.println("Json Catalog Data :: " + catalogJsonNode);



            // GET Current Username
            Map<String, String> currentUser = UserHelper.getCurrentUser();

            //Get Seller Store Data
            Optional<SellerStore> optional = sellerStoreRepository.findByUsername(currentUser.get("username"));

            if(optional.isPresent())
            {
                //Get seller Store Data
                SellerStore sellerStore = optional.get();

                //convert Payload To Modal class
                SellerCatalog sellerCatalog = modelMapper.map(catalogJsonNode, SellerCatalog.class);

                //save Catalog Files
                this.catalogFileStore(files);

                //set Status
                sellerCatalog.setCatalogStatus(String.valueOf(CatalogRole.DRAFT));

                //set Current User
                sellerCatalog.setUsername(sellerStore.getUsername());

                //set Store Name
                sellerCatalog.setSellerStoreName(sellerStore.getStoreName());

                this.sellerCatalogRepository.save(sellerCatalog);

                return ResponseGenerator.generateSuccessResponse("DATA SAVED SUCCESS" ,SellerMessageResponse.SUCCESS);

            }else{
                return ResponseGenerator.generateBadRequestResponse("Failed" ,SellerMessageResponse.FAILED);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(SellerMessageResponse.DATA_NOT_SAVED);
        }

    }


    public boolean catalogFileStore(List<MultipartFile> files)
    {
        try {
            for (MultipartFile file : files) {
                System.out.println("File Name :: " + file.getOriginalFilename());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Boolean.TRUE;
    }


}
