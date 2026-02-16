package com.efit.savaari.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayoutsDocumentDTO {

//	private Long id;
//	private byte[] document;
	private String documentType;
	private String remarks;
	private String trip;
	private String vehicleNumber;

	
}
