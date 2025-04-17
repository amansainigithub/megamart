package com.coder.springjwt.models.customerPanelModels.productReviews;

import com.coder.springjwt.models.User;
import com.coder.springjwt.models.entities.baseEntity.BaseEntity;
import com.coder.springjwt.models.sellerModels.sellerProductModels.SellerProduct;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ProductReviews")
public class ProductReviews extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String rating;

    private String review;

    private String orderItemId;

    private String userId;

    @Column(length = 1000)
    private String productFileUrl;

    @Column(length = 1000)
    private String productName;

    private String productSize;

    private String productPrice;

    private String productId;

    @OneToMany(mappedBy = "productReviews" , cascade = CascadeType.ALL)
    private List<ProductReviewFiles> productReviewFiles;

    @ManyToOne(cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_product_id")
    @JsonIgnore
    private SellerProduct sellerProduct;

    @ManyToOne(cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_user_id")
    @JsonIgnore
    private User user;

}
