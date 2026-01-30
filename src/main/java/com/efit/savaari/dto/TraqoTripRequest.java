package com.efit.savaari.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TraqoTripRequest {
	
	private String tel;              // mandatory
    private String src;              // mandatory "lat,lng"
    private String dest;             // mandatory "lat,lng"
    private String srcname;
    private String destname;
    private String truck_number;
    private String invoice;
    private String eta_hrs;

}
