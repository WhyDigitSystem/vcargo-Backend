package com.efit.savaari.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.efit.savaari.dto.FuelMasterDTO;
import com.efit.savaari.responseDTO.ResponseDTO;
import com.efit.savaari.service.FuelMasterService;

@RestController
@RequestMapping("/api/fuel")
public class FuelMasterController extends BaseController {


	public static final Logger LOGGER = LoggerFactory.getLogger(FuelMasterController.class);

	@Autowired
	FuelMasterService fuelMasterService;

	@PutMapping("/createUpdateFuel")
	public ResponseEntity<ResponseDTO> createUpdateFuel(@RequestBody FuelMasterDTO fuelMasterDTO) {

		String methodName = "createUpdateFuel()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		Map<String, Object> responseMap = new HashMap<>();

		try {
			Map<String, Object> fuel = fuelMasterService.createUpdateFuelMaster(fuelMasterDTO);
			responseMap.put("message", fuel.get("message"));
			responseMap.put("fuel", fuel.get("fuel"));

			ResponseDTO responseDTO = createServiceResponse(responseMap);
			return ResponseEntity.ok(responseDTO);
		} catch (Exception e) {

			LOGGER.error("{} - Unexpected Error: {}", methodName, e.getMessage(), e);

			ResponseDTO errorDTO = createServiceResponseError(responseMap, "Unexpected Error", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDTO);
		}
	}

	@GetMapping("/getFuelByVehicle")
	public ResponseEntity<ResponseDTO> getFuelByVehicle(
			@RequestParam Long vehicleId, @RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int count) {
		String methodName = "getFuelByVehicle()";
		LOGGER.debug("Starting {}", methodName);

		Map<String, Object> responseMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {
			Map<String, Object> fuel = fuelMasterService.getFuelByVehicle(vehicleId, page, count);
			responseMap.put("message", "Fuel Details retrieved successfully");
			responseMap.put("fuel", fuel);
			responseDTO = createServiceResponse(responseMap);
		} catch (Exception e) {
			LOGGER.error("Error in {}: {}", methodName, e.getMessage());
			responseDTO = createServiceResponseError(responseMap, "Error fetching users", e.getMessage());
		}

		LOGGER.debug("Ending {}", methodName);
		return ResponseEntity.ok(responseDTO);
	}
	
	@GetMapping("/getAllFuelByOrgId")
	public ResponseEntity<ResponseDTO> getAllFuelByOrgId(
			@RequestParam Long orgId, @RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int count) {
		String methodName = "getAllFuelByOrgId()";
		LOGGER.debug("Starting {}", methodName);

		Map<String, Object> responseMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {
			Map<String, Object> fuel = fuelMasterService.getAllFuelByOrgId(orgId, page, count);
			responseMap.put("message", "Fuel Details retrieved successfully");
			responseMap.put("fuel", fuel);
			responseDTO = createServiceResponse(responseMap);
		} catch (Exception e) {
			LOGGER.error("Error in {}: {}", methodName, e.getMessage());
			responseDTO = createServiceResponseError(responseMap, "Error fetching users", e.getMessage());
		}

		LOGGER.debug("Ending {}", methodName);
		return ResponseEntity.ok(responseDTO);
	}

	
}
