package com.coder.springjwt.services.sellerServices.sellerOrdersService.imple;

import com.coder.springjwt.constants.customerPanelConstants.messageConstants.CustMessageResponse;
import com.coder.springjwt.constants.sellerConstants.sellerMessageConstants.SellerMessageResponse;
import com.coder.springjwt.emuns.DeliveryStatus;
import com.coder.springjwt.models.customerPanelModels.CustomerOrderItems;
import com.coder.springjwt.repository.customerPanelRepositories.orderItemsRepository.OrderItemsRepository;
import com.coder.springjwt.repository.customerPanelRepositories.ordersRepository.OrderRepository;
import com.coder.springjwt.services.sellerServices.sellerOrdersService.SellerOrderOneByOneService;
import com.coder.springjwt.util.ResponseGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SellerOrderOneByOneServiceImple implements SellerOrderOneByOneService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemsRepository orderItemsRepository;

    @Override
    public ResponseEntity<?> getPendingOrderOneByOneBySeller(Integer page, Integer size) {
        log.info("<=== getPendingOrderOneByOneBySeller Flying ===>");
        try {
            Pageable pageable = PageRequest.of(page, size , Sort.by(Sort.Direction.DESC , "orderDateTime"));

            Page<CustomerOrderItems> allPendingOrders = orderItemsRepository
                                                        .findAllByDeliveryStatus(DeliveryStatus.PENDING.toString() , pageable);

            return ResponseGenerator.generateSuccessResponse(allPendingOrders , CustMessageResponse.SUCCESS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            log.error("Failed To fetch");
            return ResponseGenerator.generateBadRequestResponse(e.getMessage() , SellerMessageResponse.FAILED);
        }
    }

    @Override
    public ResponseEntity<?> getShippedOrderOneByOneBySeller(Integer page, Integer size) {
        log.info("<=== getShippedOrderOneByOneBySeller Flying ===>");
        try {
            Pageable pageable = PageRequest.of(page, size , Sort.by(Sort.Direction.DESC , "orderDateTime"));

            Page<CustomerOrderItems> allShippedOrders = orderItemsRepository
                    .findAllByDeliveryStatus(DeliveryStatus.SHIPPED.toString() ,  pageable);

            return ResponseGenerator.generateSuccessResponse(allShippedOrders , CustMessageResponse.SUCCESS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            log.error("Failed To fetch");
            return ResponseGenerator.generateBadRequestResponse(e.getMessage() , SellerMessageResponse.FAILED);
        }
    }

    @Override
    public ResponseEntity<?> getOutOfDeliveryOrderOneByOneBySeller(Integer page, Integer size) {
        log.info("<=== getOutOfDeliveryOrderOneByOneBySeller Flying ===>");
        try {
            Pageable pageable = PageRequest.of(page, size , Sort.by(Sort.Direction.DESC , "orderDateTime"));

            Page<CustomerOrderItems> allOutOfDeliveryOrders = orderItemsRepository
                    .findAllByDeliveryStatus(DeliveryStatus.OUT_OF_DELIVERY.toString() ,  pageable);

            return ResponseGenerator.generateSuccessResponse(allOutOfDeliveryOrders , CustMessageResponse.SUCCESS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            log.error("Failed To fetch");
            return ResponseGenerator.generateBadRequestResponse(e.getMessage() , SellerMessageResponse.FAILED);
        }
    }

    @Override
    public ResponseEntity<?> getDeliveredOrderOneByOneBySeller(Integer page, Integer size) {
        log.info("<=== getDeliveredOrderOneByOneBySeller Flying ===>");
        try {
            Pageable pageable = PageRequest.of(page, size , Sort.by(Sort.Direction.DESC , "orderDateTime"));

            Page<CustomerOrderItems> allDeliveredOrders = orderItemsRepository
                    .findAllByDeliveryStatus(DeliveryStatus.DELIVERED.toString() ,  pageable);

            return ResponseGenerator.generateSuccessResponse(allDeliveredOrders , CustMessageResponse.SUCCESS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            log.error("Failed To fetch");
            return ResponseGenerator.generateBadRequestResponse(e.getMessage() , SellerMessageResponse.FAILED);
        }
    }
}
