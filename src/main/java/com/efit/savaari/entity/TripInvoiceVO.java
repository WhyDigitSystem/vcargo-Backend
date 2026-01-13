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
@Table(name = "tripinvoice")
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

    @Column(name = "customer")
    private String customer;

    @ManyToOne
    @JoinColumn(name = "tvehicleid")
    private TvehicleVO vehicle;

    @ManyToOne
    @JoinColumn(name = "tdriverid")
    private TdriverVO driver;

    @ManyToOne
    @JoinColumn(name = "tripid")
    private TripVO trip;

    /* ================= INVOICE DETAILS ================= */

    @Column(name = "tripdetails")
    private String tripDetails;

    @Column(name = "issuedate")
    private LocalDate issueDate;

    @Column(name = "duedate")
    private LocalDate dueDate;

    @Column(name = "status")
    private String status;

    @Column(name = "paymentmethod")
    private String paymentMethod;

    @Column(name = "paymentdate")
    private LocalDate paymentDate;

    @Column(name = "subtotal")
    private BigDecimal subtotal;

    @Column(name = "taxrate")
    private BigDecimal taxRate;

    @Column(name = "taxamount")
    private BigDecimal taxAmount;

    @Column(name = "discount")
    private BigDecimal discount;

    @Column(name = "totalamount")
    private BigDecimal totalAmount;

    @Column(name = "amountpaid")
    private BigDecimal amountPaid;

    @Column(name = "balancedue")
    private BigDecimal balanceDue;

    @Column(name = "notes", length = 1000)
    private String notes;

    /* ================= AUDIT FIELDS ================= */

    @Column(name = "createdby")
    private String createdBy;

    @Column(name = "updatedby")
    private String updatedBy;

    @Column(name = "orgid")
    private Long orgId;

    @Column(name = "branchcode")
    private String branchCode;

    @Column(name = "branchname")
    private String branchName;

    @Column(name = "active")
    private Boolean active = true;

    @Column(name = "cancel")
    private Boolean cancel = false;

    /* ================= INVOICE ITEMS ================= */

    @OneToMany(
        mappedBy = "invoice",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<TripInvoiceItemVO> items;
}
