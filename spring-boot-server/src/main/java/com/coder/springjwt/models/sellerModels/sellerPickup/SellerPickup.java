package com.coder.springjwt.models.sellerModels.sellerPickup;

import com.coder.springjwt.models.entities.baseEntity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
public class SellerPickup extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    private String addressLineOne;

    private String addressLineTwo;

    private String landmark;

    private String pincode;

    private String city;

    private String state;

    private String country;

    private Long  recordId;
}
