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
import com.fasterxml.jackson.annotation.JsonGetter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vendorusers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendorUsersVO {

	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vendorusersgen")
	@SequenceGenerator(name = "vendorusersgen", sequenceName = "vendorusersseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "vendorusersid")
	private Long id;
	@Column(name = "users")
	private String users;
	
	
//	@Column(name = "active")
//	private boolean active;
//
//	@JsonGetter("active")
//	public String getActive() {
//		return active ? "Active" : "In-Active";
//	}
	
	@ManyToOne
	@JoinColumn(name = "vendorid")
	@JsonBackReference
	private VendorVO vendorVO;
	

}
