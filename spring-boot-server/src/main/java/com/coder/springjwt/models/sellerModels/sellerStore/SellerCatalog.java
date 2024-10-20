package com.coder.springjwt.models.sellerModels.sellerStore;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class SellerCatalog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String username;

    private String categoryId;

    private String categoryName;

    private String sellerStoreName;

    private String catalogTitle;

    private String catalogSubTitle;

    private String catalogFrontFile;

    private String catalogStatus;

}
