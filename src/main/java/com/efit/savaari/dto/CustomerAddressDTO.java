package com.efit.savaari.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerAddressDTO {

	private String primaryAddress;
	private String additionalAddress;
	private String city;
	private String type;
	private int pincode;
	private String state;

}
