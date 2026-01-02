package com.efit.savaari.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VendorResponseDTO {

	private Long id;
	private String lrNumber;
	private String vehicleNumber;
	private String driverNumber;
	private String driverName;

}
