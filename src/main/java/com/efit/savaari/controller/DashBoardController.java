package com.efit.savaari.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.efit.savaari.responseDTO.MaintenanceResponseDTO;
import com.efit.savaari.responseDTO.ResponseDTO;
import com.efit.savaari.responseDTO.TvehicleResponseDTO;
import com.efit.savaari.service.DashBoardService;

@CrossOrigin
@RestController
@RequestMapping("/api/dashboard")
public class DashBoardController extends BaseController  {

	@Autowired
	DashBoardService dashBoardService;

	public static final Logger LOGGER = LoggerFactory.getLogger(CommonMasterController.class);
	
	
//	@GetMapping("/getDashboardData")
//    public ResponseEntity<Map<String, Object>> getDashboardData(
//            @RequestParam Long orgId) {
//
//        Map<String, Object> response = dashBoardService.getDashboardData(orgId);
//        return ResponseEntity.ok(response);
//    }

	@GetMapping("/getAllRecentActivities")
	public ResponseEntity<ResponseDTO> getDashboardData(
	        @RequestParam Long orgId,
	        @RequestParam String type) {

	    String methodName = "getDashboardData()";
	    LOGGER.debug("Starting {}", methodName);

	    Map<String, Object> responseMap = new HashMap<>();
	    ResponseDTO responseDTO;

	    try {

	        Map<String, Object> dashboardData =
	                dashBoardService.getDashboardData(orgId, type);

	        responseMap.put("message", "Dashboard data retrieved successfully");
	        responseMap.put("dashboard", dashboardData);

	        responseDTO = createServiceResponse(responseMap);

	    } catch (Exception e) {

	        LOGGER.error("Error in {}: {}", methodName, e.getMessage(), e);

	        responseDTO = createServiceResponseError(
	                responseMap,
	                "Error fetching dashboard data",
	                e.getMessage());
	    }

	    LOGGER.debug("Ending {}", methodName);
	    return ResponseEntity.ok(responseDTO);
	}

	
	

	@GetMapping("/getAllDashBoardStatsDetails")
	public ResponseEntity<ResponseDTO> getAllDashBoardDetails(
	        @RequestParam Long orgId,
	        @RequestParam(required = false) String type) {

	    Map<String, Object> responseMap = new HashMap<>();
	    ResponseDTO responseDTO;

	    try {

	        Map<String, Object> dashboard =
	                dashBoardService.getAllDashBoardDetails(orgId, type);

	        responseMap.put("message", "Dashboard details fetched successfully");
	        responseMap.put("dashboard", dashboard);

	        responseDTO = createServiceResponse(responseMap);

	    } catch (Exception e) {

	        responseDTO = createServiceResponseError(
	                responseMap,
	                "Error fetching dashboard data",
	                e.getMessage());
	    }

	    return ResponseEntity.ok(responseDTO);
	}
	  
	@GetMapping("/getAllDashBoardVehicleDetails")
	public ResponseEntity<ResponseDTO> getAllDashBoardVehicleDetails(
	        @RequestParam Long orgId,
	        @RequestParam(required = false) String type) {

	        String methodName = "getAllDashBoardVehicleDetails()";
	        LOGGER.debug("Starting {}", methodName);

	        Map<String, Object> responseMap = new HashMap<>();
	        ResponseDTO responseDTO;

	        try {

	            Map<String, Object> vehicles =
	                    dashBoardService.getAllDashBoardVehicleDetails(orgId, type);

	            responseMap.put("message", "Dashboard Vehicles details fetched successfully");
	            responseMap.put("vehicles", vehicles);

	            responseDTO = createServiceResponse(responseMap);

	        } catch (Exception e) {

	            LOGGER.error("Error in {}: {}", methodName, e.getMessage(), e);

	            responseDTO = createServiceResponseError(
	                    responseMap,
	                    "Error fetching dashboard data",
	                    e.getMessage()
	            );
	        }

	        LOGGER.debug("Ending {}", methodName);
	        return ResponseEntity.ok(responseDTO);
	    }
	
