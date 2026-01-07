package com.efit.savaari.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "typemaster")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TypeMasterVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "typemastergen")
    @SequenceGenerator(
        name = "typemastergen",
        sequenceName = "typemasterseq",
        initialValue = 1000000001,
        allocationSize = 1
    )
    @Column(name = "typeid")
    private Long typeId;

    @Column(name = "serialnumber")
    private String serialNumber;

    @Column(name = "brand")
    private String brand;

    @Column(name = "model")
    private String model;

    @Column(name = "size")
    private String size;

    @Column(name = "vehicleid")
    private Long vehicleId;

    @Column(name = "position")
    private String position;

    @Column(name = "status")
    private String status;

    @Column(name = "purchasedate")
    private LocalDate purchaseDate;

    @Column(name = "purchasecost")
    private BigDecimal purchaseCost;

    @Column(name = "odometerreading")
    private BigDecimal odometerReading;

    @Column(name = "treaddepth")
    private BigDecimal treadDepth;

    @Column(name = "recommendedpressure")
    private Integer recommendedPressure;

    @Column(name = "pressure")
    private Integer pressure;

    @Column(name = "notes")
    private String notes;
}
