package com.coder.springjwt.models.sellerModels.sellerStore;

import com.coder.springjwt.models.entities.baseEntity.BaseEntity;
import com.coder.springjwt.models.sellerModels.sellerProductModels.SellerProduct;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class SellerStore extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @Column(unique = true)
    private String storeName;

    private String tags;

    @NotBlank
    @Column(nullable = false)
    private String username;

    @NotBlank
    @Column(nullable = false)
    private String ft_username;

    @OneToMany(mappedBy = "sellerStore" , cascade = CascadeType.ALL)
    private List<SellerProduct> sellerProducts;

}
