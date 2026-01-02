package com.efit.savaari.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChargeTypeDTO {

	private Long id;
	private String chargeType;
	private String unit;
	private boolean active ;

	private String createdBy;
	private Long orgId;
	private String branchCode;
	private String branch;

}
