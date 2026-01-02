package com.efit.savaari.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoutesDetailsDTO {
	
	private String name;
	private int distance;
	private int noOfTolls;
	private int tollCost;
	private int fuelCost;
	private int totalCost;
	private BigDecimal savings;
	private int duration;
	private boolean defaults;
	
}
