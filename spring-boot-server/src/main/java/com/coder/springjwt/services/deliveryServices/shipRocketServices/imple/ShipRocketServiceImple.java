package com.coder.springjwt.services.deliveryServices.shipRocketServices.imple;

import com.coder.springjwt.constants.sellerConstants.sellerMessageConstants.SellerMessageResponse;
import com.coder.springjwt.dtos.sellerDtos.deliveryStatusDto.DeliveryStatusDto;
import com.coder.springjwt.dtos.sellerDtos.shipRocketDto.CreateOrderRequestSRDto;
import com.coder.springjwt.dtos.sellerDtos.shipRocketDto.OrderItem;
import com.coder.springjwt.emuns.DeliveryStatus;
import com.coder.springjwt.models.customerPanelModels.CustomerOrderItems;
import com.coder.springjwt.models.sellerModels.props.Api_Props;
import com.coder.springjwt.models.sellerModels.sellerProductModels.ProductVariants;
import com.coder.springjwt.repository.customerPanelRepositories.orderItemsRepository.OrderItemsRepository;
import com.coder.springjwt.repository.sellerRepository.apiProps.ApiPropsRepository;
import com.coder.springjwt.services.deliveryServices.shipRocketServices.ShipRocketService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ShipRocketServiceImple implements ShipRocketService {
    private final String CREATE_ORDER = "https://apiv2.shiprocket.in/v1/external/orders/create/adhoc";
    private final String SHIPROCKET_CANCLE_ORDER_URL = "https://apiv2.shiprocket.in/v1/external/orders/cancel";
    private String ORDER_DETAILS = "https://apiv2.shiprocket.in/v1/external/orders/show/";
    private String TRACKING_URL = "https://apiv2.shiprocket.in/v1/external/courier/track/awb/";
    private String TRACK_SHIPMENTS =  "https://apiv2.shiprocket.in/v1/external/courier/track/awb/";
    private String LABEL_GENERATE_URL =  "https://apiv2.shiprocket.in/v1/external/courier/generate/label";

    @Autowired
    private ApiPropsRepository apiPropsRepository;
    @Autowired
    private OrderItemsRepository orderItemsRepository;


    @Override
    public  ResponseEntity<String> createOrder(CustomerOrderItems customerOrderItems , ProductVariants productVariant , DeliveryStatusDto deliveryStatusDto) {

       try {

           Api_Props props = this.apiPropsRepository.findByProvider("SHIP_ROCKET_SHIPPING")
                   .orElseThrow(() -> new RuntimeException(SellerMessageResponse.SHIP_ROCKET_PROPS_NOT_FOUND));

           //Order Date Creation
           LocalDateTime now = LocalDateTime.now();
           DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
           String orderDate = now.format(formatter);

           CreateOrderRequestSRDto cosp = new CreateOrderRequestSRDto();
           cosp.setOrder_id(customerOrderItems.getOrderIdPerItem());
           cosp.setOrder_date(orderDate);
           cosp.setPickup_location("Home");
           cosp.setComment("NO COMMENT");
           cosp.setBilling_customer_name(customerOrderItems.getCustomerName());
           cosp.setBilling_last_name(customerOrderItems.getCustomerName());
           cosp.setBilling_address(customerOrderItems.getAddressLine1());
           cosp.setBilling_address_2(customerOrderItems.getAddressLine2());
           cosp.setBilling_city(customerOrderItems.getCity());
           cosp.setBilling_pincode(Integer.valueOf(customerOrderItems.getPostalCode()));
           cosp.setBilling_state(customerOrderItems.getState());
           cosp.setBilling_country(customerOrderItems.getCountry());
           cosp.setBilling_email("Temparory@gmail.com"); //Required NO
           cosp.setBilling_phone(Long.parseLong(customerOrderItems.getMobileNumber()));
           cosp.setShipping_is_billing(Boolean.TRUE);

//        OrderItems Object Create
           OrderItem orderItem = new OrderItem();
           orderItem.setName(customerOrderItems.getProductName());
           orderItem.setSku(productVariant.getSkuId());
           orderItem.setUnits(Integer.parseInt(customerOrderItems.getQuantity()));
           orderItem.setSelling_price(Double.parseDouble(productVariant.getPriceMinusGst()));
           orderItem.setDiscount("0");
           orderItem.setTax("05");
           orderItem.setHsn(Integer.parseInt(productVariant.getSellerProduct().getHsn()));
           cosp.setOrder_items(Arrays.asList(orderItem));


           cosp.setPayment_method("Prepaid");
           cosp.setShipping_charges(0);
           cosp.setGiftwrap_charges(0);
           cosp.setTransaction_charges(0);
//         cosp.setTotal_discount(Double.parseDouble(customerOrderItems.getProductDiscount().replace("%","")));
           cosp.setTotal_discount(0);
           cosp.setSub_total( Integer.valueOf( customerOrderItems.getProductPrice()) * Integer.valueOf( customerOrderItems.getQuantity()));
           cosp.setLength(Integer.parseInt(deliveryStatusDto.getProductLength()));
           cosp.setBreadth(Integer.parseInt(deliveryStatusDto.getProductBreadth()));
           cosp.setHeight(Integer.parseInt(deliveryStatusDto.getProductHeight()));
           cosp.setWeight(Double.parseDouble(deliveryStatusDto.getProductWeight()));


           RestTemplate restTemplate = new RestTemplate();

           HttpHeaders headers = new HttpHeaders();
           headers.setContentType(MediaType.APPLICATION_JSON);
           headers.setBearerAuth(props.getToken());
           HttpEntity<CreateOrderRequestSRDto> entity = new HttpEntity<>(cosp, headers);
           ResponseEntity<String> response = restTemplate.exchange( CREATE_ORDER,HttpMethod.POST,entity,String.class );

           return response;
       }
       catch (Exception e)
       {
           e.printStackTrace();
           return null;
       }
    }


    public ResponseEntity<String> orderDetails(int orderId) {
        try {
            Api_Props props = this.apiPropsRepository.findByProvider("SHIP_ROCKET_SHIPPING")
                    .orElseThrow(() -> new RuntimeException(SellerMessageResponse.SHIP_ROCKET_PROPS_NOT_FOUND));

            System.out.println("orderDetails JOBS");
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");
            headers.set("Authorization", "Bearer " + props.getToken());

            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
            ResponseEntity<String> response = restTemplate.exchange(ORDER_DETAILS +orderId, HttpMethod.GET, requestEntity, String.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return response;
            }
        } catch (Exception e) {
            System.err.println("❌ Error while updating order status: " + e.getMessage());

            return null;
        }
        return null;
    }


    public ResponseEntity<String> cancelOrders(List<Integer> orderIds) {
        Api_Props props = this.apiPropsRepository.findByProvider("SHIP_ROCKET_SHIPPING")
                .orElseThrow(() -> new RuntimeException(SellerMessageResponse.SHIP_ROCKET_PROPS_NOT_FOUND));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(props.getToken());
        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("ids", orderIds);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                                            SHIPROCKET_CANCLE_ORDER_URL,
                                            HttpMethod.POST,
                                            entity,
                                            String.class );

        return response;
    }


    public ResponseEntity<String> getTrackingUrl(String awbCode) {
        try {
            Api_Props props = this.apiPropsRepository.findByProvider("SHIP_ROCKET_SHIPPING")
                    .orElseThrow(() -> new RuntimeException(SellerMessageResponse.SHIP_ROCKET_PROPS_NOT_FOUND));

            // Headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(props.getToken());
            HttpEntity<String> entity = new HttpEntity<>(headers);

            // Make the request
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(TRACKING_URL + awbCode, HttpMethod.GET, entity, String.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return response;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
        return null;
    }


    public ResponseEntity<String> shippingLabelDownload(String shipmentId) {
        try {
            Api_Props props = this.apiPropsRepository.findByProvider("SHIP_ROCKET_SHIPPING")
                    .orElseThrow(() -> new RuntimeException(SellerMessageResponse.SHIP_ROCKET_PROPS_NOT_FOUND));

            // Headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(props.getToken());// Prepare request body


            Map<String, List<String>> body = new HashMap<>();
            body.put("shipment_id", Arrays.asList(shipmentId));

            HttpEntity<Map<String, List<String>>> requestEntity = new HttpEntity<>(body, headers);

            // Make POST request
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.postForEntity(
                    LABEL_GENERATE_URL,
                    requestEntity,
                    String.class
            );

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return response;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
        return null;
    }





    @Scheduled(cron = "0 0 * * * *")  //1 HOUR
//    @Scheduled(cron = "0 * * * * *") //1 MINUTE
//    @Scheduled(cron = "*/5 * * * * *") // 5 SEC
//    @Scheduled(cron = "0 0/30 * * * *") //30 MINUTES
    public void trackShipments() {
        try {
            Api_Props props = this.apiPropsRepository.findByProvider("SHIP_ROCKET_SHIPPING")
                    .orElseThrow(() -> new RuntimeException(SellerMessageResponse.SHIP_ROCKET_PROPS_NOT_FOUND));

            List<CustomerOrderItems> shippedItems = this.orderItemsRepository.findAllByDeliveryStatus("SHIPPED");

            if (shippedItems.isEmpty())
            {
                log.info("Shipped Items Empty...");
                return;
            }

            for (CustomerOrderItems si : shippedItems) {
                String currentTrackingUrl = TRACK_SHIPMENTS + si.getSrAwbCode();
                RestTemplate restTemplate = new RestTemplate();

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.setBearerAuth(props.getToken());
                HttpEntity<String> entity = new HttpEntity<>(headers);

                ResponseEntity<String> response = restTemplate.exchange(currentTrackingUrl,
                                                                        HttpMethod.GET,
                                                                        entity,
                                                                        String.class);

                if (response.getStatusCode() == HttpStatus.OK) {
                    log.info("ID :: " + si.getId());
                    log.info("ORDER ITEM ID :: " + si.getOrderIdPerItem());
                    log.info("SHIP-ROCKET STATUS :: " + si.getDeliveryStatus());
                    log.info("API RESPONSE :: " + response.getBody());

                    String responseBody = response.getBody();
                    JSONObject jsonObject = new JSONObject(responseBody);
                    JSONObject trackingData = jsonObject.getJSONObject("tracking_data");
                    JSONArray activities = trackingData.getJSONArray("shipment_track_activities");

                    for (int i = 0; i < activities.length(); i++) {
                        JSONObject activity = activities.getJSONObject(i);
                        String statusLabel = activity.getString("sr-status-label");

                        if("OUT FOR DELIVERY".equalsIgnoreCase(statusLabel) &&
                           !DeliveryStatus.OUT_OF_DELIVERY.toString().equalsIgnoreCase(si.getDeliveryStatus()))
                        {
                            if ("OUT FOR DELIVERY".equalsIgnoreCase(statusLabel)) {
                                String date = activity.getString("date");
                                String status = activity.getString("status");
                                String location = activity.getString("location");
                                log.info("Status: OUT FOR DELIVERY, Date: " + date + ", Location: " + location);

                                si.setDeliveryStatus(DeliveryStatus.OUT_OF_DELIVERY.toString());
                                si.setSrStatus(DeliveryStatus.OUT_OF_DELIVERY.toString());
                                orderItemsRepository.save(si);
                                log.info("Order Item OUT FOR DELIVERY Update Success");
                            }
                        }


                        if("DELIVERED".equalsIgnoreCase(statusLabel) &&
                                !DeliveryStatus.DELIVERED.toString().equalsIgnoreCase(si.getDeliveryStatus())){

                            if ("DELIVERED".equalsIgnoreCase(statusLabel)) {
                                String date = activity.getString("date");
                                String status = activity.getString("status");
                                String location = activity.getString("location");
                                log.info("Status: DELIVERED, Date: " + date + ", Location: " + location);

                                si.setDeliveryStatus(DeliveryStatus.DELIVERED.toString());
                                si.setSrStatus(DeliveryStatus.DELIVERED.toString());

                                //Set Delivered Date Time
                                LocalDateTime now = LocalDateTime.now();
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM, yyyy hh:mm a");
                                String formattedDate = now.format(formatter);
                                si.setDeliveredDateTime(formattedDate);
                                orderItemsRepository.save(si);
                                log.info("Order Item DELIVERED Update Success");
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            System.err.println("FAILED TO FETCH SHIPPED ITEMS OR PROCESS THEM: " + e.getMessage());
        }

    }









}
