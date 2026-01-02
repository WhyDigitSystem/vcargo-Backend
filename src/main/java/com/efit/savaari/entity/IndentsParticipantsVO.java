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
@Table(name = "indentsparticipants")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IndentsParticipantsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tripsdocumentgen")
	@SequenceGenerator(name = "tripsdocumentgen", sequenceName = "tripsdocumentseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "tripsdocumentid")
	private Long id;

	@Column(name = "vendor", length = 255)
	private String vendor;

	@Column(name = "vendorrate", length = 75)
	private String vendorRate;

	@Column(name = "ranks", length = 255)
	private String ranks;

	@Column(name = "vendorresponse", length = 75)
	private String vendorResponse;

	@Column(name = "screencode", length = 5)
	private String screenCode = "IP";

	@Column(name = "screenname", length = 25)
	private String screenName = "INDENTSPARTICIPANTS";
	
	
	@ManyToOne
	@JoinColumn(name = "indentsid")
	@JsonBackReference
	private IndentsVO indentsVO;


}
