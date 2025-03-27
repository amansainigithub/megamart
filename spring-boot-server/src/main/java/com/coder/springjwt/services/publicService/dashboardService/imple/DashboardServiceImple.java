package com.coder.springjwt.services.publicService.dashboardService.imple;

import com.amazonaws.services.cognitoidp.model.UserNotFoundException;
import com.coder.springjwt.constants.customerPanelConstants.messageConstants.CustMessageResponse;
import com.coder.springjwt.emuns.DeliveryStatus;
import com.coder.springjwt.helpers.userHelper.UserHelper;
import com.coder.springjwt.models.User;
import com.coder.springjwt.models.customerPanelModels.CustomerOrderItems;
import com.coder.springjwt.repository.UserRepository;
import com.coder.springjwt.repository.customerPanelRepositories.ordersRepository.OrderRepository;
import com.coder.springjwt.services.publicService.dashboardService.DashboardService;
import com.coder.springjwt.util.ResponseGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class DashboardServiceImple implements DashboardService {


    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<?> getDashboard(String username) {
        try {
            Map<String,Object> data = new HashMap<>();

            String currentUser = UserHelper.getOnlyCurrentUser();
            User user = this.userRepository.findByUsername(currentUser)
                    .orElseThrow(() -> new UserNotFoundException(CustMessageResponse.USERNAME_NOT_FOUND));

            long userPendingOrderCount = this.orderRepository.countOrdersByCustomerIdNative(user.getId() , DeliveryStatus.PENDING.toString());

            long userDeliveredOrderCount = this.orderRepository.countOrdersByCustomerIdNative(user.getId() , DeliveryStatus.DELIVERED.toString());

            long totalOrders = this.orderRepository.countTotalOrdersByCustomerIdNative(user.getId());

            log.info("PENDING COUNT:: " + userPendingOrderCount);
            log.info("DELIVERED COUNT:: " + userDeliveredOrderCount);
            log.info("TOTAL COUNT:: " + totalOrders);

            data.put("newOrders" , userPendingOrderCount); // Pending For new Orders
            data.put("orderDelivered" , userDeliveredOrderCount); // Delivered Successfully
            data.put("totalOrders",totalOrders); // Total Orders


            List<CustomerOrderItems> customerOrderItems = this.orderRepository.findOrderItemsByCustomerId(user.getId() , "PAID");
            data.put("listOfOrders",customerOrderItems);

            return ResponseGenerator.generateSuccessResponse(data ,CustMessageResponse.SUCCESS);
        }  catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(CustMessageResponse.SOMETHING_WENT_WRONG);
        }
    }
}
