package com.efit.savaari.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.efit.savaari.common.CommonConstant;
import com.efit.savaari.common.UserConstants;
import com.efit.savaari.dto.DriverDTO;
import com.efit.savaari.dto.ResponseDTO;
import com.efit.savaari.dto.VehicleDTO;
import com.efit.savaari.dto.VehicleTypeDTO;
import com.efit.savaari.entity.DriverVO;
import com.efit.savaari.entity.VehicleTypeVO;
import com.efit.savaari.entity.VehicleVO;
import com.efit.savaari.repo.DriverRepo;
import com.efit.savaari.service.VehicleService;

@RestController
@RequestMapping("/api/vehicle")
public class VehicleController extends BaseController{
	
	Logger LOGGER = LoggerFactory.getLogger(VehicleController.class);

	@Autowired
    VehicleService vehicleService;
	
	@Autowired
	DriverRepo driverRepo;

    // Vehicle controller start here

    @PutMapping("/createUpdateVehicle")
    public ResponseEntity<ResponseDTO> createUpdateVehicle(@RequestBody VehicleDTO vehicleDTO) {

        String methodName = "createUpdateVehicle()";
        LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

        String errorMsg = null;
        Map<String, Object> responseObjectsMap = new HashMap<>();
        ResponseDTO responseDTO = null;

        try {
            Map<String, Object> createdVehicleVO = vehicleService.createUpdateVehicle(vehicleDTO);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					createdVehicleVO.get("Error Message") != null ? createdVehicleVO.get("Error Message")
							: "Vehicle created/updated successfully");
            responseObjectsMap.put("vehicleVO", createdVehicleVO.get("vehicleVO"));

            responseDTO = createServiceResponse(responseObjectsMap);

        } catch (Exception e) {
            errorMsg = e.getMessage();
            LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
            responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
        }

        LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
        return ResponseEntity.ok().body(responseDTO);
    }


    @GetMapping("/getVehicleById")
    public ResponseEntity<ResponseDTO> getVehicleById(@RequestParam Long id) {

        String methodName = "getVehicleById()";
        LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

        String errorMsg = null;
        Map<String, Object> responseObjectsMap = new HashMap<>();
        ResponseDTO responseDTO = null;
        VehicleVO vehicleVO = null;

        try {
            vehicleVO = vehicleService.getVehicleById(id);  // <-- DIRECT OBJECT RETURN

        } catch (Exception e) {
            errorMsg = e.getMessage();
            LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
        }

        if (StringUtils.isEmpty(errorMsg)) {
            responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Vehicle found by ID");
            responseObjectsMap.put("vehicle", vehicleVO);
            responseDTO = createServiceResponse(responseObjectsMap);
        } else {
            errorMsg = "Vehicle not found for ID: " + id;
            responseDTO = createServiceResponseError(responseObjectsMap, "Vehicle not found", errorMsg);
        }

        LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("/getAllVehicle")
	public ResponseEntity<ResponseDTO> getAllVehicle(
	        @RequestParam(required = false) String branchCode,
	        @RequestParam Long orgId,
	        @RequestParam(defaultValue = "") String search,
	        @RequestParam(defaultValue = "1") int page,
	        @RequestParam(defaultValue = "10") int count
	) {
	    String methodName = "getAllVehicle()";
	    LOGGER.debug("Starting {}", methodName);

	    Map<String, Object> responseMap = new HashMap<>();
	    ResponseDTO responseDTO;

	    try {
	        Map<String, Object> vehicleVO = vehicleService.getAllVehicle( branchCode,orgId, search, page, count);
	        responseMap.put("message", "Indents retrieved successfully");
	        responseMap.put("vehicleVO", vehicleVO);
	        responseDTO = createServiceResponse(responseMap);
	    } catch (Exception e) {
	        LOGGER.error("Error in {}: {}", methodName, e.getMessage());
	        responseDTO = createServiceResponseError(responseMap, "Error fetching users", e.getMessage());
	    }

	    LOGGER.debug("Ending {}", methodName);
	    return ResponseEntity.ok(responseDTO);
	}
    
    // Vehicle controller end here
    
    //Driver controller start here
    
    @PutMapping("/createUpdateDriver")
    public ResponseEntity<ResponseDTO> createUpdateDriver(@RequestBody DriverDTO driverDTO) {

        String methodName = "createUpdateDriver()";
        LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

        String errorMsg = null;
        Map<String, Object> responseObjectsMap = new HashMap<>();
        ResponseDTO responseDTO = null;

        try {
            Map<String, Object> createdDriverVO = vehicleService.createUpdateDriver(driverDTO);

            responseObjectsMap.put(
                    CommonConstant.STRING_MESSAGE,
                    createdDriverVO.get("message")
            );

            responseObjectsMap.put("driverVO", createdDriverVO.get("driverVO"));

            responseDTO = createServiceResponse(responseObjectsMap);

        } catch (Exception e) {
            errorMsg = e.getMessage();
            LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
            responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
        }

        LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
        return ResponseEntity.ok().body(responseDTO);
    }
    
    
    @GetMapping("/driverById")
    public ResponseEntity<ResponseDTO> getDriverById(@RequestParam Long id) {

        String methodName = "getDriverById()";
        LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

        String errorMsg = null;
        Map<String, Object> responseObjectsMap = new HashMap<>();
        ResponseDTO responseDTO = null;
        DriverVO driverVO = null;

        try {
            driverVO = vehicleService.getDriverById(id);  // <-- DIRECT OBJECT RETURN

        } catch (Exception e) {
            errorMsg = e.getMessage();
            LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
        }

        if (StringUtils.isEmpty(errorMsg)) {
            responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Vehicle found by ID");
            responseObjectsMap.put("driver", driverVO);
            responseDTO = createServiceResponse(responseObjectsMap);
        } else {
            errorMsg = "Driver not found for ID: " + id;
            responseDTO = createServiceResponseError(responseObjectsMap, "Driver not found", errorMsg);
        }

        LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
        return ResponseEntity.ok().body(responseDTO);
    }
    
    
    @GetMapping("/getAllDriver")
    public ResponseEntity<ResponseDTO> getAllDriver(
            @RequestParam(required = false) String branchCode,
            @RequestParam Long orgId,
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int count
    ) {
        String methodName = "getAllDriver()";
        LOGGER.debug("Starting {}", methodName);

        Map<String, Object> responseMap = new HashMap<>();
        ResponseDTO responseDTO;

        try {

            // 🔥 Correct service call
            Map<String, Object> driverData = vehicleService.getAllDriver(branchCode, orgId, search, page, count);

            responseMap.put("message", "Drivers retrieved successfully");
            responseMap.put("driverVO", driverData);

            responseDTO = createServiceResponse(responseMap);

        } catch (Exception e) {
            LOGGER.error("Error in {}: {}", methodName, e.getMessage());
            responseDTO = createServiceResponseError(responseMap, "Error fetching drivers", e.getMessage());
        }

        LOGGER.debug("Ending {}", methodName);
        return ResponseEntity.ok(responseDTO);
    }

    // Driver controller end here
    
 // Vehicle Type controller start here
    
    // Vehicle Type Create Update
    
    @PutMapping("/createUpdateVehicleType")
    public ResponseEntity<ResponseDTO> createUpdateVehicleType(@RequestBody VehicleTypeDTO vehicleTypeDTO) {

        String methodName = "createUpdateVehicleType()";
        LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

        String errorMsg = null;
        Map<String, Object> responseObjectsMap = new HashMap<>();
        ResponseDTO responseDTO = null;

        try {
            Map<String, Object> createdVehicleTypeVO = vehicleService.createUpdateVehicleType(vehicleTypeDTO);

            responseObjectsMap.put(
                    CommonConstant.STRING_MESSAGE,
                    createdVehicleTypeVO.get("message")
            );

            // Correct Key
            responseObjectsMap.put("vehicleTypeVO", createdVehicleTypeVO.get("vehicleTypeVO"));

            responseDTO = createServiceResponse(responseObjectsMap);

        } catch (Exception e) {
            errorMsg = e.getMessage();
            LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
            responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
        }

        LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
        return ResponseEntity.ok().body(responseDTO);
    }
    
    // end Vehicle Type Create Update
    
    
    
    // Vehicle Type Get by ID
    
    @GetMapping("/vehicleTypeById")
    public ResponseEntity<ResponseDTO> getVehicleTypeById(@RequestParam Long id) {

        String methodName = "getVehicleTypeById()";
        LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

        String errorMsg = null;
        Map<String, Object> responseObjectsMap = new HashMap<>();
        ResponseDTO responseDTO = null;
        VehicleTypeVO vehicleTypeVO = null;

        try {
        	vehicleTypeVO = vehicleService.getVehicleTypeById(id);  // <-- DIRECT OBJECT RETURN

        } catch (Exception e) {
            errorMsg = e.getMessage();
            LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
        }

        if (StringUtils.isEmpty(errorMsg)) {
            responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "VehicleType found by ID");
            responseObjectsMap.put("VehicleType", vehicleTypeVO);
            responseDTO = createServiceResponse(responseObjectsMap);
        } else {
            errorMsg = "VehicleType not found for ID: " + id;
            responseDTO = createServiceResponseError(responseObjectsMap, "VehicleType not found", errorMsg);
        }

        LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
        return ResponseEntity.ok().body(responseDTO);
    }
    
    // end Vehicle Type Get by ID
    
    // Vehicle Type Get All
    
    @GetMapping("/getAllVehicleType")
    public ResponseEntity<ResponseDTO> getAllVehicleType(
            @RequestParam(required = false) String branchCode,
            @RequestParam Long orgId,
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int count) {

        String methodName = "getAllVehicleType()";
        LOGGER.debug("Starting {}", methodName);

        Map<String, Object> responseMap = new HashMap<>();
        ResponseDTO responseDTO;

        try {

            // Correct service call
            Map<String, Object> vehicleTypeData = 
                    vehicleService.getAllVehicleType(branchCode,orgId, search, page, count);

            responseMap.put("message", "Vehicle Types retrieved successfully");
            responseMap.put("vehicleTypeData", vehicleTypeData);   // ✅ Correct key

            responseDTO = createServiceResponse(responseMap);

        } catch (Exception e) {
            LOGGER.error("Error in {}: {}", methodName, e.getMessage());
            responseDTO = createServiceResponseError(
                    responseMap,
                    "Error fetching vehicle types",
                    e.getMessage());
        }

        LOGGER.debug("Ending {}", methodName);
        return ResponseEntity.ok(responseDTO);
    }

}
