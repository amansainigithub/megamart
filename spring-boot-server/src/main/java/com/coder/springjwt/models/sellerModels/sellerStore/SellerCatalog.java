package com.coder.springjwt.models.sellerModels.sellerStore;


import com.coder.springjwt.models.entities.baseEntity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class SellerCatalog extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String username;

    private String categoryId;

    private String categoryName;

    private String sellerStoreName;

    private String catalogName;

    private String catalogSubTitle;

    private String catalogFrontFile;

    private String catalogThumbnail;

    private String file_1;

    private String file_2;

    private String file_3;

    private String file_4;

    private String catalogStatus;

    private String gst;

    private String hsnCode;

    private String size;

    private String weight;

    private String styleCode;

    private String netQuantity;

    private String catalogLength;

    private String catalogBreath;

    private String catalogHeight;

    private String material;

    private String catalogType;

    private String color;

    private String manufactureDetails;

    private String packerDetails;

    private String tags;

    @Column(length = 5000)
    private String description;

    private String sku;

    private String searchKey;

    private String sellActualPrice;

    private String defectiveReturnPrice;

    private String mrp;

    private String inventory;

    private String discount;

    private String catalogDate;

    private String catalogTime;

    private String spaceId;

    private String catalogId;

    //Action Status Data for submit Catalog
    private String errorMarked;

    private String otherSuggestion;

    @ManyToOne
    @JsonIgnore
    private SellerStore sellerStore;


}
