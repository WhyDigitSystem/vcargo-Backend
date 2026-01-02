package com.efit.savaari.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendorBankDetailsDTO {


	private String bankName;
	private String accountNumber;
	private String ifsc;
	private String accountHolderName;
	
}
