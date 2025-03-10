package com.coder.springjwt.models.publicPanelModels;

import com.coder.springjwt.models.User;
import com.coder.springjwt.models.entities.baseEntity.BaseEntity;
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
    private String status;    // Success, Failed, Pending
    private String userId;    // User-Id
    private LocalDateTime orderDate = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private User user;

    @OneToMany(mappedBy = "customerOrders", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<CustomerOrderItems> customerOrderItems;
}
