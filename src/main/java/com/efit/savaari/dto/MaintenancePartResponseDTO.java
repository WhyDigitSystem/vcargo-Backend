package com.efit.savaari.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaintenancePartResponseDTO {
	
	private Long id;
    private String name;
    private Integer quantity;
    private BigDecimal cost;

}
