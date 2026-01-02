package com.efit.savaari.dto;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.efit.savaari.entity.IndentsParticipantsVO;
import com.efit.savaari.entity.IndentsVO;
import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IndentsParticipantsDTO {

	private String vendor;
	private String vendorRate;
	private String ranks;
	private String vendorResponse;

	
	

}
