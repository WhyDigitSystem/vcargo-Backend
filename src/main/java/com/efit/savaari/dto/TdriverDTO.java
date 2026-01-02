package com.efit.savaari.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TdriverDTO {

    private Long id;

    private String name;
    private String phone;
    private String email;
    
    private Long userId;

    private String licenseNumber;
    private LocalDate licenseExpiry;

    private String aadharNumber;
    private String address;

    private String status;
    private String experience;
    private String salary;

    private String assignedVehicle;
    private String currentLocation;
    private String bloodGroup;

    private String emergencyContact;

    private String performance;

    private LocalDate joinedDate;
    private LocalDate lastTrip;

    private boolean active;
    private String createdBy;

    private Long orgId;
    private String branchCode;
    private String branchName;

    // Child table DTO list
//    private List<TdriverDocumentsDTO> tdriverDocumentsDTO;
}
