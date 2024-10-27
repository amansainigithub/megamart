package com.coder.springjwt.models.adminModels.catalog.catalogNetQuantity;

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
@Table(name = "NetQuantity")
public class CatalogNetQuantityModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Size(min=4, max=100)
    @NotBlank(message = "netQuantity must Not be Blank")
    private String netQuantity;

    private String defaultName;

    private String description;

    private boolean isActive = Boolean.FALSE;
}
