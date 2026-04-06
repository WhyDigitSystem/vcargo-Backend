package com.efit.savaari.responseDTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LastLocationDTO {

	private List<Double> loc;
    private String time;
    private String address;
    private Integer distance_remained;
}
