package com.efit.savaari.entity;

import java.math.BigDecimal;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "maintenance_parts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaintenancePartVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "maintenancepartgen")
    @SequenceGenerator(
        name = "maintenancepartgen",
        sequenceName = "maintenancepartseq",
        initialValue = 1000000001,
        allocationSize = 1
    )
    private Long partId;

    private String name;
    private Integer quantity;
    private BigDecimal cost;

    @ManyToOne
    @JoinColumn(name = "maintenanceid")
    private MaintenanceVO maintenanceVO;
}
