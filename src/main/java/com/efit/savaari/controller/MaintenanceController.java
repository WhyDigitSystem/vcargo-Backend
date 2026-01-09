package com.efit.savaari.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.efit.savaari.common.CommonConstant;
import com.efit.savaari.dto.MaintenanceDTO;
import com.efit.savaari.dto.MaintenanceResponseDTO;
import com.efit.savaari.dto.ResponseDTO;
import com.efit.savaari.service.MaintenanceService;



@RestController
@RequestMapping("/api/maintenance")
public class MaintenanceController extends BaseController {

    @Autowired
    private MaintenanceService maintenanceService;

    @PutMapping("/createUpdateMaintenance")
	public ResponseEntity<ResponseDTO> createUpdateMaintenance(@RequestBody MaintenanceDTO maintenanceDTO) {

		String methodName = "createUpdateMaintenance()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		Map<String, Object> responseMap = new HashMap<>();

		try {
			Map<String, Object> maintenance = maintenanceService.createUpdateMaintenance(maintenanceDTO);
			responseMap.put("message", maintenance.get("message"));
			responseMap.put("maintenance", maintenance.get("maintenance"));

			ResponseDTO responseDTO = createServiceResponse(responseMap);
			return ResponseEntity.ok(responseDTO);
		} catch (Exception e) {

			LOGGER.error("{} - Unexpected Error: {}", methodName, e.getMessage(), e);

			ResponseDTO errorDTO = createServiceResponseError(responseMap, "Unexpected Error", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDTO);
		}
	}

    @GetMapping("/getAllMaintenanceByOrgId")
	public ResponseEntity<ResponseDTO> getAllMaintenanceByOrgId(@RequestParam Long orgId,
			@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int count) {
		String methodName = "getAllMaintenanceByOrgId()";
		LOGGER.debug("Starting {}", methodName);

		Map<String, Object> responseMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {
			Map<String, Object> maintenance = maintenanceService.getAllMaintenanceByOrgId(orgId, page, count);
			responseMap.put("message", "Maintenance Details retrieved successfully");
			responseMap.put("maintenance", maintenance);
			responseDTO = createServiceResponse(responseMap);
		} catch (Exception e) {
			LOGGER.error("Error in {}: {}", methodName, e.getMessage());
			responseDTO = createServiceResponseError(responseMap, "Error fetching users", e.getMessage());
		}

		LOGGER.debug("Ending {}", methodName);
		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getMaintenanceById")
	public ResponseEntity<ResponseDTO> getMaintenanceById(@RequestParam Long id) {
		String methodName = "getMaintenanceById()";
		LOGGER.debug("Starting {}", methodName);

		Map<String, Object> responseMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {
			MaintenanceResponseDTO maintenance = maintenanceService.getMaintenanceById(id);
			responseMap.put("message", "Maintenance Details retrieved successfully");
			responseMap.put("maintenance", maintenance);
			responseDTO = createServiceResponse(responseMap);
		} catch (Exception e) {
			LOGGER.error("Error in {}: {}", methodName, e.getMessage());
			responseDTO = createServiceResponseError(responseMap, "Error fetching users", e.getMessage());
		}

		LOGGER.debug("Ending {}", methodName);
		return ResponseEntity.ok(responseDTO);
	}
}

