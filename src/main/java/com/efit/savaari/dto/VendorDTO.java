package com.efit.savaari.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VendorDTO {

	private Long id;
	private String vendorCode;
//    private Long industryId;

	private String status;
	private String organization;
	private String primaryPhoneNumber;
	private String primaryEmail;
	private String additionalPhoneNumber;
	private String additionalEmails;
	private String address;
//	private String accountNumber;
//	private String ifsc;
//	private String accountHolderName;

	private String vendorType;
	private double advancePercent;
	private String creditPeriod;
	private double tdsPercent;
	private String vendorSpotId;
	private String vendoruuid;
	private String tags;
	
	private String pocName;
	private String pocEmail;
	private String pocNumber;

	private boolean active ;
	private String createdBy;
	private Long orgId;
	private String branchCode;
	private String branch;
	
	List<VendorUsersDTO> vendorUsersDTO;
	List<VendorGstDTO> vendorGstDTO;
	List<VendorDetailsDTO> vendorDetailsDTO;
	List<VendorBankDetailsDTO> vendorBankDetailsDTO;

}
