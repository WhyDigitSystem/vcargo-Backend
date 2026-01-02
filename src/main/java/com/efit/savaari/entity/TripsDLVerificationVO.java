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
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tripsdlveridication")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TripsDLVerificationVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tripsdlveridicationgen")
	@SequenceGenerator(name = "tripsdlveridicationgen", sequenceName = "tripsdlveridicationseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "tripsdlveridicationid")
	private Long id;

	@Column(name = "field", length = 255)
	private String field;

	@Column(name = "value", length = 75)
	private String value;

	@Column(name = "screencode", length = 5)
	private String screenCode = "TV";

	@Column(name = "screenname", length = 25)
	private String screenName = "TRIPSDLVERIFICATION";
	
	

	@ManyToOne
	@JoinColumn(name = "tripsid")
	@JsonBackReference
	private TripsVO tripsVO;
}
