package com.example.restorentmanagementsystem.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long FeedbackId;
    private String InsertionDate;
    private String OrderID;
    private String UserName;
    private String Feedback;
}
