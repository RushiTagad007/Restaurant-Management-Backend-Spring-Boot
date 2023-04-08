package com.example.restorentmanagementsystem.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@EnableAutoConfiguration
public class UserOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "`OrderID`")
    public Long Id;
    public String InsertionDate;
    @Required
    private Long UserID;
    @Required
    private Long MenuID;
    @Required
    private Long TableID;
    @Required
    private String UserName;
    @Required
    private String PhoneNumber;
    @Required
    private String Status;
    private Long Price;
    @Required
    private boolean IsActive;
}
