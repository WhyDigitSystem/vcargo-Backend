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
@Table(name = "payoutstripsdetails")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayoutTripsDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payoutstripsdetailsgen")
	@SequenceGenerator(name = "payoutstripsdetailsgen", sequenceName = "payoutstripsdetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "payoutstripsdetailsid")
	private Long id;
	
	@Column(name = "trips")
	private String trips;
	
	@Column(name = "origin")
	private String origin;
	@Column(name = "destination")
	private String destination;
	@Column(name = "vehicle")
	private String vehicle;
	@Column(name = "status")
	private String status;
	
	@ManyToOne
	@JoinColumn(name = "payoutsid")
	@JsonBackReference
	private PayoutsVO payoutsVO;
}

