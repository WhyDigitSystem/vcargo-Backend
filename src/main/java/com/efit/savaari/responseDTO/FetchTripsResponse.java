package com.efit.savaari.responseDTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FetchTripsResponse {
	
	private String status;
    private int tot_trips;
    private int tot_consent_pending;
    private List<TraqTripDTO> trips;

}
