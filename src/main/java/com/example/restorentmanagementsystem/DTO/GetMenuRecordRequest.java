package com.example.restorentmanagementsystem.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetMenuRecordRequest {
    private Boolean IsVeg;
    private Boolean IsNonVeg;
    private Boolean IsStarter;
    private Boolean IsDesert;
    private Boolean IsMainCourse;
    private Boolean IsBeverages;
}
