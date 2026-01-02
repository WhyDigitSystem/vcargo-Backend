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
@Table(name = "tripreportmisroles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TripReportMisRolesVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tripreportmisrolesgen")
	@SequenceGenerator(name = "tripreportmisrolesgen", sequenceName = "tripreportmisrolesseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "tripreportmisrolesid")
	private Long id;

	@Column(name = "role")
	private String role;
	
	@ManyToOne
	@JoinColumn(name = "tripreportmisid")
	@JsonBackReference
	private TripReportMisVO tripReportMisVO;
}
