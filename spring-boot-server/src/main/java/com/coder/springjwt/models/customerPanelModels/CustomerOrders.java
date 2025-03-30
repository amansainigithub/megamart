package com.coder.springjwt.models.customerPanelModels;

import com.coder.springjwt.models.User;
import com.coder.springjwt.models.entities.baseEntity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customerOrder")
public class CustomerOrders extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderId;   // Razorpay Order ID
    private String paymentId; // Razorpay Payment ID
    private String paymentStatus;    // Success, Failed, Pending
    private String userId;    // User-Id
    private int totalOrders;     //Total Number Of Orders
    private int totalPrice;     //Cart Total Price
    private String orderDateTime;

    private String paymentMode;

    private String addressId;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "customerOrders", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<CustomerOrderItems> customerOrderItems;
}
