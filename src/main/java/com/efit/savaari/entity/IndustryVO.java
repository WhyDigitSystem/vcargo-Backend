package com.efit.savaari.entity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.efit.savaari.dto.CreatedUpdatedDate;
import com.fasterxml.jackson.annotation.JsonGetter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "industry")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IndustryVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "industrygen")
	@SequenceGenerator(name = "industrygen", sequenceName = "industryseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "industryid")
	private Long id;

	@Column(name = "industrycode")
	private String industryCode;
	@Column(name = "industryname")
	private String industryName;
	@Column(name = "phonenumber")
	private String phoneNumber;

	@Column(name = "email")
	private String email;

	@Column(name = "state")
	private String state;

	@Column(name = "gstnumber")
	private String gstNumber;
	@Column(name = "pannumber")
	private String panNumber;
	@Column(name = "primaryaddress")
	private String primaryAddress;
	@Column(name = "additionaladdress")
	private String additionalAddress;
//	@Column(name = "accountnumber")
//	private String accountNumber;
	@Column(name = "city")
	private String city;
	@Column(name = "industrytype")
	private String industryType;

	@Column(name = "pincode")
	private int pincode;

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
	@Column(name = "cancel")
	private boolean cancel = false;

	@Column(name = "verification")
	private String verification = "Pending";

	@Column(name = "userid")
	private Long userId;

	@Column(name = "username")
	private String userName;

	@Column(name = "userpassword")
	private String userPassword;

	@Column(name = "screencode", length = 5)
	private String screenCode = "INS";

	@Column(name = "screenname", length = 25)
	private String screenName = "INDUSTRY";

	@Column(name = "approveby", length = 20)
	private String approveBy;

	@DateTimeFormat(pattern = "dd-MM-yyyy hh:mm:ss a")
	@Column(name = "approveon")
	private String approveOn;

	@Column(name = "image", columnDefinition = "LONGBLOB")
	private byte[] image;

	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

	public boolean isActive() {
		// TODO Auto-generated method stub
		return false;
	}
}
