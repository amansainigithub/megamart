package com.coder.springjwt.controllers.customerPanelControllers.orderController;

import com.coder.springjwt.constants.customerPanelConstants.customerUrlMappings.CustomerUrlMappings;
import com.coder.springjwt.dtos.customerPanelDtos.returnExchangeDto.ExchangeRequestDto;
import com.coder.springjwt.dtos.customerPanelDtos.returnExchangeDto.ReturnRequestInitiateDto;
import com.coder.springjwt.services.publicService.orderServices.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(CustomerUrlMappings.ORDER_CONTROLLER)
public class OrderController {


    @Autowired
    private OrderService orderService;

    @GetMapping(CustomerUrlMappings.GET_CUSTOMER_ORDERS)
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> getCustomerOrders(@PathVariable(required = true) long id) {
        return this.orderService.getCustomerOrders(id);
    }
    @GetMapping(CustomerUrlMappings.GET_MY_ORDERS_DELIVERED)
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> getMyOrdersDelivered(@PathVariable(required = true) long id) {
        return this.orderService.getMyOrdersDelivered(id);
    }

    @GetMapping(CustomerUrlMappings.GET_CUSTOMER_ORDERS_BY_ID) // Order Details Particular Order
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> getCustomerOrdersById(@PathVariable(required = true) long id) {
        return this.orderService.getCustomerOrdersById(id);
    }


    @PostMapping(CustomerUrlMappings.ORDER_RETURN_REQUEST_INITIATE) // Order Details Particular Order
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> orderReturnRequestInitiate(@RequestBody ReturnRequestInitiateDto returnRequestInitiateDto) {
        return this.orderService.orderReturnRequestInitiate(returnRequestInitiateDto);
    }

    @PostMapping(CustomerUrlMappings.ORDER_EXCHANGE_REQUEST_INITIATE) // Order Details Particular Order
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> orderExchangeRequestInitiate(@RequestBody ExchangeRequestDto exchangeRequestDto) {
        return this.orderService.orderExchangeRequestInitiate(exchangeRequestDto);
    }



}
