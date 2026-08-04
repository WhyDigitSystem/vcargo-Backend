package com.efit.savaari.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.efit.savaari.common.CommonConstant;
import com.efit.savaari.common.UserConstants;
import com.efit.savaari.dto.RolesDTO;
import com.efit.savaari.dto.ScreenNamesDTO;
import com.efit.savaari.entity.RolesVO;
import com.efit.savaari.entity.ScreenNamesVO;
import com.efit.savaari.responseDTO.ResponseDTO;
import com.efit.savaari.service.CommonMasterService;

@CrossOrigin
@RestController
@RequestMapping("/api/commonmaster")
public class CommonMasterController extends BaseController {

	@Autowired
	CommonMasterService commonMasterService;

	public static final Logger LOGGER = LoggerFactory.getLogger(CommonMasterController.class);
	
	


	@GetMapping("/getAllScreenCode")
	public ResponseEntity<ResponseDTO> getAllScreenCode(@RequestParam Long orgId) {
		String methodName = "getAllScreenCode()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> finScreen = new ArrayList<>();
		try {
			finScreen = commonMasterService.getAllScreenCode(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Fin Screen information get successfully By userId");
			responseObjectsMap.put("finScreen", finScreen);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Fin Screen information receive failed By userId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// Screen Names
	@PutMapping("/createUpdateScreenNames")
	public ResponseEntity<ResponseDTO> createUpdateScreenNames(@RequestBody ScreenNamesDTO screenNamesDTO) {
		String methodName = "createUpdateScreenNames()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> screenNamesVO = commonMasterService.createUpdateScreenNames(screenNamesDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, screenNamesVO.get("message"));
			responseObjectsMap.put("screenNamesVO", screenNamesVO.get("screenNamesVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getAllScreenNames")
	public ResponseEntity<ResponseDTO> getAllScreenNames() {
		String methodName = "getAllScreenNames()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<ScreenNamesVO> screenNamesVO = new ArrayList<>();
		try {
			screenNamesVO = commonMasterService.getAllScreenNames();
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "screenNames information get successfully");
			responseObjectsMap.put("screenNamesVO", screenNamesVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "screenNames information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/screenNamesById")
	public ResponseEntity<ResponseDTO> getScreenNamesById(@RequestParam Long id) {
		String methodName = "getScreenNamesById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		ScreenNamesVO screenNamesVO = new ScreenNamesVO();
		try {
			screenNamesVO = commonMasterService.getScreenNamesById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "screenNames information get successfully");
			responseObjectsMap.put("screenNamesVO", screenNamesVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "screenNames information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
		
		//Roles
		
		@GetMapping("/getActiveRoles")
		public ResponseEntity<ResponseDTO> getAllActiveRolesByOrgId(@RequestParam Long orgId) {
			String methodName = "getAllActiveRolesByOrgId()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;
			List<Map<String, Object>> mapp = new ArrayList<>();

			try {
				mapp = commonMasterService.getAllActiveRolesByOrgId(orgId);
			} catch (Exception e) {
				errorMsg = e.getMessage();
				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			}

			if (StringUtils.isBlank(errorMsg)) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Active Roles Details retrieved successfully");
				responseObjectsMap.put("rolesVO", mapp);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve Active Roles Details", errorMsg);
			}

			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
			return ResponseEntity.ok().body(responseDTO);
		}

		@PutMapping("/createUpdateRoles")
		public ResponseEntity<ResponseDTO> createUpdateRoles(@Valid @RequestBody RolesDTO rolesDTO) {
			String methodName = "createUpdateRoles()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;
			try {
				Map<String, Object> rolesVO = commonMasterService.createUpdateRoles(rolesDTO);
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, rolesVO.get("message"));
				responseObjectsMap.put("rolesVO", rolesVO.get("rolesVO"));
				responseDTO = createServiceResponse(responseObjectsMap);
			} catch (Exception e) {
				errorMsg = e.getMessage();
				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
				responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
			}
			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
			return ResponseEntity.ok().body(responseDTO);
		}
		
		@GetMapping("getRolesById")
		public ResponseEntity<ResponseDTO> getRolesById(@RequestParam Long id) {
			String methodName = "getRolesById()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;
			RolesVO rolesVO = null;
			try {
				rolesVO = commonMasterService.getRolesById(id);
			} catch (Exception e) {
				errorMsg = e.getMessage();
				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			}
			if (StringUtils.isEmpty(errorMsg)) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Roles found by ID");
				responseObjectsMap.put("rolesVO", rolesVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = "Roles not found for ID: " + id;
				responseDTO = createServiceResponseError(responseObjectsMap, "Roles not found", errorMsg);
			}
			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
			return ResponseEntity.ok().body(responseDTO);
		}

		@GetMapping("getRolesByOrgId")
		public ResponseEntity<ResponseDTO> getRolesByOrgId(@RequestParam Long OrgId) {
			String methodName = "getRolesByOrgId()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;
			List<RolesVO>  rolesVO = null;
			try {
				rolesVO = commonMasterService.getRolesByOrgId(OrgId);
			} catch (Exception e) {
				errorMsg = e.getMessage();
				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			}
			if (StringUtils.isEmpty(errorMsg)) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Roles found by ORGID");
				responseObjectsMap.put("rolesVO", rolesVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = "Roles not found for ID: " + OrgId;
				responseDTO = createServiceResponseError(responseObjectsMap, "Roles not found", errorMsg);
			}
			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
			return ResponseEntity.ok().body(responseDTO);
		}
		
		
		
		
		
}