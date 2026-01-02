package com.efit.savaari.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.efit.savaari.dto.CreatedUpdatedDate;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tripreportmis")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TripReportMisVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tripreportmisgen")
	@SequenceGenerator(name = "tripreportmisgen", sequenceName = "tripreportmisseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "tripreportmisid")
	private Long id;

	@Column(name = "refdoctype")
	private String refDocType;
	@Column(name = "reporttype")
	private String reportType;

	@Column(name = "referencereport")
	private String referenceReport;
	@Column(name = "isstandard")
	private String isStandard;
	@Column(name = "module")
	private String module;
	@Column(name = "addtotalrow")
	private boolean addTotalRow;

	@Column(name = "disabled")
	private boolean disabled;
	@Column(name = "comment")
	private String comment;

	@Column(name = "active")
	private boolean active = true;
	@Column(name = "createdby")
	private String createdBy;
	@Column(name = "modifiedby")
	private String updatedBy;
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "cancel")
	private boolean cancel = false;
	@Column(name = "branchcode")
	private String branchCode;
	@Column(name = "branch")
	private String branch;

	@Column(name = "screencode", length = 5)
	private String screenCode = "TRM";

	@Column(name = "screenname", length = 25)
	private String screenName = "TRIPREPORTMIS";

	@OneToMany(mappedBy = "tripReportMisVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<TripReportMisRolesVO> tripReportMisRolesVO ;

	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}
