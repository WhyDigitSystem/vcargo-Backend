package com.efit.savaari.dto;

import javax.persistence.Column;

import com.efit.savaari.entity.TaggingVO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaggingDTO {
	private Long id;
	private String name;
	private String description;
	private String createdBy;
	private Long orgId;
	
	private String branchCode;
	private String branch;

}
