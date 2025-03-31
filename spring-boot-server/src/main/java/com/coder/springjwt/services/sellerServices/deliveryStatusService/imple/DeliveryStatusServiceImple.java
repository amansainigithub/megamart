package com.coder.springjwt.services.sellerServices.deliveryStatusService.imple;

import com.coder.springjwt.constants.customerPanelConstants.messageConstants.CustMessageResponse;
import com.coder.springjwt.constants.sellerConstants.sellerEmailConstants.SellerEmailConstants;
import com.coder.springjwt.constants.sellerConstants.sellerMessageConstants.SellerMessageResponse;
import com.coder.springjwt.dtos.sellerDtos.deliveryStatusDto.DeliveryStatusDto;
import com.coder.springjwt.dtos.sellerDtos.deliveryStatusDto.DeliveryStatusUpdateDto;
import com.coder.springjwt.emuns.DeliveryStatus;
import com.coder.springjwt.models.customerPanelModels.CustomerOrderItems;
import com.coder.springjwt.payload.emailPayloads.EmailHtmlPayload;
import com.coder.springjwt.repository.customerPanelRepositories.orderItemsRepository.OrderItemsRepository;
import com.coder.springjwt.services.emailServices.EmailService.EmailService;
import com.coder.springjwt.services.emailServices.EmailService.imple.EmailServiceImple;
import com.coder.springjwt.services.sellerServices.deliveryStatusService.DeliveryStatusService;
import com.coder.springjwt.util.ResponseGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
public class DeliveryStatusServiceImple implements DeliveryStatusService {


    @Autowired
    private OrderItemsRepository orderItemsRepository;

    @Autowired
    private EmailService emailService;


    @Override
    public ResponseEntity<?> updatePendingDeliveryStatus(DeliveryStatusDto deliveryStatusDto) {
        try {
                log.info("===> updateDeliveryStatusOrderItems Flying");

            CustomerOrderItems customerOrderItems = this.orderItemsRepository
                                        .findById(Long.valueOf(deliveryStatusDto.getOrderItemId()))
                                        .orElseThrow(() -> new RuntimeException(SellerMessageResponse.ORDER_ITEM_NOT_FOUND));

            //Convert Date Form and Add Time with AM and PM
            LocalDateTime localDateTime = LocalDateTime.parse(deliveryStatusDto.getDeliveryDateTime());
            ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of("UTC"))
                    .withZoneSameInstant(ZoneId.of("Asia/Kolkata"));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM, yyyy hh:mm a");
            String formattedDateTime = zonedDateTime.format(formatter);

            //customerOrderItems.setDeliveryStatus(DeliveryStatus.SHIPPED.toString());
            customerOrderItems.setDeliveryStatus(deliveryStatusDto.getDeliveryStatus());

            customerOrderItems.setOrderTrackingId(deliveryStatusDto.getTackerId());
            customerOrderItems.setDeliveryDateTime(formattedDateTime);
            customerOrderItems.setCourierName(deliveryStatusDto.getCourierName());
            this.orderItemsRepository.save(customerOrderItems);

            //Email Send
            EmailHtmlPayload emailHtmlPayload = new EmailHtmlPayload();
            emailHtmlPayload.setSubject("Your Order is On Its Way! Shoppers");
            emailHtmlPayload.setStatus("SUCCESS");
            emailHtmlPayload.setRole("CUSTOMER");
            emailHtmlPayload.setAreaMode("DELIVERY STATUS");
            emailHtmlPayload.setRecipient("amansaini1407@gmail.com");
            String shippingTemplate = SellerEmailConstants.generateOrderShippedEmail(customerOrderItems.getCustomerName(),
                    customerOrderItems.getCustomOrderNumber(), customerOrderItems.getOrderTrackingId(),
                    customerOrderItems.getCourierName(), customerOrderItems.getDeliveryDateTime(), "SHOPPERS", "https:https://www.google.co.in/");
//            String shippingTemplate = SellerEmailConstants.getTestEmail();
            emailHtmlPayload.setHtmlContent(shippingTemplate);

            emailService.sendHtmlMail(emailHtmlPayload);
            log.info("Email Sent Success| Shipping");
            return ResponseGenerator.generateSuccessResponse(CustMessageResponse.DATA_SAVED_SUCCESS , CustMessageResponse.SUCCESS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(e.getMessage() , SellerMessageResponse.FAILED);
        }
    }

    @Override
    public ResponseEntity<?> updateDeliveryStatus(DeliveryStatusUpdateDto deliveryStatusUpdateDto) {
        try {
            log.info("===> updateDeliveryStatus Flying");
            CustomerOrderItems customerOrderItems = this.orderItemsRepository
                    .findById(Long.valueOf(deliveryStatusUpdateDto.getOrderItemId()))
                    .orElseThrow(() -> new RuntimeException(SellerMessageResponse.ORDER_ITEM_NOT_FOUND));

            customerOrderItems.setDeliveryStatus(deliveryStatusUpdateDto.getUpdateDeliveryStatus());
            this.orderItemsRepository.save(customerOrderItems);

            return ResponseGenerator.generateSuccessResponse(CustMessageResponse.DATA_SAVED_SUCCESS , CustMessageResponse.SUCCESS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(e.getMessage() , SellerMessageResponse.FAILED);
        }
    }

    @Override
    public ResponseEntity<?> getDeliveryDetailsById(Long id) {
        try {
            log.info("===> getDeliveryDetailsById Flying");
            CustomerOrderItems customerOrderItems = this.orderItemsRepository.findById(id)
                                                    .orElseThrow(() -> new RuntimeException(
                                                            SellerMessageResponse.ORDER_ITEM_NOT_FOUND));
            DeliveryStatusDto deliveryStatusDto = new DeliveryStatusDto();
            deliveryStatusDto.setTackerId(customerOrderItems.getOrderTrackingId());
            deliveryStatusDto.setDeliveryStatus(customerOrderItems.getDeliveryStatus());
            deliveryStatusDto.setOrderItemId(String.valueOf(customerOrderItems.getId()));
            deliveryStatusDto.setDeliveryDateTime(customerOrderItems.getDeliveryDateTime());

            return ResponseGenerator.generateSuccessResponse(deliveryStatusDto , CustMessageResponse.SUCCESS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(e.getMessage() , SellerMessageResponse.FAILED);
        }
    }




}
