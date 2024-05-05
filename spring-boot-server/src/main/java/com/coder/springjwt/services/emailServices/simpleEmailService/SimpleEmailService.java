package com.coder.springjwt.services.emailServices.simpleEmailService;

import com.coder.springjwt.dto.emailDto.EmailDetailsDto;
import org.springframework.stereotype.Component;

@Component
public interface SimpleEmailService {

    public String sendSimpleMail(EmailDetailsDto emailDetailsDto);

}
