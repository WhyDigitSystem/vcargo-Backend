package com.efit.savaari.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tripinvoiceitem")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TripInvoiceItemVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tripinvoiceitemgen")
    @SequenceGenerator(
        name = "tripinvoiceitemgen",
        sequenceName = "tripinvoiceitemseq",
        initialValue = 1000000001,
        allocationSize = 1
    )
    @Column(name = "invoiceitemid")
    private Long id;

    @Column(name = "itemcode")
    private String itemCode;
    @Column(name = "description")
    private String description;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "unit")
    private String unit;
    @Column(name = "rate")
    private BigDecimal rate;
    @Column(name = "amount")
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "invoiceid")
    private TripInvoiceVO invoice;
}

