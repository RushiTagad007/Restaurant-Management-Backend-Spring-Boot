package com.example.restorentmanagementsystem.DTO;

import com.example.restorentmanagementsystem.Models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class LoginResponseDTO {
    public boolean isSuccess;
    public String message;
    public String token;
    public String name;
    public String email;
    public String contact;
    public User user;
}
