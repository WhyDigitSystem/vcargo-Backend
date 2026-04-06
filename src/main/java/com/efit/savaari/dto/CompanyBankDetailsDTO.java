package com.efit.savaari.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyBankDetailsDTO {

	
	private String accountHolderName;
	private String accountNumber;
	private String bankName;
	private String ifscCode;
	private String branch;
	private String branchCode;
	private boolean primary;

	
	
}
