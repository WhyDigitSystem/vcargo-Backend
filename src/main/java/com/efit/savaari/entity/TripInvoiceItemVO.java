package com.efit.savaari.entity;

import java.math.BigDecimal;

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
@Table(name = "trip_invoice_item")
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
    private Long itemId;

    private String itemCode;
    private String description;
    private Integer quantity;
    private String unit;
    private BigDecimal rate;
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "invoiceid")
    private TripInvoiceVO invoice;
}

