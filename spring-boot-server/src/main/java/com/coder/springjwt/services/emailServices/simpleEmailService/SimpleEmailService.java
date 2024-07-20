package com.coder.springjwt.services.emailServices.simpleEmailService;

import com.coder.springjwt.payload.emailPayloads.EmailDetailsPayload;
import org.springframework.stereotype.Component;

@Component
public interface SimpleEmailService {

    public String sendSimpleMail(EmailDetailsPayload emailDetailsPayload);

}
