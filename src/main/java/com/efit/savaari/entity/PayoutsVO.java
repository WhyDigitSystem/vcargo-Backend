package com.efit.savaari.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.efit.savaari.dto.CreatedUpdatedDate;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "payouts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayoutsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payoutsgen")
	@SequenceGenerator(name = "payoutsgen", sequenceName = "payoutsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "payoutsid")
	private Long id;

	@Column(name = "namingseries")
	private String namingSeries;
	@Column(name = "payoutstatus")
	private String payoutStatus;

	@Column(name = "vendor")
	private String vendor;
	@Column(name = "purpose")
	private String purpose;
	@Column(name = "approveby")
	private String approveBy;
	@Column(name = "approveon")
	private String approveOn;
	@Column(name = "invoiceamount")
	private BigDecimal invoiceAmount;

	@Column(name = "quantity")
	private int quantity;
	@Column(name = "totaladditionalcharges")
	private BigDecimal totalAdditionalCharges;

	@Column(name = "totaltripcost")
	private BigDecimal totalTripCost;

	@Column(name = "payoutreference")
	private String payoutReference;
	@Column(name = "invoice")
	private String invoice;
	@Column(name = "invoicetype")
	private String invoiceType;
	@Lob
	@Column(name = "invoicefile")
	private byte[] invoiceFile;
	
	@Column(name = "fromdate")
	private LocalDate fromDate;

	@Column(name = "todate")
	private LocalDate toDate;

	@Column(name = "active")
	private boolean active = true;
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
	private String screenCode = "PO";

	@Column(name = "screenname", length = 25)
	private String screenName = "PAYOUTS";

	@OneToMany(mappedBy = "payoutsVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<PayoutsVendorDetailsVO> payoutsVendorDetailsVO;
	
	@OneToMany(mappedBy = "payoutsVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<PayoutsTimeLineVO> payoutsTimeLineVO;
	
	@OneToMany(mappedBy = "payoutsVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<PayoutsDocumentVO> payoutsDocumentVO;
	
	@OneToMany(mappedBy = "payoutsVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<PayoutTripsDetailsVO> payoutTripsDetailsVO;

	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}
