package com.efit.savaari.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestforQuotesDTO {

	private Long id;
	private String rfqDetails;
	private String natureOfContract;
	private String material;
	private LocalDate contractStartDate;
	private String contractDuration;
	private String termsAndConditions;
	private String vendorTags;
	private String numTransporter;
	private String additionalCharges;
	private String createdBy;
	private Long orgId;
	
	private String branchCode;
	private String branch;
	
	List<LineItemsDTO> lineItemsDTO;

}