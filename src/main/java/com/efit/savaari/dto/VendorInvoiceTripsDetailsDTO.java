package com.efit.savaari.dto;

import javax.persistence.Column;

import com.efit.savaari.entity.VendorInvoiceTripsDetailsVO;
import com.efit.savaari.entity.VendorInvoiceVO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VendorInvoiceTripsDetailsDTO {

	private Long id;
	private String trips;
	private String origin;
	private String destination;
	private String vehicle;
	private String status;
	
}
