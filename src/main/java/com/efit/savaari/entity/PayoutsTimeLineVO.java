package com.efit.savaari.entity;

import java.time.LocalDate;

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
@Table(name = "payoutstimeline")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayoutsTimeLineVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payoutstimelinegen")
	@SequenceGenerator(name = "payoutstimelinegen", sequenceName = "payoutstimelineseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "payoutstimelineid")
	private Long id;
	@Column(name = "status")
	private String status;
	@Column(name = "timerecorded")
	private LocalDate timeRecorded;
	
	@ManyToOne
	@JoinColumn(name = "payoutsid")
	@JsonBackReference
	private PayoutsVO payoutsVO;
}


