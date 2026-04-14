package com.efit.savaari.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.efit.savaari.common.CommonConstant;
import com.efit.savaari.common.UserConstants;
import com.efit.savaari.dto.EwayBillNonIRNDTO;
import com.efit.savaari.responseDTO.EInvoiceGetToketDTO;
import com.efit.savaari.responseDTO.ResponseDTO;
import com.efit.savaari.service.EInvoiceService;

@RestController
@RequestMapping("/api/auth")
public class EinvoiceAuthController extends BaseController {

	@Autowired
	EInvoiceService eInvoiceService;

	@PostMapping("/createEWayBillNonIRN")
	public ResponseEntity<ResponseDTO> createEWayBillNonIRN(@RequestParam List<String> docId) {
		String methodName = "createEWayBillNonIRN()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> ewayResponseDTO = eInvoiceService.createEWayBillNonIRN(docId);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "EwayBill Generated Successfully");
			responseObjectsMap.put("ewayResponseDTO", ewayResponseDTO);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PostMapping("/getToken")
	public ResponseEntity<ResponseDTO> generateToken(@RequestBody List<EInvoiceGetToketDTO> eInvoiceGetToketDTO1) {
		String methodName = "generateToken()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		Map<String, Object> ewayBillDTO = new HashMap<>();
		try {
			ewayBillDTO = eInvoiceService.generateToken(eInvoiceGetToketDTO1);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Token Information Get Successfully");
			responseObjectsMap.put("ewayBillDTO", ewayBillDTO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Token Information Get Filed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getEwayBillNonIRNByDocId")
	public ResponseEntity<EwayBillNonIRNDTO> getEwayBillNonIRNByDocId(@RequestParam String docid) {
		String methodName = "getEwayBillNonIRNByDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		EwayBillNonIRNDTO ewayBillDTO = new EwayBillNonIRNDTO();
		try {
			ewayBillDTO = eInvoiceService.generateEwayBillByNonIRN(docid);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "EWayBill Information Get Successfully");
			responseObjectsMap.put("ewayBillDTO", ewayBillDTO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "EWayBill Information Get Filed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(ewayBillDTO);
	}
}
