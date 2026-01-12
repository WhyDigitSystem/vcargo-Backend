package com.efit.savaari.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceResponseDTO {

    private Long id;
    private String title;
    private String type;
    private String status;
    private String priority;

    private LocalDate scheduledDate;
    private LocalDate completedDate;
    private BigDecimal odometerReading;

    private BigDecimal estimatedCost;
    private BigDecimal totalCost;
    private BigDecimal totalQty;

    private String serviceCenter;
    private String mechanic;
    private String description;
    private String notes;

    private boolean active;

    private Long vehicleId;
    private String vehicle;

    private Long user;

    private String createdBy;
    private String branchCode;
    private String branchName;
    private Long orgId;

    private List<MaintenancePartResponseDTO> parts;
}
