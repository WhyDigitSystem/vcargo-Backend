package com.efit.savaari.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "trip")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TripVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tripgen")
	@SequenceGenerator(name = "tripgen", sequenceName = "tripseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "tripid")
	private Long id;

	@Column(name = "source")
	private String source;
	
	@Column(name = "destination")
	private String destination;
	
	@Column(name = "customer")
	private String customer;

	@Column(name = "distance")
	private double distance;
	
	@Column(name = "estimatedduration")
	private String estimatedDuration;

	@Column(name = "startdate")
	private LocalDate startDate;
	
	@Column(name = "starttime")
	private LocalTime startTime;
	@Column(name = "enddate")
	private LocalDate endDate;
	@Column(name = "endtime")
	private LocalTime endTime;

	@Column(name = "status")
	private String status;
	
	@Column(name = "triptype")
	private String tripType;
	
	@Column(name = "goodstype")
	private String goodsType;
	
	@Column(name = "goodsweight")
	private BigDecimal goodsWeight;
	
	@Column(name = "goodsvalue")
	private BigDecimal goodsValue;

	@Column(name = "tripcost")
	private BigDecimal tripCost;
	
	@Column(name = "revenue")
	private BigDecimal revenue;
	
	@Column(name = "profit")
	private BigDecimal profit;

	@Column(name = "fuelcost")
	private BigDecimal fuelCost;
	
	@Column(name = "tollcharges")
	private BigDecimal tollCharges;
	
	@Column(name = "otherexpenses")
	private BigDecimal otherExpenses;

	@Column(length = 1000)
	private String notes;
	
	@Column(name = "tripstarttime")
	private LocalDateTime tripStartTime;
	
	@Column(name = "tripendtime")
	private LocalDateTime tripEndTime;
	
	@Column(name = "active")
	private boolean active = true;


	@Column(name = "createdby")
	private String createdBy;

	@Column(name = "modifiedby")
	private String updatedBy;

	@Column(name = "cancel")
	private boolean cancel=false;

	@Column(name = "branchcode")
	private String branchCode;

	@Column(name = "branchname")
	private String branchName;
	
	@Column(name = "orgid")
	private Long orgId;
	
	@ManyToOne
	@JoinColumn(name = "userid")
    private UserVO user;
	
	@ManyToOne
	@JoinColumn(name = "vehicle")
	private TvehicleVO vehicle;
	
	
	
	
	@ManyToOne
	@JoinColumn(name = "driver")
	private TdriverVO driver;

	@OneToMany(mappedBy = "tripVO", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<TripWaypointVO> waypoints = new ArrayList<>();

	
}
