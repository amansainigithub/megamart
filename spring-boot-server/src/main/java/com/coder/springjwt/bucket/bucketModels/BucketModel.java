package com.coder.springjwt.bucket.bucketModels;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BucketModel {
    private String bucketUrl;
    private String fileName;

}
