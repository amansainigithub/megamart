package com.coder.springjwt.models.sellerModels.props;

import com.coder.springjwt.models.entities.baseEntity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
@Table(name = "api_props")
public class Api_Props extends BaseEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;

    @NotBlank
    private String provider;

    @NotBlank(message = "Apikey must Not be Blank!!")
    @Column(length = 1000 ,unique = true)
    private String keyId;

    @NotBlank(message = "KeySecret must Not be Blank!!")
    @Column(length = 1000 ,unique = true)
    private String keySecret;

    @NotBlank(message = "Token must Not be Blank!!")
    @Column(length = 1000 ,unique = true)
    private String token;

    @NotBlank(message = "Token must Not be Blank!!")
    @Column(length = 1000 ,unique = true)
    private String url;

    private String remarks;

    private String region;

    @Column(length = 20)
    private String status;

}
