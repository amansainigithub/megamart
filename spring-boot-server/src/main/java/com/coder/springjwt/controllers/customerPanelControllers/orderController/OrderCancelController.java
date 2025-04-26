package com.coder.springjwt.controllers.customerPanelControllers.orderController;

import com.coder.springjwt.constants.customerPanelConstants.customerUrlMappings.CustomerUrlMappings;
import com.coder.springjwt.dtos.customerPanelDtos.cancelOrderDtos.CancelOrderDto;
import com.coder.springjwt.services.publicService.orderCalcelService.OrderCancelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(CustomerUrlMappings.ORDER_CANCEL_CONTROLLER)
public class OrderCancelController {


    @Autowired
    private OrderCancelService orderCancelService;

    @PostMapping(CustomerUrlMappings.ORDER_CANCEL)
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> orderCancel(@RequestBody CancelOrderDto cancelOrderDto) {
        return this.orderCancelService.orderCancelService(cancelOrderDto);
    }


//    @GetMapping(CustomerUrlMappings.GET_CANCEL_ORDERS)
//    @PreAuthorize("hasRole('CUSTOMER')")
//    public ResponseEntity<?> getCancelOrders(@PathVariable(required = true) long id) {
//        return this.orderCancelService.getCancelOrdersService(id);
//    }
}
