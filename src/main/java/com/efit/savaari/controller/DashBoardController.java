package com.efit.savaari.controller;

import java.util.HashMap;
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

import com.efit.savaari.responseDTO.ResponseDTO;
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
	public ResponseEntity<ResponseDTO> getDashboardData(@RequestParam Long orgId) {

	    String methodName = "getDashboardData()";
	    LOGGER.debug("Starting {}", methodName);

	    Map<String, Object> responseMap = new HashMap<>();
	    ResponseDTO responseDTO;

	    try {

	        Map<String, Object> dashboardData =
	                dashBoardService.getDashboardData(orgId);

	        responseMap.put("message", "Dashboard data retrieved successfully");
	        responseMap.put("dashboard", dashboardData);

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

	
	

	  @GetMapping("/getAllDashBoardStatsDetails")
	    public ResponseEntity<ResponseDTO> getAllDashBoardDetails(
	            @RequestParam Long orgId) {

	        String methodName = "getAllDashBoardDetails()";
	        LOGGER.debug("Starting {}", methodName);

	        Map<String, Object> responseMap = new HashMap<>();
	        ResponseDTO responseDTO;

	        try {

	            Map<String, Object> dashboard =
	                    dashBoardService.getAllDashBoardDetails(orgId);

	            responseMap.put("message", "Dashboard details fetched successfully");
	            responseMap.put("dashboard", dashboard);

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

}
