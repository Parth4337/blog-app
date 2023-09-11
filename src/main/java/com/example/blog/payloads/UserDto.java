package com.example.blog.payloads;

import com.example.blog.entities.Role;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class UserDto {

    private int id;

    @NotEmpty
    @Size(min = 4, message = "User name should be minimum of 4 characters")
    private String name;

    @Email(message = "Email address is not valid")
    private String email;

    @NotEmpty(message = "Password should not be empty")
    @Size(min = 6, message = "Password should be at least 6 characters")
    private String password;
    private Set<Role> roles = new HashSet<>();
    private String about;
}
