package com.efit.savaari.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class TripInvoiceItemResponseDTO {

	private Long id;
	private String itemCode;
	private String description;
	private Integer quantity;
	private String unit;
	private BigDecimal rate;
	private BigDecimal amount;
}
