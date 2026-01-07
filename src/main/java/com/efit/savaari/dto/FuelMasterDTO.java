package com.efit.savaari.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FuelMasterDTO {


	private Long id;
    private String vehicle;
    private String driver;
    private String fuelType;
    private BigDecimal quantity;
    private BigDecimal cost;
    private BigDecimal odometerReading;
    private BigDecimal previousOdometer;
    private String station;
    private String date;   
    private String time;   
    private String receiptNumber;
    private String notes;
	private String createdBy;
	private String branchCode;
	private String branchName;
	private Long orgId;
}
