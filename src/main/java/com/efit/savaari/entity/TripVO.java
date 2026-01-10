package com.efit.savaari.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "trip")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TripVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tripgen")
    @SequenceGenerator(
        name = "tripgen",
        sequenceName = "tripseq",
        initialValue = 1000000001,
        allocationSize = 1
    )
    @Column(name = "tripid")
    private Long tripId;

    private Long customerId;
    private Long vehicleId;
    private Long driverId;

    private String source;
    private String destination;

    private BigDecimal distance;
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

    @Column(length = 1000)
    private String notes;

    @OneToMany(mappedBy = "tripVO", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TripWaypointVO> waypoints;

    @OneToMany(mappedBy = "tripVO", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TripDocumentVO> documents;
}
