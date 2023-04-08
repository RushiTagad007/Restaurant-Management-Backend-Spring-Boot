package com.example.restorentmanagementsystem.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class BasicResponseDTO<T> {
    public boolean isSuccess;
    public String message;
    public int TotalPage;
    public T data;
}
