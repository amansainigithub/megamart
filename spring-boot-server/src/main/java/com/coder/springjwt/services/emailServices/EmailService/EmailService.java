package com.coder.springjwt.services.emailServices.EmailService;

import com.coder.springjwt.payload.emailPayloads.EmailHtmlPayload;
import com.coder.springjwt.payload.emailPayloads.EmailPayload;
import org.springframework.stereotype.Component;

@Component
public interface EmailService {

    public String sendSimpleMail(EmailPayload emailPayload);

    public String sendHtmlMail(EmailHtmlPayload emailHtmlPayload);

    public void saveEmailData(EmailPayload emailPayload);

}
