package com.efit.savaari.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;

import lombok.Data;

@Data
public class VehicleHireDTO {

    private Long id;

    private LocalDate hireDate;
    private BigDecimal hireCost;
    private String pickupLocation;
    private String dropLocation;

    private String vehicleNumber;
    private String type;
    private String driver;
    private String driverPhone;
    
	private String active;

	private String createdBy;
	private Long orgId;

	private String branchCode;
	private String branchName;

}