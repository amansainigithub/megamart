package com.coder.springjwt.models.sellerModels.sellerStore;


import com.coder.springjwt.models.entities.baseEntity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString
@Entity
@Table(name = "productVariant")
public class ProductVariant  extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;

    private String productSubTitle;

    private String productFrontFile;

    private String productThumbnail;

    private String file_1;

    private String file_2;

    private String file_3;

    private String file_4;

    private String productStatus;

    private String gst;

    private String hsnCode;

    private String weight;

    private String styleCode;

    private String netQuantity;

    private String productLength;

    private String productBreath;

    private String productHeight;

    private String material;

    private String productType;

    private String color;

    private String manufactureDetails;

    private String packerDetails;

    private String tags;

    private String description;

    private String sku;

    private String searchKey;

    private String sellActualPrice;

    private String defectiveReturnPrice;

    private String mrp;

    private String inventory;

    private String productDate;

    private String productTime;

    //Action Status Data for submit Catalog
    private String actionStatus;

    private String actionDebug;

    private String other;


    @OneToMany(mappedBy = "productVariant", cascade = CascadeType.ALL , orphanRemoval = true)
    private List<ProductSizes> productSizes = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonIgnore
    private SellerProduct sellerProduct;
}
