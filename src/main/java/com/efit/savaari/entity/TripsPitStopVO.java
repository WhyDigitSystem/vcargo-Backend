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
@Table(name = "tripspitstop")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TripsPitStopVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tripspitstopgen")
	@SequenceGenerator(name = "tripspitstopgen", sequenceName = "tripspitstopseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "tripspitstopid")
	private Long id;

	@Column(name = "pitstop", length = 255)
	private String pitstop;

	
	@Column(name = "screencode", length = 5)
	private String screenCode = "TP";

	@Column(name = "screenname", length = 25)
	private String screenName = "TRIPSPITSTOP";
	
	

	@ManyToOne
	@JoinColumn(name = "tripsid")
	@JsonBackReference
	private TripsVO tripsVO;
}

