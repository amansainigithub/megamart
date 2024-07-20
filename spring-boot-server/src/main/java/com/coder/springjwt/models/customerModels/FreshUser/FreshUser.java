package com.coder.springjwt.models.customerModels.FreshUser;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
@Table(	name = "freshUser",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
        })
public class FreshUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank
    private String username;


    private String mobileOtp;

    @Column(name = "isVerified", columnDefinition = "varchar(255) default 'N'")
    private String isVerified;

}
