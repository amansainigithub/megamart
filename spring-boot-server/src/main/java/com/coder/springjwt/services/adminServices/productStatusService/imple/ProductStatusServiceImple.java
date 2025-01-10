package com.coder.springjwt.services.adminServices.productStatusService.imple;

import com.coder.springjwt.constants.adminConstants.adminMessageConstants.AdminMessageResponse;
import com.coder.springjwt.dtos.adminDtos.productStatusDtos.ProductReviewStatusDto;
import com.coder.springjwt.exception.adminException.DataNotFoundException;
import com.coder.springjwt.models.adminModels.catalog.catalogType.ProductTypeModel;
import com.coder.springjwt.models.adminModels.productStatus.ProductReviewStatusModel;
import com.coder.springjwt.repository.adminRepository.productStatusRepo.ProductReviewStatusRepository;
import com.coder.springjwt.services.adminServices.productStatusService.ProductStatusService;
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
import java.util.Optional;
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
            return ResponseGenerator.generateSuccessResponse(AdminMessageResponse.DATA_SAVED_SUCCESS, AdminMessageResponse.SUCCESS);
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(AdminMessageResponse.DATA_NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<?> deleteProductReviewStatus(long id) {
        try {
            this.productReviewStatusRepository.deleteById(id);
            log.info("Product Review Delete Success-- Id=> :: " + id);
            return ResponseGenerator.generateSuccessResponse(AdminMessageResponse.DATA_DELETE_SUCCESS, AdminMessageResponse.SUCCESS);
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(AdminMessageResponse.DATA_NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<?> getProductReviews(Integer page, Integer size) {
        try {
            Page<ProductReviewStatusModel> statusReviewsList = this.productReviewStatusRepository.
                                                    findAll(PageRequest.of(page, size, Sort.by("id").descending()));

            Page<ProductReviewStatusDto> statusReviews = statusReviewsList.map(this::convertToDTO);

            log.info("Product Review List Fetch Success:: ");
            return ResponseGenerator.generateSuccessResponse(statusReviews, AdminMessageResponse.SUCCESS);
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(AdminMessageResponse.DATA_NOT_FOUND);
        }

    }

    @Override
    public ResponseEntity<?> getProductReviewStatusById(long id) {
        try {
            ProductReviewStatusModel productReviewStatusModel = this.productReviewStatusRepository.findById(id)
                                            .orElseThrow(() -> new DataNotFoundException(AdminMessageResponse.DATA_NOT_FOUND));

            return ResponseGenerator.generateSuccessResponse(productReviewStatusModel, AdminMessageResponse.SUCCESS);
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(AdminMessageResponse.DATA_NOT_FOUND);
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
            return ResponseGenerator.generateSuccessResponse(AdminMessageResponse.SUCCESS ,
                    AdminMessageResponse.DATA_UPDATE_SUCCESS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(AdminMessageResponse.FAILED ,AdminMessageResponse.DATA_UPDATE_FAILED);
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
            return ResponseGenerator.generateSuccessResponse(data, AdminMessageResponse.SUCCESS);
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(AdminMessageResponse.DATA_NOT_FOUND);
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
