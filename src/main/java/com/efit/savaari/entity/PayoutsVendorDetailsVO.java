package com.efit.savaari.entity;

import java.math.BigDecimal;

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
@Table(name = "payoutsvendordetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayoutsVendorDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payoutsvendordetailsgen")
	@SequenceGenerator(name = "payoutsvendordetailsgen", sequenceName = "payoutsvendordetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "payoutsvendordetailsid")
	private Long id;
	@Column(name = "purpose")
	private String purpose;
	@Column(name = "amount")
	private BigDecimal amount;
	
	@Column(name = "remarks")
	private String remarks;
	@Column(name = "trip")
	private String trip;
	@Column(name = "status")
	private String status;
	@Column(name = "createpayouts")
	private boolean createPayouts;
	
	
	@ManyToOne
	@JoinColumn(name = "payoutsid")
	@JsonBackReference
	private PayoutsVO payoutsVO;
}

