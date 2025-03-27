package com.coder.springjwt.services.PaymentsServices.razorpay.imple;

import com.coder.springjwt.constants.customerPanelConstants.messageConstants.CustMessageResponse;
import com.coder.springjwt.dtos.customerPanelDtos.cartItemsDto.CartItemsDto;
import com.coder.springjwt.emuns.DeliveryStatus;
import com.coder.springjwt.emuns.PaymentModeStatus;
import com.coder.springjwt.emuns.PaymentStatus;
import com.coder.springjwt.helpers.generateDateandTime.GenerateDateAndTime;
import com.coder.springjwt.helpers.userHelper.UserHelper;
import com.coder.springjwt.models.User;
import com.coder.springjwt.models.customerPanelModels.CustomerOrderItems;
import com.coder.springjwt.models.customerPanelModels.CustomerOrders;
import com.coder.springjwt.models.customerPanelModels.address.CustomerAddress;
import com.coder.springjwt.models.customerPanelModels.payments.razorPay.PaymentsTransactions;
import com.coder.springjwt.models.sellerModels.sellerProductModels.ProductVariants;
import com.coder.springjwt.models.sellerModels.sellerProductModels.SellerProduct;
import com.coder.springjwt.payload.customerPayloads.paymentTransaction.PaymentTransactionPayload;
import com.coder.springjwt.repository.UserRepository;
import com.coder.springjwt.repository.customerPanelRepositories.addressRepository.AddressRepository;
import com.coder.springjwt.repository.customerPanelRepositories.ordersRepository.OrderRepository;
import com.coder.springjwt.repository.customerPanelRepositories.paymentRepository.PaymentRepository;
import com.coder.springjwt.repository.sellerRepository.sellerStoreRepository.SellerProductRepository;
import com.coder.springjwt.services.PaymentsServices.razorpay.RazorpayServices;
import com.coder.springjwt.util.ResponseGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Slf4j
public class RazorpayServiceImple implements RazorpayServices {

    @Value("${razorpay.key_id}")
    private String keyId;

    @Value("${razorpay.key_secret}")
    private String keySecret;


    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SellerProductRepository sellerProductRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private AddressRepository addressRepository;


