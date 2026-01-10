package com.efit.savaari.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TyreMasterResponseDTO {
	
	private Long id;
	private String serialNumber;
	private String brand;
	private String model;
	private String size;
	private String position;
	private String status;
	private LocalDate purchaseDate;
	private BigDecimal purchaseCost;
	private BigDecimal odometerReading;
	private BigDecimal treadDepth;
	private Integer recommendedPressure;
	private Integer pressure;
	private String notes;
	private boolean active;
	private String createdBy;
	private String updatedBy;
	private boolean cancel;
	private String branchCode;
	private String branchName;
	private Long orgId;
	private String vehicle;
	private Long vehicleId;
    private Long user;

}
