package com.coder.springjwt.services.publicService.orderServices.imple;

import com.amazonaws.services.cognitoidp.model.UserNotFoundException;
import com.coder.springjwt.constants.customerPanelConstants.messageConstants.CustMessageResponse;
import com.coder.springjwt.dtos.customerPanelDtos.customerOrderDtos.CustomerOrderItemDTO;
import com.coder.springjwt.dtos.customerPanelDtos.customerOrderDtos.CustomerReturnExchangeOrderDto;
import com.coder.springjwt.dtos.customerPanelDtos.returnExchangeDto.ExchangeRequestDto;
import com.coder.springjwt.dtos.customerPanelDtos.returnExchangeDto.ReturnRequestInitiateDto;
import com.coder.springjwt.emuns.*;
import com.coder.springjwt.exception.customerPanelException.DataNotFoundException;
import com.coder.springjwt.helpers.userHelper.UserHelper;
import com.coder.springjwt.models.User;
import com.coder.springjwt.models.customerPanelModels.CustomerOrderItems;
import com.coder.springjwt.models.customerPanelModels.CustomerReturnExchangeOrders;
import com.coder.springjwt.repository.UserRepository;
import com.coder.springjwt.repository.customerPanelRepositories.orderItemsRepository.OrderItemsRepository;
import com.coder.springjwt.repository.customerPanelRepositories.ordersRepository.OrderRepository;
import com.coder.springjwt.repository.customerPanelRepositories.returnExchangeRepository.ReturnExchangeRepository;
import com.coder.springjwt.services.publicService.orderServices.OrderService;
import com.coder.springjwt.util.ResponseGenerator;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImple implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemsRepository orderItemsRepository;

    @Autowired
    private ReturnExchangeRepository returnExchangeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ResponseEntity<?> getCustomerOrders(long id, Integer page, Integer size) {
        log.info("<-- getCustomerOrders Flying -->");
        try {
            String currentUser = UserHelper.getOnlyCurrentUser();
            this.userRepository.findByUsername(currentUser)
                    .orElseThrow(() -> new UserNotFoundException(CustMessageResponse.USERNAME_NOT_FOUND));

            Pageable pageable = PageRequest.of(
                    page != null ? page : 0,
                    size != null ? size : 10,
                    Sort.by("creationDate").descending()
            );

            // Original entity page
            Page<CustomerOrderItems> orderItemsPage = this.orderItemsRepository.findOrderItemsExceptDelivered(id, pageable);

            // Map content to DTOs
            List<CustomerOrderItemDTO> dtoList = orderItemsPage.getContent().stream()
                    .map(order -> modelMapper.map(order, CustomerOrderItemDTO.class))
                    .collect(Collectors.toList());

            // Create new Page object with DTOs and same pagination metadata
            Page<CustomerOrderItemDTO> dtoPage = new PageImpl<>(
                    dtoList,
                    pageable,
                    orderItemsPage.getTotalElements()
            );

            return ResponseGenerator.generateSuccessResponse(dtoPage, CustMessageResponse.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(CustMessageResponse.SOMETHING_WENT_WRONG);
        }
    }



    @Override
    public ResponseEntity<?> getMyOrdersDelivered(long id, Integer page, Integer size) {
        log.info("<-- getMyOrdersDelivered Flying -->");
        try {
            String currentUser = UserHelper.getOnlyCurrentUser();
            User user = this.userRepository.findByUsername(currentUser)
                    .orElseThrow(() -> new UserNotFoundException(CustMessageResponse.USERNAME_NOT_FOUND));

            Pageable pageable = PageRequest.of(
                    page != null ? page : 0,
                    size != null ? size : 10,
                    Sort.by("creationDate").descending()
            );

            // Get paginated result
            Page<CustomerOrderItems> deliveredItemsPage = this.orderItemsRepository.findOrderItemsDelivered(id, pageable);

            // Map to DTOs
            List<CustomerOrderItemDTO> deliveredOrders = deliveredItemsPage.getContent().stream()
                    .map(order -> {
                        CustomerOrderItemDTO itemsDto = modelMapper.map(order, CustomerOrderItemDTO.class);

                        // If return Not NUll (RETURN ITEMS)
                        if (order.getCustomerReturnExchangeOrders() != null) {
                            CustomerReturnExchangeOrders customerReturnOrders = order.getCustomerReturnExchangeOrders();
                            CustomerReturnExchangeOrderDto returnOrder = modelMapper.map(customerReturnOrders, CustomerReturnExchangeOrderDto.class);
                            itemsDto.setCustomerReturnExchangeOrderDto(returnOrder);
                        }

                        try {
                            // Create date formatter
//                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM, yyyy hh:mm a");
//                            String deliveredDateString = order.getDeliveryDateTime();
//                            LocalDateTime deliveredDate = LocalDateTime.parse(deliveredDateString, formatter);
//                            LocalDateTime returnExpiryDate = deliveredDate.plusDays(7);
//                            long daysLeft = ChronoUnit.DAYS.between(LocalDateTime.now(), returnExpiryDate);
//
//                            if (daysLeft >= 0) {
//                                itemsDto.setReturnMessage("Exchange available till " + 7 + " days");
//                            } else {
//                                itemsDto.setReturnMessage("0");
//                            }

                            // OR use this line instead of logic above
                            // itemsDto.setReturnMessage("Exchange available till " + 7 + " days");

                        } catch (Exception e) {
                            itemsDto.setReturnMessage("Delivery date invalid");
                        }

                        return itemsDto;
                    })
                    .collect(Collectors.toList());

            // Wrap DTOs into paginated response
            Page<CustomerOrderItemDTO> dtoPage = new PageImpl<>(deliveredOrders, pageable, deliveredItemsPage.getTotalElements());

            return ResponseGenerator.generateSuccessResponse(dtoPage, CustMessageResponse.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(CustMessageResponse.SOMETHING_WENT_WRONG);
        }
    }








    @Override
    public ResponseEntity<?> getCustomerOrdersById(long id) {
        log.info("<-- getCustomerOrdersById Details Flying-->");

        try {
            String currentUser = UserHelper.getOnlyCurrentUser();
            User user = this.userRepository.findByUsername(currentUser)
                    .orElseThrow(() -> new UserNotFoundException(CustMessageResponse.USERNAME_NOT_FOUND));

            CustomerOrderItems customerOrderItems = this.orderItemsRepository.
                                                    findOrderItemsById( user.getId() ,id );

            CustomerOrderItemDTO orderItem = modelMapper.map(customerOrderItems, CustomerOrderItemDTO.class);

            return ResponseGenerator.generateSuccessResponse(orderItem , CustMessageResponse.SUCCESS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(CustMessageResponse.SOMETHING_WENT_WRONG);
        }
    }

    @Override
    public ResponseEntity<?> orderReturnRequestInitiate(ReturnRequestInitiateDto returnRequestInitiateDto) {
        log.info("returnRequestInitiateDto ....." );
        CustomerReturnExchangeOrders customerReturnExchangeOrders = new CustomerReturnExchangeOrders();
        try {

            CustomerOrderItems customerOrderItems = this.orderItemsRepository
                    .findById(returnRequestInitiateDto.getId())
                    .orElseThrow(() -> new DataNotFoundException(CustMessageResponse.DATA_NOT_FOUND));

            if(customerOrderItems.getPaymentMode().equals(PaymentModeStatus.ONLINE.toString())) {

            // Change Delivery  Status to CustomerOrderItems
//            customerOrderItems.setDeliveryStatus(DeliveryStatus.RETURN.toString());

            //SET Payment Mode and Delivery Status and Customer Order Data
            customerReturnExchangeOrders.setPaymentMode(customerOrderItems.getPaymentMode());
            customerReturnExchangeOrders.setOrderItemId(customerOrderItems.getId());
            customerReturnExchangeOrders.setProductId(customerOrderItems.getProductId());
            //Refund Status
            customerReturnExchangeOrders.setReturnRefundStatus(RefundStatus.REFUND_PENDING.toString());
            //Set Delivery Status
            customerReturnExchangeOrders.setReturnDeliveryStatus(ReturnDeliveryStatus.RETURN_REQUESTED.toString());

            customerReturnExchangeOrders.setReturnReason(returnRequestInitiateDto.getReturnReason());

            //Set Customer Return to Customer Items
            customerOrderItems.setCustomerReturnExchangeOrders(customerReturnExchangeOrders);

            //Save Customer Order Items to CustomerOrderItems Table
            this.orderItemsRepository.save(customerOrderItems);
            return ResponseGenerator.generateSuccessResponse("REQUEST INITIATED" , CustMessageResponse.SUCCESS);
            }
            else
            {
                return ResponseGenerator.generateBadRequestResponse("REQUEST FAILED" , CustMessageResponse.SOMETHING_WENT_WRONG);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(CustMessageResponse.SOMETHING_WENT_WRONG);
        }
    }

    @Override
    public ResponseEntity<?> orderExchangeRequestInitiate( ExchangeRequestDto exchangeRequestDto) {
        log.info("orderExchangeRequestInitiate ....." );
        try {
            CustomerOrderItems customerOrderItems = this.orderItemsRepository
                                        .findById(exchangeRequestDto.getOrderItemId())
                                        .orElseThrow(() -> new DataNotFoundException(CustMessageResponse.DATA_NOT_FOUND));

//            customerOrderItems.setDeliveryStatus(DeliveryStatus.EXCHANGE.toString());

            CustomerReturnExchangeOrders customerReturnExchangeOrders = new CustomerReturnExchangeOrders();
            customerReturnExchangeOrders.setExchangeReason(exchangeRequestDto.getExchangeReason());
            customerReturnExchangeOrders.setExchangeProductSize(exchangeRequestDto.getSelectedLabel());
            customerReturnExchangeOrders.setOrderItemId(exchangeRequestDto.getOrderItemId());
            customerReturnExchangeOrders.setProductId(exchangeRequestDto.getProductId());
            customerReturnExchangeOrders.setExchangeDeliveryStatus(ExchangeDeliveryStatus.EXCHANGE_PENDING.toString());

            customerReturnExchangeOrders.setPaymentMode(customerOrderItems.getPaymentMode());

            customerOrderItems.setCustomerReturnExchangeOrders(customerReturnExchangeOrders);
            this.orderItemsRepository.save(customerOrderItems);
            return ResponseGenerator.generateSuccessResponse("REQUEST INITIATED" , CustMessageResponse.SUCCESS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(CustMessageResponse.SOMETHING_WENT_WRONG);
        }
    }
}
