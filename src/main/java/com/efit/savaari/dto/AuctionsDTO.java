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
public class AuctionsDTO {

	private Long id;
	private String auctionsType;
	private boolean roundTrip;
	private boolean customGeofence;
	private String loading;
	private String unloading;
	private String vehicle;
	private int vehicleQuantity;
	private LocalDate loadingDate;
	private String loadingTime;
	private LocalDate unloadingDate;
	private int duration;
	private String startDate;
	private String endDate;
	private String material;
	private int materialQuantity;
	private BigDecimal materialWeight;
	private String weightUnit;
	private double dimension;
	private String terms;
	private String organizationName;
    private Long userId;


	private String transporterTag;
	private int numTransporter;
	private String excludeTransporters;
	private String shortCuts;
	private String bidType;
	private int minBidDifferent;
	private String allowedBits;
	private String maxPrice;
	private String minPrice;
	private String additionalCharges;
	private String createdBy;
	private Long orgId;
	private String branchCode;
	private String branch;

}
