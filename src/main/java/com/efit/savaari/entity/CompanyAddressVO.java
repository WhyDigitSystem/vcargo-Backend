package com.efit.savaari.entity;

import java.time.LocalDate;

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
@Table(name = "companyaddress")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyAddressVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "companyaddressgen")
	@SequenceGenerator(name = "companyaddressgen", sequenceName = "companyaddressseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "companyaddressid")
	private Long id;
	
	@Column(name = "shippingaddress")
	private String shippingAddress;
	@Column(name = "billingaddress")
	private String billingAddress;
	@Column(name = "primarys")
	private boolean primary;
	
	@ManyToOne
	@JoinColumn(name = "companyprofileid")
	@JsonBackReference
	private CompanyProfileVO companyProfileVO;

}
