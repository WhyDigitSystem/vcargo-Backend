package com.efit.savaari.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyProfileResponseDTO {

	private Long id;
	private String companyCode;
	private String companyName;
	private String ownerName;
	private String emailAddress;
	private Long orgId;

	private String phoneNo;
	private String gstNo;
	private String panNo;
	private String accountHolderName;
	private String accountNumber;
	private String bankName;
	private String ifscCode;
	private String website;
	private String establishedYear;
	private String termsAndConditions;

	private String createdBy;

	private String branch;
	private String branchcode;
	private byte[] companyLogo;
	
    private List<CompanyAddressResponseDTO> companyAddresses;

	
}
