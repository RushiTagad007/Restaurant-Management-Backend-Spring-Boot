package com.example.restorentmanagementsystem.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterResponseDTO {
    public boolean isSuccess;
    public String message;
    public String token;
    public String email;
    public String name;
}
