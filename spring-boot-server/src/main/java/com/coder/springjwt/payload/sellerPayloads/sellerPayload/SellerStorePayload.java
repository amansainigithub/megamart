package com.coder.springjwt.payload.sellerPayloads.sellerPayload;

import com.coder.springjwt.models.entities.baseEntity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SellerStorePayload {

    @NotBlank
    private String storeName;

    private String tags;

    @NotBlank
    private String username;

    @NotBlank
    private String password;


}
