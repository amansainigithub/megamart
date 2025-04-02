package com.coder.springjwt.services.publicService.orderServices.imple;

import com.amazonaws.services.cognitoidp.model.UserNotFoundException;
import com.coder.springjwt.constants.customerPanelConstants.messageConstants.CustMessageResponse;
import com.coder.springjwt.dtos.customerPanelDtos.customerOrderDtos.CustomerOrderItemDTO;
import com.coder.springjwt.helpers.userHelper.UserHelper;
import com.coder.springjwt.models.User;
import com.coder.springjwt.models.customerPanelModels.CustomerOrderItems;
import com.coder.springjwt.repository.UserRepository;
import com.coder.springjwt.repository.customerPanelRepositories.orderItemsRepository.OrderItemsRepository;
import com.coder.springjwt.repository.customerPanelRepositories.ordersRepository.OrderRepository;
import com.coder.springjwt.services.publicService.orderServices.OrderService;
import com.coder.springjwt.util.ResponseGenerator;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ResponseEntity<?> getCustomerOrders(long id) {
        log.info("<-- getCustomerOrders Flying-->");
        try {
            String currentUser = UserHelper.getOnlyCurrentUser();
            User user = this.userRepository.findByUsername(currentUser)
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

            List<CustomerOrderItemDTO> deliveredOrders = deliveredItems.stream()
                    .map(order -> {
                        CustomerOrderItemDTO itemsDto =
                                modelMapper.map(order, CustomerOrderItemDTO.class);
                        return itemsDto;
                    })
                    .collect(Collectors.toList());

//            List<CustomerOrders> customerOrders = this.orderRepository.
//                                                    findOrdersByDeliveredCustom( user.getId());
//
//            List<CustomerOrderDTO> orderList = customerOrders.stream()
//                    .map(order -> {
//                        CustomerOrderDTO customerOrderDTO = modelMapper.map(order, CustomerOrderDTO.class);
//
//                        // Filter only delivered items and map them to DTOs
//                        List<CustomerOrderItemDTO> deliveredItems = order.getCustomerOrderItems().stream()
//                                .filter(item -> DeliveryStatus.DELIVERED.toString().equals(item.getDeliveryStatus())) // Filter condition
//                                .map(item -> modelMapper.map(item, CustomerOrderItemDTO.class)) // Mapping to DTO
//                                .collect(Collectors.toList());
//
//                        customerOrderDTO.setCustomerOrderItems(deliveredItems);
//
//                        // Only add delivered items to the DTO
//                        customerOrderDTO.setCustomerOrderItems(deliveredItems);
//
//                        return customerOrderDTO;
//                    })
//                    // Remove orders that have no delivered items
//                    .filter(orderDTO -> !orderDTO.getCustomerOrderItems().isEmpty())
//                    .collect(Collectors.toList());

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
}
