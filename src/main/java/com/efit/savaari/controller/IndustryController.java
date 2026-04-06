package com.efit.savaari.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.efit.savaari.common.CommonConstant;
import com.efit.savaari.common.UserConstants;
import com.efit.savaari.dto.IndustryDTO;
import com.efit.savaari.entity.IndustryVO;
import com.efit.savaari.entity.VendorVO;
import com.efit.savaari.responseDTO.ResponseDTO;
import com.efit.savaari.service.IndustryService;

@CrossOrigin
@RestController
@RequestMapping("/api/industry")
public class IndustryController extends BaseController {

	public static final Logger LOGGER = LoggerFactory.getLogger(IndustryController.class);

	@Autowired
	IndustryService industryService;

	@PutMapping(value = "/updateCreateIndustry")
	public ResponseEntity<ResponseDTO> updateCreateIndustry(@Valid @RequestBody IndustryDTO industryDTO) {

		String methodName = "updateCreateIndustry()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		try {
			Map<String, Object> Response = industryService.updateCreateIndustry(industryDTO);

			ResponseDTO responseDTO = createServiceResponse(Response);
			return ResponseEntity.ok(responseDTO);

		} catch (Exception e) {
			LOGGER.error("{} - Unexpected Error: {}", methodName, e.getMessage(), e);
			ResponseDTO responseDTO = createServiceResponseError(new HashMap<>(), "Unexpected Error", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
		}
	}

	@GetMapping("/getAllIndustryByOrgId")
	public ResponseEntity<ResponseDTO> getAllIndustryByOrgId(@RequestParam Long orgId,
			@RequestParam(defaultValue = "") String search, @RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int size) {
		String methodName = "getAllIndustryByOrgId()";
		LOGGER.debug("Starting {}", methodName);

		Map<String, Object> responseMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {
			Map<String, Object> documentVO = industryService.getAllIndustryByOrgId(orgId, search, page, size);
			responseMap.put("message", "Industry retrieved successfully");
			responseMap.put("documentVO", documentVO);
			responseDTO = createServiceResponse(responseMap);
		} catch (Exception e) {
			LOGGER.error("Error in {}: {}", methodName, e.getMessage());
			responseDTO = createServiceResponseError(responseMap, "Error fetching users", e.getMessage());
		}

		LOGGER.debug("Ending {}", methodName);
		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getIndustryById")
	public ResponseEntity<ResponseDTO> getIndustryById(@RequestParam Long id) {
		String methodName = "getIndustryById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		IndustryVO industryVO = new IndustryVO();
		try {
			industryVO = industryService.getIndustryById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Industry get successfully By id");
			responseObjectsMap.put("industryVO", industryVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Industry information receive failedByOrgId",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PostMapping("/uploadImageFileIndustry")
	public ResponseEntity<ResponseDTO> uploadImageFileIndustry(@RequestParam("file") MultipartFile file,
			@RequestParam Long id) {
		String methodName = "uploadImageFileIndustry()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		IndustryVO industryVO = null;
		try {
			industryVO = industryService.uploadImageFileIndustry(file, id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error("Unable To Upload PartImage", methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Image Successfully Upload");
			responseObjectsMap.put("industryVO", industryVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Image Upload Failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PostMapping("/uploadImageFileVendor")
	public ResponseEntity<ResponseDTO> uploadImageFileVendor(@RequestParam("file") MultipartFile file,
			@RequestParam Long id) {
		String methodName = "uploadImageFileVendor()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		VendorVO vendorVO = null;
		try {
			vendorVO = industryService.uploadImageFileVendor(file, id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error("Unable To Upload PartImage", methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Image Successfully Upload");
			responseObjectsMap.put("vendorVO", vendorVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Image Upload Failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

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
