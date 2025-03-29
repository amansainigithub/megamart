package com.coder.springjwt.services.sellerServices.sellerOrdersService.imple;

import com.coder.springjwt.constants.customerPanelConstants.messageConstants.CustMessageResponse;
import com.coder.springjwt.constants.sellerConstants.sellerMessageConstants.SellerMessageResponse;
import com.coder.springjwt.models.customerPanelModels.CustomerOrders;
import com.coder.springjwt.repository.customerPanelRepositories.ordersRepository.OrderRepository;
import com.coder.springjwt.services.sellerServices.sellerOrdersService.SellerOrdersService;
import com.coder.springjwt.util.ResponseGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SellerOrdersServiceImple implements SellerOrdersService {
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public ResponseEntity<?> getOrderBySeller(Integer page, Integer size) {
        try {
            Pageable pageable = PageRequest.of(page, size , Sort.by(Sort.Direction.DESC , "orderDateTime"));
            Page<CustomerOrders> customerOrders = this.orderRepository.getPendingOrderItems("PENDING", pageable);
            return ResponseGenerator.generateSuccessResponse(customerOrders , CustMessageResponse.SUCCESS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            log.error("Failed To fetch");
            return ResponseGenerator.generateBadRequestResponse(e.getMessage() , SellerMessageResponse.FAILED);
        }
    }


}
