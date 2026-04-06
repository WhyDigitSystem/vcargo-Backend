package com.efit.savaari.dto;

import lombok.Data;

@Data
public class TripDashboardDTO {
    private Long id;
    private String vehicleNo;
    private String driverName;
    private String route;
    private String status;
}

