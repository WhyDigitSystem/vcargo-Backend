package com.efit.savaari.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class TripInvoiceItemDTO {
	private String id;
	private String description;
	private Integer quantity;
	private String unit;
	private BigDecimal rate;
	private BigDecimal amount;
}
