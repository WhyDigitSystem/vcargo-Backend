package com.efit.savaari.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayoutsDTO {

	private Long id;
	private String namingSeries;
	private String payoutStatus;
	private String vendor;
	private String purpose;
	private String approveBy;
	private String approveOn;
	private BigDecimal invoiceAmount;
	private int quantity;
	private BigDecimal totalAdditionalCharges;
	private BigDecimal totalTripCost;
	private String payoutReference;
	private String invoice;
	private String invoiceType;
//	private byte[] invoiceFile;
	private LocalDate fromDate;
	private LocalDate toDate;

	private String createdBy;
	private Long orgId;
	private String branchCode;
	private String branch;
	
	List<PayoutsVendorDetailsDTO> payoutsVendorDetailsDTO;
	List<PayoutsTimeLineDTO> payoutsTimeLineDTO;
	List<PayoutsDocumentDTO> payoutsDocumentDTO;
	List<PayoutTripsDetailsDTO> payoutTripsDetailsDTO;

}
