package com.efit.savaari.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.efit.savaari.dto.CreatedUpdatedDate;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "indents")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IndentsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "indentsgen")
	@SequenceGenerator(name = "indentsgen", sequenceName = "indentsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "indentsid")
	private Long id;

	@Column(name = "status", length = 255)
	private String status;

	@Column(name = "customer", length = 75)
	private String customer;

	@Column(name = "route", length = 255)
	private String route;
	
	@Column(name = "origin", length = 255)
	private String origin;

	@Column(name = "destination", length = 75)
	private String destination;

	@Column(name = "originpoc", length = 255)
	private String originPoc;

	@Column(name = "destinationpoc", length = 75)
	private String destinationPoc;
	
	@Column(name = "vechiletype", length = 255)
	private String vechicleType;

	@Column(name = "weight", length = 75)
	private String weight;
	
	@Column(name = "pitstop", length = 75)
	private boolean pitStop;
	
	
	@Column(name = "numberofvehicles", length = 75)
	private String numberOfVechicles;

	@Column(name = "customerrate", length = 255)
	private String customerRate;

	@Column(name = "vendorratepervehicles", length = 75)
	private String vendorRateVehicles;
	
	@Column(name = "extrainfo", length = 75)
	private String extraInfo;
	
	@Column(name = "placementdate", length = 75)
	private LocalDate placementDate;

	@Column(name = "ordertype", length = 75)
	private String orderType;

	@Column(name = "dockerno", length = 255)
	private String dockerNo;

	@Column(name = "triptype", length = 75)
	private String tripType;
	
	@Column(name = "materialtype", length = 75)
	private String materialType;
	
	@Column(name = "overtimehours", length = 75)
	private int overTimeHours;
	
	@Column(name = "vendorrank", length = 75)
	private int vendorRank;
	
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
	private String screenCode = "ID";

	@Column(name = "screenname", length = 25)
	private String screenName = "INDENTS";
	
	
    @OneToMany(mappedBy = "indentsVO",cascade = CascadeType.ALL)
   	@JsonManagedReference
   	private List<TimeLineVO> timeLineVO;
    
    @OneToMany(mappedBy = "indentsVO",cascade = CascadeType.ALL)
   	@JsonManagedReference
   	private List<VendorResponseVO> vendorResponseVO;
    
    @OneToMany(mappedBy = "indentsVO",cascade = CascadeType.ALL)
   	@JsonManagedReference
   	private List<TripsLinkedVO> tripsLinkedVO;
    
    @OneToMany(mappedBy = "indentsVO",cascade = CascadeType.ALL)
   	@JsonManagedReference
   	private List<IndentsPitstopVO> indentsPitstopVO;

    @OneToMany(mappedBy = "indentsVO",cascade = CascadeType.ALL)
   	@JsonManagedReference
   	private List<TripsDocumentsVO> tripsDocumentsVO;
    
    @OneToMany(mappedBy = "indentsVO",cascade = CascadeType.ALL)
   	@JsonManagedReference
   	private List<IndentsParticipantsVO> indentsParticipantsVO;
    
	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}

	@Embedded
	@Builder.Default
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}
