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
@Table(name = "customeraddress")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerAddressVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customeraddressgen")
	@SequenceGenerator(name = "customeraddressgen", sequenceName = "customeraddressseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "customeraddressid")
	private Long id;
	
	@Column(name = "primaryaddress")
	private String primaryAddress;
	@Column(name = "additionaladdress")
	private String additionalAddress;
	@Column(name = "city")
	private String city;
	@Column(name = "state")
	private String state;
	@Column(name = "type")
	private String type;
	@Column(name = "pincode")
	private int pincode;

	
	@ManyToOne
	@JoinColumn(name = "customerid")
	@JsonBackReference
	private CustomerVO customerVO;
	

}
