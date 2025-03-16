package com.coder.springjwt.models.sellerModels.sellerProductModels;

import com.coder.springjwt.models.entities.baseEntity.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "sellerProduct")
@ToString
public class SellerProduct extends BaseEntity  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String  productName;
    private String  gst;
    private String  hsn;
    private String productWeight;


    //Product table Data
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sellerProduct",orphanRemoval = true)
    private List<ProductVariants> productRows;

    //product Size Data
    @ElementCollection
    private List<String> productSizes;

    //Product Details
    private String sleeveType;
    private String fitType;
    private String gender;
    private String materialType;
    private String productColor;
    private String country;
    private String pattern;
    private String manufactureName;
    private String netQuantity;
    private String productCode;


    //Product Other Details
    private String finishingType;
    private String brandField;
    private String description;

    private String productCreationDate;
    private String productCreationTime;


    //Set Parent Keys
    private String parentKey;

    //RowsCounter
    private long rowsCounter;

    private String productId;

    private String productStatus;

    private String shippingCharges;

    private String sellerUserName;

    private String sellerUserId;

    private String bornCategoryName;

    private String bornCategoryId;

    private String babyCategoryName;

    private String babyCategoryId;

    private String variant;

    private String productFirstSize;

    private String productLauncherId;

    private String productLauncherDate;

    private String productLauncherTime;


    @OneToMany(cascade = CascadeType.ALL,  mappedBy = "sellerProduct")
    private List<ProductFiles> productFiles;




}
