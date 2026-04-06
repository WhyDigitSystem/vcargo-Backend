package com.efit.savaari.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayoutTripsDetailsDTO {

//	private Long id;
	private String trips;
	private String origin;
	private String destination;
	private String vehicle;
	private String status;

}
