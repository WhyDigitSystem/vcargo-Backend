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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "maintenanceparts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaintenancePartVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "maintenancepartgen")
	@SequenceGenerator(name = "maintenancepartgen", sequenceName = "maintenancepartseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "maintenancepartsid")
	private Long id;
	@Column(name = "name")
	private String name;
	@Column(name = "quantity")
	private Integer quantity;
	@Column(name = "cost")
	private BigDecimal cost;

	@ManyToOne
	@JoinColumn(name = "maintenanceid")
	private MaintenanceVO maintenanceVO;
}
