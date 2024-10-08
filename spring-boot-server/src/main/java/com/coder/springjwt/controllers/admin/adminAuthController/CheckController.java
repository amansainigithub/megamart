package com.coder.springjwt.controllers.admin.adminAuthController;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/checkDelete")
public class CheckController {


    @DeleteMapping("/userDelete")
    public ResponseEntity<?> userDelete()
    {
        return ResponseEntity.ok("Delted Success");
    }

    @PostMapping("/iAmPost")
    public ResponseEntity<?> iAmPost()
    {
        return ResponseEntity.ok("POST Success");
    }


}
