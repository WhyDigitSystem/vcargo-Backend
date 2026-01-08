package com.efit.savaari.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "maintenance")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaintenanceVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "maintenancegen")
    @SequenceGenerator(
        name = "maintenancegen",
        sequenceName = "maintenanceseq",
        initialValue = 1000000001,
        allocationSize = 1
    )
    @Column(name = "maintenanceid")
    private Long maintenanceId;

    private String title;
    private Long vehicleId;

    private String type;        // preventive / corrective
    private String status;      // pending / completed
    private String priority;    // low / medium / high

    private LocalDate scheduledDate;
    private LocalDate completedDate;

    private BigDecimal odometerReading;
    private BigDecimal cost;
    private BigDecimal estimatedCost;

    private String serviceCenter;
    private String mechanic;

    @Column(length = 1000)
    private String description;

    @Column(length = 1000)
    private String notes;

    @OneToMany(mappedBy = "maintenanceVO", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MaintenancePartVO> parts;
}
