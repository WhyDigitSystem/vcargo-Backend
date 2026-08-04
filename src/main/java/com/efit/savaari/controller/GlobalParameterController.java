package com.efit.savaari.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.efit.savaari.common.CommonConstant;
import com.efit.savaari.common.UserConstants;
import com.efit.savaari.responseDTO.ResponseDTO;
import com.efit.savaari.service.GlobalParameterService;

@RestController
@RequestMapping("/api/GlobalParam")
public class GlobalParameterController extends BaseController {

	public static final Logger LOGGER = LoggerFactory.getLogger(GlobalParameterController.class);

	@Autowired
	GlobalParameterService globalParameterService;

	@GetMapping("/globalparamBranchByUserName")
	public ResponseEntity<ResponseDTO> getGlobalParameterBranchByUserName(@RequestParam Long orgid,
			@RequestParam String userName) {
		String methodName = "getAllGlobalParameterByUserName()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		Set<Object[]> globalParameters = new HashSet<>();
		try {
			globalParameters = globalParameterService.getGlobalParametersBranchAndBranchCodeByOrgIdAndUserName(orgid,
					userName);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			List<Map<String, String>> formattedParameters = formattParameter(globalParameters);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Global Parameter Branch information get successfully");
			responseObjectsMap.put("GlopalParameters", formattedParameters);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Global Parameter Branch information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	private List<Map<String, String>> formattParameter(Set<Object[]> globalParameters) {
		List<Map<String, String>> formattedParameters = new ArrayList<>();
		for (Object[] parameters : globalParameters) {
			Map<String, String> param = new HashMap<>();
			param.put("branch", parameters[0].toString());
			param.put("branchcode", parameters[1].toString());
			formattedParameters.add(param);
		}
		return formattedParameters;
	}

}