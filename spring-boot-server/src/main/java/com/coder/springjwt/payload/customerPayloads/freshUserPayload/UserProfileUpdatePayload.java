package com.coder.springjwt.payload.customerPayloads.freshUserPayload;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserProfileUpdatePayload {


    @NotNull(message = "Email cannot be null")
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    @Pattern(
            regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$",
            message = "Email must be a valid format (e.g., user@example.com)"
    )
    private String email;

    @NotNull(message = "First name cannot be null")
    @NotBlank(message = "First name cannot be blank")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;

    @NotNull(message = "Last name cannot be null")
    @NotBlank(message = "Last name cannot be blank")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    private String lastName;
}
