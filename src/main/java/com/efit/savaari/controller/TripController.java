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
import com.efit.savaari.dto.TripDTO;
import com.efit.savaari.responseDTO.ResponseDTO;
import com.efit.savaari.responseDTO.TripResponseDTO;
import com.efit.savaari.service.TripService;



@RestController
@RequestMapping("/api/trip")
public class TripController extends BaseController {

    @Autowired
    private TripService tripService;

    @PutMapping("/createUpdateTrip")
	public ResponseEntity<ResponseDTO> createUpdateTrip(@RequestBody TripDTO tripDTO) {

		String methodName = "createUpdateTrip()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		Map<String, Object> responseMap = new HashMap<>();

		try {
			Map<String, Object> trip = tripService.createUpdateTrip(tripDTO);
			responseMap.put("message", trip.get("message"));
			responseMap.put("trip", trip.get("trip"));

			ResponseDTO responseDTO = createServiceResponse(responseMap);
			return ResponseEntity.ok(responseDTO);
		} catch (Exception e) {

			LOGGER.error("{} - Unexpected Error: {}", methodName, e.getMessage(), e);

			ResponseDTO errorDTO = createServiceResponseError(responseMap, "Unexpected Error", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDTO);
		}
	}

    @GetMapping("/getAllTripByOrgId")
	public ResponseEntity<ResponseDTO> getAllTripByOrgId(@RequestParam Long orgId,
			@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int count) {
		String methodName = "getAllTripByOrgId()";
		LOGGER.debug("Starting {}", methodName);

		Map<String, Object> responseMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {
			Map<String, Object> trip = tripService.getAllTripByOrgId(orgId, page, count);
			responseMap.put("message", "Trip Details retrieved successfully");
			responseMap.put("trip", trip);
			responseDTO = createServiceResponse(responseMap);
		} catch (Exception e) {
			LOGGER.error("Error in {}: {}", methodName, e.getMessage());
			responseDTO = createServiceResponseError(responseMap, "Error fetching users", e.getMessage());
		}

		LOGGER.debug("Ending {}", methodName);
		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getTripById")
	public ResponseEntity<ResponseDTO> getTripById(@RequestParam Long id) {
		String methodName = "getTripById()";
		LOGGER.debug("Starting {}", methodName);

		Map<String, Object> responseMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {
			TripResponseDTO trip = tripService.getTripById(id);
			responseMap.put("message", "Trip Details retrieved successfully");
			responseMap.put("trip", trip);
			responseDTO = createServiceResponse(responseMap);
		} catch (Exception e) {
			LOGGER.error("Error in {}: {}", methodName, e.getMessage());
			responseDTO = createServiceResponseError(responseMap, "Error fetching users", e.getMessage());
		}

		LOGGER.debug("Ending {}", methodName);
		return ResponseEntity.ok(responseDTO);
	}
}

