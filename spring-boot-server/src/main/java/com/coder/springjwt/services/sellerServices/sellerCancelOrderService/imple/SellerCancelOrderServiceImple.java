package com.coder.springjwt.services.sellerServices.sellerCancelOrderService.imple;

import com.amazonaws.services.cognitoidp.model.UserNotFoundException;
import com.coder.springjwt.constants.customerPanelConstants.messageConstants.CustMessageResponse;
import com.coder.springjwt.constants.sellerConstants.sellerMessageConstants.SellerMessageResponse;
import com.coder.springjwt.dtos.sellerDtos.refundDtos.RefundRequestDto;
import com.coder.springjwt.emuns.DeliveryStatus;
import com.coder.springjwt.emuns.PaymentModeStatus;
import com.coder.springjwt.emuns.RefundStatus;
import com.coder.springjwt.exception.customerPanelException.DataNotFoundException;
import com.coder.springjwt.helpers.userHelper.UserHelper;
import com.coder.springjwt.models.User;
import com.coder.springjwt.models.customerPanelModels.CustomerOrderItems;
import com.coder.springjwt.repository.UserRepository;
import com.coder.springjwt.repository.customerPanelRepositories.orderItemsRepository.OrderItemsRepository;
import com.coder.springjwt.services.PaymentsServices.razorpay.imple.RazorpayServiceImple;
import com.coder.springjwt.services.deliveryServices.shipRocketServices.ShipRocketService;
import com.coder.springjwt.services.sellerServices.sellerCancelOrderService.SellerCancelOrderService;
import com.coder.springjwt.util.ResponseGenerator;
import com.razorpay.Refund;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class SellerCancelOrderServiceImple implements SellerCancelOrderService {

    @Autowired
    private OrderItemsRepository orderItemsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RazorpayServiceImple razorpayServiceImple;


    @Autowired
    private ShipRocketService shipRocketService;

    @Override
    public ResponseEntity<?> sellerCancelOrdersFetch(Integer page, Integer size) {
        log.info("<--  sellerCancelOrdersFetch Flying  -->");

            try {
                String currentUser = UserHelper.getOnlyCurrentUser();
                this.userRepository.findByUsername(currentUser)
                        .orElseThrow(() -> new UserNotFoundException(CustMessageResponse.USERNAME_NOT_FOUND));

                Pageable pageable = PageRequest.of(page, size , Sort.by(Sort.Direction.DESC , "orderDateTime"));

                Page<CustomerOrderItems> allCancelledOrders = this.orderItemsRepository.findAllByDeliveryStatus(DeliveryStatus.CANCELLED.toString(),pageable);


                return ResponseGenerator.generateSuccessResponse(allCancelledOrders, CustMessageResponse.SUCCESS);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseGenerator.generateBadRequestResponse(CustMessageResponse.SOMETHING_WENT_WRONG);
            }

    }

    @Override
    public ResponseEntity<?> sellerOrderRefundRequest(RefundRequestDto refundRequestDto) {
        log.info("<--  sellerOrderRefundRequest Flying  -->");
        try {
            CustomerOrderItems data = this.orderItemsRepository.findByIdAndUserIdAndDeliveryStatus(
                                                                refundRequestDto.getId(),
                                                                refundRequestDto.getUserId(),
                                                                DeliveryStatus.CANCELLED.toString());

            if(data == null)
            {
                throw new DataNotFoundException(SellerMessageResponse.DATA_NOT_FOUND);
            }else{
                Refund refund = this.razorpayServiceImple.initiateRefund(data.getCustomerOrders().getPaymentId()
                        , Double.parseDouble(data.getRazorpayFinalAmt()));
                log.info("Order Refund Payment Initiated Success... ");

                data.setRefundId(refund.get("id"));
                data.setRefundStatus(RefundStatus.REFUND_COMPLETED.toString());
                data.setRefundProcessedDateTime(LocalDateTime.now().toString());
                data.setRefundResponse(refund.toString());

                this.orderItemsRepository.save(data);
                return ResponseGenerator.generateSuccessResponse(SellerMessageResponse.DATA_UPDATE_SUCCESS,
                        CustMessageResponse.SUCCESS);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(CustMessageResponse.SOMETHING_WENT_WRONG);
        }
    }

    @Override
    public ResponseEntity<?> sellerCancelOrders(long id) {

        log.info("<--  seller-Cancel-Orders Flying  -->");
        try {

            String currentUser = UserHelper.getOnlyCurrentUser();
            User user = this.userRepository.findByUsername(currentUser)
                    .orElseThrow(() -> new UserNotFoundException(CustMessageResponse.USERNAME_NOT_FOUND));


            CustomerOrderItems cancelItems = this.orderItemsRepository.findById(id)
                                             .orElseThrow(()-> new DataNotFoundException(SellerMessageResponse.DATA_NOT_FOUND));

            if (cancelItems == null)
            {
                throw new RuntimeException("Unable to cancel order. Order details not found.");
            }

            if(cancelItems.getSrOrderId() == 0)
            {
                //Set Cancel Order Properties
                cancelItems.setDeliveryStatus(DeliveryStatus.CANCELLED.toString());
                cancelItems.setCancelReason("CANCEL BY SELLER");
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
                    cancelItems.setCancelReason("CANCEL BY SELLER");
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


}
