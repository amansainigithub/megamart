package com.coder.springjwt.services.PaymentsServices.razorpay.imple;

import com.coder.springjwt.constants.customerPanelConstants.messageConstants.CustMessageResponse;
import com.coder.springjwt.dtos.customerPanelDtos.cartItemsDto.CartItemsDto;
import com.coder.springjwt.emuns.OrderStatus;
import com.coder.springjwt.helpers.userHelper.UserHelper;
import com.coder.springjwt.models.User;
import com.coder.springjwt.models.customerPanelModels.CustomerOrderItems;
import com.coder.springjwt.models.customerPanelModels.CustomerOrders;
import com.coder.springjwt.models.customerPanelModels.payments.razorPay.PaymentsTransactions;
import com.coder.springjwt.models.sellerModels.sellerProductModels.ProductVariants;
import com.coder.springjwt.models.sellerModels.sellerProductModels.SellerProduct;
import com.coder.springjwt.payload.customerPayloads.paymentTransaction.PaymentTransactionPayload;
import com.coder.springjwt.repository.UserRepository;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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



    @Override
    public ResponseEntity<?> createOrder(Double amount ,  List<CartItemsDto> cartItems) {
        try {
                System.out.println("AMOUNT:: " + amount);
                System.out.println("Cart Items :: " +cartItems.toString());

                boolean isValidCart = this.validateCartItems(cartItems);
                System.out.println(isValidCart);

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

                //Check User is Valid or Not
                this.isValidUser(paymentsTransactions);

                //save Data to DB....
                this.paymentRepository.save(paymentsTransactions);
                System.out.println("Payment Saved to Database...");

                //save USer Order
                this.saveCustomerOrder(String.valueOf(orderData.getString("id")) ,cartItems);
                System.out.println("Order and cart items---- saved Success to Database");

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
        System.out.println("-------------Update Cart Working-------------");

        //Validate Cart Items
        if(!validateCartItems(paymentTransactionPayload.getCartItems())){
            throw new RuntimeException("Somethin is Wrong  in Cart Items ! Please check");
        }
        paymentTransactionPayload.setCartItems(null);

        try {
            PaymentsTransactions createdTransaction = this.paymentRepository.findByOrderId(paymentTransactionPayload.getRazorpay_order_id());
            if(createdTransaction != null)
            {
                createdTransaction.setPaymentId(paymentTransactionPayload.getRazorpay_payment_id());
                createdTransaction.setSignature(paymentTransactionPayload.getRazorpay_signature());
                createdTransaction.setStatus("PAID");

                ObjectMapper objectMapper = new ObjectMapper();
                String jsonPayload = objectMapper.writeValueAsString(paymentTransactionPayload);
                createdTransaction.setPaymentCompleteJson(jsonPayload);

                this.paymentRepository.save(createdTransaction);
                System.out.println("Data Update Success");

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


    public void isValidUser(PaymentsTransactions paymentsTransactions)
    {
        String currentUser = UserHelper.getOnlyCurrentUser();
        User user = this.userRepository.findByUsername(currentUser).orElseThrow(() -> new RuntimeException("User Not Fount"));
        paymentsTransactions.setUserName(currentUser);
        paymentsTransactions.setUserId(String.valueOf(user.getId()));
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



    public void saveCustomerOrder( String orderId , List<CartItemsDto> cartItemsList)
    {
        try {
            System.out.println("save Customer Order Starting.....");
            String currentUser = UserHelper.getOnlyCurrentUser();
            User user = this.userRepository.findByUsername(currentUser).orElseThrow(() -> new RuntimeException("User Not Fount"));

            CustomerOrders customerOrders = new CustomerOrders();
            customerOrders.setOrderId(orderId);
            customerOrders.setStatus("CREATED");
            customerOrders.setUserId(String.valueOf(user.getId()));
            customerOrders.setUser(user);

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
                customerOrderItems.setOrderStatus(OrderStatus.PENDING.toString());
                customerOrderItems.setUserId(String.valueOf(user.getId()));

                //Set Customer Order
                customerOrderItems.setCustomerOrders(customerOrders);

                //Add Order Items To List
                customerOrderItemsList.add(customerOrderItems);
            }
            //Set Customer Order Items List
            customerOrders.setCustomerOrderItems(customerOrderItemsList);

            //save the Order
            this.orderRepository.save(customerOrders);
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
            customerOrders.setStatus("PAID");
            customerOrders.setPaymentId(paymentTransactionPayload.getRazorpay_payment_id());
            this.orderRepository.save(customerOrders);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }


}
