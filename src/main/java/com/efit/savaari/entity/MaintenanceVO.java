package com.efit.savaari.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "maintenance")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaintenanceVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "maintenancegen")
	@SequenceGenerator(name = "maintenancegen", sequenceName = "maintenanceseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "maintenanceid")
	private Long id;

	@Column(name="title")
	private String title;
	@Column(name="type")
	private String type; // preventive / corrective
	@Column(name="status")
	private String status; // pending / completed
	@Column(name="priority")
	private String priority; // low / medium / high

	@Column(name="scheduleddate")
	private LocalDate scheduledDate;
	@Column(name="completeddate")
	private LocalDate completedDate;
	@Column(name="odometerreading")
	private BigDecimal odometerReading;
	@Column(name="totalcost")
	private BigDecimal totalCost;
	@Column(name="totalqty")
	private BigDecimal totalqty;
	@Column(name="estimatedcost")
	private BigDecimal estimatedCost;
	@Column(name="servicecenter")
	private String serviceCenter;
	@Column(name="mechanic")
	private String mechanic;

	@Column(name="description")
	private String description;

	@Column(name="notes")
	private String notes;
	
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

	@OneToMany(mappedBy = "maintenanceVO", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<MaintenancePartVO> parts;
}
