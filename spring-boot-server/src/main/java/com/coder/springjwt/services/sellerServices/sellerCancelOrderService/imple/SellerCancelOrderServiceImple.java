package com.coder.springjwt.services.sellerServices.sellerCancelOrderService.imple;

import com.amazonaws.services.cognitoidp.model.UserNotFoundException;
import com.coder.springjwt.constants.customerPanelConstants.messageConstants.CustMessageResponse;
import com.coder.springjwt.constants.sellerConstants.sellerMessageConstants.SellerMessageResponse;
import com.coder.springjwt.dtos.sellerDtos.refundDtos.RefundRequestDto;
import com.coder.springjwt.emuns.DeliveryStatus;
import com.coder.springjwt.emuns.RefundStatus;
import com.coder.springjwt.exception.customerPanelException.DataNotFoundException;
import com.coder.springjwt.helpers.userHelper.UserHelper;
import com.coder.springjwt.models.customerPanelModels.CustomerOrderItems;
import com.coder.springjwt.repository.UserRepository;
import com.coder.springjwt.repository.customerPanelRepositories.orderItemsRepository.OrderItemsRepository;
import com.coder.springjwt.services.PaymentsServices.razorpay.imple.RazorpayServiceImple;
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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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


}
