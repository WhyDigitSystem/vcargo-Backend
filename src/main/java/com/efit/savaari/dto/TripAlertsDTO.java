package com.efit.savaari.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TripAlertsDTO {

    private Long id;

    @NotNull(message = "Trip ID is required")
    private Long trip;

    @NotNull(message = "From Date is required")
    private LocalDate fromDate;

    @NotNull(message = "To Date is required")
    private LocalDate toDate;

    @NotNull(message = "Vehicle ID is required")
    private Long vehicle;

    @NotNull(message = "Driver ID is required")
    private Long driver;

    @NotBlank(message = "Alert type is required")
    private String type;

    @Size(max = 255, message = "Extra info cannot exceed 255 characters")
    private String extra;

    private boolean active = true;

    @NotBlank(message = "Created By cannot be empty")
    private String createdBy;

    @NotNull(message = "Org ID is required")
    private Long orgId;

    @NotBlank(message = "Branch Code is required")
    private String branchCode;

    @NotBlank(message = "Branch name is required")
    private String branch;
}
