package com.efit.savaari.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class TripDTO {

    private Long tripId; // null = create, not null = update

    private Long customerId;
    private Long vehicleId;
    private Long driverId;

    private String source;
    private String destination;

    private BigDecimal distance;
    private String estimatedDuration;

    private String startDate;
    private String startTime;
    private String endDate;
    private String endTime;

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

    private List<TripWaypointDTO> waypoints;
    private List<TripDocumentDTO> documents;
}

