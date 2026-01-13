package com.efit.savaari.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TripWaypointResponseDTO {

	private Long id;
	private String location;
	private Integer sequenceNo;

}
