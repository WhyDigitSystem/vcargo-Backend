package com.efit.savaari.responseDTO;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceDashboardDTO {


    private Long invoiceId;
    private String vehicleNo;
    private String driverName;
    private String customerName;

    private String tripDetails;

    private LocalDate issueDate;
    private LocalDate dueDate;

    private BigDecimal subtotal;
    private BigDecimal taxAmount;
    private BigDecimal discount;
    private BigDecimal totalAmount;
    private BigDecimal amountPaid;
    private BigDecimal balanceDue;

    private String paymentMethod;
    private LocalDate paymentDate;

    private String status;
}
