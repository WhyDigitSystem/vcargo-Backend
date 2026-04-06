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
@Table(name = "ewaybilldetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EwayBillDetailsVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ewaybilldetailsgen")
	@SequenceGenerator(name = "ewaybilldetailsgen", sequenceName = "ewaybilldetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "ewaybilldetailsid")
	private Long id;

	@Column(name = "productName")
	private String productName;

	@Column(name = "productDesc")
	private String productDesc;

	@Column(name = "hsnCode")
	private Number hsnCode;

	@Column(name = "quantity")
	private Number quantity;

	@Column(name = "qtyUnit")
	private String qtyUnit;

	@Column(name = "cgstRate")
	private double cgstRate;

	@Column(name = "sgstRate")
	private double sgstRate;

	@Column(name = "igstRate")
	private double igstRate;

	@Column(name = "cessRate")
	private double cessRate;

	@Column(name = "cessNonadvol")
	private double cessNonadvol;

	@Column(name = "taxableAmount")
	private double taxableAmount;

	@ManyToOne
	@JoinColumn(name = "ewaybillid")
	@JsonBackReference
	private EwayBillVO ewayBillVO;

}
