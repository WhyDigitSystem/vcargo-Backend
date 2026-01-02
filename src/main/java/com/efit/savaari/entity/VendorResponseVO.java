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
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vendorresponse")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VendorResponseVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vendorresponsegen")
	@SequenceGenerator(name = "vendorresponsegen", sequenceName = "vendorresponseseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "vendorresponseid")
	private Long id;

	@Column(name = "lrnumber", length = 255)
	private String lrNumber;

	@Column(name = "vechilenumber", length = 75)
	private String vehicleNumber;

	@Column(name = "drivernumber", length = 255)
	private String driverNumber;

	@Column(name = "drivername", length = 75)
	private String driverName;

	@Column(name = "screencode", length = 5)
	private String screenCode = "VR";

	@Column(name = "screenname", length = 25)
	private String screenName = "VENDORRESPONSE";
	
	
	@ManyToOne
	@JoinColumn(name = "indentsid")
	@JsonBackReference
	private IndentsVO indentsVO;


}

