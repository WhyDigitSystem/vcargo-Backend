package com.efit.savaari.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FuelMasterDTO {

    private Long vehicleId;
    private Long driverId;

    private String fuelType;

    private BigDecimal quantity;
    private BigDecimal cost;

    private BigDecimal odometerReading;
    private BigDecimal previousOdometer;

    private String station;

    private String date;   // yyyy-MM-dd
    private String time;   // HH:mm

    private String receiptNumber;
    private String notes;
}
