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
@Table(name = "timeline")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimeLineVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "timelinegen")
	@SequenceGenerator(name = "timelinegen", sequenceName = "timelineseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "timelineid")
	private Long id;

	@Column(name = "event", length = 255)
	private String event;

	@Column(name = "time", length = 75)
	private String time;

	@Column(name = "user", length = 255)
	private String user;

	@Column(name = "screencode", length = 5)
	private String screenCode = "TL";

	@Column(name = "screenname", length = 25)
	private String screenName = "TIMELINE";
	
	
	@ManyToOne
	@JoinColumn(name = "indentsid")
	@JsonBackReference
	private IndentsVO indentsVO;
	

}
