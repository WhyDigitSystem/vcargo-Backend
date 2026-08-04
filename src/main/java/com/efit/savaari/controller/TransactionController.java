package com.efit.savaari.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.efit.savaari.dto.CustomerBookingRequestDTO;
import com.efit.savaari.dto.TdriverDTO;
import com.efit.savaari.dto.TvehicleDTO;
import com.efit.savaari.dto.VehicleHireDTO;
import com.efit.savaari.entity.CustomerBookingRequestVO;
import com.efit.savaari.entity.TdriverVO;
import com.efit.savaari.entity.TvehicleVO;
import com.efit.savaari.responseDTO.ResponseDTO;
import com.efit.savaari.service.TransactionService;

@CrossOrigin
@RestController
@RequestMapping("/api/transaction")
public class TransactionController extends BaseController {

	public static final Logger LOGGER = LoggerFactory.getLogger(TransactionController.class);

	@Autowired
	TransactionService transactionService;

	

	@PutMapping("/createUpdateCustomerBookingRequest")
	public ResponseEntity<ResponseDTO> createUpdateCustomerBookingRequest(
			@RequestBody CustomerBookingRequestDTO customerBookingRequestDTO) {

		String methodName = "createUpdateCustomerBookingRequest()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		try {
			Map<String, Object> Response = transactionService
					.createUpdateCustomerBookingRequest(customerBookingRequestDTO);

			ResponseDTO responseDTO = createServiceResponse(Response);
			return ResponseEntity.ok(responseDTO);

		} catch (Exception e) {
			LOGGER.error("{} - Unexpected Error: {}", methodName, e.getMessage(), e);
			ResponseDTO responseDTO = createServiceResponseError(new HashMap<>(), "Unexpected Error", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
		}
	}

