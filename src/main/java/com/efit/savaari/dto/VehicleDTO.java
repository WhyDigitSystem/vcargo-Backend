package com.efit.savaari.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleDTO {

	private Long id;
	private String vehicleNumber;
	private String vehicleType;
	
	private String branch;
	private String branchCode;
	private String createdBy;
	private Long orgId;
	private boolean active;

}
