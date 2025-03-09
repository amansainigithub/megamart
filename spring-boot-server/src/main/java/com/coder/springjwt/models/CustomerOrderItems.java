package com.coder.springjwt.models;

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

    private String userId;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonBackReference
    private CustomerOrders customerOrders;
}
