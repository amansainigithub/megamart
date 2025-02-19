package com.coder.springjwt.models.sellerModels.categories;

import com.coder.springjwt.models.entities.baseEntity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "FormBuilder")
public class FormBuilderModel extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 15000)
    private String formBuilder;

    @Column(length = 1000)
    private String description;

    private String bornCategoryId;
}
