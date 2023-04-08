package com.example.restorentmanagementsystem.Repositories;

import com.example.restorentmanagementsystem.Models.Stock;
import com.example.restorentmanagementsystem.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockDAO extends JpaRepository<Stock, Long> {
}
