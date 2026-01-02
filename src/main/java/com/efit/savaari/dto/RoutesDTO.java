package com.efit.savaari.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoutesDTO {

private Long id;
	
	private String customer;
	private String origin;
	private String destination;
	private String vehicleType;
	private String mileage;
	private int fuelRate;
	private boolean showpumps;
	private String route;
	private String tat;
	private long orgId;
	private String branchCode;
	private String branchName;
	private String createdBy;
	private boolean active;
	
	List<RoutesPitstopDTO> routesPitstopDTO;
	List<RoutesPetrolPumpsDTO> routesPetrolPumpsDTO;
	List<RoutesDetailsDTO> routesDetailsDTO;


}
