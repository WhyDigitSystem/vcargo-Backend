package com.efit.savaari.entity;

import java.time.LocalDate;

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
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vendordetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendorDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vendordetailsgen")
	@SequenceGenerator(name = "vendordetailsgen", sequenceName = "vendordetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "vendordetailsid")
	private Long id;
	@Column(name = "effectiveto")
	private LocalDate effectioveTo;
	@Column(name = "effectivefrom")
	private LocalDate effectiveFrom;
	
	@Column(name = "contractattachmentname")
	private String contractAttachmentName;
	@Column(name = "backgroundverificationname")
	private String backgroundVerificationName;
	@Column(name = "securitycheckname")
	private String securitycheckName;
	
	@Lob
	@Column(name = "contractattachment")
	private byte[] contractAttachment;

	@Lob
	@Column(name = "backgroundverification")
	private byte[] backgroundVerification;

	@Lob
	@Column(name = "securitycheck")
	private byte[] securityCheck;
	
	@ManyToOne
	@JoinColumn(name = "vendorid")
	@JsonBackReference
	private VendorVO vendorVO;
}
