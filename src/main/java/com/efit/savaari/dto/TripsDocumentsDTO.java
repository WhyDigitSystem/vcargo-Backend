package com.efit.savaari.dto;

import javax.persistence.Column;
import javax.persistence.Lob;

import com.efit.savaari.entity.IndentsVO;
import com.efit.savaari.entity.TripsDocumentsVO;

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
