package com.coder.springjwt.controllers;

import com.coder.springjwt.constants.adminConstants.adminUrlMappings.AdminUrlMappings;
import com.coder.springjwt.payload.emailPayloads.EmailPayload;
import com.coder.springjwt.services.emailServices.EmailService.EmailService;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(AdminUrlMappings.AUTH_BASE_URL)
public class EmailController {

    @Value("${spring.mail.username}")
    private String sender;

    @Autowired
    private EmailService emailService;

    @Autowired
    private JavaMailSender javaMailSender;

    @PostMapping("EmailChecker")
    public ResponseEntity<?> EmailChecker() {



        // Try block to check for exceptions
        try {
            EmailPayload emailPayload = new EmailPayload();
            emailPayload.setRecipient("amansaini140707@gmail.com");
            emailPayload.setSubject("Pass-Key");
            emailPayload.setContent("Hi Your Pass-Key : SpringBootServerApplication" +
                    " : Started SpringBootServerApplication in 17.712 seconds (proce");
            emailPayload.setMailArea("TRIAL");

            emailService.sendSimpleMail(emailPayload);

            return ResponseEntity.ok("mail Sent success");
        }

        // Catch block to handle the exceptions
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok("mail Sent Failed");

        }
    }




    @PostMapping("EmailCheckerhtml")
    public ResponseEntity<?> EmailCheckerhtml() {


        String to = "amansaini140707@example.com";
        String subject = "Test HTML Email";
        String htmlContent = "<h1>Welcome</h1><p>This is a test" +
                " <a href=\"https://www.w3schools.com\">Visit W3Schools.com!</a> with HTML content.</p>";


        // Try block to check for exceptions
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(htmlContent, true);  // true indicates HTML
            helper.setTo(sender);
            helper.setSubject(subject);
            helper.setFrom("amansaini140707@gmail.com");
            javaMailSender.send(mimeMessage);


            return ResponseEntity.ok("HTML -- mail Sent success");
        }

        // Catch block to handle the exceptions
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok("mail Sent Failed");

        }
    }





}
