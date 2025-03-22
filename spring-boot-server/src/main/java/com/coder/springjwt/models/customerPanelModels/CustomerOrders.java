package com.coder.springjwt.models.customerPanelModels;

import com.coder.springjwt.models.User;
import com.coder.springjwt.models.entities.baseEntity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "customerOrder")
public class CustomerOrders extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderId;   // Razorpay Order ID
    private String paymentId; // Razorpay Payment ID
    private String paymentStatus;    // Success, Failed, Pending
    private String deliveryStatus;
    private String userId;    // User-Id
    private int quantity;     //Cart Quantity Items
    private int totalPrice;     //Cart Total Price
    private String orderDateTime;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "customerOrders", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<CustomerOrderItems> customerOrderItems;
}
