package com.efit.savaari.entity;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.efit.savaari.dto.CreatedUpdatedDate;
import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "payoutsdocument")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayoutsDocumentVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payoutsdocumentgen")
	@SequenceGenerator(name = "payoutsdocumentgen", sequenceName = "payoutsdocumentseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "payoutsdocumentid")
	private Long id;
	@Lob
	@Column(name = "document")
	private byte[] document;
	
	@Column(name = "documenttype")
	private String documentType;
	
	@Column(name = "remarks")
	private String remarks;
	@Column(name = "trip")
	private String trip;
	@Column(name = "vehiclenumber")
	private String vehicleNumber;

	
	@ManyToOne
	@JoinColumn(name = "payoutsid")
	@JsonBackReference
	private PayoutsVO payoutsVO;
}

