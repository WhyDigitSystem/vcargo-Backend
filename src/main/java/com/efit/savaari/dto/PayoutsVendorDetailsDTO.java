package com.efit.savaari.dto;

import java.math.BigDecimal;

import javax.persistence.Column;

import com.efit.savaari.entity.PayoutsVO;
import com.efit.savaari.entity.PayoutsVendorDetailsVO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayoutsVendorDetailsDTO {

//	private Long id;
	private String purpose;
	private BigDecimal amount;
	private String remarks;
	private String trip;
	private String status;
	private boolean createPayouts;
	
}
