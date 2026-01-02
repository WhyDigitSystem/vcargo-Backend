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
@Table(name = "routespetrolpumps")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoutesPetrolPumpsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "routespetrolpumpsgen")
	@SequenceGenerator(name = "routespetrolpumpsgen", sequenceName = "routespetrolpumpsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "routespetrolpumpsid")
	private Long id;
	
	@Column(name = "name")
	private String name;
	@Column(name = "address")
	private String address;
	@Column(name = "city")
	private String city;
	@Column(name = "state")
	private String state;
	@Column(name = "type")
	private String type;
	
	@Column(name = "screenname")
	private String screenName="ROUTESPETROLPUMPS";
	
	@Column(name = "screencode")
	private String screenCode ="RPP";
	

	@ManyToOne
	@JoinColumn(name = "routesid")
	@JsonBackReference
	private RoutesVO routesVO;
}
