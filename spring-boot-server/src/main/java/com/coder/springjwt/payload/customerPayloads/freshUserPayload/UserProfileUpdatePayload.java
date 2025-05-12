package com.coder.springjwt.payload.customerPayloads.freshUserPayload;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserProfileUpdatePayload {

    @NotNull(message = "First name cannot be null")
    @NotBlank(message = "First name cannot be blank")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;

    @NotNull(message = "Last name cannot be null")
    @NotBlank(message = "Last name cannot be blank")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    private String lastName;


    @Size(min = 2, max = 50, message = "Customer Gender must be between 2 and 50 characters")
    private String customerGender;
}
