package com.example.restorentmanagementsystem.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InsertMenuDetailsRequest {
    @Required
    private String MenuName;
    private String MenuDescription;
    @Required
    private Long Price;
    @Required
    private String MenuType;
    @Required
    private String MenuSubType;
    private MultipartFile File;
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
