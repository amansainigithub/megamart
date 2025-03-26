package com.coder.springjwt.payload.customerPayloads.freshUserPayload;

import lombok.Data;

@Data
public class UserprofilePayload {

    private String email;

    private String firstName;

    private String lastName;

    private String mobile;

    private String emailVerify;
}
