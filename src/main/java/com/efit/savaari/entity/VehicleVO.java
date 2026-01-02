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
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vehicle")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vehiclegen")
	@SequenceGenerator(name = "vehiclegen", sequenceName = "vehicleseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "vehicleid")
	private Long id;

	@Column(name = "vehiclenumber")
	private String vehicleNumber;
	@Column(name = "vehicletype")
	private String vehicleType;
//	@Column(name = "status")
//	private String status;
	
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
	private String screenCode ="VC";
	
	
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


//	@Override
//	public String toString() {
//		return "VehicleVO [id=" + id + ", vehicleNumber=" + vehicleNumber + ", vehicleType=" + vehicleType + ", status="
//				+ status + ", branch=" + branch + ", branchCode=" + branchCode + ", active=" + active + ", createdBy="
//				+ createdBy + ", updatedBy=" + updatedBy + ", orgId=" + orgId + ", cancel=" + cancel + ", screenName="
//				+ screenName + ", screenCode=" + screenCode + ", commonDate=" + commonDate + ", getActive()="
//				+ getActive() + ", getCancel()=" + getCancel() + ", getId()=" + getId() + ", getVehicleNumber()="
//				+ getVehicleNumber() + ", getVehicleType()=" + getVehicleType() + ", getStatus()=" + getStatus()
//				+ ", getBranch()=" + getBranch() + ", getBranchCode()=" + getBranchCode() + ", getCreatedBy()="
//				+ getCreatedBy() + ", getUpdatedBy()=" + getUpdatedBy() + ", getOrgId()=" + getOrgId()
//				+ ", getScreenName()=" + getScreenName() + ", getScreenCode()=" + getScreenCode() + ", getCommonDate()="
//				+ getCommonDate() + ", hashCode()=" + hashCode() + ", getClass()=" + getClass() + ", toString()="
//				+ super.toString() + "]";
//	}



}
