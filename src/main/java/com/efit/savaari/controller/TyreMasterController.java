package com.efit.savaari.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import com.efit.savaari.dto.TyreMasterDTO;
import com.efit.savaari.entity.TyreMasterVO;
import com.efit.savaari.responseDTO.ResponseDTO;
import com.efit.savaari.responseDTO.TyreMasterResponseDTO;
import com.efit.savaari.service.TyreMasterService;

@RestController
@RequestMapping("/api/tyre")
public class TyreMasterController extends BaseController {

	@Autowired
	TyreMasterService tyreMasterService;

	@PutMapping("/createUpdateTyre")
	public ResponseEntity<ResponseDTO> createUpdateTyre(@RequestBody TyreMasterDTO tyreMasterDTO) {

		String methodName = "createUpdateTyre()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		Map<String, Object> responseMap = new HashMap<>();

		try {
			Map<String, Object> tyre = tyreMasterService.createUpdateTyreMaster(tyreMasterDTO);
			responseMap.put("message", tyre.get("message"));
			responseMap.put("tyre", tyre.get("tyre"));

			ResponseDTO responseDTO = createServiceResponse(responseMap);
			return ResponseEntity.ok(responseDTO);
		} catch (Exception e) {

			LOGGER.error("{} - Unexpected Error: {}", methodName, e.getMessage(), e);

			ResponseDTO errorDTO = createServiceResponseError(responseMap, "Unexpected Error", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDTO);
		}
	}

	@GetMapping("/getAllTyreByOrgId")
	public ResponseEntity<ResponseDTO> getAllTyreByOrgId(@RequestParam Long orgId) {
		String methodName = "getAllTyreByOrgId()";
		LOGGER.debug("Starting {}", methodName);

		Map<String, Object> responseMap = new HashMap<>();
		ResponseDTO responseDTO;

		List<TyreMasterVO> tyre = new ArrayList<>();
		try {
			tyre = tyreMasterService.getAllTyreByOrgId(orgId);
			responseMap.put("message", "Tyre Details retrieved successfully");
			responseMap.put("tyre", tyre);
			responseDTO = createServiceResponse(responseMap);
		} catch (Exception e) {
			LOGGER.error("Error in {}: {}", methodName, e.getMessage());
			responseDTO = createServiceResponseError(responseMap, "Error fetching users", e.getMessage());
		}

		LOGGER.debug("Ending {}", methodName);
		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getTyreById")
	public ResponseEntity<ResponseDTO> getTyreById(@RequestParam Long id) {
		String methodName = "getTyreById()";
		LOGGER.debug("Starting {}", methodName);

		Map<String, Object> responseMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {
			TyreMasterResponseDTO tyre = tyreMasterService.getTyreById(id);
			responseMap.put("message", "Tyre Details retrieved successfully");
			responseMap.put("tyre", tyre);
			responseDTO = createServiceResponse(responseMap);
		} catch (Exception e) {
			LOGGER.error("Error in {}: {}", methodName, e.getMessage());
			responseDTO = createServiceResponseError(responseMap, "Error fetching users", e.getMessage());
		}

		LOGGER.debug("Ending {}", methodName);
		return ResponseEntity.ok(responseDTO);
	}
}
