package com.coder.springjwt.models.sellerModels.sellerProductModels;

import com.coder.springjwt.models.entities.baseEntity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "productFiles")
@ToString
public class ProductFiles extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String fileUrl;

    private String fileName;

    private String fileSize;

    private String fileType;

    @ManyToOne
    @JoinColumn(name = "product_files_id")
    @ToString.Exclude
    @JsonIgnore
    private SellerProduct sellerProduct;


}
