package com.efit.savaari.entity;

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

import org.springframework.format.annotation.DateTimeFormat;

import com.efit.savaari.dto.CreatedUpdatedDate;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vendor")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendorVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vendorgen")
	@SequenceGenerator(name = "vendorgen", sequenceName = "vendorseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "vendorid")
	private Long id;
	
//	@ManyToOne
//	@JoinColumn(name = "industryid")
//    private IndustryVO industry;

	@Column(name = "vendorcode")
	private String vendorCode;
	@Column(name = "vendorname")
	private String vendorName;
	@Column(name = "status")
	private String status;

	@Column(name = "organization")
	private String organization;
	@Column(name = "approvalstatus")
	private String approvalStatus;

	@Column(name = "primaryphonenumber")
	private String primaryPhoneNumber;

	@Column(name = "primaryemail")
	private String primaryEmail;
	@Column(name = "additionalphonenumber")
	private String additionalPhoneNumber;

	@Column(name = "additionalemails")
	private String additionalEmails;

//	@Column(name = "gst")
//	private String gst;
	@Column(name = "address")
	private String address;
//	@Column(name = "accountnumber")
//	private String accountNumber;
//	@Column(name = "ifsc")
//	private String ifsc;
//	@Column(name = "accountholdername")
//	private String accountHolderName;

	@Column(name = "vendortype")
	private String vendorType;
	@Column(name = "advancepercent")
	private double advancePercent;
	@Column(name = "creditperiod")
	private String creditPeriod;
	@Column(name = "tdspercent")
	private double tdsPercent;
	@Column(name = "vendorspotid")
	private String vendorSpotId;
	@Column(name = "vendoruuid")
	private String vendoruuid;
	@Column(name = "tags")
	private String tags;
	
	@Column(name = "pocname")
	private String pocName;
	@Column(name = "pocemail")
	private String pocEmail;

	@Column(name = "pocnumber")
	private String pocNumber;

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
	@Column(name="verification")
	private String verification="Pending";
	
	@Column(name="userid")
	private Long userId;
	
	@Column(name="username")
	private String userName;
	
	@Column(name="userpassword")
	private String userPassword;

	@Column(name = "screencode", length = 5)
	private String screenCode = "VEN";

	@Column(name = "screenname", length = 25)
	private String screenName = "VENDOR";
	
	@Column(name = "image", columnDefinition = "LONGBLOB")
	private byte[] image;
	
	@Column(name="approveby",length = 20)
	private String approveBy;
	
	@DateTimeFormat(pattern = "dd-MM-yyyy hh:mm:ss a")
	@Column(name="approveon")
	private String approveOn;

	@OneToMany(mappedBy = "vendorVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<VendorUsersVO> vendorUsersVO;
	
	@OneToMany(mappedBy = "vendorVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<VendorGstVO> vendorGstVO;
	
	@OneToMany(mappedBy = "vendorVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<VendorDetailsVO> vendorDetailsVO;
	
	@OneToMany(mappedBy = "vendorVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<VendorBankDetailsVO> vendorBankDetailsVO;

	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}