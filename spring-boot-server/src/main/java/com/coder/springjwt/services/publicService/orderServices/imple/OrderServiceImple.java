package com.coder.springjwt.services.publicService.orderServices.imple;

import com.amazonaws.services.cognitoidp.model.UserNotFoundException;
import com.coder.springjwt.constants.customerPanelConstants.messageConstants.CustMessageResponse;
import com.coder.springjwt.dtos.customerPanelDtos.customerOrderDtos.CustomerOrderItemDTO;
import com.coder.springjwt.dtos.customerPanelDtos.customerOrderDtos.CustomerReturnOrderDto;
import com.coder.springjwt.dtos.customerPanelDtos.returnExchangeDto.ReturnRequestInitiateDto;
import com.coder.springjwt.emuns.DeliveryStatus;
import com.coder.springjwt.emuns.PaymentModeStatus;
import com.coder.springjwt.emuns.RefundStatus;
import com.coder.springjwt.exception.customerPanelException.DataNotFoundException;
import com.coder.springjwt.helpers.userHelper.UserHelper;
import com.coder.springjwt.models.User;
import com.coder.springjwt.models.customerPanelModels.CustomerOrderItems;
import com.coder.springjwt.models.customerPanelModels.CustomerReturnOrders;
import com.coder.springjwt.repository.UserRepository;
import com.coder.springjwt.repository.customerPanelRepositories.orderItemsRepository.OrderItemsRepository;
import com.coder.springjwt.repository.customerPanelRepositories.ordersRepository.OrderRepository;
import com.coder.springjwt.repository.customerPanelRepositories.returnRepository.ReturnRepository;
import com.coder.springjwt.services.publicService.orderServices.OrderService;
import com.coder.springjwt.util.ResponseGenerator;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
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
    private ReturnRepository returnRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ResponseEntity<?> getCustomerOrders(long id) {
        log.info("<-- getCustomerOrders Flying-->");
        try {
            String currentUser = UserHelper.getOnlyCurrentUser();
            this.userRepository.findByUsername(currentUser)
                    .orElseThrow(() -> new UserNotFoundException(CustMessageResponse.USERNAME_NOT_FOUND));


            List<CustomerOrderItems> orderItemsExceptDelivered = this.orderItemsRepository.findOrderItemsExceptDelivered(id);

            List<CustomerOrderItemDTO> itemsList = orderItemsExceptDelivered.stream()
                                            .map(order -> {
                                                    CustomerOrderItemDTO itemsDto =
                                                            modelMapper.map(order, CustomerOrderItemDTO.class);
                                                    return itemsDto;
                                            })
                                            .collect(Collectors.toList());
            return ResponseGenerator.generateSuccessResponse(itemsList , CustMessageResponse.SUCCESS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(CustMessageResponse.SOMETHING_WENT_WRONG);
        }
    }


    @Override
    public ResponseEntity<?> getMyOrdersDelivered(long id) {
        log.info("<-- getMyOrdersDelivered Flying-->");
        try {
            String currentUser = UserHelper.getOnlyCurrentUser();
            User user = this.userRepository.findByUsername(currentUser)
                    .orElseThrow(() -> new UserNotFoundException(CustMessageResponse.USERNAME_NOT_FOUND));

            List<CustomerOrderItems> deliveredItems = this.orderItemsRepository.findOrderItemsDelivered(id);

            // Create date formatter
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM, yyyy hh:mm a");

            List<CustomerOrderItemDTO> deliveredOrders = deliveredItems.stream()
                    .map(order -> {
                        CustomerOrderItemDTO itemsDto = modelMapper.map(order, CustomerOrderItemDTO.class);

                        if(order.getCustomerReturnOrders() != null) {
                            CustomerReturnOrders customerReturnOrders = order.getCustomerReturnOrders();
                            CustomerReturnOrderDto returnOrder = modelMapper.map(customerReturnOrders, CustomerReturnOrderDto.class);
                            itemsDto.setCustomerReturnOrderDto(returnOrder);
                        }

                        try {
                            String deliveredDateString = order.getDeliveryDateTime(); // Assuming String
                            LocalDateTime deliveredDate = LocalDateTime.parse(deliveredDateString, formatter);
                            LocalDateTime returnExpiryDate = deliveredDate.plusDays(7);
                            long daysLeft = ChronoUnit.DAYS.between(LocalDateTime.now(), returnExpiryDate);

                            if (daysLeft > 0) {
                                itemsDto.setReturnMessage("Return/Exchange available till " + daysLeft + " days");
                            } else {
                                itemsDto.setReturnMessage("0");
                            }

                        } catch (Exception e) {
                            itemsDto.setReturnMessage("Delivery date invalid");
                        }

                        return itemsDto;
                    })
                    .collect(Collectors.toList());

            return ResponseGenerator.generateSuccessResponse(deliveredOrders , CustMessageResponse.SUCCESS);
        }
        catch (Exception e)
        {
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
        CustomerReturnOrders customerReturnOrders = new CustomerReturnOrders();
        try {

            CustomerOrderItems customerOrderItems = this.orderItemsRepository
                    .findById(returnRequestInitiateDto.getId())
                    .orElseThrow(() -> new DataNotFoundException(CustMessageResponse.DATA_NOT_FOUND));

            if(customerOrderItems.getPaymentMode().equals(PaymentModeStatus.COD.toString())) {

                customerOrderItems.setDeliveryStatus(DeliveryStatus.RETURN.toString());

                //Return Data Save To Return Table
                customerReturnOrders.setReturnReason(returnRequestInitiateDto.getReturnReason());
                //Customer Account Details
                customerReturnOrders.setAccountNumber(returnRequestInitiateDto.getAccountNumber());
                customerReturnOrders.setIfscCode(returnRequestInitiateDto.getIfscCode());
                customerReturnOrders.setBankName(returnRequestInitiateDto.getBankName());
                //Refund Status
                customerReturnOrders.setReturnRefundStatus(RefundStatus.PENDING.toString());
                customerReturnOrders.setReturnRefundRequestDateTime(LocalDateTime.now().toString());
                //Set OrderItemId
                customerReturnOrders.setOrderItemId(customerOrderItems.getId());

                //Set Delivery Status
                customerReturnOrders.setDeliveryStatus(DeliveryStatus.RETURN.toString());

            }else if(customerOrderItems.getPaymentMode().equals(PaymentModeStatus.ONLINE.toString())) {

                //Set Cancel Order Properties
                customerOrderItems.setDeliveryStatus(DeliveryStatus.RETURN.toString());

                customerReturnOrders.setReturnReason(returnRequestInitiateDto.getReturnReason());
                customerReturnOrders.setReturnRefundRequestDateTime(LocalDateTime.now().toString());
                customerReturnOrders.setReturnRefundStatus(RefundStatus.PENDING.toString());
                //Set OrderItemId
                customerReturnOrders.setOrderItemId(customerOrderItems.getId());

                //Set Delivery Status
                customerReturnOrders.setDeliveryStatus(DeliveryStatus.RETURN.toString());
            }

            //Set Customer Return to Customer Items
            customerOrderItems.setCustomerReturnOrders(customerReturnOrders);

            //Save Customer Order Items to CustomerOrderItems Table
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
