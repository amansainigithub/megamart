package com.coder.springjwt.models.sellerModels.sellerProductModels;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@Entity
@Table(name = "sellerProduct")
@ToString
public class SellerProduct {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String  productName;
    private String  gst;
    private String  hsn;
    private String productCode;

    //Product table Data
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sellerProduct")
    private List<ProductRows> productRows;


    //product Size Data
    private List<String> productSize;


    //Product Details
    private String styleName;
    private String sleeveType;
    private String fitType;
    private String gender;
    private String materialType;
    private String productColor;
    private String country;
    private String pattern;
    private String manufactureName;

    //Other Details
    private String description;
    private String numberOfItems;
    private String finishingType;
    private String brandField;


}
