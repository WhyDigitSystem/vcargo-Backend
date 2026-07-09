package com.efit.savaari.service;

import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface DashBoardService {

//	Map<String, Object> getDashboardData(Long orgId);

	Map<String, Object> getAllDashBoardDetails(Long orgId, String type);

	Map<String, Object> getDashboardData(Long orgId, String type);

	Map<String, Object> getAllDashBoardVehicleDetails(Long orgId, String type);

}
