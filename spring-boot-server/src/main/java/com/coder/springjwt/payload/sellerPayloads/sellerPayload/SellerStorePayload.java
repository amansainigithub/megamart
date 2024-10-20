package com.coder.springjwt.payload.sellerPayloads.sellerPayload;

import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.ToString;

import java.util.List;

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

    @OneToMany(cascade = CascadeType.ALL)
    private List<SellerCatalogPayload> sellerProductPayload;

}
