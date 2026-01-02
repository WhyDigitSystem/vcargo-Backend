package com.efit.savaari.entity;

import java.math.BigDecimal;

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
@Table(name = "routesdetails")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoutesDetailsVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "routesdetailsgen")
	@SequenceGenerator(name = "routesdetailsgen", sequenceName = "routesdetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "routesdetailsid")
	private Long id;
	
	@Column(name = "name")
	private String name;
	@Column(name = "distance")
	private int distance;
	@Column(name = "nooftolls")
	private int noOfTolls;
	@Column(name = "tollcost")
	private int tollCost;
	@Column(name = "fuelcost")
	private int fuelCost;
	@Column(name = "totalcost")
	private int totalCost;
	@Column(name = "savings")
	private BigDecimal savings;
	@Column(name = "duration")
	private int duration;
	@Column(name = "defaults")
	private boolean defaults;
	
	@Column(name = "screenname")
	private String screenName="ROUTESPETROLPUMPS";
	
	@Column(name = "screencode")
	private String screenCode ="RPP";
	

	@ManyToOne
	@JoinColumn(name = "routesid")
	@JsonBackReference
	private RoutesVO routesVO;
}
