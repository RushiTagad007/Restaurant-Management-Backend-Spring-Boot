package com.example.restorentmanagementsystem.Repositories;

import com.example.restorentmanagementsystem.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDAO extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String email);
    Boolean existsByEmail(String email);
    Optional<User> findUserByUserName(String userName);
}
