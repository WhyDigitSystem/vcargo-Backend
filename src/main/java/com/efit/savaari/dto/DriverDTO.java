package com.efit.savaari.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DriverDTO {

    private Long id;
    private String driverName;
    private String driverNumber;
//    private String status;

    // Common fields
    private String branch;
    private String branchCode;
    private String createdBy;
    private Long orgId;
    private boolean active;

	
}

