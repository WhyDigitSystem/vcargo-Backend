package com.efit.savaari.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ApprovedQuoteDTO {

    private Long auctionsId;
    private String auctionsType;
    private String loading;
    private String unloading;
    private String additionalCharges;
    private String allowedBids;
    private String bidType;
    private Double dimension;

    private String material;
    private Integer materialQuantity;
    private BigDecimal materialWeight;

    private String maxPrice;
    private Integer minBidDifference;
    private String minPrice;
    private String shortCuts;
    private String unloadingDate;
    private String vehicle;
    private Integer vehicleQuantity;
    private String weightUnit;

    private String approvedVendor;
    private BigDecimal approvedAmount;

    private String minVendor;
    private BigDecimal minQuotedAmount;

    private String maxVendor;
    private BigDecimal maxQuotedAmount;
}

