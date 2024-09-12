package com.coder.springjwt.payload.emailPayloads;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailPayload {
    private String recipient;

    private String subject;

    private String content;

    private String mailArea;

    private String status;




}
