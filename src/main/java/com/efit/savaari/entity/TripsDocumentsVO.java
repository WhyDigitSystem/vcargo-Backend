package com.efit.savaari.entity;

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

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tripsdocument")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TripsDocumentsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tripsdocumentgen")
	@SequenceGenerator(name = "tripsdocumentgen", sequenceName = "tripsdocumentseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "tripsdocumentid")
	private Long id;

	@Column(name = "doctype", length = 255)
	private String docType;

	@Column(name = "remarks", length = 75)
	private String remarks;

	@Column(name = "trip", length = 255)
	private String trip;

	@Column(name = "vechilenumber", length = 75)
	private String vechileNumber;
	
	@Lob
	@Column(name = "contractattachment")
	private byte[] contractAttachment;

	@Column(name = "screencode", length = 5)
	private String screenCode = "TD";

	@Column(name = "screenname", length = 25)
	private String screenName = "TRIPSDOCUMENTS";
	
	
	@ManyToOne
	@JoinColumn(name = "indentsid")
	@JsonBackReference
	private IndentsVO indentsVO;

}
