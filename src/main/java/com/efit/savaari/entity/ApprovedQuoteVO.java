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
@Table(name = "approvedquote")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApprovedQuoteVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "approvedquotegen")
	@SequenceGenerator(name = "approvedquotegen", sequenceName = "approvedquoteseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "approvedquoteid")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "auctionsid")
    private AuctionsVO auction;

	@Column(name = "additionalnotes")
	private String additionalNotes;
	
	@Column(name = "estimateddeliverydate")
	private LocalDate estimatedDeliveryDate;

	@Column(name = "termsandconditions")
	private String termsAndConditions;
	
	@Column(name = "approvedamount")
	private BigDecimal approvedAmount;
	
	@Column(name = "minquotedamount")
	private BigDecimal minQuotedAmount;
	
	@Column(name = "maxquotedamount")
	private BigDecimal maxQuotedAmount;
	
	@ManyToOne
	@JoinColumn(name = "approveduserid")
    private UserVO approvedUser;
	
	@ManyToOne
	@JoinColumn(name = "minquoteduserid")
    private UserVO minQuotedUser;
	
	@ManyToOne
	@JoinColumn(name = "maxquoteduserid")
    private UserVO maxQuotedUser;
	
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
	private String screenCode = "AQT";

	@Column(name = "screenname", length = 25)
	private String screenName = "APPROVEDQUOTE";
	
  

	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}

	@Embedded
	@Builder.Default
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}


