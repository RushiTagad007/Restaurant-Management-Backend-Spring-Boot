package com.example.restorentmanagementsystem.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long MenuId;
    private String InsertionDate;
    @Required
    private String MenuName;
    private String MenuDescription;
    @Required
    private Long Price;
    @Required
    private String MenuType;
    @Required
    private String MenuSubType;
    @Required
    private String ImageUrl;
    @Required
    private String StockName_1;
    @Required
    private Long StockQuantity_1;
    @Required
    private String StockName_2;
    @Required
    private Long StockQuantity_2;
    @Required
    private String StockName_3;
    @Required
    private Long StockQuantity_3;

    private Boolean IsActive;
}
