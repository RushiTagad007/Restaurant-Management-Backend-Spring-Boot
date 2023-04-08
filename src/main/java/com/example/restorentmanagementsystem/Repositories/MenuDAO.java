package com.example.restorentmanagementsystem.Repositories;

import com.example.restorentmanagementsystem.Models.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuDAO extends JpaRepository<Menu, Long> {
}
