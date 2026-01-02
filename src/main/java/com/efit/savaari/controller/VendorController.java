package com.efit.savaari.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.efit.savaari.common.CommonConstant;
import com.efit.savaari.dto.ResponseDTO;
import com.efit.savaari.dto.VendorDTO;
import com.efit.savaari.entity.VendorVO;
import com.efit.savaari.service.VendorService;


@CrossOrigin
@RestController
@RequestMapping("/api/vendor")
public class VendorController extends BaseController {

	@Autowired
	VendorService vendorService;

	public static final Logger LOGGER = LoggerFactory.getLogger(VendorController.class);

	
//	@PutMapping("/createUpdateVendor")
//	public ResponseEntity<ResponseDTO> createUpdateVendor(@RequestBody VendorDTO vendorDTO)
//	        throws ApplicationException {
//
//	    String methodName = "createUpdateVendor()";
//	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
//
//	    ResponseDTO responseDTO;
//	    Map<String, Object> responseObjectsMap = new HashMap<>();
//
//	    try {
//	        // Call service
//	        Map<String, Object> vendorResponse = vendorService.createUpdateVendor(vendorDTO);
//
//	        // Extract actual vendorVO object & message
//	        Object vendorVO = vendorResponse.get("vendorVO");
//	        String message = (String) vendorResponse.get("message");
//
//	        // Prepare response
//	        responseObjectsMap.put("vendorVO", vendorVO);
//	        responseObjectsMap.put("message", message);
//
//	        // Final response wrapper
//	        responseDTO = createServiceResponse(responseObjectsMap);
//
//	    } catch (Exception e) {
//	        LOGGER.error("{} - Unexpected Error: {}", methodName, e.getMessage(), e);
//	        responseDTO = createServiceResponseError(responseObjectsMap, "Unexpected Error", "Something went wrong.");
//	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
//	    }
//
//	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
//	    return ResponseEntity.ok(responseDTO);
//	}
	
	
//	@PutMapping(
//	        value = "/createUpdateVendor",
//	        consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }
//	)
//	
	@PutMapping("/createUpdateVendor")
	public ResponseEntity<ResponseDTO> createUpdateVendor(
	        @RequestBody VendorDTO vendorDTO
//	        @RequestPart(value = "contractAttachment", required = false) MultipartFile contractAttachment,
//	        @RequestPart(value = "backgroundVerification", required = false) MultipartFile backgroundVerification,
//	        @RequestPart(value = "securityCheck", required = false) MultipartFile securityCheck
	) {

	    String methodName = "createUpdateVendor()";
	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	    try {

	        Map<String, Object> vendorResponse =
//	                vendorService.createUpdateVendor(vendorDTO, contractAttachment, backgroundVerification, securityCheck);
            vendorService.createUpdateVendor(vendorDTO);

	        ResponseDTO responseDTO = createServiceResponse(vendorResponse);
	        return ResponseEntity.ok(responseDTO);

	    } catch (Exception e) {
	        LOGGER.error("{} - Unexpected Error: {}", methodName, e.getMessage(), e);
	        ResponseDTO responseDTO = createServiceResponseError(
	                new HashMap<>(),
	                "Unexpected Error",
	                e.getMessage()
	        );
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
	    }
	}

	
	@PostMapping("/uploadVendorDocuments")
	public ResponseEntity<ResponseDTO> uploadVendorDocuments(
	        @RequestParam Long vendorId,
	        @RequestPart(value = "contractAttachment", required = false) MultipartFile contractAttachment,
	        @RequestPart(value = "backgroundVerification", required = false) MultipartFile backgroundVerification,
	        @RequestPart(value = "securityCheck", required = false) MultipartFile securityCheck,
	        @RequestParam String createdBy
	) {
	    try {

	        Map<String, Object> responseMap =
	                vendorService.uploadVendorDocuments(vendorId, contractAttachment, backgroundVerification, securityCheck,createdBy);

	        ResponseDTO responseDTO = createServiceResponse(responseMap);
	        return ResponseEntity.ok(responseDTO);

	    } catch (Exception e) {
	        ResponseDTO responseDTO = createServiceResponseError(
	                new HashMap<>(),
	                "Unexpected Error",
	                e.getMessage()
	        );
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
	    }
	}


	
	
	@GetMapping("/getVendorByOrgId")
	public ResponseEntity<ResponseDTO> getUsersByOrgId(
	        @RequestParam(required = false) String branchCode,
	        @RequestParam Long orgId,
	        @RequestParam(defaultValue = "") String search,
	        @RequestParam(defaultValue = "1") int page,
	        @RequestParam(defaultValue = "10") int count
	) {
	    String methodName = "getVendorByOrgId()";
	    LOGGER.debug("Starting {}", methodName);

	    Map<String, Object> responseMap = new HashMap<>();
	    ResponseDTO responseDTO;

	    try {
	        Map<String, Object> data = vendorService.getVendorByOrgId( branchCode,orgId, search, page, count);
	        responseMap.put("message", "Vendor retrieved successfully");
	        responseMap.put("data", data);
	        responseDTO = createServiceResponse(responseMap);
	    } catch (Exception e) {
	        LOGGER.error("Error in {}: {}", methodName, e.getMessage());
	        responseDTO = createServiceResponseError(responseMap, "Error fetching users", e.getMessage());
	    }

	    LOGGER.debug("Ending {}", methodName);
	    return ResponseEntity.ok(responseDTO);
	}
	
	@GetMapping("/getVendorById")
	public ResponseEntity<ResponseDTO> getVendorById(@RequestParam Long id) {

	    String methodName = "getVendorById()";
	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO;

	    try {
	        VendorVO vendorVO = vendorService.getVendorById(id);

	        if (vendorVO == null) {
	            String errorMsg = "Vendor not found for ID: " + id;
	            responseDTO = createServiceResponseError(responseObjectsMap, "Not Found", errorMsg);
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
	        }

	        responseObjectsMap.put("message", "Vendor found successfully");
	        responseObjectsMap.put("vendorVO", vendorVO);

	        responseDTO = createServiceResponse(responseObjectsMap);

	    } catch (Exception e) {
	        LOGGER.error("{} - Unexpected Error: {}", methodName, e.getMessage(), e);
	        responseDTO = createServiceResponseError(responseObjectsMap, "Unexpected Error", e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
	    }

	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
	    
	    return ResponseEntity.ok(responseDTO);
	}
	
	
	@GetMapping("/getOrganizationAndCodeList")
	public ResponseEntity<ResponseDTO> getOrganizationAndCodeList(
	        @RequestParam(required = false) String branchCode,
	        @RequestParam Long orgId

	) {
	    String methodName = "getOrganizationAndCodeList()";
	    LOGGER.debug("Starting {}", methodName);

	    Map<String, Object> responseMap = new HashMap<>();
	    ResponseDTO responseDTO;

	    try {
	    	List<Map<String, Object>> vendorVO = vendorService.getOrganizationAndCodeList( branchCode,orgId);
	        responseMap.put("message", "Vendor retrieved successfully");
	        responseMap.put("vendorVO", vendorVO);
	        responseDTO = createServiceResponse(responseMap);
	    } catch (Exception e) {
	        LOGGER.error("Error in {}: {}", methodName, e.getMessage());
	        responseDTO = createServiceResponseError(responseMap, "Error fetching users", e.getMessage());
	    }
	    
	    LOGGER.debug("Ending {}", methodName);
	    return ResponseEntity.ok(responseDTO);
	}
	

}
