package com.coder.springjwt.services.publicService.orderCalcelService.imple;

import com.amazonaws.services.cognitoidp.model.UserNotFoundException;
import com.coder.springjwt.constants.customerPanelConstants.messageConstants.CustMessageResponse;
import com.coder.springjwt.dtos.customerPanelDtos.cancelOrderDtos.CancelOrderDto;
import com.coder.springjwt.emuns.DeliveryStatus;
import com.coder.springjwt.emuns.PaymentModeStatus;
import com.coder.springjwt.emuns.RefundStatus;
import com.coder.springjwt.helpers.userHelper.UserHelper;
import com.coder.springjwt.models.User;
import com.coder.springjwt.models.customerPanelModels.CustomerOrderItems;
import com.coder.springjwt.repository.UserRepository;
import com.coder.springjwt.repository.customerPanelRepositories.orderItemsRepository.OrderItemsRepository;
import com.coder.springjwt.repository.customerPanelRepositories.ordersRepository.OrderRepository;
import com.coder.springjwt.services.PaymentsServices.razorpay.imple.RazorpayServiceImple;
import com.coder.springjwt.services.deliveryServices.shipRocketServices.ShipRocketService;
import com.coder.springjwt.services.publicService.orderCalcelService.OrderCancelService;
import com.coder.springjwt.util.ResponseGenerator;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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

    @Autowired
    private ShipRocketService shipRocketService;



    @Override
    public ResponseEntity<?> orderCancelService(CancelOrderDto cancelOrderDto) {
        log.info("<--  orderCancelService Flying  -->");
        try {

            String currentUser = UserHelper.getOnlyCurrentUser();
            User user = this.userRepository.findByUsername(currentUser)
                       .orElseThrow(() -> new UserNotFoundException(CustMessageResponse.USERNAME_NOT_FOUND));


            CustomerOrderItems cancelItems = this.orderItemsRepository.findByCancelOrder(
                                                cancelOrderDto.getId(),
                                                String.valueOf(user.getId()),
                                                cancelOrderDto.getCustomerOrderNumber(),
                                                DeliveryStatus.PENDING.toString(),
                                                cancelOrderDto.getRazorpayOrderId());

            if (cancelItems == null)
            {
                throw new RuntimeException("Unable to cancel order. Order details not found.");
            }

            if(cancelItems.getSrOrderId() == 0)
            {
                //Set Cancel Order Properties
                cancelItems.setDeliveryStatus(DeliveryStatus.CANCELLED.toString());
                cancelItems.setCancelReason(cancelOrderDto.getCancelReason());
                cancelItems.setRefundRequestDateTime(LocalDateTime.now().toString());

                if(cancelItems.getPaymentMode().equals(PaymentModeStatus.ONLINE.toString()) )
                {
                    cancelItems.setRefundStatus(RefundStatus.REFUND_PENDING.toString());
                }
                else if (cancelItems.getPaymentMode().equals(PaymentModeStatus.COD.toString()))
                {
                    cancelItems.setRefundStatus(RefundStatus.REFUND_COMPLETED.toString());
                }

                //Save To Cancel Order Items To DB
                this.orderItemsRepository.save(cancelItems);
                log.info("Order Cancel | ShipRocket Order Current Not Generated | Success");
                return ResponseGenerator.generateSuccessResponse("Order Cancel Success" ,CustMessageResponse.SUCCESS);
            }
            else if(cancelItems.getSrOrderId() != 0) {

                ResponseEntity<String> response = this.shipRocketService.cancelOrders(List.of(cancelItems.getSrOrderId()));

                if (response.getStatusCode() == HttpStatus.OK) {

                    //Set Cancel Order Properties
                    cancelItems.setDeliveryStatus(DeliveryStatus.CANCELLED.toString());
                    cancelItems.setCancelReason(cancelOrderDto.getCancelReason());
                    cancelItems.setRefundRequestDateTime(LocalDateTime.now().toString());

                    if(cancelItems.getPaymentMode().equals(PaymentModeStatus.ONLINE.toString()) )
                    {
                        cancelItems.setRefundStatus(RefundStatus.REFUND_PENDING.toString());
                        cancelItems.setSrStatus(DeliveryStatus.CANCELLED.toString());
                    }
                    else if (cancelItems.getPaymentMode().equals(PaymentModeStatus.COD.toString()))
                    {
                        cancelItems.setRefundStatus(RefundStatus.REFUND_COMPLETED.toString());
                    }

                    //Save To Cancel Order Items To DB
                    this.orderItemsRepository.save(cancelItems);
                    log.info("Order Cancel | ShipRocket Order Cancel | Success");
                    return ResponseGenerator.generateSuccessResponse("Order Cancel Success" ,CustMessageResponse.SUCCESS);
            }
            }
            else{
                throw new RuntimeException("Order Cancel Failed | OR Error in ShipRocket API");
            }

        }  catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(CustMessageResponse.SOMETHING_WENT_WRONG);
        }
        return ResponseGenerator.generateBadRequestResponse(CustMessageResponse.SOMETHING_WENT_WRONG);
    }


    public void codPaymentRefundProcess(CustomerOrderItems customerOrderItems){
        System.out.println("COD PAYMENT Process Starting....");
    }

}
