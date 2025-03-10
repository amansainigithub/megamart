package com.coder.springjwt.services.hotDealsEngineService.imple;

import com.coder.springjwt.constants.sellerConstants.sellerMessageConstants.SellerMessageResponse;
import com.coder.springjwt.dtos.sellerDtos.hotDealsDtos.HotDealsEngineDto;
import com.coder.springjwt.exception.customerException.CategoryNotFoundException;
import com.coder.springjwt.exception.customerException.DataNotFoundException;
import com.coder.springjwt.models.sellerModels.catalog.hsn.HsnCodes;
import com.coder.springjwt.models.sellerModels.hotDealsEngine.HotDealsEngineModel;
import com.coder.springjwt.repository.hotDealsRepos.HotDealsEngineRepo;
import com.coder.springjwt.services.hotDealsEngineService.HotDealsEngineService;
import com.coder.springjwt.util.MessageResponse;
import com.coder.springjwt.util.ResponseGenerator;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class HotDealsEngineServiceImple implements HotDealsEngineService {

    @Autowired
    private HotDealsEngineRepo hotDealsEngineRepo;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public ResponseEntity<?> saveHotDealsEngine(HotDealsEngineDto hotDealsEngineDto) {
        MessageResponse response =new MessageResponse();
        try {
            HotDealsEngineModel hotDealsEngineModel =  modelMapper.map(hotDealsEngineDto, HotDealsEngineModel.class);

            this.hotDealsEngineRepo.save(hotDealsEngineModel);
            log.info("Hot Deals Engine Saved Success");

            response.setMessage("Hot Deals Engine Saved Success");
            response.setStatus(HttpStatus.OK);
            return ResponseGenerator.generateSuccessResponse(response, SellerMessageResponse.SUCCESS);
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
    public ResponseEntity<?> getHotDealsEngine(Long engineId) {
        try {
            HotDealsEngineModel hotDealsEngineModel = this.hotDealsEngineRepo.findById(engineId).orElseThrow(
                    () -> new RuntimeException(SellerMessageResponse.DATA_NOT_FOUND));
            HotDealsEngineDto hotDealsEngineDto = modelMapper.map(hotDealsEngineModel, HotDealsEngineDto.class);
            log.info("Home Slider Fetch Success !");
            return ResponseGenerator.generateSuccessResponse(hotDealsEngineDto , SellerMessageResponse.SUCCESS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            log.error("");
            return ResponseGenerator.generateBadRequestResponse(e.getMessage() , SellerMessageResponse.FAILED);
        }
    }

    @Override
    public ResponseEntity<?> getHotDealsEngines(Integer page, Integer size) {
        try {
            Page<HotDealsEngineModel> hotDealsEngineList = this.hotDealsEngineRepo
                                                        .findAll(PageRequest.of(page , size, Sort.by("id").descending()));
            log.info("Hot Deals Engine List Fetch Success");
            return ResponseGenerator.generateSuccessResponse(hotDealsEngineList, SellerMessageResponse.SUCCESS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            log.error("");
            return ResponseGenerator.generateBadRequestResponse(e.getMessage() , SellerMessageResponse.FAILED);
        }
    }

    @Override
    public ResponseEntity<?> deleteHotDealsEngine(Long engineId) {
        try {
            HotDealsEngineModel data = this.hotDealsEngineRepo.findById(engineId).orElseThrow(
                    () -> new CategoryNotFoundException("Category id not Found"));

            this.hotDealsEngineRepo.deleteById(data.getId());
            log.info("Delete Success => Engine id :: " + engineId );
            return ResponseGenerator.generateSuccessResponse(SellerMessageResponse.DELETE_SUCCESS , SellerMessageResponse.SUCCESS);

        }
        catch (Exception e)
        {
            e.printStackTrace();
            log.error("Slider Could Not deleted");
            return ResponseGenerator.generateBadRequestResponse
                    ("Category Could not deleted :: " + e.getMessage() , "Error");
        }
    }

    @Override
    public ResponseEntity<?> updateHotDealsEngine(HotDealsEngineDto hotDealsEngineDto) {
        try {
            log.info(hotDealsEngineDto.toString());

            this.hotDealsEngineRepo.findById(hotDealsEngineDto.getId()).orElseThrow(()->new DataNotFoundException(SellerMessageResponse.DATA_NOT_FOUND));
            HotDealsEngineModel hotDealsEngineModel =  modelMapper.map(hotDealsEngineDto , HotDealsEngineModel.class);
            this.hotDealsEngineRepo.save(hotDealsEngineModel);

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
}
