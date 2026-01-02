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
@Table(name = "tripslinked")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TripsLinkedVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tripslinkedgen")
	@SequenceGenerator(name = "tripslinkedgen", sequenceName = "tripslinkedseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "tripslinkedid")
	private Long id;

	@Column(name = "trips", length = 255)
	private String trips;

	@Column(name = "origin", length = 75)
	private String origin;

	@Column(name = "destination", length = 255)
	private String destination;

	@Column(name = "vechile", length = 75)
	private String vechile;
	
	@Column(name = "status", length = 75)
	private String status;

	@Column(name = "screencode", length = 5)
	private String screenCode = "TL";

	@Column(name = "screenname", length = 25)
	private String screenName = "TRIPSLINKED";
	
	
	@ManyToOne
	@JoinColumn(name = "indentsid")
	@JsonBackReference
	private IndentsVO indentsVO;

}
