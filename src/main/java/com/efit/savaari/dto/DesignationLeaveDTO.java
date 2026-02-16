package com.efit.savaari.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DesignationLeaveDTO {

	private Long id;
	private String leaveType;
	private String leaveCode;
	private BigDecimal totalLeave;
	
    private String designationCode;
	private String designation;
	private boolean active;

	private String createdBy;
	private Long orgId;
}
