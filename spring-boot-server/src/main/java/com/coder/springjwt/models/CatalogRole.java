package com.coder.springjwt.models;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public enum CatalogRole {

    ALL ,
    DRAFT ,

    QC_IN_PROGRESS ,

    QC_ERROR ,

    QC_PASS ,


}
