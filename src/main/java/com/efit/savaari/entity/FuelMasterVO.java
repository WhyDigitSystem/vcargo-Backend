package com.efit.savaari.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "fuelmaster")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FuelMasterVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fuelmastergen")
    @SequenceGenerator(
        name = "fuelmastergen",
        sequenceName = "fuelmasterseq",
        initialValue = 1000000001,
        allocationSize = 1
    )
    @Column(name = "fuelid")
    private Long id;

    @Column(name = "vehicleid")
    private Long vehicleId;

    @Column(name = "driverid")
    private Long driverId;

    @Column(name = "fueltype")
    private String fuelType; // diesel / petrol / cng / electric

    @Column(name = "quantity")
    private BigDecimal quantity;

    @Column(name = "cost")
    private BigDecimal cost;

    @Column(name = "odometerreading")
    private BigDecimal odometerReading;

    @Column(name = "previousodometer")
    private BigDecimal previousOdometer;

    @Column(name = "station")
    private String station;

    @Column(name = "fueldate")
    private LocalDate date;

    @Column(name = "fueltime")
    private LocalTime time;

    @Column(name = "receiptnumber")
    private String receiptNumber;

    @Column(name = "notes")
    private String notes;
}
