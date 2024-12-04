package com.coder.springjwt.models.sellerModels.sellerStore;

import com.coder.springjwt.models.entities.baseEntity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Entity
@Table(name = "productSize")
public class ProductSizes  extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productColor;

    private String productSize;

    private String inventory;

    private String price;

    private String mrp;

    @ManyToOne
    @JoinColumn(name = "size_id")
    @JsonIgnore
    private ProductVariant productVariant;
}
