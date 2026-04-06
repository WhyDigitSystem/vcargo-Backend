package com.efit.savaari.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlaceDetailsDTO {

	private Long id;
	private String place;
	private String createdBy;
	private Long orgId;
	private String branchCode;
	private String branch;
}