	@GetMapping("/getCustomerBookingRequestById")
	public ResponseEntity<ResponseDTO> getCustomerBookingRequestById(@RequestParam Long id) {

		String methodName = "getCustomerBookingRequestById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {
			CustomerBookingRequestVO customerBookingRequestVO = transactionService.getCustomerBookingRequestById(id);

			if (customerBookingRequestVO == null) {
				String errorMsg = "customerBookingRequest not found for ID: " + id;
				responseDTO = createServiceResponseError(responseObjectsMap, "Not Found", errorMsg);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
			}

			responseObjectsMap.put("message", "CustomerBookingRequest found successfully");
			responseObjectsMap.put("customerBookingRequestVO", customerBookingRequestVO);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {
			LOGGER.error("{} - Unexpected Error: {}", methodName, e.getMessage(), e);
			responseDTO = createServiceResponseError(responseObjectsMap, "Unexpected Error", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getCustomerBookingRequestByOrgId")
	public ResponseEntity<ResponseDTO> getCustomerBookingRequestByOrgId(
			@RequestParam(required = false) String branchCode, @RequestParam Long orgId,
			@RequestParam(defaultValue = "") String search, @RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int count) {
		String methodName = "getCustomerBookingRequestByOrgId()";
		LOGGER.debug("Starting {}", methodName);

		Map<String, Object> responseMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {
			Map<String, Object> customerBookingRequestVO = transactionService
					.getCustomerBookingRequestByOrgId(branchCode, orgId, search, page, count);
			responseMap.put("message", "CustomerBookingRequest retrieved successfully");
			responseMap.put("customerBookingRequestVO", customerBookingRequestVO);
			responseDTO = createServiceResponse(responseMap);
		} catch (Exception e) {
			LOGGER.error("Error in {}: {}", methodName, e.getMessage());
			responseDTO = createServiceResponseError(responseMap, "Error fetching users", e.getMessage());
		}

		LOGGER.debug("Ending {}", methodName);
		return ResponseEntity.ok(responseDTO);
	}

	
	

	@PutMapping(value = "/createUpdateTvehicle",consumes = "multipart/form-data")
	public ResponseEntity<ResponseDTO> createUpdateTvehicle(
			@RequestPart("tvehicleDTO") TvehicleDTO tvehicleDTO,
			@RequestPart(value = "RC", required = false) MultipartFile[] rcFiles,
			@RequestPart(value = "INSURANCE", required = false) MultipartFile[] insuranceFiles,
			@RequestPart(value = "FC", required = false) MultipartFile[] fcFiles,
			@RequestPart(value = "PERMIT", required = false) MultipartFile[] permitFiles,
			@RequestPart(value = "PUC", required = false) MultipartFile[] pucFiles,
			@RequestPart(value = "OTHER", required = false) MultipartFile[] otherFiles) {

		String methodName = "createUpdateTvehicle()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseMap = new HashMap<>();

		try {
			Map<String, Object> serviceResponse = transactionService.createUpdateTvehicle(tvehicleDTO, rcFiles,
					insuranceFiles, fcFiles, permitFiles, pucFiles, otherFiles);

			responseMap.put("message", serviceResponse.get("message"));
			responseMap.put("tvehicleVO", serviceResponse.get("tvehicleVO"));

			ResponseDTO responseDTO = createServiceResponse(responseMap);
			return ResponseEntity.ok(responseDTO);

		} catch (Exception e) {

			LOGGER.error("{} - Unexpected Error: {}", methodName, e.getMessage(), e);

			ResponseDTO errorDTO = createServiceResponseError(responseMap, "Unexpected Error", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDTO);
		}
	}
	
	@PutMapping(value = "/createUpdateHireTvehicle")
	public ResponseEntity<ResponseDTO> createUpdateHireTvehicle(
			@RequestBody VehicleHireDTO vehicleHireDTO) {

		String methodName = "createUpdateTvehicle()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseMap = new HashMap<>();

		try {
			Map<String, Object> serviceResponse = transactionService.createUpdateHireTvehicle(vehicleHireDTO);

			responseMap.put("message", serviceResponse.get("message"));
			responseMap.put("tvehicleVO", serviceResponse.get("vehicle"));

			ResponseDTO responseDTO = createServiceResponse(responseMap);
			return ResponseEntity.ok(responseDTO);

		} catch (Exception e) {

			LOGGER.error("{} - Unexpected Error: {}", methodName, e.getMessage(), e);

			ResponseDTO errorDTO = createServiceResponseError(responseMap, "Unexpected Error", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDTO);
		}
	}

	@GetMapping("/files/**")
	public ResponseEntity<byte[]> viewFile(HttpServletRequest request) throws IOException {
		return transactionService.viewFile(request);
	}

	@GetMapping("/getTvehiclesById")
	public ResponseEntity<ResponseDTO> getTvehiclesById(@RequestParam Long id) {

		String methodName = "getTvehiclesById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {

			TvehicleVO tvehicleVO = transactionService.getTvehiclesById(id);

			if (tvehicleVO == null) {
				String errorMsg = "Tvehicle not found for ID: " + id;
				responseDTO = createServiceResponseError(responseObjectsMap, "Not Found", errorMsg);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
			}

			responseObjectsMap.put("message", "Tvehicle found successfully");
			responseObjectsMap.put("tvehicleVO", tvehicleVO);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {
			LOGGER.error("{} - Unexpected Error: {}", methodName, e.getMessage(), e);
			responseDTO = createServiceResponseError(responseObjectsMap, "Unexpected Error", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getTvehiclesByOrgId")
	public ResponseEntity<ResponseDTO> getTvehiclesByOrgId(@RequestParam(required = false) String branchCode,
			@RequestParam Long orgId) {
		String methodName = "getTvehiclesByOrgId()";
		LOGGER.debug("Starting {}", methodName);

		Map<String, Object> responseMap = new HashMap<>();
		ResponseDTO responseDTO;

		List<TvehicleVO> tvehicleVO = new ArrayList<>();
		try {
			tvehicleVO = transactionService.getTvehiclesByOrgId(branchCode, orgId);
			responseMap.put("message", "Tvehicles retrieved successfully");
			responseMap.put("tvehicleVO", tvehicleVO);
			responseDTO = createServiceResponse(responseMap);
		} catch (Exception e) {
			LOGGER.error("Error in {}: {}", methodName, e.getMessage());
			responseDTO = createServiceResponseError(responseMap, "Error fetching users", e.getMessage());
		}

		LOGGER.debug("Ending {}", methodName);
		return ResponseEntity.ok(responseDTO);
	}

	// TDRIVER

//	@PutMapping(value = "/createUpdateTdriver", consumes = "multipart/form-data")
	@PutMapping(value = "/createUpdateTdriver")
	public ResponseEntity<ResponseDTO> createUpdateTdriver(@RequestPart("tdriverDTO") TdriverDTO tdriverDTO,
			@RequestPart(value = "DL", required = false) MultipartFile[] dlFiles,
			@RequestPart(value = "AADHAR", required = false) MultipartFile[] aadharFiles,
			@RequestPart(value = "PAN", required = false) MultipartFile[] panFiles,
			@RequestPart(value = "PHOTO", required = false) MultipartFile[] photoFiles,
			@RequestPart(value = "EXP", required = false) MultipartFile[] expFiles,
			@RequestPart(value = "MEDICAL", required = false) MultipartFile[] medicalFiles,
			@RequestPart(value = "OTHER", required = false) MultipartFile[] otherFiles) {

		String methodName = "createUpdateTdriver()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseMap = new HashMap<>();

		try {
			Map<String, Object> serviceResponse = transactionService.createUpdateTdriver(tdriverDTO,dlFiles,aadharFiles,panFiles,photoFiles,expFiles,medicalFiles,otherFiles);

			responseMap.put("message", serviceResponse.get("message"));
			responseMap.put("tdriverVO", serviceResponse.get("tdriverVO"));

			ResponseDTO responseDTO = createServiceResponse(responseMap);
			return ResponseEntity.ok(responseDTO);

		} catch (Exception e) {

			LOGGER.error("{} - Unexpected Error: {}", methodName, e.getMessage(), e);

			ResponseDTO errorDTO = createServiceResponseError(responseMap, "Unexpected Error", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDTO);
		}
	}

	@GetMapping("/driverFiles/**")
	public ResponseEntity<byte[]> viewDriverFile(HttpServletRequest request) throws IOException {
		return transactionService.viewDriverFile(request);
	}

	@GetMapping("/getTdriverById")
	public ResponseEntity<ResponseDTO> getTdriverById(@RequestParam Long id) {

		String methodName = "getTdriverById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {

			TdriverVO tdriverVO = transactionService.getTdriverById(id);

			if (tdriverVO == null) {
				String errorMsg = "Tdriver not found for ID: " + id;
				responseDTO = createServiceResponseError(responseObjectsMap, "Not Found", errorMsg);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
			}

			responseObjectsMap.put("message", "Tdriver found successfully");
			responseObjectsMap.put("tdriverVO", tdriverVO);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {
			LOGGER.error("{} - Unexpected Error: {}", methodName, e.getMessage(), e);
			responseDTO = createServiceResponseError(responseObjectsMap, "Unexpected Error", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getTdriverByOrgId")
	public ResponseEntity<ResponseDTO> getTdriverByOrgId(@RequestParam(required = false) String branchCode,
			@RequestParam Long orgId) {
		String methodName = "getTdriverByOrgId()";
		LOGGER.debug("Starting {}", methodName);

		Map<String, Object> responseMap = new HashMap<>();
		ResponseDTO responseDTO;
		
		List<TdriverVO> tdriverVO = new ArrayList<>();

		try {
			 tdriverVO = transactionService.getTdriverByOrgId(branchCode, orgId);
			responseMap.put("message", "Tdriver retrieved successfully");
			responseMap.put("tdriverVO", tdriverVO);
			responseDTO = createServiceResponse(responseMap);
		} catch (Exception e) {
			LOGGER.error("Error in {}: {}", methodName, e.getMessage());
			responseDTO = createServiceResponseError(responseMap, "Error fetching users", e.getMessage());
		}

		LOGGER.debug("Ending {}", methodName);
		return ResponseEntity.ok(responseDTO);
	}

	// Bulk upload TVehicles

	@PostMapping("/uploadTVehicleExcel")
	public ResponseEntity<ResponseDTO> uploadTVehicleExcel(@RequestParam("files") MultipartFile file,
			@RequestParam("createdBy") String createdBy, @RequestParam(value = "orgId", required = false) Long orgId) {

		String methodName = "uploadTVehicleExcel()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {

			List<TvehicleVO> tvehicleVO = transactionService.uploadTVehicleExcel(file, createdBy, orgId);

			responseMap.put("tvehicleVO", tvehicleVO);
			responseMap.put("message", "TVehicle Excel uploaded successfully");

			responseDTO = createServiceResponse(responseMap);

		} catch (Exception e) {

			LOGGER.error("{} - Unexpected Error: {}", methodName, e.getMessage(), e);

			responseDTO = createServiceResponseError(responseMap, "Unexpected Error", e.getMessage());
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok(responseDTO);
	}

	// TDriver Bulk Upload

	@PostMapping("/uploadTDriverExcel")
	public ResponseEntity<ResponseDTO> uploadTDriverExcel(@RequestParam("files") MultipartFile file,
			@RequestParam("createdBy") String createdBy, @RequestParam(value = "orgId", required = false) Long orgId) {

		String methodName = "uploadTDriverExcel()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {

			List<TdriverVO> tdriverVO = transactionService.uploadTDriverExcel(file, createdBy, orgId);

			responseMap.put("tdriveVO", tdriverVO);
			responseMap.put("message", "TDriver Excel uploaded successfully");

			responseDTO = createServiceResponse(responseMap);

		} catch (Exception e) {

			LOGGER.error("{} - Unexpected Error: {}", methodName, e.getMessage(), e);

			responseDTO = createServiceResponseError(responseMap, "Unexpected Error", e.getMessage());
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok(responseDTO);
	}

// Approval api quotes

	


	
	
	//excel upload Tdriver
	 
		@PostMapping("/tDriverExcelUpload")
		public ResponseEntity<ResponseDTO> tDriverExcelUpload(
	            @RequestParam("file") MultipartFile file, @RequestParam("createdBy") Long  createdBy,@RequestParam("orgId") Long orgId) throws Exception {

			String methodName = "uploadExcel()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

			try {
				Map<String, Object> Response = transactionService.tDriverExcelUpload(file,createdBy, orgId);

				ResponseDTO responseDTO = createServiceResponse(Response);
				return ResponseEntity.ok(responseDTO);

			} catch (Exception e) {
				LOGGER.error("{} - Unexpected Error: {}", methodName, e.getMessage(), e);
				ResponseDTO responseDTO = createServiceResponseError(new HashMap<>(), "Unexpected Error", e.getMessage());
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
			}
		}
		
		
		//TVehicle Excel upload
		
		@PostMapping("/tVehicleExcelUpload")
		public ResponseEntity<ResponseDTO> tVehicleExcelUpload(
	            @RequestParam("file") MultipartFile file, @RequestParam("createdBy") Long  createdBy,@RequestParam("orgId") Long orgId) throws Exception {

			String methodName = "tVehicleExcelUpload()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

			try {
				Map<String, Object> Response = transactionService.tVehicleExcelUpload(file,createdBy, orgId);

				ResponseDTO responseDTO = createServiceResponse(Response);
				return ResponseEntity.ok(responseDTO);

			} catch (Exception e) {
				LOGGER.error("{} - Unexpected Error: {}", methodName, e.getMessage(), e);
				ResponseDTO responseDTO = createServiceResponseError(new HashMap<>(), "Unexpected Error", e.getMessage());
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
			}
		}


}
