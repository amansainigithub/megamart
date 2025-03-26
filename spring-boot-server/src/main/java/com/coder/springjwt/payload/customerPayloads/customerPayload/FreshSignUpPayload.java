package com.coder.springjwt.payload.customerPayloads.customerPayload;


import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class FreshSignUpPayload {

    @NotNull(message = "First name cannot be null")
    @NotBlank(message = "First name cannot be blank")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;

    @NotNull(message = "Last name cannot be null")
    @NotBlank(message = "Last name cannot be blank")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    private String lastName;

    @NotNull(message = "Email cannot be null")
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    @Pattern(
            regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$",
            message = "Email must be a valid format (e.g., user@example.com)"
    )
    private String email;

    @NotNull
    @NotBlank
    private String username;

    @NotNull
    @NotBlank
//    @Pattern(
//            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
//            message = "Password must be at least 8 characters long, contain one uppercase letter, one lowercase letter, one digit, and one special character."
//    )
    private String password;


}
