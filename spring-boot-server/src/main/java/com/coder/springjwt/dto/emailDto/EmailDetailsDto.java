package com.coder.springjwt.dto.emailDto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class EmailDetailsDto {
    private String recipient;
    private String msgBody;
    private String subject;
    private String attachment;
}
