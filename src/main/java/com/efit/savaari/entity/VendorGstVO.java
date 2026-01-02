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
@Table(name = "vendorgst")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendorGstVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vendorgstgen")
	@SequenceGenerator(name = "vendorgstgen", sequenceName = "vendorgstseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "vendorgstid")
	private Long id;
	
//	@ManyToOne
//	@JoinColumn(name = "industryid")
	
//    private IndustryVO industry;

	@Column(name = "gst")
	private String gst;
	
	
	@ManyToOne
	@JoinColumn(name = "vendorid")
	@JsonBackReference
	private VendorVO vendorVO;
	

}