package com.efit.savaari.responseDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FuelResponseDTO {

	private Long id;
	private String fuelType;
	private BigDecimal quantity;
	private BigDecimal cost;
	private BigDecimal odometerReading;
	private BigDecimal previousOdometer;
	private String station;
	private LocalDate date;
	private LocalTime time;
	private String receiptNumber;
	private String notes;
	private boolean active;
	private String createdBy;
	private String updatedBy;
	private boolean cancel;
	private String branchCode;
	private String branchName;
	private Long orgId;
	private Long vehicleId;
	private String vehicle;
	private Long driverId;
	private String driver;
	private Long user;
}
