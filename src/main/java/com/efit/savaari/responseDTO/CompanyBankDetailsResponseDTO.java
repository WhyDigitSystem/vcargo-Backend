package com.efit.savaari.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyBankDetailsResponseDTO {

	private Long id;
	private String accountHolderName;
	private String accountNumber;
	private String bankName;
	private String ifscCode;
	private String branch;
	private String branchCode;
	private boolean primary;

}
