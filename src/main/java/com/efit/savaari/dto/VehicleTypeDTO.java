package com.efit.savaari.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleTypeDTO {

	
	private Long id;
	private String vehicleType;
	private String status;
	private double mileage;
	private String unit;
	private double hight;
	private double width;
	private double length;
	private double vehicleSqftCapacity;
	private double vehicleTonnageCapacity;
	
	
	private String branch;
	private String branchCode;
	private String createdBy;
	private Long orgId;
	
	
}
