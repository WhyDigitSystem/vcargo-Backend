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
@Table(name = "placedetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlaceDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "placedetailsgen")
	@SequenceGenerator(name = "placedetailsgen", sequenceName = "placedetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "placedetailsid")
	private Long id;

	@Column(name = "place", length = 255)
	private String place;
	
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
	
	@Column(name = "branchcode")
	private String branchCode;
	@Column(name = "branch")
	private String branch;

	@Column(name = "screencode", length = 5)
	private String screenCode = "PD";

	@Column(name = "screenname", length = 25)
	private String screenName = "PLACEDETAILS";
	
	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}

	@Embedded
	@Builder.Default
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}


