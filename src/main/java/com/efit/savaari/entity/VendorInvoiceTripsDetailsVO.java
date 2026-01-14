package com.efit.savaari.entity;

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
@Table(name = "vendorinvoicetripdetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VendorInvoiceTripsDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vendorinvoicetripdetailsgen")
	@SequenceGenerator(name = "vendorinvoicetripdetailsgen", sequenceName = "vendorinvoicetripdetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "vendorinvoicetripdetailsid")
	private Long id;

	@Column(name = "trips", length = 255)
	private String trips;

	@Column(name = "origin")
	private String origin;

	@Column(name = "destination")
	private String destination;
	
	@Column(name = "vehicle")
	private String vehicle;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "screencode")
	private String screenCode = "VITD";

	@Column(name = "screenname")
	private String screenName = "VENDORINVOICETRIPSDETAILS";
	
	

	@ManyToOne
	@JoinColumn(name = "vendorinvoiceid")
	@JsonBackReference
	private VendorInvoiceVO vendorInvoiceVO;
}

