package com.efit.savaari.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.efit.savaari.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "fuel")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FuelVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fuelgen")
	@SequenceGenerator(name = "fuelgen", sequenceName = "fuelseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "fuelid")
	private Long id;

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

	@Column(name = "active")
	private boolean active = true;


	@Column(name = "createdby")
	private String createdBy;

	@Column(name = "modifiedby")
	private String updatedBy;

	@Column(name = "cancel")
	private boolean cancel;

	@Column(name = "branchcode")
	private String branchCode;

	@Column(name = "branchname")
	private String branchName;
	
	@Column(name = "orgid")
	private Long orgId;
	
	@ManyToOne
	@JoinColumn(name = "userid")
    private UserVO user;
	
	@ManyToOne
	@JoinColumn(name = "vehicle")
	private TvehicleVO vehicle;
	
	@ManyToOne
	@JoinColumn(name = "driver")
	private TdriverVO driver;

	@Embedded
	private CreatedUpdatedDate createdUpdatedDate;
}
