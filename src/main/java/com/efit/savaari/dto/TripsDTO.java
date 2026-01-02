package com.efit.savaari.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TripsDTO {

	private Long id;
	private String vendor;
	private String customer;
	private String lrNumber;
	private String vehicleNumber;
	private String route;
	private boolean routeTrip;
	private boolean pitStop;
	private String origin;
	private String destination;
	private String driverNumber;
	private String driverName;
	private String eta;
	private String tatDays;
	private String status;
	private String materialType;
	private String vehicleType;
	private String vehicleTonnageCapacity;
	private String overTimeHours;
	private String materialSqFt;
	private String weight;
	private String createdBy;
	private Long orgId;	
	private String branchCode;
	private String branch;
	
	List<TripsDLVerificationDTO> tripsDLVerificationDTO;
	List<TripsPitStopDTO> tripsPitStopDTO;

}
