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
@Table(name = "companybankdetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyBankDetailsVO {


	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "companybankdetailsgen")
	@SequenceGenerator(name = "companybankdetailsgen", sequenceName = "companybankdetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "companybankdetailsid")
	private Long id;
	

	@Column(name = "accountholdername")
	private String accountHolderName;
	@Column(name = "accountnumber")
	private String accountNumber;
	@Column(name = "bankname")
	private String bankName;
	@Column(name = "ifsccode ")
	private String ifscCode;
	@Column(name = "branch")
	private String branch;
	@Column(name = "branchcode")
	private String branchCode;
	@Column(name = "primarys")
	private boolean primary;
	
	@ManyToOne
	@JoinColumn(name = "companyprofileid")
	@JsonBackReference
	private CompanyProfileVO companyProfileVO;
}
