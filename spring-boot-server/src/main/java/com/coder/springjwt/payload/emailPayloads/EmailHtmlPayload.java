package com.coder.springjwt.payload.emailPayloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailHtmlPayload {

    private String recipient;

    private String subject;

    //for send to Html content
    private String htmlContent;

}