	//maintainance
//	
//	@GetMapping("/getMaintenanceScheduleForDashBoard")
//	public ResponseEntity<ResponseDTO> getMaintenanceScheduleForDashBoard(
//	        @RequestParam Long orgId,
//	        @RequestParam(required = false) String vehicleNumber,
//	        @RequestParam String type) {
//
//	    String methodName = "getMaintenanceScheduleForDashBoard()";
//	    LOGGER.debug("Starting {}", methodName);
//
//	    Map<String, Object> responseMap = new HashMap<>();
//	    ResponseDTO responseDTO;
//
//	    try {
//	    	
//	        List<MaintenanceResponseDTO> maintenanceList =
//	        		dashBoardService.getMaintenanceScheduleForDashBoard(orgId, vehicleNumber, type);
//
//	        responseMap.put("message", "Maintenance schedule fetched successfully");
//	        responseMap.put("maintenance", maintenanceList);
//
//	        responseDTO = createServiceResponse(responseMap);
//
//	    } catch (Exception e) {
//
//	        LOGGER.error("Error in {}: {}", methodName, e.getMessage(), e);
//
//	        responseDTO = createServiceResponseError(
//	                responseMap,
//	                "Error fetching maintenance schedule",
//	                e.getMessage()
//	        );
//	    }
//
//	    LOGGER.debug("Ending {}", methodName);
//	    return ResponseEntity.ok(responseDTO);
//	}
//	
//	@GetMapping("/getInsuranceExpiryForDashBoard")
//	public ResponseEntity<ResponseDTO> getInsuranceExpiryForDashBoard(
//	        @RequestParam Long orgId,
//	        @RequestParam(required = false) String vehicleNumber,
//	        @RequestParam String type) {
//
//	    String methodName = "getInsuranceExpiryForDashBoard()";
//	    LOGGER.debug("Starting {}", methodName);
//
//	    Map<String, Object> responseMap = new HashMap<>();
//	    ResponseDTO responseDTO;
//
//	    try {
//
//	        List<TvehicleResponseDTO> vehicles =
//	                dashBoardService.getInsuranceExpiryForDashBoard(orgId, vehicleNumber, type);
//
//	        responseMap.put("message", "Insurance expiry details fetched successfully");
//	        responseMap.put("vehicles", vehicles);
//	        responseMap.put("count", vehicles.size());
//
//	        responseDTO = createServiceResponse(responseMap);
//
//	    } catch (Exception e) {
//
//	        LOGGER.error("Error in {}: {}", methodName, e.getMessage(), e);
//
//	        responseDTO = createServiceResponseError(
//	                responseMap,
//	                "Error fetching insurance expiry details",
//	                e.getMessage());
//	    }
//
//	    LOGGER.debug("Ending {}", methodName);
//	    return ResponseEntity.ok(responseDTO);
//	}
	
	@GetMapping("/getEscalationDashboard")
	public ResponseEntity<ResponseDTO> getEscalationDashboard(
	        @RequestParam Long orgId) {

	    String methodName = "getEscalationDashboard()";
	    LOGGER.debug("Starting {}", methodName);

	    Map<String, Object> responseMap = new HashMap<>();
	    ResponseDTO responseDTO;

	    try {

	        Map<String, Object> dashboard =
	                dashBoardService.getEscalationDashboard(orgId);

	        responseMap.put("message", "Escalation Dashboard fetched successfully");
	        responseMap.put("dashboard", dashboard);

	        responseDTO = createServiceResponse(responseMap);

	    } catch (Exception e) {

	        LOGGER.error("Error in {} : {}", methodName, e.getMessage(), e);

	        responseDTO = createServiceResponseError(
	                responseMap,
	                "Error fetching escalation dashboard",
	                e.getMessage());
	    }

	    return ResponseEntity.ok(responseDTO);
	}
	
	
	@GetMapping("/getFuelSummaryDashboard")
	public ResponseEntity<ResponseDTO> getFuelSummaryDashboard(
	        @RequestParam Long orgId,
	        @RequestParam String type) {

	    String methodName = "getFuelSummaryDashboard()";
	    LOGGER.debug("Starting {}", methodName);

	    Map<String, Object> responseMap = new HashMap<>();
	    ResponseDTO responseDTO;

	    try {

	        Map<String, Object> dashboard =
	                dashBoardService.getFuelSummaryDashboard(orgId, type);

	        responseMap.put("message", "Fuel Summary Dashboard fetched successfully");
	        responseMap.put("dashboard", dashboard);

	        responseDTO = createServiceResponse(responseMap);

	    } catch (Exception e) {

	        LOGGER.error("Error in {} : {}", methodName, e.getMessage(), e);

	        responseDTO = createServiceResponseError(
	                responseMap,
	                "Error fetching fuel summary dashboard",
	                e.getMessage());
	    }

	    return ResponseEntity.ok(responseDTO);
	}
	
