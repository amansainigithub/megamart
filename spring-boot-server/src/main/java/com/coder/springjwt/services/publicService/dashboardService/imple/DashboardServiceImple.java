package com.coder.springjwt.services.publicService.dashboardService.imple;

import com.amazonaws.services.cognitoidp.model.UserNotFoundException;
import com.coder.springjwt.constants.customerPanelConstants.messageConstants.CustMessageResponse;
import com.coder.springjwt.emuns.DeliveryStatus;
import com.coder.springjwt.helpers.userHelper.UserHelper;
import com.coder.springjwt.models.User;
import com.coder.springjwt.models.customerPanelModels.CustomerOrderItems;
import com.coder.springjwt.repository.UserRepository;
import com.coder.springjwt.repository.customerPanelRepositories.orderItemsRepository.OrderItemsRepository;
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
    private OrderItemsRepository orderItemsRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<?> getDashboard(String username) {
        log.info("<--  getDashboard Flying  -->");
        try {
            Map<String,Object> data = new HashMap<>();

            String currentUser = UserHelper.getOnlyCurrentUser();
            User user = this.userRepository.findByUsername(currentUser)
                    .orElseThrow(() -> new UserNotFoundException(CustMessageResponse.USERNAME_NOT_FOUND));

            long pendingCount = this.orderItemsRepository.countByDeliveryStatusAndUserId(DeliveryStatus.PENDING.toString(),
                    String.valueOf(user.getId()));

            long deliveredCount = this.orderItemsRepository.countByDeliveryStatusAndUserId(DeliveryStatus.DELIVERED.toString(),
                    String.valueOf(user.getId()));

            long totalCounts = this.orderItemsRepository.countByUserId(String.valueOf(user.getId()));


            data.put("newOrders" , pendingCount); // Pending For new Orders
            data.put("orderDelivered" , deliveredCount); // Delivered Successfully
            data.put("totalOrders",totalCounts); // Total Orders
            return ResponseGenerator.generateSuccessResponse(data ,CustMessageResponse.SUCCESS);
        }  catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(CustMessageResponse.SOMETHING_WENT_WRONG);
        }
    }
}
