package com.coder.springjwt.models.adminModels.productStatus;


import com.coder.springjwt.models.entities.baseEntity.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "productReviewStatus")
@Data
public class ProductReviewStatusModel extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 500 ,unique = true)
    private String productStatus;

    @Column(length = 500 ,unique = true)
    private String productStatusValue;

    private String description;

    private String mailSubject;

    private String mailBody;

    private boolean isActive = Boolean.TRUE;


}
