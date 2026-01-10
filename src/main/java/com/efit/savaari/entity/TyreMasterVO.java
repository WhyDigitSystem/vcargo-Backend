package com.efit.savaari.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.*;

import com.efit.savaari.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tyremaster")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TyreMasterVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tyremastergen")
	@SequenceGenerator(name = "tyremastergen", sequenceName = "tyremasterseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "tyreid")
	private Long id;

	@Column(name = "serialnumber")
	private String serialNumber;

	@Column(name = "brand")
	private String brand;

	@Column(name = "model")
	private String model;

	@Column(name = "size")
	private String size;

	@Column(name = "position")
	private String position;

	@Column(name = "status")
	private String status;

	@Column(name = "purchasedate")
	private LocalDate purchaseDate;

	@Column(name = "purchasecost")
	private BigDecimal purchaseCost;

	@Column(name = "odometerreading")
	private BigDecimal odometerReading;

	@Column(name = "treaddepth")
	private BigDecimal treadDepth;

	@Column(name = "recommendedpressure")
	private Integer recommendedPressure;

	@Column(name = "pressure")
	private Integer pressure;

	@Column(name = "notes")
	private String notes;
	
	@Column(name = "active")
	private boolean active = true;


	@Column(name = "createdby")
	private String createdBy;

	@Column(name = "modifiedby")
	private String updatedBy;

	@Column(name = "cancel")
	private boolean cancel;

	@Column(name = "branchcode")
	private String branchCode;

	@Column(name = "branchname")
	private String branchName;
	
	@Column(name = "orgid")
	private Long orgId;
	
	@ManyToOne
	@JoinColumn(name = "vehicle")
	private TvehicleVO vehicle;
	
	@ManyToOne
	@JoinColumn(name = "userid")
    private UserVO user;
	
	@Embedded
	private CreatedUpdatedDate createdUpdatedDate;
}
