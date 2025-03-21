package com.coder.springjwt.controllers.customerPanelControllers.orderController;

import com.coder.springjwt.constants.customerPanelConstants.customerUrlMappings.CustomerUrlMappings;
import com.coder.springjwt.services.orderServices.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(CustomerUrlMappings.ORDER_CONTROLLER)
public class OrderController {


    @Autowired
    private OrderService orderService;

    @PostMapping(CustomerUrlMappings.GET_CUSTOMER_ORDERS)
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> getCustomerOrders(@PathVariable(required = true) long id) {
        return this.orderService.getCustomerOrders(id);
    }


}
