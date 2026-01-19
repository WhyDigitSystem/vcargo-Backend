package com.efit.savaari.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "companyprofile")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyProfileVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "companyprofilegen")
	@SequenceGenerator(name = "companyprofilegen", sequenceName = "companyprofileseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "companyprofileid")
	private Long id;
	
	
	@Column(name = "companycode")
	private String companyCode;
	@Column(name = "companyname")
	private String companyName;
	@Column(name = "ownername")
	private String ownerName;
	@Column(name = "emailaddress")
	private String emailAddress;

	@Column(name = "phoneno")
	private String phoneNo;
	@Column(name = "gstno")
	private String gstNo;
	@Column(name = "panno")
	private String panNo;
	@Column(name = "accountholdername")
	private String accountHolderName;
	@Column(name = "accountnumber")
	private String accountNumber;
	@Column(name = "bankname")
	private String bankName;
	@Column(name = "ifsccode ")
	private String ifscCode;
	@Column(name = "website")
	private String website;
	@Column(name = "establishedyear")
	private String establishedYear;
	@Column(name = "termsandconditions")
	private String termsAndConditions;
	
	@Column(name = "active")
	private boolean active;
	@Column(name = "createdby")
	private String createdBy;
	@Column(name = "modifiedby")
	private String updatedBy;
	@Column(name = "cancel")
	private boolean cancel;
	@Column(name = "cancelremarks")
	private String cancelRemarks;
	@Column(name = "branch")
	private String branch;
	@Column(name = "branchcode")
	private String branchcode;
	@Column(name = "orgid")
	private Long orgId;
	
	@Column(name = "companylogo", columnDefinition = "LONGBLOB")
	private byte[] companyLogo;
	
	
	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}

	// Optionally, if you want to control serialization for 'cancel' field similarly
	@JsonGetter("cancel")
	public String getCancel() {
		return cancel ? "T" : "F";
	}
	
	@OneToMany(mappedBy = "companyProfileVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<CompanyAddressVO> companyAddressVO;
	
	
	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
	
}
