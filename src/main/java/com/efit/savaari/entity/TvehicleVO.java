package com.efit.savaari.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.efit.savaari.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tvehicle")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TvehicleVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tvehiclegen")
	@SequenceGenerator(name = "tvehiclegen", sequenceName = "tvehicleseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "tvehicleid")
	private Long id;

	@Column(name = "vehiclenumber", length = 50, unique = true)
	private String vehicleNumber;

	@ManyToOne
	@JoinColumn(name = "userid")
	private UserVO user;

	@Column(name = "type", length = 100)
	private String type;

	@Column(name = "model", length = 150)
	private String model;

	@Column(name = "capacity", length = 100)
	private String capacity;

	@Column(name = "insuranceexpiry")
	private LocalDate insuranceExpiry;

	@Column(name = "fitnessexpiry")
	private LocalDate fitnessExpiry;

	@Column(name = "lastservice")
	private LocalDate lastService;

	@Column(name = "nextservice")
	private LocalDate nextService;

	@Column(name = "driver", length = 100)
	private String driver;

	@Column(name = "driverphone", length = 25)
	private String driverPhone;

	@Column(name = "currentlocation", length = 150)
	private String currentLocation;

	@Column(name = "fuelefficiency", length = 50)
	private String fuelEfficiency;

	@Column(name = "maintenancerequired")
	private boolean maintenanceRequired;

	@Column(name = "year")
	private int year;

	@Column(name = "chassisnumber", length = 100)
	private String chassisNumber;

	@Column(name = "enginenumber", length = 100)
	private String engineNumber;

	@Column(name = "permittype", length = 50)
	private String permitType;

	@Column(name = "ownername", length = 100)
	private String ownerName;
	
	@Column(name = "registrationtype", length = 100)
	private String registrationType;
	
	@Column(name = "active")
	private String active;

	@Column(name = "createdby")
	private String createdBy;

	@Column(name = "modifiedby")
	private String updatedBy;

	@Column(name = "orgid")
	private Long orgId;

	@Column(name = "branchcode")
	private String branchCode;

	@Column(name = "branchname")
	private String branchName;

	@Column(name = "cancel")
	private boolean cancel = false;

	@OneToMany(mappedBy = "tvehicle", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<TvehicleDocumentsVO> documents;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
