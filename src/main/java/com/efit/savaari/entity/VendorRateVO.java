package com.efit.savaari.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.efit.savaari.dto.CreatedUpdatedDate;
import com.fasterxml.jackson.annotation.JsonGetter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vendorrate")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendorRateVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vendorrategen")
	@SequenceGenerator(name = "vendorrategen", sequenceName = "vendorrateseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "vendorrateid")
	private Long id;

	@Column(name = "state")
	private String state;
	@Column(name = "vendor")
	private String vendor;
	@Column(name = "namingseries")
	private String namingSeries;
	@Column(name = "effectiveto")
	private LocalDate effectiveTo;
	@Column(name = "effectivefrom")
	private LocalDate effectiveFrom;
	@Column(name = "priority")
	private String priority;
	@Column(name = "origin")
	private String origin;
	@Column(name = "destination")
	private String destination;
	@Column(name = "rate")
	private String rate;
	@Column(name = "vehicletype")
	private String vehicleType;
	@Column(name = "ratetype")
	private String rateType;
	@Column(name = "weight")
	private String weight;
	@Column(name = "detentioncharge")
	private String detentioncharge;
	@Column(name = "ranks")
	private int rank;
	@Column(name = "unloadingcharges")
	private String unloadingCharges;
	@Column(name = "remarks")
	private String remarks;
	@Column(name = "extracharges")
	private String extraCharges;
	
	@Column(name = "active")
	private boolean active ;
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
	private String screenCode = "VR";

	@Column(name = "screenname", length = 25)
	private String screenName = "VENDORRATE";

	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

	public boolean isActive() {
		// TODO Auto-generated method stub
		return false;
	}
}
