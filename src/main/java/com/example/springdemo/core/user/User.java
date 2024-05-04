package com.example.springdemo.core.user;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

import static com.example.springdemo.core.ConstantValidator.EMAIL_PATTERN;

@Data
@Builder
@AllArgsConstructor
public class User {

    @NotBlank(message = "Email is mandatory")
    @Pattern(regexp = EMAIL_PATTERN, message = "Please enter a valid email")
    private String email;
    @NotBlank(message = "First name is mandatory")
    private String firstName;
    @NotBlank(message = "Last name is mandatory")
    private String lastName;
    @NotNull(message = "Birth date is mandatory")
    @Past(message = "Birth date must be earlier than current date")
    private Date birthDate;
    private String address;
    private String phoneNumber;
}
