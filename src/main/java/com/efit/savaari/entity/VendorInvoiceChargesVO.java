package com.efit.savaari.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vendorinvoicecharges")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VendorInvoiceChargesVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vendorinvoicechargesgen")
	@SequenceGenerator(name = "vendorinvoicechargesgen", sequenceName = "vendorinvoicechargesseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "vendorinvoicechargesid")
	private Long id;

	@Column(name = "purpose", length = 255)
	private String purpose;

	@Column(name = "amount")
	private BigDecimal amount;

	@Column(name = "remarks")
	private String remarks;
	
	@Column(name = "trip")
	private String trip;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "screencode")
	private String screenCode = "VIC";

	@Column(name = "screenname")
	private String screenName = "VENDORINVOICECHARGES";
	
	

	@ManyToOne
	@JoinColumn(name = "vendorinvoiceid")
	@JsonBackReference
	private VendorInvoiceVO vendorInvoiceVO;
}
