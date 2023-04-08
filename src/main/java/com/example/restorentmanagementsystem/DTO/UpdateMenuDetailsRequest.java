package com.example.restorentmanagementsystem.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMenuDetailsRequest {
    @Required
    private Long MenuID;
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
