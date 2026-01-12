package com.efit.savaari.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "trip_invoice")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TripInvoiceVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tripinvoicegen")
    @SequenceGenerator(
        name = "tripinvoicegen",
        sequenceName = "tripinvoiceseq",
        initialValue = 1000000001,
        allocationSize = 1
    )
    @Column(name = "invoiceid")
    private Long invoiceId;

    /* ================= RELATIONS ================= */

    @ManyToOne
    @JoinColumn(name = "customerid")
    private CustomerVO customer;

    @ManyToOne
    @JoinColumn(name = "vehicleid")
    private VehicleVO vehicle;

    @ManyToOne
    @JoinColumn(name = "driverid")
    private DriverVO driver;

    @ManyToOne
    @JoinColumn(name = "tripid")
    private TripVO trip;

    /* ================= INVOICE DETAILS ================= */

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

    @Column(length = 1000)
    private String notes;

    @OneToMany(
        mappedBy = "invoice",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<TripInvoiceItemVO> items;
}
