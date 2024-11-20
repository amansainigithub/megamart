package com.coder.springjwt.models.adminModels.catalog.catalogHeight;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CatalogHeight")
public class CatalogHeightModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Size(min=4, max=100)
    @NotBlank(message = "Catalog Height must Not be Blank")
    @Column(unique = true)
    private String catalogHeight;

    private String defaultName;

    private String description;

    private boolean isActive = Boolean.FALSE;
}
