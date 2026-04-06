package com.efit.savaari.responseDTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TripLocationResponseDTO {
	
	private String status;
    private Long tel;
    private List<Double> loc;
    private String address;
    private String time_recorded;
    private Integer distance_travel;
    private Integer total_distance;
    private String eta_hrs;
    private String eta;

}
