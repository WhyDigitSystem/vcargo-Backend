package com.efit.savaari.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class TripInvoiceDTO {

    private Long Id;
    private Long orgId;

    private Long customerId;
    private String vehicleNumber;
    private String driverNumber;
    private Long tripId;

    private String tripDetails;
    private LocalDate issueDate;
    private LocalDate dueDate;

    private String status;
    private String paymentMethod;
    private LocalDate paymentDate;

    private BigDecimal subtotal;
    private BigDecimal taxRate;
    private BigDecimal taxAmount;
    private BigDecimal discount;
    private BigDecimal totalAmount;
    private BigDecimal amountPaid;
    private BigDecimal balanceDue;

    private String createdBy;
    private String notes;
    private List<TripInvoiceItemDTO> items;
}
