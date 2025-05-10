package com.coder.springjwt.services.deliveryServices.shipRocketServices.imple;

import com.coder.springjwt.dtos.sellerDtos.deliveryStatusDto.DeliveryStatusDto;
import com.coder.springjwt.dtos.sellerDtos.shipRocketDto.CreateOrderRequestSRDto;
import com.coder.springjwt.dtos.sellerDtos.shipRocketDto.OrderItem;
import com.coder.springjwt.models.customerPanelModels.CustomerOrderItems;
import com.coder.springjwt.models.sellerModels.sellerProductModels.ProductVariants;
import com.coder.springjwt.services.deliveryServices.shipRocketServices.ShipRocketService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.UUID;
import org.springframework.http.*;

@Service
@Slf4j
public class ShipRocketServiceImple implements ShipRocketService {

    private final String API_URL = "https://apiv2.shiprocket.in/v1/external/orders/create/adhoc";
    private final String TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOjY1MTI5NTMsInNvdXJjZSI6InNyLWF1dGgtaW50IiwiZXhwIjoxNzQ3NDkxMzcwLCJqdGkiOiJyNGVHTnc5Mm9HYUdjSGZaIiwiaWF0IjoxNzQ2NjI3MzcwLCJpc3MiOiJodHRwczovL3NyLWF1dGguc2hpcHJvY2tldC5pbi9hdXRob3JpemUvdXNlciIsIm5iZiI6MTc0NjYyNzM3MCwiY2lkIjo2Mjg3NDQ5LCJ0YyI6MzYwLCJ2ZXJib3NlIjpmYWxzZSwidmVuZG9yX2lkIjowLCJ2ZW5kb3JfY29kZSI6IiJ9.OeKTbRkYaSXOG2PFOSfMrh2mDmlQz6WUVzGWcbs_5S8";


    @Override
    public  ResponseEntity<String> createOrder(CustomerOrderItems customerOrderItems , ProductVariants productVariant , DeliveryStatusDto deliveryStatusDto) {

       try {
           //Order Date Creation
           LocalDateTime now = LocalDateTime.now();
           DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
           String orderDate = now.format(formatter);

           CreateOrderRequestSRDto cosp = new CreateOrderRequestSRDto();
           cosp.setOrder_id(UUID.randomUUID().toString());
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
           orderItem.setDiscount(customerOrderItems.getProductDiscount().replace("%",""));
           orderItem.setTax(productVariant.getSellerProduct().getGst().replace("%","").trim());
           orderItem.setHsn(Integer.parseInt(productVariant.getSellerProduct().getHsn()));
           cosp.setOrder_items(Arrays.asList(orderItem));


           cosp.setPayment_method("Prepaid");
           cosp.setShipping_charges(0);
           cosp.setGiftwrap_charges(0);
           cosp.setTransaction_charges(0);
           cosp.setTotal_discount(Double.parseDouble(customerOrderItems.getProductDiscount().replace("%","")));
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
           ResponseEntity<String> response = restTemplate.exchange( API_URL,HttpMethod.POST,entity,String.class );

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
}
