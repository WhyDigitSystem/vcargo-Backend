package com.efit.savaari.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "customer")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customergen")
	@SequenceGenerator(name = "customergen", sequenceName = "customerseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "customerid")
	private Long id;
	
//	@ManyToOne
//	@JoinColumn(name = "industryid")
//    private IndustryVO industry;

	@Column(name = "customercode")
	private String customerCode;
	@Column(name = "customername")
	private String customerName;
	@Column(name = "phonenumber")
	private String phoneNumber;
	
	@Column(name = "customertype")
	private String customerType;
	@Column(name = "salesperson")
	private String salesPerson;
//	@Column(name = "location")
//	private String location;
//	@Column(name = "approvalstatus")
//	private String approvalStatus;
//	@Column(name = "approveby")
//	private String approveBy;
//	@Column(name = "approveon")
//	private String approveOn;

	@Column(name = "email")
	private String email;

	@Column(name = "gstnumber")
	private String gstNumber;
	@Column(name = "pannumber")
	private String panNumber;

	@Column(name = "pocname")
	private String pocName;
	@Column(name = "pocemail")
	private String pocEmail;

	@Column(name = "pocnumber")
	private String pocNumber;

	@Column(name = "active")
	private boolean active;
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

	@Column(name = "image", columnDefinition = "LONGBLOB")
	private byte[] image;
	
	@Column(name = "screencode", length = 5)
	private String screenCode = "CUS";

	@Column(name = "screenname", length = 25)
	private String screenName = "CUSTOMER";

	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}

	
	@OneToMany(mappedBy = "customerVO",cascade = CascadeType.ALL)
   	@JsonManagedReference
   	private List<CustomerAddressVO> customerAddressVO;
	
	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

	public boolean isActive() {
		// TODO Auto-generated method stub
		return false;
	}
}
