package com.efit.savaari.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class FuelDashboardDTO {

    private String id;          // FUL-0789 or fuelid
    private String vehicle;     // MH-12-AB-1234
    private String station;
    private BigDecimal quantity;    // "250 liters"
    private BigDecimal total;        // "₹1200"
    private String date;        // formatted
    private String driver;      // driver name
    private BigDecimal rate;      // driver name

}

