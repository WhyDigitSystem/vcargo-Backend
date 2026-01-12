package com.efit.savaari.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class TripInvoiceDTO {

    private Long invoiceId;

    private Long customerId;
    private Long vehicleId;
    private Long driverId;
    private Long tripId;

    private String tripDetails;
    private String issueDate;
    private String dueDate;

    private String status;
    private String paymentMethod;
    private String paymentDate;

    private BigDecimal subtotal;
    private BigDecimal taxRate;
    private BigDecimal taxAmount;
    private BigDecimal discount;
    private BigDecimal totalAmount;
    private BigDecimal amountPaid;
    private BigDecimal balanceDue;

    private String notes;
    private List<TripInvoiceItemDTO> items;
}

