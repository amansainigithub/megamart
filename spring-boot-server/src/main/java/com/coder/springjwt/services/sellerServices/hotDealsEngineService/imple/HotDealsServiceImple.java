package com.coder.springjwt.services.sellerServices.hotDealsEngineService.imple;

import com.coder.springjwt.bucket.bucketModels.BucketModel;
import com.coder.springjwt.bucket.bucketService.BucketService;
import com.coder.springjwt.constants.sellerConstants.sellerMessageConstants.SellerMessageResponse;
import com.coder.springjwt.dtos.sellerDtos.hotDealsDtos.HotDealsDto;
import com.coder.springjwt.exception.customerPanelException.DataNotFoundException;
import com.coder.springjwt.models.sellerModels.hotDealsEngine.HotDealsEngineModel;
import com.coder.springjwt.models.sellerModels.hotDealsEngine.HotDealsModel;
import com.coder.springjwt.repository.sellerRepository.hotDealsRepos.HotDealsEngineRepo;
import com.coder.springjwt.repository.sellerRepository.hotDealsRepos.HotDealsRepo;
import com.coder.springjwt.services.sellerServices.hotDealsEngineService.HotDealsService;
import com.coder.springjwt.util.MessageResponse;
import com.coder.springjwt.util.ResponseGenerator;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class HotDealsServiceImple implements HotDealsService {

    @Autowired
    private HotDealsRepo hotDealsRepo;
    @Autowired
    private HotDealsEngineRepo hotDealsEngineRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BucketService bucketService;


    @Override
    public ResponseEntity<?> saveHotDealsService(HotDealsDto hotDealsDto) {
        MessageResponse response =new MessageResponse();
        try {
            if(hotDealsDto.getEngineId() != null || hotDealsDto.getEngineId() != ""){
                HotDealsEngineModel hotDealsEngineModel = this.hotDealsEngineRepo.findById(Long.parseLong(hotDealsDto.getEngineId()))
                        .orElseThrow(() -> new DataNotFoundException(SellerMessageResponse.DATA_NOT_FOUND));


                HotDealsModel hotDealsModel =  modelMapper.map(hotDealsDto, HotDealsModel.class);
                hotDealsModel.setHotDealsEngineModel(hotDealsEngineModel);

                this.hotDealsRepo.save(hotDealsModel);
                log.info("Hot Deals Saved Success");

                response.setMessage("Hot Deals Saved Success");
                response.setStatus(HttpStatus.OK);
                return ResponseGenerator.generateSuccessResponse(response, SellerMessageResponse.SUCCESS);
            }
            else{
                throw new RuntimeException("Engine Id Not Found | please Check");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            response.setMessage(e.getMessage());
            response.setStatus(HttpStatus.BAD_REQUEST);
            return ResponseGenerator.generateBadRequestResponse(response);
        }
    }

    @Override
    public ResponseEntity<?> deleteHotDeal(Long id) {
        try {
            HotDealsModel data = this.hotDealsRepo.findById(id).orElseThrow(
                    () -> new RuntimeException("Hot Deal id not Found"));

            this.hotDealsRepo.deleteById(data.getId());
            log.info("Delete Success => Hot Deals id :: " + id );
            return ResponseGenerator.generateSuccessResponse(SellerMessageResponse.DELETE_SUCCESS , SellerMessageResponse.SUCCESS);

        }
        catch (Exception e)
        {
            e.printStackTrace();
            log.error("Hot Deal Not deleted");
            return ResponseGenerator.generateBadRequestResponse
                    ("Hot Deal Not deleted :: " + e.getMessage() , SellerMessageResponse.ERROR);
        }
    }

    @Override
    public ResponseEntity<?> getHotDeals() {
        try {
            List<HotDealsModel> hotDealsEngineList = this.hotDealsRepo.findAll();
            List<HotDealsModel> hotDealsEngineDtoList = hotDealsEngineList.stream()
                    .map(model -> modelMapper.map(model, HotDealsModel.class))
                    .collect(Collectors.toList());
            log.info("Hot Deals List Fetch Success");
            return ResponseGenerator.generateSuccessResponse(hotDealsEngineDtoList , SellerMessageResponse.SUCCESS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            log.error("Failed To fetch");
            return ResponseGenerator.generateBadRequestResponse(e.getMessage() , SellerMessageResponse.FAILED);
        }
    }

    @Override
    public ResponseEntity<?> updateHotDeals(HotDealsDto hotDealsDto) {
        try {
            log.info(hotDealsDto.toString());

            HotDealsModel hotDealData = this.hotDealsRepo.findById(hotDealsDto.getId()).orElseThrow(() -> new DataNotFoundException(SellerMessageResponse.DATA_NOT_FOUND));
            HotDealsModel hotDealsModel =  modelMapper.map(hotDealsDto , HotDealsModel.class);
            hotDealsModel.setHotDealsEngineModel(hotDealData.getHotDealsEngineModel());
            this.hotDealsRepo.save(hotDealsModel);

            log.info(SellerMessageResponse.DATA_SAVED_SUCCESS);
            return ResponseGenerator.generateSuccessResponse(SellerMessageResponse.SUCCESS , SellerMessageResponse.DATA_UPDATE_SUCCESS);
        }
        catch (Exception e)
        {
            log.info("Data Update Failed");
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(SellerMessageResponse.FAILED ,SellerMessageResponse.DATA_UPDATE_FAILED);
        }
    }

    @Override
    public ResponseEntity<?> getHotDeal(Long id) {
        try {
            HotDealsModel hotDealsModel = this.hotDealsRepo.findById(id).orElseThrow(
                    () -> new RuntimeException(SellerMessageResponse.DATA_NOT_FOUND));
            HotDealsDto hotDealsDto = modelMapper.map(hotDealsModel, HotDealsDto.class);
            log.info("Hot Deal Fetch Success !");
            return ResponseGenerator.generateSuccessResponse(hotDealsDto , SellerMessageResponse.SUCCESS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            log.error("Failed To Fetch Hot Deals !! Error");
            return ResponseGenerator.generateBadRequestResponse(e.getMessage() , SellerMessageResponse.FAILED);
        }
    }

    @Override
    public ResponseEntity<?> deleteAllHotDeals() {
        try {
            this.hotDealsRepo.deleteAll();
            log.info("All hot Deals Delete Success =>");
            return ResponseGenerator.generateSuccessResponse(SellerMessageResponse.DELETE_SUCCESS , SellerMessageResponse.SUCCESS);

        }
        catch (Exception e)
        {
            e.printStackTrace();
            log.error("Hot Deal Not deleted");
            return ResponseGenerator.generateBadRequestResponse
                    ("Hot Deal Not deleted :: " + e.getMessage() , SellerMessageResponse.ERROR);
        }
    }

    @Override
    public ResponseEntity<?> updateHotDealFile(MultipartFile file, Long dealId) {
        try {
            HotDealsModel hotDealsModel = this.hotDealsRepo.findById(dealId).orElseThrow(()-> new DataNotFoundException("DATA_NOT_FOUND"));

            //Delete old File
            try {
                bucketService.deleteFile(hotDealsModel.getFileUrl());
            }catch (Exception e)
            {
                log.error("File Not deleted Id:: " + dealId);
            }
            //upload New File
            BucketModel bucketModel = bucketService.uploadFile(file);
            if(bucketModel != null)
            {
                hotDealsModel.setFileUrl(bucketModel.getBucketUrl());
                this.hotDealsRepo.save(hotDealsModel);
                return ResponseGenerator.generateSuccessResponse(SellerMessageResponse.SUCCESS,"FILE Update Success");
            }
            else {
                log.error("Bucket Model is null | please check AWS bucket configuration");
                throw new Exception("Bucket AWS is Empty");
            }
        }
        catch (Exception e)
        {
            log.info("Exception" , e.getMessage());
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse("Error" ,"File Not Upload");
        }
    }
}
