package com.coder.springjwt.models.sellerModels.sellerStore;

import com.coder.springjwt.models.entities.baseEntity.BaseEntity;
import com.coder.springjwt.payload.sellerPayloads.sellerPayload.ProductVariantPayload;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString
@Entity
@Table(name = "sellerProduct")
public class SellerProduct extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    private String username;

    private String categoryId;

    private String categoryName;

    private String sellerStoreName;

    @OneToMany(mappedBy = "sellerProduct", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductVariant> productVariants = new ArrayList<>();;
}
