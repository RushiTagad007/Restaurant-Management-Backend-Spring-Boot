package com.example.restorentmanagementsystem.DTO;

import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;

public class GetAllOrderDetailsResponseDTO {

    public Long Id;
    public String InsertionDate;

    public Long UserID;

    public Long MenuID;

    public Long TableID;

    public String UserName;

    public String PhoneNumber;

    public String Status;

    public Long Price;

    public boolean IsActive;
}
