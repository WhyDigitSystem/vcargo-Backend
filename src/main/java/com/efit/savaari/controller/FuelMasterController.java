package com.efit.savaari.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.efit.savaari.common.CommonConstant;
import com.efit.savaari.common.UserConstants;
import com.efit.savaari.dto.FuelMasterDTO;
import com.efit.savaari.dto.ResponseDTO;
import com.efit.savaari.entity.FuelMasterVO;
import com.efit.savaari.service.FuelMasterService;

@RestController
@RequestMapping("/api/fuel")
public class FuelMasterController extends BaseController {

    @Autowired
    FuelMasterService fuelMasterService;

    /**
     * Create Fuel Entry
     */
    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createFuel(@RequestBody FuelMasterDTO fuelMasterDTO) {

        String methodName = "createFuel()";
        LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

        String errorMsg = null;
        Map<String, Object> responseObjectsMap = new HashMap<>();
        ResponseDTO responseDTO = null;

        try {
            FuelMasterVO fuel = fuelMasterService.createFuelEntry(fuelMasterDTO);
            responseObjectsMap.put("fuel", fuel);
            responseDTO = createServiceResponse(responseObjectsMap);
        } catch (Exception e) {
            errorMsg = e.getMessage();
            LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
            responseDTO = createServiceResponseError(
                responseObjectsMap,
                "Fuel entry creation failed",
                errorMsg
            );
        }

        LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
        return ResponseEntity.ok().body(responseDTO);
    }

    /**
     * Get Fuel By Vehicle
     */
    @GetMapping("/byVehicle")
    public ResponseEntity<ResponseDTO> getFuelByVehicle(@RequestParam Long vehicleId) {

        String methodName = "getFuelByVehicle()";
        LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

        String errorMsg = null;
        Map<String, Object> responseObjectsMap = new HashMap<>();
        ResponseDTO responseDTO = null;
        List<FuelMasterVO> fuelList = new ArrayList<>();

        try {
            fuelList = fuelMasterService.getFuelByVehicle(vehicleId);
        } catch (Exception e) {
            errorMsg = e.getMessage();
            LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
        }

        if (StringUtils.isBlank(errorMsg)) {
            responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Fuel details fetched successfully");
            responseObjectsMap.put("fuelList", fuelList);
            responseDTO = createServiceResponse(responseObjectsMap);
        } else {
            responseDTO = createServiceResponseError(
                responseObjectsMap,
                "Fuel details fetch failed",
                errorMsg
            );
        }

        LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
        return ResponseEntity.ok().body(responseDTO);
    }

    
    @PostMapping("/save")
    public ResponseEntity<ResponseDTO> saveFuel(@RequestBody FuelMasterDTO fuelMasterDTO) {

        String methodName = "saveFuel()";
        LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

        String errorMsg = null;
        Map<String, Object> responseObjectsMap = new HashMap<>();
        ResponseDTO responseDTO = null;

        try {
            FuelMasterVO fuel = fuelMasterService.saveOrUpdateFuel(fuelMasterDTO);

            String msg = fuelMasterDTO.getFuelId() == null
                    ? "Fuel entry created successfully"
                    : "Fuel entry updated successfully";

            responseObjectsMap.put(CommonConstant.STRING_MESSAGE, msg);
            responseObjectsMap.put("fuel", fuel);

            responseDTO = createServiceResponse(responseObjectsMap);

        } catch (Exception e) {
            errorMsg = e.getMessage();
            LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
            responseDTO = createServiceResponseError(
                responseObjectsMap,
                "Fuel save failed",
                errorMsg
            );
        }

        LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
        return ResponseEntity.ok().body(responseDTO);
    }

    
    /**
     * Get Fuel By Driver
     */
    @GetMapping("/byDriver")
    public ResponseEntity<ResponseDTO> getFuelByDriver(@RequestParam Long driverId) {

        String methodName = "getFuelByDriver()";
        LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

        String errorMsg = null;
        Map<String, Object> responseObjectsMap = new HashMap<>();
        ResponseDTO responseDTO = null;
        List<FuelMasterVO> fuelList = new ArrayList<>();

        try {
            fuelList = fuelMasterService.getFuelByDriver(driverId);
        } catch (Exception e) {
            errorMsg = e.getMessage();
            LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
        }

        if (StringUtils.isBlank(errorMsg)) {
            responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Fuel details fetched successfully");
            responseObjectsMap.put("fuelList", fuelList);
            responseDTO = createServiceResponse(responseObjectsMap);
        } else {
            responseDTO = createServiceResponseError(
                responseObjectsMap,
                "Fuel details fetch failed",
                errorMsg
            );
        }

        LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
        return ResponseEntity.ok().body(responseDTO);
    }

    /**
     * Delete Fuel Entry
     */
    @PutMapping("/delete")
    public ResponseEntity<ResponseDTO> deleteFuel(@RequestParam Long fuelId) {

        String methodName = "deleteFuel()";
        LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

        String errorMsg = null;
        Map<String, Object> responseObjectsMap = new HashMap<>();
        ResponseDTO responseDTO = null;

        try {
            fuelMasterService.deleteFuelEntry(fuelId);
            responseDTO = createServiceResponse(responseObjectsMap);
        } catch (Exception e) {
            errorMsg = e.getMessage();
            LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
            responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
        }

        LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
        return ResponseEntity.ok().body(responseDTO);
    }
}
