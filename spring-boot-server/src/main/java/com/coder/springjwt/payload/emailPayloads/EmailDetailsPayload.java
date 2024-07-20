package com.coder.springjwt.payload.emailPayloads;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class EmailDetailsPayload {
    private String recipient;
    private String msgBody;
    private String subject;
    private String attachment;
}
