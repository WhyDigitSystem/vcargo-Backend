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
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vendorbankdetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendorBankDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vendorbankdetailsgen")
	@SequenceGenerator(name = "vendorbankdetailsgen", sequenceName = "vendorbankdetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "vendorbankdetailsid")
	private Long id;

	@Column(name = "bankname")
	private String bankName;
	@Column(name = "accountnumber")
	private String accountNumber;
	@Column(name = "ifsc")
	private String ifsc;
	@Column(name = "accountholdername")
	private String accountHolderName;
	
	
	@ManyToOne
	@JoinColumn(name = "vendorid")
	@JsonBackReference
	private VendorVO vendorVO;
	


}
