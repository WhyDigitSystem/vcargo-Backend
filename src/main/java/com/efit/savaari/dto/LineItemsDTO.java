package com.efit.savaari.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LineItemsDTO {

	private String origin;
	private String destination;
	private String vechileType;
	private int round;
	private String zone;
	private String way;
	
}
