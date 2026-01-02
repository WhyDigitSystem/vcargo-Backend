package com.efit.savaari.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {

	private Long id;
	private String customerCode;
	private String customerName;
	private String phoneNumber;
//	private Long industryId;
	private String customerType;


	private String salesPerson;
//	private String location;



	private String email;
	private String gstNumber;
	private String panNumber;

	private String pocName;
	private String pocEmail;
	private String pocNumber;
	private boolean active ;
	private String createdBy;
	private Long orgId;
	private String branchCode;
	private String branch;
	
	List<CustomerAddressDTO> customerAddressDTO;
}
