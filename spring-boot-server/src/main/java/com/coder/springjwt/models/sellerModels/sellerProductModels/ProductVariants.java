package com.coder.springjwt.models.sellerModels.sellerProductModels;

import com.coder.springjwt.models.entities.baseEntity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "productVariants")
@ToString
public class ProductVariants extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String colorVariant;
    private String productLabel;
    private String productPrice;
    private String productMrp;
    private String productLength;
    private String skuId;

    private String productInventory;
    private String waistSize;
    private String shoulderWidth;
    private String chestBustSize;

    private String calculatedGst;
    private String calculatedTds;
    private String calculatedTcs;
    private String calculatedTotalPrice;
    private String calculatedDiscount;

    private String bankSettlementAmount;




    @ManyToOne
    @JoinColumn(name = "product_variant_id")
    @ToString.Exclude
    @JsonIgnore
    private SellerProduct sellerProduct;
}
