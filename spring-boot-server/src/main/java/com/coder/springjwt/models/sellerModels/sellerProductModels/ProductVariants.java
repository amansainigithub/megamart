package com.coder.springjwt.models.sellerModels.sellerProductModels;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "productVariants")
@ToString
public class ProductVariants {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String productLabel;
    private String productPrice;
    private String productMrp;
    private String productLength;
    private String skuId;

    @ManyToOne
    @JoinColumn(name = "product_variant_id")
    @ToString.Exclude
    private SellerProduct sellerProduct;
}
