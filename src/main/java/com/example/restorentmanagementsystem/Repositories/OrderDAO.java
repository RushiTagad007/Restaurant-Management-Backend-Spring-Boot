package com.example.restorentmanagementsystem.Repositories;

import com.example.restorentmanagementsystem.Models.UserOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDAO extends JpaRepository<UserOrder, Long> {
}
