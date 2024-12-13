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

    private String variantSize;
    private String price;
    private String mrp;
    private String skuId;
    private String productLength;
    private String productBreath;
    private String productHeight;

    @ManyToOne
    @JoinColumn(name = "product_variant_id")
    @ToString.Exclude
    private SellerProduct sellerProduct;
}
