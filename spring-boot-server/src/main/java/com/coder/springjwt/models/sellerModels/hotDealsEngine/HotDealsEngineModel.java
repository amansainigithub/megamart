package com.coder.springjwt.models.sellerModels.hotDealsEngine;

import com.coder.springjwt.models.entities.baseEntity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hotDealsEngine")
public class HotDealsEngineModel extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String dealName;

    private String description;

    private Boolean status = Boolean.FALSE;

    @OneToMany(cascade = CascadeType.ALL , mappedBy = "hotDealsEngineModel")
    @JsonIgnore
    private List<HotDealsModel> hotDealsModels;


}
