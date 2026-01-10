package com.efit.savaari.entity;

import javax.persistence.*;

import lombok.Data;

@Entity
@Table(name = "tripwaypoints")
@Data
public class TripWaypointVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tripwaypointgen")
	@SequenceGenerator(name = "tripwaypointgen", sequenceName = "tripwaypointseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "waypointId")
	private Long id;

	@Column(name = "location")
	private String location;
	
	@Column(name = "sequenceno")
	private Integer sequenceNo;

	@ManyToOne
	@JoinColumn(name = "tripid")
	private TripVO tripVO;
}
