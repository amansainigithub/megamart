package com.coder.springjwt.services.orderServices.imple;

import com.amazonaws.services.cognitoidp.model.UserNotFoundException;
import com.coder.springjwt.constants.customerPanelConstants.messageConstants.CustMessageResponse;
import com.coder.springjwt.dtos.customerPanelDtos.customerOrderDtos.CustomerOrderDTO;
import com.coder.springjwt.dtos.customerPanelDtos.customerOrderDtos.CustomerOrderItemDTO;
import com.coder.springjwt.emuns.DeliveryStatus;
import com.coder.springjwt.emuns.PaymentStatus;
import com.coder.springjwt.helpers.userHelper.UserHelper;
import com.coder.springjwt.models.User;
import com.coder.springjwt.models.customerPanelModels.CustomerOrderItems;
import com.coder.springjwt.models.customerPanelModels.CustomerOrders;
import com.coder.springjwt.repository.UserRepository;
import com.coder.springjwt.repository.customerPanelRepositories.ordersRepository.OrderRepository;
import com.coder.springjwt.services.orderServices.OrderService;
import com.coder.springjwt.util.ResponseGenerator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImple implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ResponseEntity<?> getCustomerOrders(long id) {

        try {
            String currentUser = UserHelper.getOnlyCurrentUser();
            User user = this.userRepository.findByUsername(currentUser)
                    .orElseThrow(() -> new UserNotFoundException(CustMessageResponse.USERNAME_NOT_FOUND));

            List<CustomerOrders> customerOrderItems = this.orderRepository.
                                                                findOrderItemsByCustomerIdCustom(
                                                                user.getId() ,
                                                                "PAID"  ,"PENDING");

            List<CustomerOrderDTO> orderList = customerOrderItems.stream().map(order -> {
                CustomerOrderDTO orderDTO = modelMapper.map(order, CustomerOrderDTO.class);

                // Manually map nested items
//                List<CustomerOrderItemDTO> itemDTOs = order.getCustomerOrderItems().stream()
//                        .map(item -> modelMapper.map(item, CustomerOrderItemDTO.class))
//                        .collect(Collectors.toList());
//
//                orderDTO.setCustomerOrderItems(itemDTOs);
                return orderDTO;
            }).collect(Collectors.toList());

            return ResponseGenerator.generateSuccessResponse(orderList , CustMessageResponse.SOMETHING_WENT_WRONG);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(CustMessageResponse.SOMETHING_WENT_WRONG);
        }
    }
}
