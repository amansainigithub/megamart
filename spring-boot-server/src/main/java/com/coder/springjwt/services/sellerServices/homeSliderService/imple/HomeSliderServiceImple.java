package com.coder.springjwt.services.sellerServices.homeSliderService.imple;

import com.coder.springjwt.bucket.bucketModels.BucketModel;
import com.coder.springjwt.bucket.bucketService.BucketService;
import com.coder.springjwt.constants.sellerConstants.sellerMessageConstants.SellerMessageResponse;
import com.coder.springjwt.dtos.sellerDtos.homeSliderDtos.HomeSliderDto;
import com.coder.springjwt.exception.customerPanelException.CategoryNotFoundException;
import com.coder.springjwt.exception.customerPanelException.DataNotFoundException;
import com.coder.springjwt.models.sellerModels.homeSliders.HomeSliderModel;
import com.coder.springjwt.repository.sellerRepository.homeSliderRepo.HomeSliderRepo;
import com.coder.springjwt.services.sellerServices.homeSliderService.HomeSliderService;
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
public class HomeSliderServiceImple implements HomeSliderService {
    @Autowired
    private HomeSliderRepo homeSliderRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    BucketService bucketService;

    @Override
    public ResponseEntity<?> saveHomeSlider(HomeSliderDto homeSliderDto) {
        log.info("<-- saveHomeSlider Flying -->");

        MessageResponse response =new MessageResponse();
        try {
            HomeSliderModel homeSliderModel =  modelMapper.map(homeSliderDto, HomeSliderModel.class);

            this.homeSliderRepo.save(homeSliderModel);
            log.info("Home Slider Saved Success");

            response.setMessage(SellerMessageResponse.DATA_SAVED_SUCCESS);
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
    public ResponseEntity<?> updateHomeSliderFile(MultipartFile file, Long homeSliderId) {
        log.info("<-- updateHomeSliderFile Flying -->");
        try {
            HomeSliderModel homeSliderModel = this.homeSliderRepo.findById(homeSliderId).orElseThrow(()-> new DataNotFoundException("DATA_NOT_FOUND"));

            //Delete old File
            try {
                bucketService.deleteFile(homeSliderModel.getFileUrl());
            }catch (Exception e)
            {
                log.error("File Not deleted Id:: " + homeSliderId);
            }
            //upload New File
            BucketModel bucketModel = bucketService.uploadFile(file);
            if(bucketModel != null)
            {
                homeSliderModel.setFileUrl(bucketModel.getBucketUrl());
                this.homeSliderRepo.save(homeSliderModel);
                return ResponseGenerator.generateSuccessResponse(SellerMessageResponse.SUCCESS,SellerMessageResponse.FILE_UPDATE_SUCCESS);
            }
            else {
                log.error("Bucket Model is null | please check AWS bucket configuration");
                throw new Exception(SellerMessageResponse.AWS_BUCKET_IS_EMPTY);
            }
        }
        catch (Exception e)
        {
            log.info("Exception" , e.getMessage());
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(SellerMessageResponse.ERROR ,SellerMessageResponse.FILE_NOT_UPLOAD);
        }
    }

    @Override
    public ResponseEntity<?> deleteHomeSlider(Long homeSliderId) {
        log.info("<-- deleteHomeSlider Flying -->");

        try {
            HomeSliderModel data = this.homeSliderRepo.findById(homeSliderId).orElseThrow(
                    () -> new CategoryNotFoundException(SellerMessageResponse.ID_NOT_FOUND));

            this.homeSliderRepo.deleteById(data.getId());
            log.info("Delete Success => Slider id :: " + homeSliderId );
            return ResponseGenerator.generateSuccessResponse(SellerMessageResponse.DELETE_SUCCESS , SellerMessageResponse.SUCCESS);

        }
        catch (Exception e)
        {
            e.printStackTrace();
            log.error("Slider Could Not deleted");
            return ResponseGenerator.generateBadRequestResponse
                    (SellerMessageResponse.CATEGORY_COULD_NOT_DELETE + e.getMessage() , SellerMessageResponse.ERROR);
        }
    }

    @Override
    public ResponseEntity<?> getHomeSliderById(long homeSliderId) {
        log.info("<-- getHomeSliderById Flying -->");

        try {
            HomeSliderModel homeSliderModel = this.homeSliderRepo.findById(homeSliderId).orElseThrow(
                    () -> new RuntimeException(SellerMessageResponse.DATA_NOT_FOUND));
            HomeSliderDto homeSliderDto = modelMapper.map(homeSliderModel, HomeSliderDto.class);
            log.info("Home Slider Fetch Success !");
            return ResponseGenerator.generateSuccessResponse(homeSliderDto , SellerMessageResponse.SUCCESS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            log.error("");
            return ResponseGenerator.generateBadRequestResponse(e.getMessage() , SellerMessageResponse.FAILED);
        }
    }

    @Override
    public ResponseEntity<?> getHomeSliderList() {
        log.info("<-- getHomeSliderList Flying -->");
        try {
            List<HomeSliderModel> sliderList =  this.homeSliderRepo.findAll();
            List<HomeSliderDto> homeSliderDtos =   sliderList
                    .stream()
                    .map(category -> modelMapper.map(category, HomeSliderDto.class))
                    .collect(Collectors.toList());
            return ResponseGenerator.generateSuccessResponse(homeSliderDtos,SellerMessageResponse.SUCCESS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(SellerMessageResponse.FAILED);
        }
    }

    @Override
    public ResponseEntity<?> updateHomeSlider(HomeSliderDto homeSliderDto) {
        log.info("<-- updateHomeSlider Flying -->");
        MessageResponse response = new MessageResponse();
        try {
            log.info(homeSliderDto.toString());

            this.homeSliderRepo.findById(homeSliderDto.getId()).orElseThrow(()->new DataNotFoundException(SellerMessageResponse.DATA_NOT_FOUND));
            HomeSliderModel homeSliderModel =  modelMapper.map(homeSliderDto , HomeSliderModel.class);
            this.homeSliderRepo.save(homeSliderModel);

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
