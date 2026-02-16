package com.efit.savaari.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TyreMasterDTO {

    private Long id; // null = create, not null = update
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
	private String createdBy;
	private String branchCode;
	private String branchName;
	private Long orgId;
	private String vehicle;
    private Long user;
}
