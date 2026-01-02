package com.efit.savaari.dto;

import java.time.LocalDate;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendorDetailsDTO {

	private Long id;
	private LocalDate effectioveTo;
	private LocalDate effectiveFrom;
	private String contractAttachmentName;
	private String backgroundVerificationName;
	private String securitycheckName;
	
//	private byte[] contractAttachment;
//	private byte[] backgroundVerification;
//	private byte[] securityCheck;

}