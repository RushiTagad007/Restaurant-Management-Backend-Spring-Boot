package com.example.restorentmanagementsystem.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data @NoArgsConstructor @AllArgsConstructor
public class LoginRequestDTO {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String Role;
    //private UserRole role;
}
