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
@Table(name = "routespitstop")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoutesPitstopVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "routespitstopgen")
	@SequenceGenerator(name = "routespitstopgen", sequenceName = "routespitstopseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "routespitstopid")
	private Long id;
	
	@Column(name = "pitshop")
	private String pitShop;
	
	@Column(name = "screenname")
	private String screenName="ROUTESPITSTOP";
	
	@Column(name = "screencode")
	private String screenCode ="PS";
	

	@ManyToOne
	@JoinColumn(name = "routesid")
	@JsonBackReference
	private RoutesVO routesVO;
	
}
