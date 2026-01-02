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
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.efit.savaari.dto.CreatedUpdatedDate;
import com.fasterxml.jackson.annotation.JsonGetter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "quote")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuoteVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quotegen")
	@SequenceGenerator(name = "quotegen", sequenceName = "quoteseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "quoteid")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "auctionsid")
    private AuctionsVO auction;
	
	@Column(name = "quoteamount", length = 255)
	private BigDecimal quoteAmount;

	@Column(name = "additionalnotes")
	private String additionalNotes;
	
	@Column(name = "estimateddeliverydate")
	private LocalDate estimatedDeliveryDate;

	@Column(name = "termsandconditions")
	private String termsAndConditions;
	
//	@ManyToOne
//	@JoinColumn(name = "vendorid")
//    private VendorVO vendor;
	
	@ManyToOne
	@JoinColumn(name = "userid")
    private UserVO user;
	
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
	
	@Column(name = "approvedstatus")
	private String approvedStatus;
	@Column(name = "approveby")
	private String approveBy;
	@Column(name = "approveon")
	private String approveOn;

	@Column(name = "screencode", length = 5)
	private String screenCode = "QT";

	@Column(name = "screenname", length = 25)
	private String screenName = "QUOTE";
	
  

	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}

	@Embedded
	@Builder.Default
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}

