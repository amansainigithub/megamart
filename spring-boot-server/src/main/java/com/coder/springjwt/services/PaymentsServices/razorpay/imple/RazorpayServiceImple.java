package com.coder.springjwt.services.PaymentsServices.razorpay.imple;

import com.coder.springjwt.constants.customerConstants.messageConstants.test.CustMessageResponse;
import com.coder.springjwt.models.payments.razorPay.PaymentsTransactions;
import com.coder.springjwt.payload.paymentTransaction.PaymentTransactionPayload;
import com.coder.springjwt.repository.paymentRepository.PaymentRepository;
import com.coder.springjwt.services.PaymentsServices.razorpay.RazorpayServices;
import com.coder.springjwt.util.ResponseGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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


    @Override
    public ResponseEntity<?> createOrder(Double amount) {
        try {
            System.out.println("KEY ID :: " + keyId);
            System.out.println("SECRET KEY :: " + keySecret);

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

            //save Data to DB....
            this.paymentRepository.save(paymentsTransactions);
            System.out.println("Payment Saved to Database...");
            return ResponseGenerator.generateSuccessResponse(order.toString() , CustMessageResponse.SUCCESS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(CustMessageResponse.SOMETHING_WENT_WRONG);
        }
    }

    @Override
    public ResponseEntity<?> orderUpdate(PaymentTransactionPayload paymentTransactionPayload) {
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


}
