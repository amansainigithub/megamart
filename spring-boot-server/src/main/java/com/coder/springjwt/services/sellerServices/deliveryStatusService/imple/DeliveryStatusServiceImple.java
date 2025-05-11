package com.coder.springjwt.services.sellerServices.deliveryStatusService.imple;

import com.coder.springjwt.constants.customerPanelConstants.messageConstants.CustMessageResponse;
import com.coder.springjwt.constants.sellerConstants.sellerEmailConstants.SellerEmailConstants;
import com.coder.springjwt.constants.sellerConstants.sellerMessageConstants.SellerMessageResponse;
import com.coder.springjwt.dtos.sellerDtos.deliveryStatusDto.DeliveryStatusDto;
import com.coder.springjwt.dtos.sellerDtos.deliveryStatusDto.DeliveryStatusUpdateDto;
import com.coder.springjwt.emuns.DeliveryStatus;
import com.coder.springjwt.exception.customerPanelException.DataNotFoundException;
import com.coder.springjwt.models.customerPanelModels.CustomerOrderItems;
import com.coder.springjwt.models.sellerModels.sellerProductModels.ProductVariants;
import com.coder.springjwt.models.sellerModels.sellerProductModels.SellerProduct;
import com.coder.springjwt.payload.emailPayloads.EmailHtmlPayload;
import com.coder.springjwt.repository.customerPanelRepositories.orderItemsRepository.OrderItemsRepository;
import com.coder.springjwt.repository.sellerRepository.sellerStoreRepository.ProductVariantsRepository;
import com.coder.springjwt.repository.sellerRepository.sellerStoreRepository.SellerProductRepository;
import com.coder.springjwt.services.deliveryServices.shipRocketServices.ShipRocketService;
import com.coder.springjwt.services.deliveryServices.shipRocketServices.imple.ShipRocketServiceImple;
import com.coder.springjwt.services.emailServices.EmailService.EmailService;
import com.coder.springjwt.services.sellerServices.deliveryStatusService.DeliveryStatusService;
import com.coder.springjwt.util.ResponseGenerator;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class DeliveryStatusServiceImple implements DeliveryStatusService {


    @Autowired
    private OrderItemsRepository orderItemsRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ShipRocketService shipRocketServiceImple;

    @Autowired
    private SellerProductRepository sellerProductRepository;

    @Autowired
    private ProductVariantsRepository productVariantsRepository;

    @Override
    public ResponseEntity<?> updatePendingDeliveryStatus(DeliveryStatusDto deliveryStatusDto) {
        try {
                log.info("===> updateDeliveryStatusOrderItems Flying");
                log.info("ORDER ITEMS :: " + deliveryStatusDto.getOrderItemId());

            CustomerOrderItems customerOrderItems = this.orderItemsRepository
                                        .findById(Long.valueOf(deliveryStatusDto.getOrderItemId()))
                                        .orElseThrow(() -> new RuntimeException(SellerMessageResponse.ORDER_ITEM_NOT_FOUND));

            ProductVariants productVariant = productVariantsRepository.findByLabelAndProductId(customerOrderItems.getProductSize(),
                                             customerOrderItems.getProductId());

            ResponseEntity<String> response = shipRocketServiceImple.createOrder(customerOrderItems, productVariant, deliveryStatusDto);
            System.out.println(response.toString());
            if (response == null)
            {
                throw new RuntimeException(SellerMessageResponse.SOMETHING_WENT_WRONG);
            }

            if(response != null)
            {
                //Set Ship Rocket Response To Customer Order Item Object or POJO and save Data
                JSONObject jsonObject = new JSONObject(response.getBody());
                customerOrderItems.setSrOrderId(jsonObject.getInt("order_id"));
                customerOrderItems.setSrChannelOrderId(jsonObject.getString("channel_order_id"));
                customerOrderItems.setSrShipmentId(jsonObject.getInt("shipment_id"));
                customerOrderItems.setSrStatus(jsonObject.getString("status"));
                customerOrderItems.setSrStatusCode(jsonObject.getInt("status_code"));
                customerOrderItems.setSrAwbCode(jsonObject.getString("awb_code"));
                customerOrderItems.setSrCourierName(jsonObject.getString("courier_name"));
                customerOrderItems.setSrRequest("REQUEST ABHI BAKI HAIN");
                customerOrderItems.setSrResponse(response.getBody().toString());

                orderItemsRepository.save(customerOrderItems);

            }



//            //Email Send
//            EmailHtmlPayload emailHtmlPayload = new EmailHtmlPayload();
//            emailHtmlPayload.setSubject("Your Order is On Its Way! Shoppers");
//            emailHtmlPayload.setStatus("SUCCESS");
//            emailHtmlPayload.setRole("CUSTOMER");
//            emailHtmlPayload.setAreaMode("DELIVERY STATUS");
//            emailHtmlPayload.setRecipient("amansaini1407@gmail.com");
//
//            Map<String,String> emailData = new HashMap<>();
//            emailData.put("customerName",customerOrderItems.getCustomerName());
//            emailData.put("orderNumber",customerOrderItems.getCustomOrderNumber());
//            emailData.put("productName",customerOrderItems.getProductName());
//            emailData.put("quantity",customerOrderItems.getQuantity());
//            emailData.put("trackingNumber",customerOrderItems.getOrderTrackingId());
//            emailData.put("productPrice",customerOrderItems.getProductPrice());
//            emailData.put("estimatedDeliveryDate",customerOrderItems.getOrderDateTime());
//            emailData.put("courierPartner",customerOrderItems.getCourierName());
//            emailData.put("trackingNumber",customerOrderItems.getOrderTrackingId());
//            emailData.put("companyName" , "Shoppers");
//            String shippingTemplate = SellerEmailConstants.generateOrderShippedEmail(emailData);
//            emailHtmlPayload.setHtmlContent(shippingTemplate);
//
//            //emailService.sendHtmlMail(emailHtmlPayload);
//            log.info("Email Sent Success| Shipping");
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
            return ResponseGenerator.generateSuccessResponse(deliveryStatusDto , CustMessageResponse.SUCCESS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(e.getMessage() , SellerMessageResponse.FAILED);
        }
    }

    @Override
    public ResponseEntity<?> awbNumberMapping(Long id) {
        log.info("awbNumberMapping Flying...");
        try {
            CustomerOrderItems customerOrderItems = this.orderItemsRepository.findById(id)
                                                    .orElseThrow(() -> new DataNotFoundException(SellerMessageResponse.DATA_NOT_FOUND));
            ResponseEntity<String> response = shipRocketServiceImple.orderDetails(customerOrderItems.getSrOrderId());
            JSONObject jsonObject = new JSONObject(response.getBody());
            JSONObject data = jsonObject.getJSONObject("data");
            JSONObject shipments = data.getJSONObject("shipments");

            String awb = shipments.optString("awb", "null");
            String courier = shipments.optString("courier", "null");
            String etd = shipments.optString("etd", "null");
            String status = shipments.optString("status", "null");

            if(awb != "null")
            {
                System.out.println("AWB Number: " + awb);
                System.out.println("Courier: " + courier);
                System.out.println("Delivered Date: " + etd);
                System.out.println("Status: " + status);

                customerOrderItems.setSrAwbCode(awb);
                customerOrderItems.setSrCourierName(courier);
                customerOrderItems.setSrEtd(etd);
                customerOrderItems.setSrStatus(status);

                //Set Delivery Status
                customerOrderItems.setDeliveryStatus(DeliveryStatus.SHIPPED.toString());


                //Get Delivery Tracker URL
                ResponseEntity<String> tracking = this.shipRocketServiceImple.getTrackingUrl(awb);
                JSONObject trackerBody = new JSONObject(tracking.getBody());
                JSONObject trackingData = trackerBody.getJSONObject("tracking_data");
                String track_url = trackingData.getString("track_url");
                customerOrderItems.setSrTrackerUrl(track_url);

                //save Customer Order Items
                orderItemsRepository.save(customerOrderItems);




                return ResponseGenerator.generateSuccessResponse(SellerMessageResponse.AWB_MAPPED_SUCCESS, CustMessageResponse.SUCCESS);
            }
            else {
                throw new RuntimeException(SellerMessageResponse.COULD_NOT_GENERATE_AWB);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(e.getMessage() , SellerMessageResponse.FAILED);

        }
    }





}
