package com.efit.savaari.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayoutsTimeLineDTO {

	private String status;
	private LocalDate timeRecorded;
}
