
package com.efit.savaari.entity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.efit.savaari.dto.CreatedUpdatedDate;
import com.efit.savaari.dto.UserStatus;
import com.fasterxml.jackson.annotation.JsonGetter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVO {

	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usersgen")
	@SequenceGenerator(name = "usersgen", sequenceName = "usersseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "userid")
	private Long id;
	
	@Column(name = "username")
	private String userName;
	@Column(name = "mobileno")
	private String mobileNo;
	@Column(name = "email")
	private String email;
	@Column(name = "password")
	private String password;
	@Column(name = "type")
	private String type;
	@Column(name = "organizationname")
	private String organizationName;
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "branch")
	private String branch;
	@Column(name = "branchcode")
	private String branchCode;
	@Column(name = "active")
	private boolean active;
	@Column(name = "createdby")
	private String createdby;
	@Column(name = "modifiedby")
	private String updatedby;
	@Column(name = "status")
	private UserStatus status;
	@Column(name = "approveby")
	private String approveBy;
	@Column(name = "approveon")
	private String approveOn;
	
	@Column(name = "vendorid")
	private Long vendorId;


	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

		
//	@ManyToOne
//	@JoinColumn(name="companyid")
//	private CompanyVO companyVO;
	
	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}
	
	
	

	public boolean isActive() {
		// TODO Auto-generated method stub
		return true;
	}

	

	
}
