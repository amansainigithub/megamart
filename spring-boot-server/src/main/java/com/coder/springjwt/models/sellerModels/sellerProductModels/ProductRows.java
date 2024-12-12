package com.coder.springjwt.models.sellerModels.sellerProductModels;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "productRows")
@ToString
public class ProductRows {

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
    @JsonIgnore
    private SellerProduct sellerProduct;
}
