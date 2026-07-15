package com.efit.savaari.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EscalationDashboardDTO {

	private String type;
    private String vehicleNumber;
    private String driver;
    private LocalDate dueDate;
    private String status;
    private String severity;
    private Long days;
}
