package com.efit.savaari.responseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyAddressResponseDTO {

	
	private Long id;
	private String shippingAddress;
	private String billingAddress;
	private boolean primary;

}