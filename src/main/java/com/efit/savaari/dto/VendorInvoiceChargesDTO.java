package com.efit.savaari.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VendorInvoiceChargesDTO {

	private String purpose;
	private BigDecimal amount;
	private String remarks;
	private String trip;
	private String status;
	
}
