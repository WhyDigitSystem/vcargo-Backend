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
@Table(name = "lineitems")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LineItemsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lineitemsgen")
	@SequenceGenerator(name = "lineitemsgen", sequenceName = "lineitemsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "lineitemsid")
	private Long id;

	@Column(name = "origin", length = 255)
	private String origin;

	@Column(name = "destination", length = 75)
	private String destination;
	
	@Column(name = "vechiletype", length = 75)
	private String vechileType;

	@Column(name = "round", length = 75)
	private int round;
	@Column(name = "zone", length = 75)
	private String zone;
	
	@Column(name = "way", length = 75)
	private String way;
	
	@Column(name = "screencode", length = 5)
	private String screenCode = "LI";

	@Column(name = "screenname", length = 25)
	private String screenName = "LINEITEMS";
	
	
	@ManyToOne
	@JoinColumn(name = "requestforQuotesid")
	@JsonBackReference
	private RequestforQuotesVO requestforQuotesVO;

}

