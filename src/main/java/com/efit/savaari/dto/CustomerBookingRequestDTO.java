package com.efit.savaari.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerBookingRequestDTO {

	private Long id;
	private String customer;
	private String origin;
	private String status;
	private String destination;
	private String vehicleType;
	private int noOfVehicles;
	private LocalDate placementDate;
	private String orderType;
	private String serviceType;
	private String orderingParty;
	private String materialType;
	private String billToParty;
	private String docketNo;
	private long orgId;
	private String branchCode;
	private String branchName;
	private String createdBy;
	private boolean active;
	private String remarks;

}
