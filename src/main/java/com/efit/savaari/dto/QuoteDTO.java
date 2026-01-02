package com.efit.savaari.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuoteDTO {

    private Long id;

//    @NotNull(message = "Auction ID is required")
    private Long auction;

//    @NotNull(message = "Quote Amount is required")
//    @Min(value = 1, message = "Quote Amount must be greater than 0")
    private BigDecimal quoteAmount;

//    @Size(max = 500, message = "Additional Notes cannot exceed 500 characters")
    private String additionalNotes;

//    @Size(max = 500, message = "Terms & Conditions cannot exceed 500 characters")
    private String termsAndConditions;

//    @NotBlank(message = "Vendor Code is required")
//    private String vendorCode;

    private Long userCode;


//    @NotBlank(message = "Created By cannot be empty")
    private String createdBy;


//    @NotNull(message = "Org ID is required")
    private Long orgId;
    
	private LocalDate estimatedDeliveryDate;



//    @NotBlank(message = "Branch Code is required")
    private String branchCode;

//    @NotBlank(message = "Branch Name is required")
    private String branch;
}
