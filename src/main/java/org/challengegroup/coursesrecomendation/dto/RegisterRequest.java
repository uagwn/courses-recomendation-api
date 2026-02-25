package org.challengegroup.coursesrecomendation.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank(message = "Name cannot be blank")
    @Size(min = 3, max = 100, message = "Name has to be between 3 and 100 characters")
    private String name;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Invalid Email")
    private String email;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 6, message = "Password has to be at least 6 characters")
    private String password;
}
