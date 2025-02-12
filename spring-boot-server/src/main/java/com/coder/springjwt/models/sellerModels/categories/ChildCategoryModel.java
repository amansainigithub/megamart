package com.coder.springjwt.models.sellerModels.categories;

import com.coder.springjwt.models.entities.baseEntity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ChildCategory")
@ToString
public class ChildCategoryModel extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true,nullable = false )
    @Size(min=4, max=100)
    @NotBlank(message = "Category Name must Not be Blank")
    private String categoryName;

    private String defaultName;

    private String slug;

    @Column(length = 500)
    private String description;

    private String metaDescription;

    private String featuredStatus;

    private String categoryFile;

    private String permalink;

    private String user;

    private boolean isActive = Boolean.FALSE;

    @ManyToOne
    @JoinColumn( referencedColumnName = "id" )
    private ParentCategoryModel parentCategory;

    @OneToMany(mappedBy = "childCategoryModel",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<BabyCategoryModel> babyCategoryModel ;


}
