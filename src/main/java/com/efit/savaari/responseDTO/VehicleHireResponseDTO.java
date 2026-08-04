package com.efit.savaari.responseDTO;


import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;

import lombok.Data;

@Data
public class VehicleHireResponseDTO {

    private Long id;
    private String vehicleNumber;
    private String type;

    private String driver;
    private String driverPhone;

    private LocalDate hireDate;
    private BigDecimal hireCost;

    private String pickupLocation;
    private String dropLocation;

    private String createdBy;
    private String updatedBy;
   	private String active;
	private Long orgId;
	private String branchCode;
	private String branchName;
}
