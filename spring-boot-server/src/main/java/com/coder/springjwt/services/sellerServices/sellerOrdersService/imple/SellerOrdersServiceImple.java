package com.coder.springjwt.services.sellerServices.sellerOrdersService.imple;

import com.coder.springjwt.constants.customerPanelConstants.messageConstants.CustMessageResponse;
import com.coder.springjwt.constants.sellerConstants.sellerMessageConstants.SellerMessageResponse;
import com.coder.springjwt.emuns.DeliveryStatus;
import com.coder.springjwt.models.customerPanelModels.CustomerOrderItems;
import com.coder.springjwt.models.customerPanelModels.CustomerOrders;
import com.coder.springjwt.repository.customerPanelRepositories.orderItemsRepository.OrderItemsRepository;
import com.coder.springjwt.repository.customerPanelRepositories.ordersRepository.OrderRepository;
import com.coder.springjwt.services.sellerServices.sellerOrdersService.SellerOrdersService;
import com.coder.springjwt.util.ResponseGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SellerOrdersServiceImple implements SellerOrdersService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemsRepository orderItemsRepository;
    @Override
    public ResponseEntity<?> getPendingOrderBySeller(Integer page, Integer size) {
        log.info("<=== getPendingOrderBySeller Flying ===>");
        try {
            Pageable pageable = PageRequest.of(page, size , Sort.by(Sort.Direction.DESC , "orderDateTime"));
            Page<CustomerOrders> customerOrders = this.orderRepository
                    .getPendingOrderItems(DeliveryStatus.PENDING.toString(), pageable);


            List<CustomerOrders> pendingOrders = customerOrders.getContent().stream()
                    .map(order -> {
                        List<CustomerOrderItems> pendingItems = order.getCustomerOrderItems().stream()
                                .filter(item -> item.getDeliveryStatus().equals(DeliveryStatus.PENDING.toString()))
                                .collect(Collectors.toList());

                        // Set filtered items back to the order
                        order.setCustomerOrderItems(pendingItems);
                        return order;
                    })
                    .filter(order -> !order.getCustomerOrderItems().isEmpty()) // Remove orders with no pending items
                    .collect(Collectors.toList());
            Page<CustomerOrders> pendingOrdersPage = new PageImpl<>(pendingOrders, pageable, pendingOrders.size());
            return ResponseGenerator.generateSuccessResponse(pendingOrdersPage , CustMessageResponse.SUCCESS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            log.error("Failed To fetch");
            return ResponseGenerator.generateBadRequestResponse(e.getMessage() , SellerMessageResponse.FAILED);
        }
    }

    @Override
    public ResponseEntity<?> getShippedOrderBySeller(Integer page, Integer size) {
        log.info("<=== getShippedOrderBySeller Flying ===>");

        try {
            Pageable pageable = PageRequest.of(page, size , Sort.by(Sort.Direction.DESC , "orderDateTime"));
            Page<CustomerOrders> customerOrders = this.orderRepository
                                                .getShippedOrders(DeliveryStatus.SHIPPED.toString(), pageable);

            List<CustomerOrders> shippedOrders = customerOrders.getContent().stream()
                    .map(order -> {
                        List<CustomerOrderItems> shippedItems = order.getCustomerOrderItems().stream()
                                .filter(item -> item.getDeliveryStatus().equals(DeliveryStatus.SHIPPED.toString()))
                                .collect(Collectors.toList());

                        // Set filtered items back to the order
                        order.setCustomerOrderItems(shippedItems);
                        return order;
                    })
                    .filter(order -> !order.getCustomerOrderItems().isEmpty()) // Remove orders with no shipped items
                    .collect(Collectors.toList());

            Page<CustomerOrders> shippedOrdersPage = new PageImpl<>(shippedOrders, pageable, shippedOrders.size());
            return ResponseGenerator.generateSuccessResponse(shippedOrdersPage , CustMessageResponse.SUCCESS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            log.error("Failed To fetch");
            return ResponseGenerator.generateBadRequestResponse(e.getMessage() , SellerMessageResponse.FAILED);
        }
    }

    @Override
    public ResponseEntity<?> getOutOfDeliveryOrderBySellerService(Integer page, Integer size) {
        log.info("<=== getOutOfDeliveryOrderBySellerService Flying ===>");

        try {
            Pageable pageable = PageRequest.of(page, size , Sort.by(Sort.Direction.DESC , "orderDateTime"));
            Page<CustomerOrders> customerOrders = this.orderRepository
                    .getOutOfDeliveryOrders(DeliveryStatus.OUT_OF_DELIVERY.toString(), pageable);

            List<CustomerOrders> outOfDeliveryOrders = customerOrders.getContent().stream()
                    .map(order -> {
                        List<CustomerOrderItems> shippedItems = order.getCustomerOrderItems().stream()
                                .filter(item -> item.getDeliveryStatus().equals(DeliveryStatus.OUT_OF_DELIVERY.toString()))
                                .collect(Collectors.toList());

                        // Set filtered items back to the order
                        order.setCustomerOrderItems(shippedItems);
                        return order;
                    })
                    .filter(order -> !order.getCustomerOrderItems().isEmpty()) // Remove orders with no shipped items
                    .collect(Collectors.toList());

            Page<CustomerOrders> outOfDeliveryOrdersPage = new PageImpl<>(outOfDeliveryOrders, pageable, outOfDeliveryOrders.size());
            return ResponseGenerator.generateSuccessResponse(outOfDeliveryOrdersPage , CustMessageResponse.SUCCESS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            log.error("Failed To fetch");
            return ResponseGenerator.generateBadRequestResponse(e.getMessage() , SellerMessageResponse.FAILED);
        }
    }

    @Override
    public ResponseEntity<?> getDeliveredOrderBySellerService(Integer page, Integer size) {
        log.info("<=== getDeliveredOrderBySellerService Flying ===>");

        try {
            Pageable pageable = PageRequest.of(page, size , Sort.by(Sort.Direction.DESC , "orderDateTime"));
            Page<CustomerOrders> customerOrders = this.orderRepository
                    .getDeliveryOrders(DeliveryStatus.DELIVERED.toString(), pageable);

            List<CustomerOrders> deliveredOrders = customerOrders.getContent().stream()
                    .map(order -> {
                        List<CustomerOrderItems> shippedItems = order.getCustomerOrderItems().stream()
                                .filter(item -> item.getDeliveryStatus().equals(DeliveryStatus.DELIVERED.toString()))
                                .collect(Collectors.toList());

                        // Set filtered items back to the order
                        order.setCustomerOrderItems(shippedItems);
                        return order;
                    })
                    .filter(order -> !order.getCustomerOrderItems().isEmpty()) // Remove orders with no shipped items
                    .collect(Collectors.toList());

            Page<CustomerOrders> deliveredOrdersPage = new PageImpl<>(deliveredOrders, pageable, deliveredOrders.size());
            return ResponseGenerator.generateSuccessResponse(deliveredOrdersPage , CustMessageResponse.SUCCESS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            log.error("Failed To fetch");
            return ResponseGenerator.generateBadRequestResponse(e.getMessage() , SellerMessageResponse.FAILED);
        }
    }


}
