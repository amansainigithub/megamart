package com.coder.springjwt.models.sellerModels.categories;

import com.coder.springjwt.models.entities.baseEntity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ParentCategory")
public class ParentCategoryModel extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true,nullable = false )
    @Size(min=4, max=100)
    @NotBlank(message = "Category Name must Not be Blank")
    private String categoryName;

    private String defaultName;

    private String slug;

    private String description;

    @Column(length = 500)
    private String metaDescription;

    private String featuredStatus;

    private String categoryFile;

    private String permalink;

    private String user;

    private boolean isActive = Boolean.FALSE;

    @OneToMany(cascade = CascadeType.ALL , mappedBy = "parentCategory")
    @JsonIgnore
    private List<ChildCategoryModel> childCategoryModelList;



}
