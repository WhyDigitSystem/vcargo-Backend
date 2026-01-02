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
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customerbookingrequest")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerBookingRequestVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customerbookingrequestgen")
	@SequenceGenerator(name = "customerbookingrequestgen", sequenceName = "customerbookingrequestseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "customerbookingrequestid")
	private Long id;
	
	@Column(name = "customer")
	private String customer;
	@Column(name = "origin")
	private String origin;
	@Column(name = "status")
	private String status;
	@Column(name = "destination")
	private String destination;
	@Column(name = "vehicletype")
	private String vehicleType;
	
	@Column(name = "noofvehicles")
	private int noOfVehicles;
	@Column(name = "placementdate")
	private LocalDate placementDate;
	@Column(name = "ordertype")
	private String orderType;
	@Column(name = "servicetype")
	private String serviceType;
	@Column(name = "orderingparty")
	private String orderingParty;
	@Column(name = "materialtype")
	private String materialType;
	@Column(name = "billtoparty")
	private String billToParty;
	@Column(name = "docketno")
	private String docketNo;
	
	@Column(name = "orgid")
	private long orgId;
	@Column(name = "branchcode")
	private String branchCode;
	@Column(name = "branchname")
	private String branchName;
	@Column(name = "createdby")
	private String createdBy;
	@Column(name = "modifiedby")
	private String updatedBy;
	@Column(name = "cancel")
	private boolean cancel;
	@Column(name = "active")
	private boolean active;
	@Column(name = "remarks")
	private String remarks;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}

