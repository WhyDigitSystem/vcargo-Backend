package com.efit.savaari.entity;

import javax.persistence.Column;
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
@Table(name = "driver")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "drivergen")
	@SequenceGenerator(name = "drivergen", sequenceName = "driverseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "driverid")
	private Long id;
	
	@Column(name = "drivername")
	private String driverName;
	
	@Column(name = "drivernumber")
	private String driverNumber;
	
	@Column(name = "status")
	private String status;
	
	//common fields
	
	@Column(name = "branch")
	private String branch;
	
	@Column(name = "branchcode")
	private String branchCode;
	
	@Column(name = "active")
	private boolean active = true;
	
	@Column(name = "createdby")
	private String createdBy;
	
	@Column(name = "modifiedby")
	private String updatedBy;
	
	@Column(name = "orgid")
	private Long orgId;
	
	@Column(name = "cancel")
	private boolean cancel;
	
	@Column(name = "screenname")
	private String screenName="VECHICLE";
	
	@Column(name = "screencode")
	private String screenCode ="DR";
	
	
	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}

	// Optionally, if you want to control serialization for 'cancel' field similarly
	@JsonGetter("cancel")
	public String getCancel() {
		return cancel ? "T" : "F";
	}

	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();



	
}
