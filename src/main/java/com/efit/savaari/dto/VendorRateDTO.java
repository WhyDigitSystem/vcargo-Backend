package com.efit.savaari.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendorRateDTO {

	private Long id;
	private String state;
	private String vendor;
	private String namingSeries;
	private LocalDate effectiveTo;
	private LocalDate effectiveFrom;
	private String priority;
	private String origin;
	private String destination;
	private String rate;
	private String vehicleType;
	private String rateType;
	private String weight;
	private String detentioncharge;
	private int rank;
	private String unloadingCharges;
	private String remarks;
	private String extraCharges;
	private boolean active ;
	private String createdBy;
	private Long orgId;
	private String branchCode;
	private String branch;

}
