package com.efit.savaari.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRateDTO {

	private Long id;

	private String customer;
	private String namingSeries;
	private String origin;
	private String destination;
//	private Long industryId;

	private Double rate;
	private String vehicleType;
	private String rateType;
	private double weight;

	private boolean active ;
	private String createdBy;
	private Long orgId;
	private String branchCode;
	private String branch;
	

}
