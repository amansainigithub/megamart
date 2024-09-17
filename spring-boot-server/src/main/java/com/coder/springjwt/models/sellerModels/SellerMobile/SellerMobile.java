package com.coder.springjwt.models.sellerModels.SellerMobile;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "SellerMobile")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SellerMobile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String mobile;
    private String otp;
    private LocalDateTime expiresAt;
    private Boolean isVerified;

    // Getters and Setters

}
