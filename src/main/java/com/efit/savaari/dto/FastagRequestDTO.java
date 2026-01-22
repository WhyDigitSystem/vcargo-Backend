package com.efit.savaari.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FastagRequestDTO {
	private String subid;
	private String productid;
	private String mode;

	private String token; // ✅ from frontend (optional if you want)

	private String vehiclenumber;
}