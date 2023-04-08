package com.example.restorentmanagementsystem.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InsertOrderDetailsRequest {
    @Required
    private Long UserID;
    @Required
    private Long MenuID;
    @Required
    private String UserName;
    @Required
    private String MobileNumber;
    @Required
    private Long TableID;

}
