package com.efit.savaari.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TypeMasterDTO {

    private Long typeId; // null = create, not null = update

    private String serialNumber;
    private String brand;
    private String model;
    private String size;

    private Long vehicleId;
    private String position;
    private String status;

    private String purchaseDate; // yyyy-MM-dd
    private BigDecimal purchaseCost;

    private BigDecimal odometerReading;
    private BigDecimal treadDepth;

    private Integer recommendedPressure;
    private Integer pressure;

    private String notes;
}
