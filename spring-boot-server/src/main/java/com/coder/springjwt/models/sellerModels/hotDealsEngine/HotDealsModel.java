package com.coder.springjwt.models.sellerModels.hotDealsEngine;

import com.coder.springjwt.models.entities.baseEntity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hotDeals")
public class HotDealsModel extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String categoryId;

    private String engineId;

    private String name;

    private String offerName;

    private String fileUrl;

    private String dealColor;

    //Optional
    private String redirectUrl;

    private Boolean status = Boolean.FALSE;

    @ManyToOne
    @JoinColumn( referencedColumnName = "id" )
    private HotDealsEngineModel hotDealsEngineModel;

}
