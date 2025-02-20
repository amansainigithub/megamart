package com.coder.springjwt.models.sellerModels.homeSliders;

import com.coder.springjwt.models.entities.baseEntity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "HomeSlider")
public class HomeSliderModel extends BaseEntity  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    private String fileUrl;

    private String slug;

    private String categoryId;

    private String categoryName;

    private Boolean status = Boolean.FALSE;
}
