package com.efit.savaari.responseDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TripResponseDTO {

    private Long id;
    private String source;
    private String destination;
    private String customer;
    private double distance;
    private String estimatedDuration;

    private LocalDate startDate;
    private LocalTime startTime;
    private LocalDate endDate;
    private LocalTime endTime;

    private String status;
    private String tripType;
    private String goodsType;

    private BigDecimal goodsWeight;
    private BigDecimal goodsValue;
    private BigDecimal tripCost;
    private BigDecimal revenue;
    private BigDecimal profit;

    private BigDecimal fuelCost;
    private BigDecimal tollCharges;
    private BigDecimal otherExpenses;

    private String notes;
    private boolean active;

    private String createdBy;
    private String branchCode;
    private String branchName;
    private Long orgId;

    private Long user;
    private Long vehicleId;
    private Long driverId;
    private String vehicle;
    private String driver;

    private List<TripWaypointResponseDTO> waypoints;
}
