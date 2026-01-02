package com.efit.savaari.dto;

import javax.persistence.Column;
import javax.persistence.Lob;

import com.efit.savaari.entity.VendorInvoiceTripsDocumentVO;
import com.efit.savaari.entity.VendorInvoiceVO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VendorInvoiceTripsDocumentDTO {

	private String docType;
	private String remarks;
	private String trip;
	private String vehicleNumber;
	
}
