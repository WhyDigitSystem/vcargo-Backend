package com.efit.savaari.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;

import javax.persistence.*;

import lombok.AllArgsConstructor;

import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaintenancePartDTO {
    private String name;
    private Integer quantity;
    private BigDecimal cost;
}

