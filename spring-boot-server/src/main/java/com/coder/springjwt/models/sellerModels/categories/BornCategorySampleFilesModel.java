package com.coder.springjwt.models.sellerModels.categories;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "BornCategorySampleFiles")
@ToString
public class BornCategorySampleFilesModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    private String description;

    private String fileName;

    @ManyToOne
    @JsonIgnore
    private BornCategoryModel bornCategoryModel;
}
