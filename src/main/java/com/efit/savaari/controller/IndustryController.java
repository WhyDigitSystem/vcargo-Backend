package com.efit.savaari.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.efit.savaari.common.CommonConstant;
import com.efit.savaari.common.UserConstants;
import com.efit.savaari.responseDTO.ResponseDTO;
import com.efit.savaari.service.IndustryService;

@CrossOrigin
@RestController
@RequestMapping("/api/industry")
public class IndustryController extends BaseController {

	public static final Logger LOGGER = LoggerFactory.getLogger(IndustryController.class);

	@Autowired
	IndustryService industryService;

	

	
	@PutMapping("/approveUserAdmin")
	public ResponseEntity<ResponseDTO> approveUserAdmin(@RequestParam Long id, @RequestParam String action,
			@RequestParam String actionBy, @RequestParam String type) {

		String methodName = "approveUserAdmin()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {
			industryService.approveUserAdmin(id, action, actionBy, type);

			responseMap.put("message", "User approval completed successfully");
			responseDTO = createServiceResponse(responseMap);

		} catch (Exception e) {
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage());
			responseDTO = createServiceResponseError(responseMap, e.getMessage(), e.getMessage());
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok(responseDTO);
	}

}
