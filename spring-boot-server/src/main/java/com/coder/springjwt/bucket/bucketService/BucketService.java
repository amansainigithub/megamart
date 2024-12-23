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

    //Cloudnairy Api Settings
    private final String CLOUDINARY = "cloudinary";
    private final String CLOUD_NAME = "doeatgrpl";
    private final String API_KEY = "211869472659872";
    private final String API_SECRET = "jtMvpCA3CS2DviLvCH_tkFFatxY";
    private final Boolean SECURE = Boolean.TRUE;

    @Autowired
    private ObjectMapper objectMapper;


    @Autowired
    private AmazonS3 s3Client;

    public BucketModel uploadFile(MultipartFile file) {

        if(CLOUDINARY == "cloudinary1")
        {
            return  this.uploadCloudinaryFile(file);
        }

       try {
           File fileObj = convertMultiPartFileToFile(file);
           String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
           s3Client.putObject(new PutObjectRequest(bucketName, fileName, fileObj));
           fileObj.delete();

           //Creating Bucket Models
           return new BucketModel(BucketUrlMappings.BUCKET_URL + fileName,fileName);
       }
       catch (Exception e)
       {
           log.error("Exception : " , e);
           log.error("AWS Configuration Problem :::::::::::::::: {}");
           return this.getRandomFile();
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
           log.error("Exception : " , e);
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



    //Cloudniary File Upload Process Starting

    public BucketModel uploadCloudinaryFile(@RequestParam("file") MultipartFile file)
    {
        System.out.println("Cloudinary File Upload---Flying");
        try {
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                                      "cloud_name", CLOUD_NAME,
                                            "api_key", API_KEY,
                                            "api_secret", API_SECRET,
                                            "secure", SECURE));

        // Upload the image
        Map conditionVerifier = ObjectUtils.asMap(
                                        "use_filename", true,
                                               "unique_filename", file.getOriginalFilename(),
                                               "overwrite", false,
                                                "public_id", file.getOriginalFilename());

            Map cloudinaryUpload = cloudinary.uploader().upload(file.getBytes(), conditionVerifier);
            String cloudinaryResponse = objectMapper.writeValueAsString(cloudinaryUpload);
            Map<String,String > node = objectMapper.readValue(cloudinaryResponse, Map.class);

            System.out.println("======= Final Node As Json Format ============");
            System.out.println( node );

            //Creating Bucket Models
            return new BucketModel(node.get("url") ,file.getOriginalFilename().toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
