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
    public ResponseEntity<?> returnPaymentInitiated(Long id) {
        log.info("<=== returnPaymentInitiated Flying ===>");
        try {

            CustomerReturnExchangeOrders returnExchangeOrders = this.returnExchangeRepository.findById(id)
                    .orElseThrow(() -> new DataNotFoundException(SellerMessageResponse.DATA_NOT_FOUND));

            if(returnExchangeOrders == null)
            {
                throw new DataNotFoundException(SellerMessageResponse.DATA_NOT_FOUND);
            }else{

                CustomerOrderItems orderItems = this.orderItemsRepository.findById(returnExchangeOrders.getOrderItemId())
                                                        .orElseThrow(() -> new DataNotFoundException(SellerMessageResponse.DATA_NOT_FOUND));

                Refund refund = this.razorpayServiceImple.initiateRefund(orderItems.getCustomerOrders().getPaymentId()
                                    , Double.parseDouble(orderItems.getRazorpayFinalAmt()));

                if(refund == null)
                {
                    throw new RuntimeException("Error in Refund Amount Razorpay");
                }

                log.info("Order Refund Payment Initiated Success... ");

                returnExchangeOrders.setReturnRefundId(refund.get("id"));
                returnExchangeOrders.setReturnRefundStatus(RefundStatus.SUCCESS.toString());
                this.orderItemsRepository.save(orderItems);

                return ResponseGenerator.generateSuccessResponse(SellerMessageResponse.DATA_UPDATE_SUCCESS,
                        CustMessageResponse.SUCCESS);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            log.error("Failed To fetch");
            return ResponseGenerator.generateBadRequestResponse(e.getMessage() , SellerMessageResponse.FAILED);
        }
    }

    @Override
    public ResponseEntity<?> changeReturnDeliveryStatus(Long id, String returnDeliveryStatus) {
        log.info("<=== changeReturnDeliveryStatus Flying ===>");
        try {
            CustomerReturnExchangeOrders returnExchangeOrders = this.returnExchangeRepository.findById(id)
                                        .orElseThrow(() -> new DataNotFoundException(SellerMessageResponse.DATA_NOT_FOUND));

            returnExchangeOrders.setReturnDeliveryStatus(returnDeliveryStatus);

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
    public ResponseEntity<?> changeReturnPickUpDateTime(Long id, String pickupDateTime) {
        log.info("<=== changeReturnPickUpDateTime Flying ===>");
        try {
            System.out.println("changeReturnPickUpDateTime ::  " + pickupDateTime );
            System.out.println("ID ::  " + id );

            CustomerReturnExchangeOrders pickUpDateTime = this.returnExchangeRepository.findById(id)
                    .orElseThrow(() -> new DataNotFoundException(SellerMessageResponse.DATA_NOT_FOUND));

            pickUpDateTime.setReturnDeliveryPickUpDateTime(pickupDateTime);
            this.returnExchangeRepository.save(pickUpDateTime);

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
