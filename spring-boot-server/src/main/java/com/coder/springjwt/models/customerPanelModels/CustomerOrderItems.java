package com.coder.springjwt.models.customerPanelModels;

import com.coder.springjwt.models.entities.baseEntity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
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

    private String deliveryStatus;

    private String orderTrackingId;

    private String userId;

    private String addressId;

    private String paymentMode;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonBackReference
    @JsonIgnore
    private CustomerOrders customerOrders;
}