    @Override
    public ResponseEntity<?> createOrder(Double amount , long addressId, List<CartItemsDto> cartItems) {
        try {
                System.out.println("AMOUNT :: " + amount);
                System.out.println("ADDRESS ID :: " + addressId);
                System.out.println("Cart Items :: " +cartItems.toString());

                //validate Address
                CustomerAddress customerAddress = this.addressWhereAreYou(addressId);

                //Validate Card
                boolean isValidCart = this.validateCartItems(cartItems);
                System.out.println(isValidCart);

                String currentUser = UserHelper.getOnlyCurrentUser();
                User user = this.userRepository.findByUsername(currentUser).orElseThrow(() -> new RuntimeException("User Not Fount"));

                if(isValidCart){

                RazorpayClient client = new RazorpayClient(keyId, keySecret);
                JSONObject orderRequest = new JSONObject();
                orderRequest.put("amount", amount * 100);
                orderRequest.put("currency", "INR");

                //Create Order
                Order order = client.Orders.create(orderRequest);
                System.out.println("order created Success!!");

                //Save Data to database
                JSONObject orderData = new JSONObject(order.toString());
                PaymentsTransactions paymentsTransactions =new PaymentsTransactions();
                paymentsTransactions.setCurrency("INR");
                paymentsTransactions.setStatus("CREATED");
                paymentsTransactions.setAmount(String.valueOf(amount));
                paymentsTransactions.setOrderId(orderData.getString("id"));
                paymentsTransactions.setCreated_at(String.valueOf(orderData.get("created_at")));
                paymentsTransactions.setAttempts(String.valueOf(orderData.getInt("attempts")));
                paymentsTransactions.setPaymentCreatedJson(order.toString());

                //SET PAYMENT MODE STATUS
                paymentsTransactions.setPaymentMode(PaymentModeStatus.ONLINE.toString());

                //Set Users Data
                paymentsTransactions.setUserName(user.getUsername());
                paymentsTransactions.setUserId(String.valueOf(user.getId()));

                //save Data to DB....
                this.paymentRepository.save(paymentsTransactions);
                System.out.println("Payment Transaction Saved Success...");


                //save USer Order
                this.saveCustomerOrder(String.valueOf(orderData.getString("id")) ,cartItems , customerAddress);
                System.out.println("Customer Order Saved Success...");

                return ResponseGenerator.generateSuccessResponse(orderData.toString() ,CustMessageResponse.SOMETHING_WENT_WRONG);
            }
           else {
                    return ResponseGenerator.generateBadRequestResponse(CustMessageResponse.SOMETHING_WENT_WRONG);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(CustMessageResponse.SOMETHING_WENT_WRONG);
        }
    }

    @Override
    public ResponseEntity<?> orderUpdate(PaymentTransactionPayload paymentTransactionPayload) {
        //Validate Cart Items
        if(!validateCartItems(paymentTransactionPayload.getCartItems())){
            throw new RuntimeException("Somethin is Wrong  in Cart Items ! Please check");
        }

        try {
            PaymentsTransactions paymentsTransactions = this.paymentRepository.findByOrderId(paymentTransactionPayload.getRazorpay_order_id());
            if(paymentsTransactions != null)
            {
                //save Cart Items To Database Final Added
                this.saveCustomerOrderItems(paymentTransactionPayload.getRazorpay_order_id() ,  paymentTransactionPayload.getCartItems());
                paymentTransactionPayload.setCartItems(null); //cart Items Null Because of Object mapper can't Convert Items
                //save Cart Items To Database Final Added

                //Payment Transaction Updated
                paymentsTransactions.setPaymentId(paymentTransactionPayload.getRazorpay_payment_id());
                paymentsTransactions.setSignature(paymentTransactionPayload.getRazorpay_signature());
                paymentsTransactions.setStatus("PAID");
                ObjectMapper objectMapper = new ObjectMapper();
                String jsonPayload = objectMapper.writeValueAsString(paymentTransactionPayload);
                paymentsTransactions.setPaymentCompleteJson(jsonPayload);
                this.paymentRepository.save(paymentsTransactions);

                System.out.println("Payment Transaction Data Update Success");

                //Update Customer Order
                this.updateCustomerOrderStatus(paymentTransactionPayload);
                System.out.println("Customer Order Update Success ::::  ORDER ID-> "
                                    + paymentTransactionPayload.getRazorpay_order_id());

                return ResponseGenerator.generateSuccessResponse("Payment Paid Success" , CustMessageResponse.SUCCESS);

            }else{
                throw new RuntimeException("ORDER ID NOT FOUND");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse( CustMessageResponse.SOMETHING_WENT_WRONG);
        }
    }




    @Override
    public ResponseEntity<?> payCod(Double amount, long addressId, List<CartItemsDto> cartItems) {
       try {

           System.out.println("============ COD ORDER PROCESSED =================");
           System.out.println("AMOUNT :: " + amount);
           System.out.println("ADDRESS ID :: " + addressId);
           System.out.println("Cart Items :: " + cartItems.toString());

           //validate Address
           CustomerAddress customerAddress = this.addressWhereAreYou(addressId);

           //Validate Card
           boolean isValidCart = this.validateCartItems(cartItems);
           System.out.println(isValidCart);

           String currentUser = UserHelper.getOnlyCurrentUser();
           User user = this.userRepository.findByUsername(currentUser)
                        .orElseThrow(() -> new RuntimeException("User Not Fount"));


           if (isValidCart) {

               String orderId = generateCodOrderId();
               //Save Data to database
               PaymentsTransactions paymentsTransactions = new PaymentsTransactions();
               paymentsTransactions.setCurrency("INR");
               paymentsTransactions.setAmount(String.valueOf(amount));
               paymentsTransactions.setOrderId(orderId);
               paymentsTransactions.setCreated_at(GenerateDateAndTime.getLocalDateTime());
               paymentsTransactions.setAttempts("0");
               paymentsTransactions.setPaymentCreatedJson(null);
               paymentsTransactions.setPaymentId("PENDING");
               paymentsTransactions.setSignature("PENDING");
               paymentsTransactions.setStatus(PaymentStatus.UNPAID.toString());
               paymentsTransactions.setUserName(user.getUsername());
               paymentsTransactions.setUserId(String.valueOf(user.getId()));

               //SET PAYMENT MODE STATUS
               paymentsTransactions.setPaymentMode(PaymentModeStatus.COD.toString());

               //save Data to DB....
               this.paymentRepository.save(paymentsTransactions);
               System.out.println("Payment Transaction Saved to Database...");

               //save Customer Orders Data
               this.saveCustomerOrderCOD(orderId, cartItems, customerAddress, user);
               System.out.println("Customer Order Data Saved Success");

               //save Customer Order Items
               this.saveCustomerOrderItemsCOD(orderId , cartItems);
               System.out.println("Customer Order Items Data Saved Success");

               return ResponseGenerator.generateSuccessResponse("SUCCESS", CustMessageResponse.DATA_SAVED_SUCCESS);
           }else{
               throw new RuntimeException("Card Not Validate ! Please Check");
           }
       }
       catch (Exception e)
       {
           e.printStackTrace();
           return ResponseGenerator.generateBadRequestResponse( CustMessageResponse.SOMETHING_WENT_WRONG);
       }

    }


//=====================================

    public CustomerAddress addressWhereAreYou(long addressId)
    {
        String currentUser = UserHelper.getOnlyCurrentUser();
        User user = this.userRepository.findByUsername(currentUser).orElseThrow(() -> new RuntimeException("User Not Fount"));
        CustomerAddress customerAddress = this.addressRepository.findByUserIdAndId(user.getId(), addressId);
        if(customerAddress != null)
        {
            return customerAddress;
        }else{
            throw new RuntimeException("Address Not Found User Id ==>" + user.getId());
        }
    }


    public boolean validateCartItems(@NotNull List<CartItemsDto> cartItems) {

        int variantTotalPrice = 0;
        int cartTotalPrice = 0;

        for (CartItemsDto ci : cartItems) {
            Optional<SellerProduct> productOpt = sellerProductRepository.findById(Long.parseLong(ci.getPId()));

            if (productOpt.isEmpty()) {
                return Boolean.FALSE; // Product not found
            }

            SellerProduct sellerProduct = productOpt.get();
            List<ProductVariants> variantsList = sellerProduct.getProductRows();

            boolean isValid =Boolean.FALSE;
            for(ProductVariants pv : variantsList)
            {
                System.out.println("---------------------------------");
                System.out.println("Product Price :: " + pv.getProductPrice());
                System.out.println("cart Iem Price :: " + ci.getPPrice());
                System.out.println("Product Inventory :: " + pv.getProductInventory());

                if(pv.getProductPrice().equals(String.valueOf(ci.getPPrice()))
                        && pv.getProductLabel().equals(ci.getPSize())
                        && Integer.parseInt(pv.getProductInventory()) > 0 )
                {
                    variantTotalPrice += Integer.parseInt(pv.getProductPrice()) * ci.getQuantity();
                    cartTotalPrice += Integer.parseInt(ci.getPPrice()) * ci.getQuantity();
                    System.out.println("Matched");
                    isValid = Boolean.TRUE;
                }
                else{
                    System.out.println("Not matched");
                }
            }

            System.out.println(isValid);
            if(!isValid)
            {
                throw new RuntimeException("Price ,Size and Inventory Could Not Matched");
            }
        }

        System.out.println("Variant Total Price :: " + variantTotalPrice);
        System.out.println("Cart Total Price :: " + cartTotalPrice);

        if(variantTotalPrice != cartTotalPrice)
        {
            throw new RuntimeException("Cart Total Price and Product Variant Total Price Could Not matched");
        }

        return Boolean.TRUE; // All items are valid
    }

    public void saveCustomerOrder( String orderId , List<CartItemsDto> cartItemsList, CustomerAddress customerAddress)
    {
        try {
            System.out.println("save Customer Order Starting.....");
            String currentUser = UserHelper.getOnlyCurrentUser();
            User user = this.userRepository.findByUsername(currentUser).orElseThrow(() -> new RuntimeException("User Not Fount"));

            //Creating Object CustomerOrder
            CustomerOrders customerOrders = new CustomerOrders();

            //Customer Order Product Details Fitting
            customerOrders.setOrderId(orderId);
            customerOrders.setPaymentStatus("CREATED");
            customerOrders.setUserId(String.valueOf(user.getId()));
            customerOrders.setUser(user);
            customerOrders.setTotalOrders(cartItemsList.size());

            //Set Customer AddressId
            customerOrders.setAddressId(String.valueOf(customerAddress.getId()));


            // Define the required format TimeStamp
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM, yyyy hh:mm a");
            String formattedDate = now.format(formatter);

            //Set Date Time
            customerOrders.setOrderDateTime(formattedDate);

            int totalPrice = 0;
            for(CartItemsDto ci : cartItemsList)
            {
                totalPrice += Integer.parseInt(ci.getPPrice()) * ci.getQuantity();
            }
            //Set Total Price
            customerOrders.setTotalPrice(totalPrice);

            //SET PAYMENT MODE STATUS
            customerOrders.setPaymentMode(PaymentModeStatus.ONLINE.toString());

            this.orderRepository.save(customerOrders);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void saveCustomerOrderItems( String orderId , List<CartItemsDto> cartItemsList)
    {
        try {
            String currentUser = UserHelper.getOnlyCurrentUser();
            User user = this.userRepository.findByUsername(currentUser).orElseThrow(() -> new RuntimeException("User Not Fount"));

            CustomerOrders customerOrders = this.orderRepository.findByOrderId(orderId);

            //Check Customer Address
            //validate Address
            CustomerAddress customerAddress = this.addressWhereAreYou(Long.parseLong(customerOrders.getAddressId()));

            List<CustomerOrderItems> customerOrderItemsList = new ArrayList<>();
            for(CartItemsDto ci :  cartItemsList)
            {
                CustomerOrderItems customerOrderItems = new CustomerOrderItems();
                customerOrderItems.setProductId(ci.getPId());
                customerOrderItems.setProductName(ci.getPName());
                customerOrderItems.setProductPrice(ci.getPPrice());
                customerOrderItems.setProductBrand(ci.getPBrand());
                customerOrderItems.setProductSize(ci.getPSize());
                customerOrderItems.setQuantity(String.valueOf(ci.getQuantity()));
                customerOrderItems.setTotalPrice(String.valueOf(ci.getTotalPrice()));
                customerOrderItems.setFileUrl(ci.getPFileUrl());
                customerOrderItems.setProductColor(ci.getPColor());
                customerOrderItems.setProductMrp(String.valueOf(ci.getPMrp()));
                customerOrderItems.setProductDiscount(ci.getPCalculatedDiscount());
                customerOrderItems.setRazorpayOrderId(orderId);
                customerOrderItems.setPaymentStatus(PaymentStatus.PAID.toString());
                customerOrderItems.setDeliveryStatus(DeliveryStatus.PENDING.toString());
                customerOrderItems.setUserId(String.valueOf(user.getId()));
                customerOrderItems.setPaymentMode(PaymentModeStatus.ONLINE.toString());

                //Set Customer Delivery Address To Customer Order Items
                customerOrderItems.setCustomerName(customerAddress.getCustomerName());
                customerOrderItems.setAddressId(String.valueOf(customerAddress.getId()));
                customerOrderItems.setCountry(customerAddress.getCountry());
                customerOrderItems.setMobileNumber(customerAddress.getMobileNumber());
                customerOrderItems.setArea(customerAddress.getArea());
                customerOrderItems.setPostalCode(customerAddress.getPostalCode());
                customerOrderItems.setAddressLine1(customerAddress.getAddressLine1());
                customerOrderItems.setAddressLine2(customerAddress.getAddressLine2());

                //Set Customer Order
                customerOrderItems.setCustomerOrders(customerOrders);

                //Add Order Items To List
                customerOrderItemsList.add(customerOrderItems);
            }
            //Set Customer Order Items List
            customerOrders.setCustomerOrderItems(customerOrderItemsList);

            //save the Order
            this.orderRepository.save(customerOrders);

            log.info("Customer Order Items Saved Success!!!");

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    public void updateCustomerOrderStatus(PaymentTransactionPayload paymentTransactionPayload)
    {
        try {
            CustomerOrders customerOrders = this.orderRepository.findByOrderId(paymentTransactionPayload.getRazorpay_order_id());
            customerOrders.setPaymentStatus(PaymentStatus.PAID.toString());
            customerOrders.setPaymentId(paymentTransactionPayload.getRazorpay_payment_id());
            this.orderRepository.save(customerOrders);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    //SAVE COD ORDER'S
    public void saveCustomerOrderCOD( String orderId ,
                                      List<CartItemsDto> cartItemsList,
                                      CustomerAddress customerAddress ,
                                      User user)
    {
        try {
            System.out.println("save Customer Order Starting.....");


            CustomerOrders customerOrders = new CustomerOrders();

            customerOrders.setOrderId(orderId);
            customerOrders.setPaymentStatus(PaymentStatus.UNPAID.toString());
            customerOrders.setUserId(String.valueOf(user.getId()));
            customerOrders.setUser(user);
            customerOrders.setTotalOrders(cartItemsList.size());
            customerOrders.setPaymentId("PENDING");

            //Set Customer Address
            customerOrders.setAddressId(String.valueOf(customerAddress.getId()));

            // Define the required format
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM, yyyy hh:mm a");
            String formattedDate = now.format(formatter);

            //Set Date Time
            customerOrders.setOrderDateTime(formattedDate);

            int totalPrice = 0;
            for(CartItemsDto ci : cartItemsList)
            {
                totalPrice += Integer.parseInt(ci.getPPrice()) * ci.getQuantity();
            }
            //Set Total Price
            customerOrders.setTotalPrice(totalPrice);

            //SET PAYMENT MODE STATUS
            customerOrders.setPaymentMode(PaymentModeStatus.COD.toString());

            this.orderRepository.save(customerOrders);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    public void saveCustomerOrderItemsCOD( String orderId , List<CartItemsDto> cartItemsList)
    {
        try {
            String currentUser = UserHelper.getOnlyCurrentUser();
            User user = this.userRepository.findByUsername(currentUser).orElseThrow(() -> new RuntimeException("User Not Fount"));

            CustomerOrders customerOrders = this.orderRepository.findByOrderId(orderId);

            CustomerAddress customerAddress = this.addressWhereAreYou(Long.parseLong(customerOrders.getAddressId()));

            List<CustomerOrderItems> customerOrderItemsList = new ArrayList<>();
            for(CartItemsDto ci :  cartItemsList)
            {
                CustomerOrderItems customerOrderItems = new CustomerOrderItems();
                customerOrderItems.setProductId(ci.getPId());
                customerOrderItems.setProductName(ci.getPName());
                customerOrderItems.setProductPrice(ci.getPPrice());
                customerOrderItems.setProductBrand(ci.getPBrand());
                customerOrderItems.setProductSize(ci.getPSize());
                customerOrderItems.setQuantity(String.valueOf(ci.getQuantity()));
                customerOrderItems.setTotalPrice(String.valueOf(ci.getTotalPrice()));
                customerOrderItems.setFileUrl(ci.getPFileUrl());
                customerOrderItems.setProductColor(ci.getPColor());
                customerOrderItems.setProductMrp(String.valueOf(ci.getPMrp()));
                customerOrderItems.setProductDiscount(ci.getPCalculatedDiscount());
                customerOrderItems.setRazorpayOrderId(orderId);
                customerOrderItems.setPaymentStatus(PaymentStatus.UNPAID.toString());
                customerOrderItems.setDeliveryStatus(DeliveryStatus.PENDING.toString());
                customerOrderItems.setUserId(String.valueOf(user.getId()));
                customerOrderItems.setPaymentMode(PaymentModeStatus.COD.toString());

                //Set Customer Delivery Address To Customer Order Items
                customerOrderItems.setCustomerName(customerAddress.getCustomerName());
                customerOrderItems.setAddressId(String.valueOf(customerAddress.getId()));
                customerOrderItems.setCountry(customerAddress.getCountry());
                customerOrderItems.setMobileNumber(customerAddress.getMobileNumber());
                customerOrderItems.setArea(customerAddress.getArea());
                customerOrderItems.setPostalCode(customerAddress.getPostalCode());
                customerOrderItems.setAddressLine1(customerAddress.getAddressLine1());
                customerOrderItems.setAddressLine2(customerAddress.getAddressLine2());

                //Set Customer Order
                customerOrderItems.setCustomerOrders(customerOrders);

                //Add Order Items To List
                customerOrderItemsList.add(customerOrderItems);
            }
            //Set Customer Order Items List
            customerOrders.setCustomerOrderItems(customerOrderItemsList);

            //save the Order
            this.orderRepository.save(customerOrders);

            log.info("Customer Order Items Saved Success!!!");

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static String generateCodOrderId() {
        // Get the current date in yyyyMMdd format
        String currentDate = new SimpleDateFormat("yyyyMMdd").format(new Date());

        // Define the alphanumeric characters
        String alphanumeric = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder randomAlphanumeric = new StringBuilder(9);

        // Generate 9-character alphanumeric string
        for (int i = 0; i < 9; i++) {
            randomAlphanumeric.append(alphanumeric.charAt(random.nextInt(alphanumeric.length())));
        }

        // Concatenate COD, current date, and alphanumeric string
        return "COD" + "_" + currentDate + "_"+ randomAlphanumeric;
    }

}
