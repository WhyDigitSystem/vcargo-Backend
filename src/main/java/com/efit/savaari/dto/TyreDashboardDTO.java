package com.efit.savaari.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;

@Data
public class TyreDashboardDTO {
    private Long id;
    private String Vehicle;
    private String brand;
    private String position;
    private String status;
    private String vehicleNumber;
    
	private LocalDate installedDate;
	private BigDecimal depth;
}
