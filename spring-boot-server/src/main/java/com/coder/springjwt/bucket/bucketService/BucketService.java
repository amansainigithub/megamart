package com.coder.springjwt.bucket.bucketService;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.coder.springjwt.bucket.BucketUrlMappings;
import com.coder.springjwt.bucket.bucketModels.BucketModel;
import com.coder.springjwt.constants.sellerConstants.sellerMessageConstants.SellerMessageResponse;
import com.coder.springjwt.models.sellerModels.props.Api_Props;
import com.coder.springjwt.repository.sellerRepository.apiProps.ApiPropsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

@Service
@Slf4j
public class BucketService {

    @Value("${application.bucket.name}")
    private String bucketName;
    private final AmazonS3 s3Client;
    @Autowired
    private ObjectMapper objectMapper;

    //CLOUDINARY Api Settings
    private final String CLOUDINARY = "cloudinary";
    private final String CLOUD_NAME = "doeatgrpl";

    private final Boolean SECURE = Boolean.TRUE;


    @Autowired
    private ApiPropsRepository apiPropsRepository;

    @Autowired
    public BucketService(AmazonS3 s3Client) {
        this.s3Client = s3Client;
    }


    public BucketModel uploadFile(MultipartFile file) {
       try {
           File fileObj = convertMultiPartFileToFile(file);
           String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
           s3Client.putObject(new PutObjectRequest(bucketName, fileName, fileObj));
           fileObj.delete();
           log.info("File Write Success to S3");

           //Creating Bucket Models
           return new BucketModel(BucketUrlMappings.BUCKET_URL + fileName,fileName);
       }
       catch (Exception e)
       {
           log.error("Exception : AWS Secret Credentials Not Found | Check AWS CRED...");
           //Upload Cloudinary
           return this.uploadCloudinaryFile(file);
           //return this.getRandomFile();
       }
    }


    public byte[] downloadFile(String fileName) {
        S3Object s3Object = s3Client.getObject(bucketName, fileName);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        try {
            byte[] content = IOUtils.toByteArray(inputStream);
            return content;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public String deleteFile(String fileName) {
       try {
           s3Client.deleteObject(bucketName, fileName);
       }catch (Exception e)
       {
           e.printStackTrace();
           log.error("Error in delete File in AWS Bucket :::::::::::  {}");
       }
        return fileName + " removed ...";
    }


    private File convertMultiPartFileToFile(MultipartFile file) {
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            //log.error("Error converting multipartFile to file", e);
        }
        return convertedFile;
    }


    private BucketModel getRandomFile()
    {
        return new BucketModel(
                "https://images.unsplash.com/photo-1591696205602-2f950c417cb9?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1470&q=80",
                "BUSINESS");
    }



    //cloudinary File Upload Process Starting
    public BucketModel uploadCloudinaryFile(@RequestParam("file") MultipartFile file)
    {
        log.info("Cloudinary File Upload---Flying");
        try {

            Api_Props apiProps = apiPropsRepository.findByProvider("CLOUDINARY_FILES")
                    .orElseThrow(() -> new RuntimeException(SellerMessageResponse.CLOUDINARY_PROPS_NOT_FOUND));

            Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                                      "cloud_name", CLOUD_NAME,
                                            "api_key", apiProps.getKeyId(),
                                            "api_secret", apiProps.getKeySecret(),
                                            "secure", SECURE));
        // Upload the image
        Map conditionVerifier = ObjectUtils.asMap(
                                        "use_filename", true,
                                               "unique_filename", file.getOriginalFilename(),
                                               "overwrite", false,
                                               "public_id", file.getOriginalFilename(),
                                                "folder", "user_uploads/RADJAC");

            Map cloudinaryUpload = cloudinary.uploader().upload(file.getBytes(), conditionVerifier);
            String cloudinaryResponse = objectMapper.writeValueAsString(cloudinaryUpload);
            Map<String,String > node = objectMapper.readValue(cloudinaryResponse, Map.class);

           log.info("======= File Upload in Cloudinary ============");
            //Creating Bucket Models
            return new BucketModel(node.get("url") ,file.getOriginalFilename().toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
