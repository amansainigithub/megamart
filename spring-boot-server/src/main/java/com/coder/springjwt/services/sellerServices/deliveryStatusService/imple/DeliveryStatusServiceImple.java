package com.coder.springjwt.services.sellerServices.deliveryStatusService.imple;

import com.coder.springjwt.constants.customerPanelConstants.messageConstants.CustMessageResponse;
import com.coder.springjwt.constants.sellerConstants.sellerMessageConstants.SellerMessageResponse;
import com.coder.springjwt.dtos.sellerDtos.deliveryStatusDto.DeliveryStatusDto;
import com.coder.springjwt.emuns.DeliveryStatus;
import com.coder.springjwt.models.customerPanelModels.CustomerOrderItems;
import com.coder.springjwt.repository.customerPanelRepositories.orderItemsRepository.OrderItemsRepository;
import com.coder.springjwt.services.sellerServices.deliveryStatusService.DeliveryStatusService;
import com.coder.springjwt.util.ResponseGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DeliveryStatusServiceImple implements DeliveryStatusService {


    @Autowired
    private OrderItemsRepository orderItemsRepository;


    @Override
    public ResponseEntity<?> updateDeliveryStatusOrderItems(DeliveryStatusDto deliveryStatusDto) {
        try {
                log.info("===> updateDeliveryStatusOrderItems Flying");

            CustomerOrderItems customerOrderItems = this.orderItemsRepository
                                        .findById(Long.valueOf(deliveryStatusDto.getOrderItemId()))
                                        .orElseThrow(() -> new RuntimeException(SellerMessageResponse.ORDER_ITEM_NOT_FOUND));

            customerOrderItems.setDeliveryStatus(deliveryStatusDto.getDeliveryStatus());
            customerOrderItems.setOrderTrackingId(deliveryStatusDto.getTackerId());
            customerOrderItems.setDeliveryDate(deliveryStatusDto.getDeliveryDate());

            this.orderItemsRepository.save(customerOrderItems);

            return ResponseGenerator.generateSuccessResponse(CustMessageResponse.DATA_SAVED_SUCCESS , CustMessageResponse.SUCCESS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(e.getMessage() , SellerMessageResponse.FAILED);
        }
    }

    @Override
    public ResponseEntity<?> getDeliveryDetailsById(Long id) {
        try {
            log.info("===> getDeliveryDetailsById Flying");
            CustomerOrderItems customerOrderItems = this.orderItemsRepository.findById(id)
                                                    .orElseThrow(() -> new RuntimeException(
                                                            SellerMessageResponse.ORDER_ITEM_NOT_FOUND));
            DeliveryStatusDto deliveryStatusDto = new DeliveryStatusDto();
            deliveryStatusDto.setTackerId(customerOrderItems.getOrderTrackingId());
            deliveryStatusDto.setDeliveryStatus(customerOrderItems.getDeliveryStatus());
            deliveryStatusDto.setOrderItemId(String.valueOf(customerOrderItems.getId()));
            deliveryStatusDto.setDeliveryDate(customerOrderItems.getDeliveryDate());

            return ResponseGenerator.generateSuccessResponse(deliveryStatusDto , CustMessageResponse.SUCCESS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(e.getMessage() , SellerMessageResponse.FAILED);
        }
    }
}
