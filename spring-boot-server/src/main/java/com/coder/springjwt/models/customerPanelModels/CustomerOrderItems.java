package com.coder.springjwt.models.customerPanelModels;

import com.coder.springjwt.models.entities.baseEntity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customerOrderItems")
public class CustomerOrderItems extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productId;

    private String productName;

    private String productPrice;

    private String productBrand;

    private String productSize;

    private String quantity;

    private String totalPrice;

    private String fileUrl;

    private String productColor;

    private String productMrp;

    private String productDiscount;

    private String razorpayOrderId;

    private String paymentStatus;



    private String userId;

    private String paymentMode;

    private String customOrderNumber;



    //Address Data
    private String customerName;
    private String addressId;
    private String country;
    private String mobileNumber;
    private String area;
    private String postalCode;
    private String addressLine1;
    private String addressLine2;
    private boolean defaultAddress;

    //Delivery Data
    private String deliveryStatus;

    private String orderTrackingId;

    private String deliveryDateTime;

    private String courierName;

    private String orderDateTime;

    private String invoiceNumber;

    private String invoiceDateTime;

    //Razorpay Fees And status
    private String razorpayFees;
    private String razorpayGst;
    private String razorpayTotalFees;
    private String razorpayFinalAmt;

    private String refundStatus;
    private String refundRequestDateTime;
    private String refundProcessedDateTime;
    private String refundId;


    @Column(length = 2000)
    private String refundResponse;

    @Column(length = 500)
    private String cancelReason;
    //Refund Var Ending

    //By Default is Rating =  N
    private boolean isRating = Boolean.FALSE;

    @OneToOne(cascade = CascadeType.ALL )
    @JoinColumn(name = "return_order_item_id")
    private CustomerReturnExchangeOrders customerReturnExchangeOrders;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonBackReference
    @JsonIgnore
    private CustomerOrders customerOrders;
}
