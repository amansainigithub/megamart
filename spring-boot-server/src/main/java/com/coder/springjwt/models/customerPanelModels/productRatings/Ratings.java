package com.coder.springjwt.models.customerPanelModels.productRatings;


import com.coder.springjwt.models.User;
import com.coder.springjwt.models.entities.baseEntity.BaseEntity;
import com.coder.springjwt.models.sellerModels.sellerProductModels.SellerProduct;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ratings extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int rating; // 1-5 stars

    private String review; // Review text

    private String orderItemsId; //Order Item ID

    private String productId; // Product-Id

    private String userId; //USER ID

    @ManyToOne
    @JoinColumn(name = "seller_product_id", nullable = false)
    @JsonIgnore
    private SellerProduct sellerProduct;

    @ManyToOne
    @JoinColumn(name = "customer_user_id", nullable = false)
    @JsonIgnore
    private User user;
}
