package com.efit.savaari.entity;

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
@Table(name = "customerrate")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRateVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customerrategen")
	@SequenceGenerator(name = "customerrategen", sequenceName = "customerrateseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "customerrateid")
	private Long id;

//	@ManyToOne
//	@JoinColumn(name = "industryid")
//    private IndustryVO industry;
	
	@Column(name = "customer")
	private String customer;
	@Column(name = "namingseries")
	private String namingSeries;

	@Column(name = "origin")
	private String origin;

	@Column(name = "destination")
	private String destination;
	
	@Column(name = "rate")
	private Double rate;
	@Column(name = "vehicletype")
	private String vehicleType;
	@Column(name = "ratetype")
	private String rateType;
	@Column(name = "weight")
	private double weight;

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
	private String screenCode = "CUSR";

	@Column(name = "screenname", length = 25)
	private String screenName = "CUSTOMERRATE";

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

