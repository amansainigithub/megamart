package com.coder.springjwt.services.deliveryServices.shipRocketServices.imple;

import com.coder.springjwt.dtos.sellerDtos.deliveryStatusDto.DeliveryStatusDto;
import com.coder.springjwt.dtos.sellerDtos.shipRocketDto.CreateOrderRequestSRDto;
import com.coder.springjwt.dtos.sellerDtos.shipRocketDto.OrderItem;
import com.coder.springjwt.emuns.DeliveryStatus;
import com.coder.springjwt.models.customerPanelModels.CustomerOrderItems;
import com.coder.springjwt.models.sellerModels.sellerProductModels.ProductVariants;
import com.coder.springjwt.repository.customerPanelRepositories.orderItemsRepository.OrderItemsRepository;
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

    private final String TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOjY1NjY0MzEsInNvdXJjZSI6InNyLWF1dGgtaW50IiwiZXhwIjoxNzQ3NzY2MDA3LCJqdGkiOiI1Y0pqREh5eUJNdlVpSUhFIiwiaWF0IjoxNzQ2OTAyMDA3LCJpc3MiOiJodHRwczovL3NyLWF1dGguc2hpcHJvY2tldC5pbi9hdXRob3JpemUvdXNlciIsIm5iZiI6MTc0NjkwMjAwNywiY2lkIjo1MzY5NDM2LCJ0YyI6MzYwLCJ2ZXJib3NlIjpmYWxzZSwidmVuZG9yX2lkIjowLCJ2ZW5kb3JfY29kZSI6IiJ9.vK6TDxb-6pzykGxLUdDv9AkrJg9UNBog_m117DyuBtg";
    private final String CREATE_ORDER = "https://apiv2.shiprocket.in/v1/external/orders/create/adhoc";
    private final String SHIPROCKET_CANCLE_ORDER_URL = "https://apiv2.shiprocket.in/v1/external/orders/cancel";
    private String ORDER_DETAILS = "https://apiv2.shiprocket.in/v1/external/orders/show/";
    private String TRACKING_URL = "https://apiv2.shiprocket.in/v1/external/courier/track/awb/";
    private String TRACK_SHIPMENTS =  "https://apiv2.shiprocket.in/v1/external/courier/track/awb/";


    @Autowired
    private OrderItemsRepository orderItemsRepository;


    @Override
    public  ResponseEntity<String> createOrder(CustomerOrderItems customerOrderItems , ProductVariants productVariant , DeliveryStatusDto deliveryStatusDto) {

       try {
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
           orderItem.setSelling_price(Integer.parseInt(customerOrderItems.getProductPrice()));
//         orderItem.setDiscount(customerOrderItems.getProductDiscount().replace("%",""));
           orderItem.setDiscount("0");
//         orderItem.setTax(productVariant.getSellerProduct().getGst().replace("%","").trim());
           orderItem.setTax("0");
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
           headers.setBearerAuth(TOKEN);
           HttpEntity<CreateOrderRequestSRDto> entity = new HttpEntity<>(cosp, headers);
           ResponseEntity<String> response = restTemplate.exchange( CREATE_ORDER,HttpMethod.POST,entity,String.class );

//           System.out.println("Response " + response);
//           JSONObject jsonObject = new JSONObject(response.getBody());
//           System.out.println("jsonObject " + jsonObject);
//           int order_id = jsonObject.getInt("order_id");
//           String channel_order_id = jsonObject.getString("channel_order_id");
//           int shipment_id = jsonObject.getInt("shipment_id");
//           String status = jsonObject.getString("status");
//           int status_code = jsonObject.getInt("status_code");
//           int onboarding_completed_now = jsonObject.getInt("onboarding_completed_now");
//           String awb_code = jsonObject.getString("awb_code");
//           String courier_company_id = jsonObject.getString("courier_company_id");
//           String courier_name = jsonObject.getString("courier_name");
//           boolean new_channel = jsonObject.getBoolean("new_channel");
//           String packaging_box_error = jsonObject.getString("packaging_box_error");

           //Set the Data Customer Order Items
//           System.out.println("Order ID: " + order_id);
//           System.out.println("Channel Order ID: " + channel_order_id);
//           System.out.println("Shipment ID: " + shipment_id);
//           System.out.println("Status: " + status);
//           System.out.println("Status Code: " + status_code);
//           System.out.println("Onboarding Completed Now: " + onboarding_completed_now);
//           System.out.println("AWB Code: " + awb_code);
//           System.out.println("Courier Company ID: " + courier_company_id);
//           System.out.println("Courier Name: " + courier_name);
//           System.out.println("New Channel: " + new_channel);
//           System.out.println("Packaging Box Error: " + packaging_box_error);

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
            System.out.println("orderDetails JOBS");
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");
            headers.set("Authorization", "Bearer " + TOKEN);

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

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(TOKEN);
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
            // Headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(TOKEN);
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





    //CORN JOBS
//    @Scheduled(cron = "*/5 * * * * *") // 5 SEC
//    @Scheduled(cron = "0 0/30 * * * *") //30 MINUTES
//
    @Scheduled(cron = "0 0 * * * *")  //1 HOUR
//    @Scheduled(cron = "0 * * * * *") //1 MINUTE
    public void trackShipments() {
        try {
            System.out.println("-----------------------------------------------------");
            List<CustomerOrderItems> shippedItems = this.orderItemsRepository.findAllByDeliveryStatus("SHIPPED");

            if (shippedItems.isEmpty())
            {
                System.out.println("Shipped Items Empty...");
                return;
            }

            for (CustomerOrderItems si : shippedItems) {
                String currentTrackingUrl = TRACK_SHIPMENTS + si.getSrAwbCode();
                RestTemplate restTemplate = new RestTemplate();

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.setBearerAuth(TOKEN);
                HttpEntity<String> entity = new HttpEntity<>(headers);

                ResponseEntity<String> response = restTemplate.exchange(currentTrackingUrl,
                                                                        HttpMethod.GET,
                                                                        entity,
                                                                        String.class);

                if (response.getStatusCode() == HttpStatus.OK) {
                    System.out.println("ID :: " + si.getId());
                    System.out.println("ORDER ITEM ID :: " + si.getOrderIdPerItem());
                    System.out.println("SHIP-ROCKET STATUS :: " + si.getDeliveryStatus());
                    System.out.println("API RESPONSE :: " + response.getBody());

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
                                System.out.println("Status: OUT FOR DELIVERY, Date: " + date + ", Location: " + location);

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
                                System.out.println("Status: DELIVERED, Date: " + date + ", Location: " + location);

                                si.setDeliveryStatus(DeliveryStatus.DELIVERED.toString());
                                si.setSrStatus(DeliveryStatus.DELIVERED.toString());
                                orderItemsRepository.save(si);
                                log.info("Order Item DELIVERED Update Success");
                            }
                        }
                    }
                }
                System.out.println("-----------------------------------------------------");
            }

        } catch (Exception e) {
            System.err.println("FAILED TO FETCH SHIPPED ITEMS OR PROCESS THEM: " + e.getMessage());
        }

    }









}
