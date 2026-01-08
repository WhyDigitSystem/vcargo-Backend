package com.efit.savaari.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaintenanceDTO {

    private Long maintenanceId; // null = create, not null = update

    private String title;
    private Long vehicleId;

    private String type;
    private String status;
    private String priority;

    private String scheduledDate;
    private String completedDate;

    private BigDecimal odometerReading;
    private BigDecimal cost;
    private BigDecimal estimatedCost;

    private String serviceCenter;
    private String mechanic;

    private String description;
    private String notes;

    private List<MaintenancePartDTO> parts;
}
