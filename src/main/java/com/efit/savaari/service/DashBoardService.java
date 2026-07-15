package com.efit.savaari.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.efit.savaari.exception.ApplicationException;
import com.efit.savaari.responseDTO.MaintenanceResponseDTO;
import com.efit.savaari.responseDTO.TvehicleResponseDTO;

@Service
public interface DashBoardService {

//	Map<String, Object> getDashboardData(Long orgId);

	Map<String, Object> getAllDashBoardDetails(Long orgId, String type);

	Map<String, Object> getDashboardData(Long orgId, String type);

	Map<String, Object> getAllDashBoardVehicleDetails(Long orgId, String type);


//	Map<String, Object> getDashboardAlerts(Long orgId);

	Map<String, Object> getEscalationDashboard(Long orgId);

//	List<MaintenanceResponseDTO> getMaintenanceScheduleForDashBoard(Long orgId, String vehicleNumber, String type) throws ApplicationException;
//
//	List<TvehicleResponseDTO> getInsuranceExpiryForDashBoard(Long orgId, String vehicleNumber, String type) throws ApplicationException;


}
