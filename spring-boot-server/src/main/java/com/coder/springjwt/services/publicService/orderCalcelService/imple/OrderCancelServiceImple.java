package com.coder.springjwt.services.publicService.orderCalcelService.imple;

import com.amazonaws.services.cognitoidp.model.UserNotFoundException;
import com.coder.springjwt.constants.customerPanelConstants.messageConstants.CustMessageResponse;
import com.coder.springjwt.dtos.customerPanelDtos.cancelOrderDtos.CancelOrderDto;
import com.coder.springjwt.dtos.customerPanelDtos.customerOrderDtos.CustomerOrderItemDTO;
import com.coder.springjwt.emuns.DeliveryStatus;
import com.coder.springjwt.emuns.PaymentModeStatus;
import com.coder.springjwt.emuns.RefundStatus;
import com.coder.springjwt.helpers.userHelper.UserHelper;
import com.coder.springjwt.models.User;
import com.coder.springjwt.models.customerPanelModels.CustomerOrderItems;
import com.coder.springjwt.models.customerPanelModels.CustomerOrders;
import com.coder.springjwt.repository.UserRepository;
import com.coder.springjwt.repository.customerPanelRepositories.orderItemsRepository.OrderItemsRepository;
import com.coder.springjwt.repository.customerPanelRepositories.ordersRepository.OrderRepository;
import com.coder.springjwt.services.PaymentsServices.razorpay.imple.RazorpayServiceImple;
import com.coder.springjwt.services.publicService.orderCalcelService.OrderCancelService;
import com.coder.springjwt.util.ResponseGenerator;
import com.razorpay.RazorpayException;
import com.razorpay.Refund;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class OrderCancelServiceImple implements OrderCancelService {

    @Autowired
    private OrderItemsRepository orderItemsRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RazorpayServiceImple razorpayServiceImple;



    @Override
    public ResponseEntity<?> orderCancelService(CancelOrderDto cancelOrderDto) {
        log.info("<--  orderCancelService Flying  -->");
        try {
            System.out.println( cancelOrderDto.toString());

            String currentUser = UserHelper.getOnlyCurrentUser();
            User user = this.userRepository.findByUsername(currentUser)
                       .orElseThrow(() -> new UserNotFoundException(CustMessageResponse.USERNAME_NOT_FOUND));


            CustomerOrderItems cancelItems = this.orderItemsRepository.findByCancelOrder(
                                                cancelOrderDto.getId(),
                                                String.valueOf(user.getId()),
                                                cancelOrderDto.getCustomerOrderNumber(),
                                                cancelOrderDto.getRazorpayOrderId());

            if (cancelItems == null)
            {
                throw new RuntimeException("Unable to cancel order. Order details not found.");
            }

            if(cancelItems.getPaymentMode().equals(PaymentModeStatus.ONLINE.toString()))
            {
                this.onlinePaymentRefundProcess(cancelItems);
            }
            else if (cancelItems.getPaymentMode().equals(PaymentModeStatus.COD.toString()))
            {
                this.codPaymentRefundProcess(cancelItems);
            }

            CustomerOrderItemDTO customerOrderItemDTO = modelMapper.map(cancelItems, CustomerOrderItemDTO.class);
            System.out.println(customerOrderItemDTO);
            return ResponseGenerator.generateSuccessResponse(customerOrderItemDTO ,CustMessageResponse.SUCCESS);
        }  catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(CustMessageResponse.SOMETHING_WENT_WRONG);
        }
    }



    public void onlinePaymentRefundProcess(CustomerOrderItems customerOrderItems){
        System.out.println("ONLINE PAYMENT Process Starting....");
        customerOrderItems.setDeliveryStatus(DeliveryStatus.CANCELLED.toString());
        customerOrderItems.setRefundStatus(RefundStatus.INITIATED.toString());
        customerOrderItems.setRefundRequestDateTime(LocalDateTime.now().toString());
        customerOrderItems.setCancelReason("Or Kuch Order Karna Hain...");

        //Get Payment Id
        CustomerOrders customerOrders = this.orderRepository.findById(customerOrderItems.getCustomerOrders().getId())
                                        .orElseThrow(() -> new RuntimeException(CustMessageResponse.DATA_NOT_FOUND));

        try {
            Refund refund = razorpayServiceImple.initiateRefund( customerOrders.getPaymentId(),
                                    Double.parseDouble(customerOrderItems.getTotalPrice()) );


            JSONObject jsonObject = new JSONObject(refund.toString());
            String refundId = jsonObject.getString("id");
            String status = jsonObject.getString("status");

            System.out.println("Refund Id:: " + refundId);
            System.out.println("status:: " + status);

            //Set Refund ID
            customerOrderItems.setRefundId(refundId);

            //Set Payment Status
            if(status.equals("created")){
                customerOrderItems.setRefundStatus(RefundStatus.INITIATED.toString());
            } else if (status.equals("pending")) {
                customerOrderItems.setRefundStatus(RefundStatus.BANK_PROCESSED.toString());
            } else if (status.equals("processed")) {
                customerOrderItems.setRefundStatus(RefundStatus.SUCCESS.toString());
            }else if (status.equals("failed")) {
                customerOrderItems.setRefundStatus(RefundStatus.FAILED.toString());
            }

            //Refund Processed Date
            customerOrderItems.setRefundProcessedDateTime(LocalDateTime.now().toString());

            this.orderItemsRepository.save(customerOrderItems);
        } catch (RazorpayException e) {
            throw new RuntimeException(e);
        }


    }


    public void codPaymentRefundProcess(CustomerOrderItems customerOrderItems){
        System.out.println("COD PAYMENT Process Starting....");
    }


}
