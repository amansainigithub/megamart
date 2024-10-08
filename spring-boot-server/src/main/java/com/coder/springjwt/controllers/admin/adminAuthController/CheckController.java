package com.coder.springjwt.controllers.admin.adminAuthController;


import org.springframework.context.annotation.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
// Import the required packages

import com.cloudinary.*;
import com.cloudinary.utils.ObjectUtils;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;


@RestController
@RequestMapping("/admin/checkDelete")
public class CheckController {

    // Set your Cloudinary credentials



    @DeleteMapping("/userDelete")

    public ResponseEntity<?> userDelete()
    {
        return ResponseEntity.ok("Delted Success");
    }

    // Copy and paste your API environment variable

    String CLOUDINARY_URL="cloudinary://211869472659872:jtMvpCA3CS2DviLvCH_tkFFatxY@doeatgrpl";

    @PostMapping("/iAmPost")
    public ResponseEntity<?> iAmPost(@RequestParam("file") MultipartFile file)
    {


        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "doeatgrpl",
                "api_key", "211869472659872",
                "api_secret", "jtMvpCA3CS2DviLvCH_tkFFatxY",
                "secure", true));


// Upload the image
        Map params1 = ObjectUtils.asMap(
                "use_filename", true,
                "unique_filename", false,
                "overwrite", true
        );

        try {
            System.out.println(
                    cloudinary.uploader().upload(file.getBytes(), params1));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return ResponseEntity.ok("POST Success");
    }


}