	@GetMapping("/getUpcomingMaintenanceDashboard")
	public ResponseEntity<ResponseDTO> getUpcomingMaintenanceDashboard(
	        @RequestParam Long orgId) {

	    String methodName = "getUpcomingMaintenanceDashboard()";
	    LOGGER.debug("Starting {}", methodName);

	    Map<String, Object> responseMap = new HashMap<>();
	    ResponseDTO responseDTO;

	    try {

	        Map<String, Object> dashboard =
	                dashBoardService.getUpcomingMaintenanceDashboard(orgId);

	        responseMap.put("message", "Upcoming Maintenance Dashboard fetched successfully");
	        responseMap.put("dashboard", dashboard);

	        responseDTO = createServiceResponse(responseMap);

	    } catch (Exception e) {

	        LOGGER.error("Error in {} : {}", methodName, e.getMessage(), e);

	        responseDTO = createServiceResponseError(
	                responseMap,
	                "Error fetching upcoming maintenance dashboard",
	                e.getMessage());
	    }

	    return ResponseEntity.ok(responseDTO);
	}
	
	
	@GetMapping("/getExpirySummaryDashboard")
	public ResponseEntity<ResponseDTO> getExpirySummaryDashboard(
	        @RequestParam Long orgId) {

	    String methodName = "getExpirySummaryDashboard()";
	    LOGGER.debug("Starting {}", methodName);

	    Map<String, Object> responseMap = new HashMap<>();
	    ResponseDTO responseDTO;

	    try {

	        Map<String, Object> dashboard =
	                dashBoardService.getExpirySummaryDashboard(orgId);

	        responseMap.put("message", "Fitness Expiry Dashboard fetched successfully");
	        responseMap.put("dashboard", dashboard);

	        responseDTO = createServiceResponse(responseMap);

	    } catch (Exception e) {

	        LOGGER.error("Error in {} : {}", methodName, e.getMessage(), e);

	        responseDTO = createServiceResponseError(
	                responseMap,
	                "Error fetching fitness expiry dashboard",
	                e.getMessage());
	    }

	    return ResponseEntity.ok(responseDTO);
	}
	
	@GetMapping("/getTripCompletionDashboard")
	public ResponseEntity<ResponseDTO> getTripCompletionDashboard(
	        @RequestParam Long orgId,
	        @RequestParam String type) {

	    String methodName = "getTripCompletionDashboard()";
	    LOGGER.debug("Starting {}", methodName);

	    Map<String, Object> responseMap = new HashMap<>();
	    ResponseDTO responseDTO;

	    try {

	        Object dashboard = dashBoardService.getTripCompletionDashboard(orgId, type);

	        responseMap.put("message", "Trip Completion Dashboard fetched successfully");
	        responseMap.put("dashboard", dashboard);

	        responseDTO = createServiceResponse(responseMap);

	    } catch (Exception e) {

	        LOGGER.error("Error in {} : {}", methodName, e.getMessage(), e);

	        responseDTO = createServiceResponseError(responseMap,
	                "Error fetching trip completion dashboard",
	                e.getMessage());
	    }

	    return ResponseEntity.ok(responseDTO);
	}
	
}
