package com.efit.savaari.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TripsDocumentsDTO {

	private Long id;
	private String docType;
	private String remarks;
	private String trip;
	private String vechileNumber;
	
	private byte[] contractAttachment;

}
