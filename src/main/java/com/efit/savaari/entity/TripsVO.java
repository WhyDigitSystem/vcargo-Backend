package com.efit.savaari.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.efit.savaari.Enum.TripStatus;
import com.efit.savaari.dto.CreatedUpdatedDate;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "trips")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TripsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tripsgen")
	@SequenceGenerator(name = "tripsgen", sequenceName = "tripsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "tripsid")
	private Long id;

	// ===== Reference IDs (NEW – IMPORTANT) =====
	@Column(name = "vendorid")
	private Long vendorId;

	@Column(name = "vehicleid")
	private Long vehicleId;

	@Column(name = "driverid")
	private Long driverId;

	// ===== Snapshot Data (KEEP) =====
	@Column(name = "vendor", length = 255)
	private String vendor;

	@Column(name = "vehiclenumber", length = 75)
	private String vehicleNumber;

	@Column(name = "vehicletype", length = 75)
	private String vehicleType;

	@Column(name = "drivername", length = 75)
	private String driverName;

	@Column(name = "drivernumber", length = 75)
	private String driverNumber;

	// ===== Business Fields =====
	@Column(name = "customer", length = 75)
	private String customer;

	@Column(name = "lrnumber", length = 255)
	private String lrNumber;

	@Column(name = "route", length = 255)
	private String route;

	@Column(name = "routetrip")
	private boolean routeTrip;

	@Column(name = "pitstop")
	private boolean pitStop;

	@Column(name = "origin")
	private String origin;

	@Column(name = "destination")
	private String destination;

	@Column(name = "eta")
	private String eta;

	@Column(name = "tatdays")
	private String tatDays;

	// ===== Status (CHANGED TO ENUM) =====
	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private TripStatus status;

	// ===== Material =====
	@Column(name = "materialtype")
	private String materialType;

	@Column(name = "materialsqft")
	private String materialSqFt;

	@Column(name = "weight")
	private String weight;

	// ===== Audit & Org =====
	@Column(name = "active")
	private boolean active = true;

	@Column(name = "cancel")
	private boolean cancel;

	@Column(name = "createdby")
	private String createdBy;

	@Column(name = "modifiedby")
	private String updatedBy;

	@Column(name = "orgid")
	private Long orgId;

	@Column(name = "branchcode")
	private String branchCode;

	@Column(name = "branch")
	private String branch;

	@Column(name = "screencode")
	private String screenCode = "TP";

	@Column(name = "screenname")
	private String screenName = "TRIPS";

	// ===== Child Tables =====
	@OneToMany(mappedBy = "tripsVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<TripsDLVerificationVO> tripsDLVerificationVO;

	@OneToMany(mappedBy = "tripsVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<TripsPitStopVO> tripsPitStopVO;

	@Embedded
	@Builder.Default
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}
}
