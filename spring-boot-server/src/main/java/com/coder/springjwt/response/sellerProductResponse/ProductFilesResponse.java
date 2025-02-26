package com.coder.springjwt.response.sellerProductResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductFilesResponse {

    private long id;

    private String fileUrl;

    private String fileName;
}
