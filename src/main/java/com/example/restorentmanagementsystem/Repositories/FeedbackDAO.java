package com.example.restorentmanagementsystem.Repositories;

import com.example.restorentmanagementsystem.Models.Feedback;
import com.example.restorentmanagementsystem.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackDAO extends JpaRepository<Feedback, Long> {
}
