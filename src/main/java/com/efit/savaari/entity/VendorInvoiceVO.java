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
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
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
@Table(name = "vendorinvoice")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VendorInvoiceVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vendorinvoicegen")
	@SequenceGenerator(name = "vendorinvoicegen", sequenceName = "vendorinvoiceseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "vendorinvoiceid")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "vendorid")
    private VendorVO vendor;
	
	@Column(name = "invoicetype", length = 255)
	private String invoiceType;

	@Column(name = "tripcost")
	private BigDecimal tripCost;
	
	@Column(name = "totaladditionalcharges")
	private BigDecimal totalAdditionalCharges;

	@Column(name = "subtotal")
	private BigDecimal subTotal;
	
	@Column(name = "totalamount")
	private BigDecimal totalAmount;
	
	@Column(name = "tds")
	private String tds;
	
	@Column(name = "payableamount")
	private BigDecimal payableAmount;
	
	@Column(name = "invoicenumber")
	private String invoiceNumber;
	
	@Column(name = "duedate")
	private LocalDate dueDate;
	
	@Column(name = "invoicedate")
	private LocalDate invoiceDate;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "fromdate")
	private LocalDate fromDate;
	
	@Column(name = "todate")
	private LocalDate toDate;
	
	@Lob
	@Column(name = "invoicefile")
	private byte[] invoiceFile;
	
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
	private String screenCode = "VI";

	@Column(name = "screenname", length = 25)
	private String screenName = "VENDORINVOICE";
	
	@OneToMany(mappedBy = "vendorInvoiceVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<VendorInvoiceChargesVO> vendorInvoiceChargesVO;
	
	@OneToMany(mappedBy = "vendorInvoiceVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<VendorInvoiceTripsDetailsVO> vendorInvoiceTripsDetailsVO;
	
	@OneToMany(mappedBy = "vendorInvoiceVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<VendorInvoiceTripsDocumentVO> vendorInvoiceTripsDocumentVO;

	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}

	@Embedded
	@Builder.Default
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}


