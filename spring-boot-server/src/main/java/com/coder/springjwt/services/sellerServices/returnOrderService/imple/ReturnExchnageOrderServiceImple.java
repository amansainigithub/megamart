package com.coder.springjwt.services.sellerServices.returnOrderService.imple;

import com.coder.springjwt.constants.customerPanelConstants.messageConstants.CustMessageResponse;
import com.coder.springjwt.constants.sellerConstants.sellerMessageConstants.SellerMessageResponse;
import com.coder.springjwt.emuns.RefundStatus;
import com.coder.springjwt.exception.customerPanelException.DataNotFoundException;
import com.coder.springjwt.models.customerPanelModels.CustomerOrderItems;
import com.coder.springjwt.models.customerPanelModels.CustomerReturnExchangeOrders;
import com.coder.springjwt.repository.customerPanelRepositories.orderItemsRepository.OrderItemsRepository;
import com.coder.springjwt.repository.customerPanelRepositories.ordersRepository.OrderRepository;
import com.coder.springjwt.repository.customerPanelRepositories.returnExchangeRepository.ReturnExchangeRepository;
import com.coder.springjwt.services.PaymentsServices.razorpay.imple.RazorpayServiceImple;
import com.coder.springjwt.services.sellerServices.returnOrderService.ReturnExchnageOrderService;
import com.coder.springjwt.util.ResponseGenerator;
import com.razorpay.Refund;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ReturnExchnageOrderServiceImple implements ReturnExchnageOrderService {


    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ReturnExchangeRepository returnExchangeRepository;

    @Autowired
    private OrderItemsRepository orderItemsRepository;
    @Autowired
    private RazorpayServiceImple razorpayServiceImple;




    @Override
    public ResponseEntity<?> exchangeDeliveryStatus(Long id,String exchangeDeliveryStatus) {
        log.info("<=== changeReturnDeliveryStatus Flying ===>");
        try {
            CustomerReturnExchangeOrders returnExchangeOrders = this.returnExchangeRepository.findById(id)
                    .orElseThrow(() -> new DataNotFoundException(SellerMessageResponse.DATA_NOT_FOUND));

            returnExchangeOrders.setExchangeDeliveryStatus(exchangeDeliveryStatus);

            this.returnExchangeRepository.save(returnExchangeOrders);

            return ResponseGenerator.generateSuccessResponse(SellerMessageResponse.DATA_UPDATE_SUCCESS,CustMessageResponse.SUCCESS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            log.error("Failed To fetch");
            return ResponseGenerator.generateBadRequestResponse(e.getMessage() , SellerMessageResponse.FAILED);
        }
    }

    @Override
    public ResponseEntity<?> exchangePickupDateTime(Long id, String pickupDateTime) {
        log.info("<=== exchangePickupDateTime Flying ===>");
        try {
            System.out.println("changeReturnPickUpDateTime ::  " + pickupDateTime );
            System.out.println("ID ::  " + id );

            CustomerReturnExchangeOrders excPickUpDateTime = this.returnExchangeRepository.findById(id)
                    .orElseThrow(() -> new DataNotFoundException(SellerMessageResponse.DATA_NOT_FOUND));

            excPickUpDateTime.setExchangeDeliveryPickUpDateTime(pickupDateTime);
            this.returnExchangeRepository.save(excPickUpDateTime);

            return ResponseGenerator.generateSuccessResponse(SellerMessageResponse.DATA_UPDATE_SUCCESS,CustMessageResponse.SUCCESS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            log.error("Failed To fetch");
            return ResponseGenerator.generateBadRequestResponse(e.getMessage() , SellerMessageResponse.FAILED);
        }
    }
}
