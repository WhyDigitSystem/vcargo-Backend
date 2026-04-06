package com.efit.savaari.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;

@Data
public class MaintenanceDashboardDTO {
    private Long id;
    private LocalDate completedDate;
    private String description;
    private String vehicleNo;
    private String type;
    private String status;
	private String priority; // low / medium / high
	private BigDecimal totalCost; // low / medium / high

}
