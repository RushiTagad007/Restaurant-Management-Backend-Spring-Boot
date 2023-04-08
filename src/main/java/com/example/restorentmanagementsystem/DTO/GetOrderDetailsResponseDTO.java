package com.example.restorentmanagementsystem.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetOrderDetailsResponseDTO {
    public Long Id;
    public String InsertionDate;

    public Long UserID;

    public Long MenuID;

    public Long TableID;

    public String UserName;

    public String PhoneNumber;

    public String Status;
    public Long Price;

}
