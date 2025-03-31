package com.coder.springjwt.services.publicService.orderServices.imple;

import com.amazonaws.services.cognitoidp.model.UserNotFoundException;
import com.coder.springjwt.constants.customerPanelConstants.messageConstants.CustMessageResponse;
import com.coder.springjwt.dtos.customerPanelDtos.customerOrderDtos.CustomerOrderDTO;
import com.coder.springjwt.dtos.customerPanelDtos.customerOrderDtos.CustomerOrderItemDTO;
import com.coder.springjwt.emuns.DeliveryStatus;
import com.coder.springjwt.helpers.userHelper.UserHelper;
import com.coder.springjwt.models.User;
import com.coder.springjwt.models.customerPanelModels.CustomerOrderItems;
import com.coder.springjwt.models.customerPanelModels.CustomerOrders;
import com.coder.springjwt.repository.UserRepository;
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

            List<CustomerOrders> customerOrders = this.orderRepository.
                                                                findOrderItemsByCustomerIdCustom(
                                                                user.getId());

            List<CustomerOrderDTO> orderList = customerOrders.stream()
                    .map(order -> {
                        CustomerOrderDTO orderDTO = modelMapper.map(order, CustomerOrderDTO.class);

                        List<CustomerOrderItemDTO> filteredItems = order.getCustomerOrderItems().stream()
                                .filter(item -> item.getDeliveryStatus().equals(DeliveryStatus.PENDING.toString()) ||
                                        item.getDeliveryStatus().equals(DeliveryStatus.SHIPPED.toString()) ||
                                        item.getDeliveryStatus().equals(DeliveryStatus.OUT_OF_DELIVERY.toString())) // Excludes "DELIVERED"
                                .map(item -> modelMapper.map(item, CustomerOrderItemDTO.class)) // Convert to DTO
                                .collect(Collectors.toList());

                        // Set filtered items in DTO
                        orderDTO.setCustomerOrderItems(filteredItems);

                        return orderDTO;
                    })
                    .filter(orderDTO -> !orderDTO.getCustomerOrderItems().isEmpty())
                    .collect(Collectors.toList());


            return ResponseGenerator.generateSuccessResponse(orderList , CustMessageResponse.SUCCESS);
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

            List<CustomerOrders> customerOrders = this.orderRepository.
                                                    findOrdersByDeliveredCustom( user.getId());

            List<CustomerOrderDTO> orderList = customerOrders.stream()
                    .map(order -> {
                        CustomerOrderDTO customerOrderDTO = modelMapper.map(order, CustomerOrderDTO.class);

                        // Filter only delivered items and map them to DTOs
                        List<CustomerOrderItemDTO> deliveredItems = order.getCustomerOrderItems().stream()
                                .filter(item -> DeliveryStatus.DELIVERED.toString().equals(item.getDeliveryStatus())) // Filter condition
                                .map(item -> modelMapper.map(item, CustomerOrderItemDTO.class)) // Mapping to DTO
                                .collect(Collectors.toList());

                        customerOrderDTO.setCustomerOrderItems(deliveredItems);

                        // Only add delivered items to the DTO
                        customerOrderDTO.setCustomerOrderItems(deliveredItems);

                        return customerOrderDTO;
                    })
                    // Remove orders that have no delivered items
                    .filter(orderDTO -> !orderDTO.getCustomerOrderItems().isEmpty())
                    .collect(Collectors.toList());

            return ResponseGenerator.generateSuccessResponse(orderList , CustMessageResponse.SUCCESS);
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

            CustomerOrders customerOrders = this.orderRepository.
                    getOrderWithUserIdAndOrderId( user.getId() ,id );

            CustomerOrderDTO orderDTO = modelMapper.map(customerOrders, CustomerOrderDTO.class);

            return ResponseGenerator.generateSuccessResponse(orderDTO , CustMessageResponse.SUCCESS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(CustMessageResponse.SOMETHING_WENT_WRONG);
        }
    }
}
