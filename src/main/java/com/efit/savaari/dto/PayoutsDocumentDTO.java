package com.efit.savaari.dto;

import javax.persistence.Column;
import javax.persistence.Lob;

import com.efit.savaari.entity.PayoutsDocumentVO;
import com.efit.savaari.entity.PayoutsVO;

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
