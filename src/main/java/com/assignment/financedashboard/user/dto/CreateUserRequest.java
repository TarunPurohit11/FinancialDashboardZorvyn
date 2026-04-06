package com.assignment.financedashboard.user.dto;

import com.assignment.financedashboard.common.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateUserRequest {

    @NotBlank(message = "Name is required")
    public String name;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    public String email;

    @Size(min = 6, message = "Password must be at least 6 characters")
    public String password;

    @NotNull(message = "Role is required")
    public Role role;
}
