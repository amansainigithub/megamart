package com.coder.springjwt.services.emailServices.EmailService.imple;

import com.coder.springjwt.helpers.userHelper.UserHelper;
import com.coder.springjwt.payload.emailPayloads.EmailHtmlPayload;
import com.coder.springjwt.payload.emailPayloads.EmailPayload;
import com.coder.springjwt.payload.emailPayloads.EmailSendContent;
import com.coder.springjwt.repository.emailRepository.EmailRepository;
import com.coder.springjwt.services.emailServices.EmailService.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class EmailServiceImple implements EmailService {

    @Value("${spring.mail.username}")
    private String sender;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    EmailRepository emailRepository;


    public String sendSimpleMail(EmailPayload emailPayload)
    {
        // Try block to check for exceptions
        try {
            // Creating a simple mail message
            SimpleMailMessage mailMessage
                    = new SimpleMailMessage();

            // Setting up necessary details
            mailMessage.setFrom(sender);
            mailMessage.setTo(emailPayload.getRecipient());
            mailMessage.setText(emailPayload.getContent());
            mailMessage.setSubject(emailPayload.getSubject());

            // Sending the mail
            javaMailSender.send(mailMessage);

            //set mail Status
            emailPayload.setStatus("SUCCESS");

            //Save Data to DB
            this.saveEmailData(emailPayload);
            log.info("======Mail Sent Success========");

            return "email Sent Success";
        }

        // Catch block to handle the exceptions
        catch (Exception e) {
            //set mail Status
            emailPayload.setStatus("FAILED");

            //Save Data to DB
            this.saveEmailData(emailPayload);

            e.printStackTrace();
            return "Error while Sending Mail";
        }

    }

    public void saveEmailData(EmailPayload emailPayload)
    {
        Map<String,String> node =  UserHelper.getCurrentUser();
        node.get("username");
        node.get("roles");

        // Create a JSONObject from the Map
        JSONObject jsonObject = new JSONObject(emailPayload);
        // Convert the JSONObject to a JSON string
        String jsonString = jsonObject.toString();

        EmailSendContent emailSendContent = new EmailSendContent();
        emailSendContent.setUser(node.get("username"));
        emailSendContent.setRole(node.get("role"));
        emailSendContent.setContent(emailPayload.getContent());
        emailSendContent.setRequestJson(jsonString);
        emailSendContent.setResponseJson("setResponseJson");
        emailSendContent.setMailArea(emailPayload.getMailArea());
        emailSendContent.setStatus(emailPayload.getStatus());
        emailSendContent.setMailFrom(sender);
        emailSendContent.setMailTo(emailPayload.getRecipient());

        EmailSendContent save = this.emailRepository.save(emailSendContent);
        log.info("Data Saved Success Email Content ");
    }

    @Override
    public String sendHtmlMail(EmailHtmlPayload emailHtmlPayload) {
        return null;
    }
}
