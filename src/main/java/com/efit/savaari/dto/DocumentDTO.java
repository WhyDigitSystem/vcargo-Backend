package com.efit.savaari.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentDTO {
	private Long id;
	private String name;
	private String type;
	private LocalDate issueDate;
	private LocalDate expiryDate;
	private String associatedWith;
	private String associationType;
	private String description;
	private String tags;
	private String documentNo;
	private String status;
	private Long userId;
	private String varifiedStatus;

	private String branchCode;

	private Long orgId;
	private String createdBy;
}
