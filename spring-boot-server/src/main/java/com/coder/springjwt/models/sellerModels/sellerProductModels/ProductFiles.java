package com.coder.springjwt.models.sellerModels.sellerProductModels;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@Entity
@Table(name = "productFiles")
@ToString
public class ProductFiles {

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
    private SellerProduct sellerProduct;


}
