package com.efit.savaari.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TripGeofenceAlertsDTO {

	private Long id;
	private Long trip;
	private String tag;
	private String vehicle;
	private String invoiceType;
	private String geofence;
	
	private String timestamp;
	private long orgId;
	private String branchCode;
	private String branchName;
	private String createdBy;
	private boolean cancel;
	private boolean active;
}
