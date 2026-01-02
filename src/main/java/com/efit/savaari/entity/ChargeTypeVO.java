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
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "chargetype")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChargeTypeVO {


	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chargetypegen")
	@SequenceGenerator(name = "chargetypegen", sequenceName = "chargetypeseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "chargetypeid")
	private Long id;

	@Column(name = "chargetype")
	private String chargeType;
	
	@Column(name = "unit")
	private String unit;

	
	@Column(name = "active")
	private boolean active ;
	@Column(name = "createdby")
	private String createdBy;
	@Column(name = "modifiedby")
	private String updatedBy;
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "cancel")
	private boolean cancel;
	
	@Column(name = "branchcode")
	private String branchCode;
	@Column(name = "branch")
	private String branch;

	@Column(name = "screencode", length = 5)
	private String screenCode = "CT";

	@Column(name = "screenname", length = 25)
	private String screenName = "CHARGETYPE";
	
  

	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}

	@Embedded
	@Builder.Default
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}


