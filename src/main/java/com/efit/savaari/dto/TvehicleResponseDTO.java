package com.efit.savaari.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class TvehicleResponseDTO {

    private Long id;
    private String vehicleNumber;
    
    private Long user;

    // Vehicle details
    private String type;
    private String model;
    private String capacity;

    private String driver;
    private String driverPhone;
    private String currentLocation;

    private String fuelEfficiency;
    private boolean maintenanceRequired;

    private int year;
    private String chassisNumber;
    private String engineNumber;
    private String permitType;
    private String ownerName;

    // Dates
    private LocalDate insuranceExpiry;
    private LocalDate fitnessExpiry;
    private LocalDate lastService;
    private LocalDate nextService;
    private String registrationType;

    // Status
    private boolean active;
    private boolean cancel;

    // Organization info
    private Long orgId;
    private String branchCode;
    private String branchName;

    // ✅ Child documents
    private List<TvehicleDocumentResponseDTO> documents;
}

