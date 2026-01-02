package com.efit.savaari.dto;

import java.math.BigDecimal;
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
public class VendorInvoiceDTO {

	
	private Long id;
    private Long vendorId;
	private String invoiceType;
	private BigDecimal tripCost;
	private BigDecimal totalAdditionalCharges;
	private BigDecimal subTotal;
	private BigDecimal totalAmount;
	private String tds;
	private BigDecimal payableAmount;
	private String invoiceNumber;
	private LocalDate dueDate;
	private LocalDate invoiceDate;
	private String description;
	private LocalDate fromDate;
	private LocalDate toDate;

	private String createdBy;
	private Long orgId;
	
	private String branchCode;
	private String branch;
	
	List<VendorInvoiceChargesDTO> vendorInvoiceChargesDTO;
	List<VendorInvoiceTripsDetailsDTO> vendorInvoiceTripsDetailsDTO;
	List<VendorInvoiceTripsDocumentDTO> vendorInvoiceTripsDocumentDTO;

}
