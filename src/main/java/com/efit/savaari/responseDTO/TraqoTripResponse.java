package com.efit.savaari.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TraqoTripResponse {
	
	private String status;
    private String tripId;

}
