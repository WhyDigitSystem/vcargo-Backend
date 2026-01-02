package com.efit.savaari.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.efit.savaari.dto.CreatedUpdatedDate;
import com.fasterxml.jackson.annotation.JsonGetter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "auctions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuctionsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "auctionsgen")
	@SequenceGenerator(name = "auctionsgen", sequenceName = "auctionsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "auctionsid")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "userid")
    private UserVO user;

	@Column(name = "auctionstype", length = 255)
	private String auctionsType;

	@Column(name = "roundtrip")
	private boolean roundTrip;

	@Column(name = "customgeofence")
	private boolean customGeofence;

	@Column(name = "loading", length = 255)
	private String loading;

	@Column(name = "unloading", length = 255)
	private String unloading;

	@Column(name = "vehicle", length = 255)
	private String vehicle;

	@Column(name = "vehiclequantity")
	private int vehicleQuantity;

	@Column(name = "loadingdate")
	private LocalDate loadingDate;

	@Column(name = "loadingtime")
	private String loadingTime;

	@Column(name = "unloadingdate")
	private LocalDate unloadingDate;

	@Column(name = "duration")
	private int duration;

	@Column(name = "startdate")
	private String startDate;

	@Column(name = "enddate")
	private String endDate;

	@Column(name = "material")
	private String material;

	@Column(name = "materialquantity")
	private int materialQuantity;

	@Column(name = "materialweight")
	private BigDecimal materialWeight;

	@Column(name = "weightunit")
	private String weightUnit;

	@Column(name = "dimension")
	private double dimension;

	@Column(name = "transportertag")
	private String transporterTag;
	@Column(name = "numtransporter")
	private int numTransporter;
	@Column(name = "excludetransporters")
	private String excludeTransporters;
	@Column(name = "shortcuts")
	private String shortCuts;
	@Column(name = "terms")
	private String terms;
	@Lob
	@Column(name = "documents")
	private byte[] documents;
	@Column(name = "organizationname")
	private String organizationName;
	@Column(name = "bidtype")
	private String bidType;
	@Column(name = "minbiddifference")
	private int minBidDifferent;
	@Column(name = "allowedbids")
	private String allowedBits;
	@Column(name = "maxprice")
	private String maxPrice;
	@Column(name = "minprice")
	private String minPrice;
	@Column(name = "additionalcharges")
	private String additionalCharges;

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
	private String screenCode = "AT";

	@Column(name = "screenname", length = 25)
	private String screenName = "AUCTIONS";
	
	@Column(name = "quotes")
	private boolean quotes = false;

	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}
	
	

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

//	@Column(name = "status", insertable = false, updatable = false)
	@Transient
	private Integer status;
	
	@Transient
	private Integer auctionStatus;
	
	
	
	@ManyToOne
	@JoinColumn(name = "approvedvendorid")
	private VendorVO approvedVendor;


}
