package com.efit.savaari.dto;

import java.math.BigDecimal;

import javax.persistence.Column;

import com.efit.savaari.entity.VendorInvoiceChargesVO;
import com.efit.savaari.entity.VendorInvoiceVO;

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
