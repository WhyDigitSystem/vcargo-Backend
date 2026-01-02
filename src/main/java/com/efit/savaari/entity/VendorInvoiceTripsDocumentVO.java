package com.efit.savaari.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vendorinvoicetripdocument")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VendorInvoiceTripsDocumentVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vendorinvoicetripdocumentgen")
	@SequenceGenerator(name = "vendorinvoicetripdocumentgen", sequenceName = "vendorinvoicetripdocumentseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "vendorinvoicetripdocumentid")
	private Long id;

	@Lob
	@Column(name = "documents")
	private byte[] documents;
	
	@Column(name = "doctype")
	private String docType;

	@Column(name = "remarks")
	private String remarks;
	
	@Column(name = "trip")
	private String trip;
	
	@Column(name = "vehiclenumber")
	private String vehicleNumber;
	
	@Column(name = "screencode")
	private String screenCode = "VITDD";

	@Column(name = "screenname")
	private String screenName = "VENDORINVOICETRIPSDOCUMENTS";
	
	

	@ManyToOne
	@JoinColumn(name = "vendorinvoiceid")
	@JsonBackReference
	private VendorInvoiceVO vendorInvoiceVO;
}

