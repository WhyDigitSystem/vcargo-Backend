package com.efit.savaari.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.efit.savaari.dto.FastagRequestDTO;
import com.efit.savaari.responseDTO.ResponseDTO;
import com.efit.savaari.service.TrackingTokenService;
import com.fasterxml.jackson.databind.JsonNode;

@RestController
@RequestMapping("/api/fasttag")
public class TrackingTokenController extends BaseController{

	public static final Logger LOGGER = LoggerFactory.getLogger(TransactionController.class);

    @Autowired
    private TrackingTokenService trackingTokenService;
    
    
    @PostMapping(value = "/getToken", produces = "application/json")
    public ResponseEntity<ResponseDTO> getOrCreateToken() {

        String methodName = "getOrCreateToken()";
        LOGGER.debug("Starting {}", methodName);

        Map<String, Object> responseMap = new HashMap<>();
        ResponseDTO responseDTO;

        try {
            Map<String, Object> token = trackingTokenService.getToken();
            responseMap.put("message", "Token fetched successfully");
            responseMap.put("token", token);
            responseDTO = createServiceResponse(responseMap);
        } catch (Exception e) {
            LOGGER.error("Error in {}: {}", methodName, e.getMessage(), e);
            responseDTO = createServiceResponseError(responseMap, "Token fetch failed", e.getMessage());
        }

        LOGGER.debug("Ending {}", methodName);
        return ResponseEntity.ok(responseDTO);
    }
    
    
    @PostMapping(value = "/fastag",
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<ResponseDTO> getFastag(@RequestBody FastagRequestDTO dto) {

        String methodName = "getFastag()";
        LOGGER.debug("Starting {}", methodName);

        Map<String, Object> responseMap = new HashMap<>();
        ResponseDTO responseDTO;

        try {
            Map<String, Object> result =
                    trackingTokenService.callFastagApi(dto);

            responseMap.put("fastagResponse", result);
            responseDTO = createServiceResponse(responseMap);

        } catch (Exception e) {
            LOGGER.error("Error in {}: {}", methodName, e.getMessage(), e);
            responseDTO = createServiceResponseError(
                    responseMap, "FASTAG API failed", e.getMessage());
        }

        LOGGER.debug("Ending {}", methodName);
        return ResponseEntity.ok(responseDTO);
    }
}
