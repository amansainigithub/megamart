package com.coder.springjwt.dtos.sellerDtos.categoriesDtos.bornDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FileMetadata {

    private String title;
    private String description;
    private String fileName;
}
