package com.efit.savaari.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TvehicleDTO {
	
	private Long id;
	private String vehicleNumber;
    private Long userId;
	private String type;
	private String model;
	private String capacity;
	private LocalDate insuranceExpiry;
	private LocalDate fitnessExpiry;
	private LocalDate lastService;
	private LocalDate nextService;
	private String driver;
	private String driverPhone;
	private String currentLocation;
	private String fuelEfficiency;
	private boolean maintenanceRequired;
	private int year;
	private String chassisNumber;
	private String engineNumber;
	private String permitType;
	private String ownerName;
	private boolean active;
	private String createdBy;
	private Long orgId;
	private String branchCode;
	private String branchName;
	private String registrationType;

}
