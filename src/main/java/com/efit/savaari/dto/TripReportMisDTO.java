package com.efit.savaari.dto;

import java.util.List;

import javax.persistence.Column;

import com.efit.savaari.entity.TripReportMisRolesVO;
import com.efit.savaari.entity.TripReportMisVO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TripReportMisDTO {

	private Long id;
	private String refDocType;
	private String reportType;
	private String referenceReport;
	private String isStandard;
	private String module;
	private boolean addTotalRow;
	private boolean disabled;
	private String comment;

	private String createdBy;
	private Long orgId;
	private String branchCode;
	private String branch;
	
	List<TripReportMisRolesDTO> tripReportMisRolesDTO;
}
