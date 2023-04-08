package com.example.restorentmanagementsystem.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDTO {
    @NotBlank
    private String userName;
    @NotBlank
    private String email;
    @NotBlank
    private String  password;
    //@NotBlank
    //private String  confirmPassword;
    @NotBlank
    private String  masterPassword;
    @NotBlank
    private String role;
    //private UserRole role;
}
