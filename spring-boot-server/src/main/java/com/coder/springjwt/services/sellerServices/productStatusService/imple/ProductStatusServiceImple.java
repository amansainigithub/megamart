package com.coder.springjwt.services.sellerServices.productStatusService.imple;

import com.coder.springjwt.constants.sellerConstants.sellerMessageConstants.SellerMessageResponse;
import com.coder.springjwt.dtos.sellerDtos.productStatusDtos.ProductReviewStatusDto;
import com.coder.springjwt.exception.customerPanelException.DataNotFoundException;
import com.coder.springjwt.models.productStatus.ProductReviewStatusModel;
import com.coder.springjwt.repository.sellerRepository.productStatusRepo.ProductReviewStatusRepository;
import com.coder.springjwt.services.sellerServices.productStatusService.ProductStatusService;
import com.coder.springjwt.util.ResponseGenerator;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductStatusServiceImple implements ProductStatusService {

    @Autowired
    private ProductReviewStatusRepository productReviewStatusRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ResponseEntity<?> saveProductReviewStatus(ProductReviewStatusDto productReviewStatusDto) {
        try {
            ProductReviewStatusModel productReviewStatus = modelMapper.map(productReviewStatusDto, ProductReviewStatusModel.class);
            ProductReviewStatusModel reviewSaved = this.productReviewStatusRepository.save(productReviewStatus);
            log.info("Product Review Status Saved Success-- Id=> :: " + reviewSaved.getId());
            return ResponseGenerator.generateSuccessResponse(SellerMessageResponse.DATA_SAVED_SUCCESS, SellerMessageResponse.SUCCESS);
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(SellerMessageResponse.DATA_NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<?> deleteProductReviewStatus(long id) {
        try {
            this.productReviewStatusRepository.deleteById(id);
            log.info("Product Review Delete Success-- Id=> :: " + id);
            return ResponseGenerator.generateSuccessResponse("DATA_DELETE_SUCCESS", SellerMessageResponse.SUCCESS);
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(SellerMessageResponse.DATA_NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<?> getProductReviews(Integer page, Integer size) {
        try {
            Page<ProductReviewStatusModel> statusReviewsList = this.productReviewStatusRepository.
                                                    findAll(PageRequest.of(page, size, Sort.by("id").descending()));

            Page<ProductReviewStatusDto> statusReviews = statusReviewsList.map(this::convertToDTO);

            log.info("Product Review List Fetch Success:: ");
            return ResponseGenerator.generateSuccessResponse(statusReviews, SellerMessageResponse.SUCCESS);
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(SellerMessageResponse.DATA_NOT_FOUND);
        }

    }

    @Override
    public ResponseEntity<?> getProductReviewStatusById(long id) {
        try {
            ProductReviewStatusModel productReviewStatusModel = this.productReviewStatusRepository.findById(id)
                                            .orElseThrow(() -> new DataNotFoundException(SellerMessageResponse.DATA_NOT_FOUND));

            return ResponseGenerator.generateSuccessResponse(productReviewStatusModel, SellerMessageResponse.SUCCESS);
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(SellerMessageResponse.DATA_NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<?> updateProductReviewStatus(ProductReviewStatusDto productReviewStatusDto) {
        try {
            this.productReviewStatusRepository.findById(productReviewStatusDto.getId())
                    .orElseThrow(()->new DataNotFoundException("Data not Found"));

            ProductReviewStatusModel productReviewStatusModel = modelMapper.map(productReviewStatusDto, ProductReviewStatusModel.class);

            this.productReviewStatusRepository.save(productReviewStatusModel);

            log.info("Data Updated Success");
            return ResponseGenerator.generateSuccessResponse(SellerMessageResponse.SUCCESS ,
                    SellerMessageResponse.DATA_UPDATE_SUCCESS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(SellerMessageResponse.FAILED ,SellerMessageResponse.DATA_UPDATE_FAILED);
        }
    }

    @Override
    public ResponseEntity<?> getProductReviewsStatusList() {
        try {
            List<ProductReviewStatusModel> productReviewStatusModelList = this.productReviewStatusRepository.
                    findAll(Sort.by("id").descending());

            // Convert List<ProductReviewStatusModel> to List<ProductReviewStatusDto>
            List<ProductReviewStatusDto> data = productReviewStatusModelList.stream()
                    .map(model -> modelMapper.map(model, ProductReviewStatusDto.class))
                    .collect(Collectors.toList());

            log.info("Product Review List Fetch Success:: ");
            return ResponseGenerator.generateSuccessResponse(data, SellerMessageResponse.SUCCESS);
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(SellerMessageResponse.DATA_NOT_FOUND);
        }

    }


    private ProductReviewStatusDto convertToDTO(ProductReviewStatusModel model) {
        ProductReviewStatusDto dto = new ProductReviewStatusDto();
        dto.setId(model.getId());
        dto.setProductStatus(model.getProductStatus());
        dto.setProductStatusValue(model.getProductStatusValue());
        dto.setDescription(model.getDescription());
        return dto;
    }
}
