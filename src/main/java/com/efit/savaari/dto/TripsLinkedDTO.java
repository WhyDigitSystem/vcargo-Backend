package com.efit.savaari.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TripsLinkedDTO {

	private String trips;
	private String origin;
	private String destination;
	private String vechile;
	private String status;

}
