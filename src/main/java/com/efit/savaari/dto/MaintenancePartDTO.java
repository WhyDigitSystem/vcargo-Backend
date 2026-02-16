package com.efit.savaari.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaintenancePartDTO {
	private String name;
	private Integer quantity;
	private BigDecimal cost;
}
