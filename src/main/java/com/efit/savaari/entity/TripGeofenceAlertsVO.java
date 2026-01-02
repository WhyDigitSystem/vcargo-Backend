package com.efit.savaari.entity;

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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tripgeoferencealerts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TripGeofenceAlertsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tripgeoferencealertsgen")
	@SequenceGenerator(name = "tripgeoferencealertsgen", sequenceName = "tripgeoferencealertsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "tripgeoferencealertsid")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "tripsid")
    private TripsVO trip;
	
	@Column(name = "tag")
	private String tag;
	@Column(name = "vehicle")
	private String vehicle;
	@Column(name = "invoicetype")
	private String invoiceType;
	@Column(name = "geofence")
	private String geofence;
	
	@Column(name = "timestamp")
	private String timestamp;
	
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

