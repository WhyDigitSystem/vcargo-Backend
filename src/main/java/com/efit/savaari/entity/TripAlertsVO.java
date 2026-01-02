package com.efit.savaari.entity;

import java.time.LocalDate;

import javax.persistence.Column;
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
import com.fasterxml.jackson.annotation.JsonGetter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tripalerts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TripAlertsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tripalertsgen")
	@SequenceGenerator(name = "tripalertsgen", sequenceName = "tripalertsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "tripalertsid")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "tripsid")
    private TripsVO trip;
	

	@Column(name = "fromdate", length = 75)
	private LocalDate fromDate;

	@Column(name = "todate", length = 255)
	private LocalDate toDate;

	@ManyToOne
	@JoinColumn(name = "vehicleid")
    private VehicleVO vehicle;
	
	@ManyToOne
	@JoinColumn(name = "driverid")
    private DriverVO driver;

	@Column(name = "type", length = 75)
	private String type;

	@Column(name = "extra", length = 75)
	private String extra;
	
	@Column(name = "active")
	private boolean active = true;
	@Column(name = "createdby")
	private String createdBy;
	@Column(name = "modifiedby")
	private String updatedBy;
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "cancel")
	private boolean cancel = false;
	@Column(name = "branchcode")
	private String branchCode;
	@Column(name = "branch")
	private String branch;

	@Column(name = "screencode", length = 5)
	private String screenCode = "TA";

	@Column(name = "screenname", length = 25)
	private String screenName = "TRIPALERTS";
	

	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}
	
	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
	
}
