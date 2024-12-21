package com.coder.springjwt.dtos.adminDtos.categoriesDtos.bornDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FileMetadata {

    private String title;
    private String description;
    private String fileName;
}
