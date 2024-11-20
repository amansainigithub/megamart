package com.coder.springjwt.models;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public enum CatalogRole {

    ALL ,
    QC_DRAFT ,
    QC_PROGRESS ,
    QC_ERROR ,
    QC_PASS ,


}
