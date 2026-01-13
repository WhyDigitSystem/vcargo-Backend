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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.efit.savaari.common.CommonConstant;
import com.efit.savaari.common.UserConstants;
import com.efit.savaari.dto.BranchDTO;
import com.efit.savaari.dto.ChargeTypeDTO;
import com.efit.savaari.dto.CustomerDTO;
import com.efit.savaari.dto.CustomerRateDTO;
import com.efit.savaari.dto.IndentsDTO;
import com.efit.savaari.dto.ListOfValuesDTO;
import com.efit.savaari.dto.PlaceDetailsDTO;
import com.efit.savaari.dto.RoutesDTO;
import com.efit.savaari.dto.VendorRateDTO;
import com.efit.savaari.entity.BranchVO;
import com.efit.savaari.entity.ChargeTypeVO;
import com.efit.savaari.entity.CustomerRateVO;
import com.efit.savaari.entity.CustomerVO;
import com.efit.savaari.entity.IndentsVO;
import com.efit.savaari.entity.ListOfValuesVO;
import com.efit.savaari.entity.PlaceDetailsVO;
import com.efit.savaari.entity.RoutesVO;
import com.efit.savaari.entity.VendorRateVO;
import com.efit.savaari.exception.ApplicationException;
import com.efit.savaari.responseDTO.ResponseDTO;
import com.efit.savaari.service.MasterService;

@CrossOrigin
@RestController
@RequestMapping("/api/master")
public class MasterController extends BaseController {

	@Autowired
	MasterService masterService;
	
	

	public static final Logger LOGGER = LoggerFactory.getLogger(MasterController.class);

	// Branch
		@GetMapping("/branch")
		public ResponseEntity<ResponseDTO> getAllBranch(@RequestParam Long orgid) {
			String methodName = "getAllBranch()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;
			List<BranchVO> branchVO = new ArrayList<>();
			try {
				branchVO = masterService.getAllBranch(orgid);
			} catch (Exception e) {
				errorMsg = e.getMessage();
				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			}
			if (StringUtils.isBlank(errorMsg)) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Branch information get successfully");
				responseObjectsMap.put("branchVO", branchVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				responseDTO = createServiceResponseError(responseObjectsMap, "Branch information receive failed", errorMsg);
			}
			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
			return ResponseEntity.ok().body(responseDTO);
		}

		@GetMapping("/branch/{branchid}")
		public ResponseEntity<ResponseDTO> getBranchById(@PathVariable Long branchid) {
			String methodName = "getBranchById()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;
			BranchVO branchVO = null;
			try {
				branchVO = masterService.getBranchById(branchid).orElse(null);
			} catch (Exception e) {
				errorMsg = e.getMessage();
				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			}
			if (StringUtils.isEmpty(errorMsg)) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Branch found by ID");
				responseObjectsMap.put("Branch", branchVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = "Branch not found for ID: " + branchid;
				responseDTO = createServiceResponseError(responseObjectsMap, "Branch not found", errorMsg);
			}
			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
			return ResponseEntity.ok().body(responseDTO);
		}
	 
		@PutMapping("/createUpdateBranch")
		public ResponseEntity<ResponseDTO> createUpdateBranch(@RequestBody BranchDTO branchDTO) {
			String methodName = "createBranch()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;
			try {
				Map<String, Object> createdBranchVO = masterService.createUpdateBranch(branchDTO);
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE,createdBranchVO.get("message"));
				responseObjectsMap.put("branchVO", createdBranchVO.get("branchVO"));
				responseDTO = createServiceResponse(responseObjectsMap);
			} catch (Exception e) {
		        errorMsg = e.getMessage();
		        LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		        responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		    }
		    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		    return ResponseEntity.ok().body(responseDTO);
		}
		

		
		
		@PutMapping("/createUpdateCustomer")
		public ResponseEntity<ResponseDTO> createUpdateCustomer(@RequestBody CustomerDTO dto) {
		    Map<String, Object> response = new HashMap<>();
		    ResponseDTO responseDTO;

		    try {
		        Map<String, Object> result = masterService.createUpdateCustomer(dto);

		        response.put("customerVO", result.get("customerVO"));
		        response.put("message", result.get("message"));

		        responseDTO = createServiceResponse(response);

		    } catch (Exception e) {
		        responseDTO = createServiceResponseError(response,"Unexpected Error",e.getMessage());
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
		    }
		    return ResponseEntity.ok(responseDTO);
		}

		
		
		@GetMapping("/getCustomerById")
		public ResponseEntity<ResponseDTO> getCustomerById(@RequestParam Long id) {

		    String methodName = "getCustomerById()";
		    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		    Map<String, Object> responseObjectsMap = new HashMap<>();
		    ResponseDTO responseDTO;

		    try {
		        CustomerVO customerVO = masterService.getCustomerById(id);

		        if (customerVO == null) {
		            String errorMsg = "customer not found for ID: " + id;
		            responseDTO = createServiceResponseError(responseObjectsMap, "Not Found", errorMsg);
		            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
		        }

		        responseObjectsMap.put("message", "customer found successfully");
		        responseObjectsMap.put("customerVO", customerVO);

		        responseDTO = createServiceResponse(responseObjectsMap);

		    } catch (Exception e) {
		        LOGGER.error("{} - Unexpected Error: {}", methodName, e.getMessage(), e);
		        responseDTO = createServiceResponseError(responseObjectsMap, "Unexpected Error", e.getMessage());
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
		    }

		    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		    
		    return ResponseEntity.ok(responseDTO);
		}
		
		@GetMapping("/getCustomerByOrgId")
		public ResponseEntity<ResponseDTO> getUsersByOrgId(
		        @RequestParam(required = false) String branchCode,
		        @RequestParam Long orgId,
		        @RequestParam(defaultValue = "") String search,
		        @RequestParam(defaultValue = "1") int page,
		        @RequestParam(defaultValue = "10") int count
		) {
		    String methodName = "getCustomerByOrgId()";
		    LOGGER.debug("Starting {}", methodName);

		    Map<String, Object> responseMap = new HashMap<>();
		    ResponseDTO responseDTO;

		    try {
		        Map<String, Object> customerVO = masterService.getCustomerByOrgId( branchCode,orgId, search, page, count);
		        responseMap.put("message", "Customer retrieved successfully");
		        responseMap.put("customerVO", customerVO);
		        responseDTO = createServiceResponse(responseMap);
		    } catch (Exception e) {
		        LOGGER.error("Error in {}: {}", methodName, e.getMessage());
		        responseDTO = createServiceResponseError(responseMap, "Error fetching users", e.getMessage());
		    }

		    LOGGER.debug("Ending {}", methodName);
		    return ResponseEntity.ok(responseDTO);
		}


		@PutMapping(value = "/createUpdateIndents", consumes = "multipart/form-data")
		public ResponseEntity<ResponseDTO> createUpdateIndents(
		        @RequestPart("indentsDTO") IndentsDTO indentsDTO,
		        @RequestPart(value = "tripLinkedAttachment", required = false) List<MultipartFile> tripLinkedAttachment
		) {

		    String methodName = "createUpdateIndents()";
		    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		    try {

		        Map<String, Object> indentsResponse =
		        		masterService.createUpdateIndents(indentsDTO, tripLinkedAttachment);

		        ResponseDTO responseDTO = createServiceResponse(indentsResponse);
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

		
		@GetMapping("/getIndentsById")
		public ResponseEntity<ResponseDTO> getIndentsById(@RequestParam Long id) {

		    String methodName = "getIndentsById()";
		    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		    Map<String, Object> responseObjectsMap = new HashMap<>();
		    ResponseDTO responseDTO;

		    try {
		        IndentsVO indentsVO = masterService.getIndentsById(id);

		        if (indentsVO == null) {
		            String errorMsg = "Indents not found for ID: " + id;
		            responseDTO = createServiceResponseError(responseObjectsMap, "Not Found", errorMsg);
		            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
		        }

		        responseObjectsMap.put("message", "Indents found successfully");
		        responseObjectsMap.put("indentsVO", indentsVO);

		        responseDTO = createServiceResponse(responseObjectsMap);

		    } catch (Exception e) {
		        LOGGER.error("{} - Unexpected Error: {}", methodName, e.getMessage(), e);
		        responseDTO = createServiceResponseError(responseObjectsMap, "Unexpected Error", e.getMessage());
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
		    }

		    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		    
		    return ResponseEntity.ok(responseDTO);
		}
		 
		
		@GetMapping("/getIndentsByOrgId")
		public ResponseEntity<ResponseDTO> getIndentsByOrgId(
		        @RequestParam(required = false) String branchCode,
		        @RequestParam Long orgId,
		        @RequestParam(defaultValue = "") String search,
		        @RequestParam(defaultValue = "1") int page,
		        @RequestParam(defaultValue = "10") int count
		) {
		    String methodName = "getIndentsByOrgId()";
		    LOGGER.debug("Starting {}", methodName);

		    Map<String, Object> responseMap = new HashMap<>();
		    ResponseDTO responseDTO;

		    try {
		        Map<String, Object> indentsVO = masterService.getIndentsByOrgId( branchCode, orgId,search, page, count);
		        responseMap.put("message", "Indents retrieved successfully");
		        responseMap.put("indentsVO", indentsVO);
		        responseDTO = createServiceResponse(responseMap);
		    } catch (Exception e) {
		        LOGGER.error("Error in {}: {}", methodName, e.getMessage());
		        responseDTO = createServiceResponseError(responseMap, "Error fetching users", e.getMessage());
		    }
		    
		    

		    LOGGER.debug("Ending {}", methodName);
		    return ResponseEntity.ok(responseDTO);
		}

		
		@PutMapping("/createUpdatePlaceDetails")
		public ResponseEntity<ResponseDTO> createUpdatePlaceDetails(
		        @RequestBody PlaceDetailsDTO placeDetailsDTO	) {

		    String methodName = "createUpdatePlaceDetails()";
		    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		    try {
		        Map<String, Object> Response = masterService.createUpdatePlaceDetails(placeDetailsDTO);

		        ResponseDTO responseDTO = createServiceResponse(Response);
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
		
		
		@GetMapping("/getPlaceDetailsById")
		public ResponseEntity<ResponseDTO> getPlaceDetailsById(@RequestParam Long id) {

		    String methodName = "getPlaceDetailsById()";
		    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		    Map<String, Object> responseObjectsMap = new HashMap<>();
		    ResponseDTO responseDTO;

		    try {
		    	PlaceDetailsVO placeDetailsVO = masterService.getPlaceDetailsById(id);

		        if (placeDetailsVO == null) {
		            String errorMsg = "PlaceDetails not found for ID: " + id;
		            responseDTO = createServiceResponseError(responseObjectsMap, "Not Found", errorMsg);
		            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
		        }

		        responseObjectsMap.put("message", "PlaceDetails found successfully");
		        responseObjectsMap.put("placeDetailsVO", placeDetailsVO);

		        responseDTO = createServiceResponse(responseObjectsMap);

		    } catch (Exception e) {
		        LOGGER.error("{} - Unexpected Error: {}", methodName, e.getMessage(), e);
		        responseDTO = createServiceResponseError(responseObjectsMap, "Unexpected Error", e.getMessage());
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
		    }

		    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		    
		    return ResponseEntity.ok(responseDTO);
		}

		
		@GetMapping("/getPlaceDetailsByOrgId")
		public ResponseEntity<ResponseDTO> getPlaceDetailsByOrgId(
		        @RequestParam(required = false) String branchCode,
		        @RequestParam(defaultValue = "") String search,
		        @RequestParam(defaultValue = "1") int page,
		        @RequestParam(defaultValue = "10") int count
		) {
		    String methodName = "getPlaceDetailsByOrgId()";
		    LOGGER.debug("Starting {}", methodName);

		    Map<String, Object> responseMap = new HashMap<>();
		    ResponseDTO responseDTO;

		    try {
		        Map<String, Object> placeDetailsVO = masterService.getPlaceDetailsByOrgId( branchCode, search, page, count);
		        responseMap.put("message", "PlaceDetails retrieved successfully");
		        responseMap.put("placeDetailsVO", placeDetailsVO);
		        responseDTO = createServiceResponse(responseMap);
		    } catch (Exception e) {
		        LOGGER.error("Error in {}: {}", methodName, e.getMessage());
		        responseDTO = createServiceResponseError(responseMap, "Error fetching users", e.getMessage());
		    }

		    LOGGER.debug("Ending {}", methodName);
		    return ResponseEntity.ok(responseDTO);
		}

		
		@PutMapping("/createUpdateVendorRate")
		public ResponseEntity<ResponseDTO> createUpdateVendorRate(
		        @RequestBody VendorRateDTO vendorRateDTO	) {

		    String methodName = "createUpdateVendorRate()";
		    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		    try {
		        Map<String, Object> Response = masterService.createUpdateVendorRate(vendorRateDTO);

		        ResponseDTO responseDTO = createServiceResponse(Response);
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
		
		
		@GetMapping("/getVendorRateById")
		public ResponseEntity<ResponseDTO> getVendorRateById(@RequestParam Long id) {

		    String methodName = "getVendorRateById()";
		    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		    Map<String, Object> responseObjectsMap = new HashMap<>();
		    ResponseDTO responseDTO;

		    try {
		        VendorRateVO vendorRateVO = masterService.getVendorRateById(id);

		        if (vendorRateVO == null) {
		            String errorMsg = "VendorRate not found for ID: " + id;
		            responseDTO = createServiceResponseError(responseObjectsMap, "Not Found", errorMsg);
		            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
		        }

		        responseObjectsMap.put("message", "VendorRate found successfully");
		        responseObjectsMap.put("vendorRateVO", vendorRateVO);

		        responseDTO = createServiceResponse(responseObjectsMap);

		    } catch (Exception e) {
		        LOGGER.error("{} - Unexpected Error: {}", methodName, e.getMessage(), e);
		        responseDTO = createServiceResponseError(responseObjectsMap, "Unexpected Error", e.getMessage());
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
		    }

		    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		    
		    return ResponseEntity.ok(responseDTO);
		}
		
		
		@GetMapping("/getVendorRateByOrgId")
		public ResponseEntity<ResponseDTO> getVendorRateByOrgId(
		        @RequestParam(required = false) String branchCode,
		        @RequestParam Long orgId,
		        @RequestParam(defaultValue = "") String search,
		        @RequestParam(defaultValue = "1") int page,
		        @RequestParam(defaultValue = "10") int count
		) {
		    String methodName = "getVendorRateByOrgId()";
		    LOGGER.debug("Starting {}", methodName);

		    Map<String, Object> responseMap = new HashMap<>();
		    ResponseDTO responseDTO;

		    try {
		        Map<String, Object> vendorRateVO = masterService.getVendorRateByOrgId( branchCode,orgId, search, page, count);
		        responseMap.put("message", "VendorRate retrieved successfully");
		        responseMap.put("vendorRateVO", vendorRateVO);
		        responseDTO = createServiceResponse(responseMap);
		    } catch (Exception e) {
		        LOGGER.error("Error in {}: {}", methodName, e.getMessage());
		        responseDTO = createServiceResponseError(responseMap, "Error fetching users", e.getMessage());
		    }

		    LOGGER.debug("Ending {}", methodName);
		    return ResponseEntity.ok(responseDTO);
		}

		
		@PutMapping("/createUpdateCustomerRate")
		public ResponseEntity<ResponseDTO> createUpdateCustomerRate(
		        @RequestBody CustomerRateDTO customerRateDTO	) {

		    String methodName = "createUpdateCustomerRate()";
		    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		    try {
		        Map<String, Object> Response = masterService.createUpdateCustomerRate(customerRateDTO);

		        ResponseDTO responseDTO = createServiceResponse(Response);
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
		
		
		@GetMapping("/getCustomerRateById")
		public ResponseEntity<ResponseDTO> getCustomerRateById(@RequestParam Long id) {

		    String methodName = "getCustomerRateById()";
		    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		    Map<String, Object> responseObjectsMap = new HashMap<>();
		    ResponseDTO responseDTO;

		    try {
		        CustomerRateVO customerRateVO = masterService.getCustomerRateById(id);

		        if (customerRateVO == null) {
		            String errorMsg = "CustomerRate not found for ID: " + id;
		            responseDTO = createServiceResponseError(responseObjectsMap, "Not Found", errorMsg);
		            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
		        }

		        responseObjectsMap.put("message", "CustomerRate found successfully");
		        responseObjectsMap.put("customerRateVO", customerRateVO);

		        responseDTO = createServiceResponse(responseObjectsMap);

		    } catch (Exception e) {
		        LOGGER.error("{} - Unexpected Error: {}", methodName, e.getMessage(), e);
		        responseDTO = createServiceResponseError(responseObjectsMap, "Unexpected Error", e.getMessage());
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
		    }

		    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		    
		    return ResponseEntity.ok(responseDTO);
		}
		
		
		@GetMapping("/getCustomerRateByOrgId")
		public ResponseEntity<ResponseDTO> getCustomerRateByOrgId(
		        @RequestParam(required = false) String branchCode,
		        @RequestParam Long orgId,
		        @RequestParam(defaultValue = "") String search,
		        @RequestParam(defaultValue = "1") int page,
		        @RequestParam(defaultValue = "10") int count
		) {
		    String methodName = "getCustomerRateByOrgId()";
		    LOGGER.debug("Starting {}", methodName);

		    Map<String, Object> responseMap = new HashMap<>();
		    ResponseDTO responseDTO;

		    try {
		        Map<String, Object> customerRateVO = masterService.getCustomerRateByOrgId( branchCode,orgId, search, page, count);
		        responseMap.put("message", "CustomerRate retrieved successfully");
		        responseMap.put("customerRateVO", customerRateVO);
		        responseDTO = createServiceResponse(responseMap);
		    } catch (Exception e) {
		        LOGGER.error("Error in {}: {}", methodName, e.getMessage());
		        responseDTO = createServiceResponseError(responseMap, "Error fetching users", e.getMessage());
		    }
		    
		    

		    LOGGER.debug("Ending {}", methodName);
		    return ResponseEntity.ok(responseDTO);
		}

		@GetMapping("/getCustomerNameByOrgId")
		public ResponseEntity<ResponseDTO> getCustomerNameByOrgId(
		        @RequestParam(required = false) String branchCode,
		        @RequestParam Long orgId

		) {
		    String methodName = "getCustomerNameByOrgId()";
		    LOGGER.debug("Starting {}", methodName);

		    Map<String, Object> responseMap = new HashMap<>();
		    ResponseDTO responseDTO;

		    try {
		    	List<Map<String, Object>> customerVO = masterService.getCustomerNameByOrgId( branchCode,orgId);
		        responseMap.put("message", "Customer retrieved successfully");
		        responseMap.put("customerVO", customerVO);
		        responseDTO = createServiceResponse(responseMap);
		    } catch (Exception e) {
		        LOGGER.error("Error in {}: {}", methodName, e.getMessage());
		        responseDTO = createServiceResponseError(responseMap, "Error fetching users", e.getMessage());
		    }
		    
		    

		    LOGGER.debug("Ending {}", methodName);
		    return ResponseEntity.ok(responseDTO);
		}
		
		
		@PutMapping("/createUpdateRoutes")
		public ResponseEntity<ResponseDTO> createUpdateRoute(
		        @RequestBody RoutesDTO routesDTO	) {

		    String methodName = "createUpdateRoutes()";
		    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		    try {
		        Map<String, Object> Response = masterService.createUpdateRoutes(routesDTO);

		        ResponseDTO responseDTO = createServiceResponse(Response);
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

		
		
		@GetMapping("/getRoutesById")
		public ResponseEntity<ResponseDTO> getRoutesById(@RequestParam Long id) {

		    String methodName = "getRoutesById()";
		    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		    Map<String, Object> responseObjectsMap = new HashMap<>();
		    ResponseDTO responseDTO;

		    try {
		        RoutesVO routesVO = masterService.getRoutesById(id);

		        if (routesVO == null) {
		            String errorMsg = "Routes not found for ID: " + id;
		            responseDTO = createServiceResponseError(responseObjectsMap, "Not Found", errorMsg);
		            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
		        }

		        responseObjectsMap.put("message", "Routes found successfully");
		        responseObjectsMap.put("routesVO", routesVO);

		        responseDTO = createServiceResponse(responseObjectsMap);

		    } catch (Exception e) {
		        LOGGER.error("{} - Unexpected Error: {}", methodName, e.getMessage(), e);
		        responseDTO = createServiceResponseError(responseObjectsMap, "Unexpected Error", e.getMessage());
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
		    }

		    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		    
		    return ResponseEntity.ok(responseDTO);
		}
		
		@GetMapping("/getRoutesByOrgId")
		public ResponseEntity<ResponseDTO> getRoutesByOrgId(
		        @RequestParam(required = false) String branchCode,
		        @RequestParam Long orgId,
		        @RequestParam(defaultValue = "") String search,
		        @RequestParam(defaultValue = "1") int page,
		        @RequestParam(defaultValue = "10") int count
		) {
		    String methodName = "getRoutesByOrgId()";
		    LOGGER.debug("Starting {}", methodName);

		    Map<String, Object> responseMap = new HashMap<>();
		    ResponseDTO responseDTO;

		    try {
		        Map<String, Object> routesVO = masterService.getRoutesByOrgId( branchCode,orgId, search, page, count);
		        responseMap.put("message", "Routes retrieved successfully");
		        responseMap.put("routesVO", routesVO);
		        responseDTO = createServiceResponse(responseMap);
		    } catch (Exception e) {
		        LOGGER.error("Error in {}: {}", methodName, e.getMessage());
		        responseDTO = createServiceResponseError(responseMap, "Error fetching users", e.getMessage());
		    }
		    
		    LOGGER.debug("Ending {}", methodName);
		    return ResponseEntity.ok(responseDTO);
		}

		@GetMapping("/getVehicleTypeListByOrgId")
		public ResponseEntity<ResponseDTO> getVehicleTypeListByOrgId(
		        @RequestParam(required = false) String branchCode,
		        @RequestParam Long orgId

		) {
		    String methodName = "getVehicleTypeByOrgId()";
		    LOGGER.debug("Starting {}", methodName);

		    Map<String, Object> responseMap = new HashMap<>();
		    ResponseDTO responseDTO;

		    try {
		    	List<Map<String, Object>> vehicleTypeVO = masterService.getVehicleTypeListByOrgId( branchCode,orgId);
		        responseMap.put("message", "VehicleType retrieved successfully");
		        responseMap.put("vehicleTypeVO", vehicleTypeVO);
		        responseDTO = createServiceResponse(responseMap);
		    } catch (Exception e) {
		        LOGGER.error("Error in {}: {}", methodName, e.getMessage());
		        responseDTO = createServiceResponseError(responseMap, "Error fetching users", e.getMessage());
		    }
		    
		    LOGGER.debug("Ending {}", methodName);
		    return ResponseEntity.ok(responseDTO);
		}
		
		
		@GetMapping("/getOriginAndDestinationList")
		public ResponseEntity<ResponseDTO> getOriginAndDestinationList(
		        @RequestParam(required = false) String branchCode,
		        @RequestParam Long orgId

		) {
		    String methodName = "getOriginAndDestinationList()";
		    LOGGER.debug("Starting {}", methodName);

		    Map<String, Object> responseMap = new HashMap<>();
		    ResponseDTO responseDTO;

		    try {
		    	List<Map<String, Object>> routesVO = masterService.getOriginAndDestinationList( branchCode,orgId);
		        responseMap.put("message", "Routes retrieved successfully");
		        responseMap.put("routesVO", routesVO);
		        responseDTO = createServiceResponse(responseMap);
		    } catch (Exception e) {
		        LOGGER.error("Error in {}: {}", methodName, e.getMessage());
		        responseDTO = createServiceResponseError(responseMap, "Error fetching users", e.getMessage());
		    }
		    
		    LOGGER.debug("Ending {}", methodName);
		    return ResponseEntity.ok(responseDTO);
		}
		
		@GetMapping("/getVendorRateAndOriginList")
		public ResponseEntity<ResponseDTO> getVendorRateAndOriginList(
		        @RequestParam(required = false) String branchCode,
		        @RequestParam Long orgId

		) {
		    String methodName = "getVendorRateAndOriginList()";
		    LOGGER.debug("Starting {}", methodName);

		    Map<String, Object> responseMap = new HashMap<>();
		    ResponseDTO responseDTO;

		    try {
		    	List<Map<String, Object>> vendorRateVO = masterService.getVendorRateAndOriginList( branchCode,orgId);
		        responseMap.put("message", "VendorRate retrieved successfully");
		        responseMap.put("vendorRateVO", vendorRateVO);
		        responseDTO = createServiceResponse(responseMap);
		    } catch (Exception e) {
		        LOGGER.error("Error in {}: {}", methodName, e.getMessage());
		        responseDTO = createServiceResponseError(responseMap, "Error fetching users", e.getMessage());
		    }
		    
		    LOGGER.debug("Ending {}", methodName);
		    return ResponseEntity.ok(responseDTO);
		}
		
		
		@GetMapping("/getCustomerRateAndOriginList")
		public ResponseEntity<ResponseDTO> getCustomerRateAndOriginList(
		        @RequestParam(required = false) String branchCode,
		        @RequestParam Long orgId

		) {
		    String methodName = "getCustomerRateAndOriginList()";
		    LOGGER.debug("Starting {}", methodName);

		    Map<String, Object> responseMap = new HashMap<>();
		    ResponseDTO responseDTO;

		    try {
		    	List<Map<String, Object>> customerRateVO = masterService.getCustomerRateAndOriginList( branchCode,orgId);
		        responseMap.put("message", "CustomerRate retrieved successfully");
		        responseMap.put("customerRateVO", customerRateVO);
		        responseDTO = createServiceResponse(responseMap);
		    } catch (Exception e) {
		        LOGGER.error("Error in {}: {}", methodName, e.getMessage());
		        responseDTO = createServiceResponseError(responseMap, "Error fetching users", e.getMessage());
		    }
		    
		    LOGGER.debug("Ending {}", methodName);
		    return ResponseEntity.ok(responseDTO);
		}
		
		
		@PutMapping(value = "/createUpdateChargeType")
		public ResponseEntity<ResponseDTO> createUpdateChargeType(
		        @RequestBody ChargeTypeDTO chargeTypeDTO
		        ) {

		    String methodName = "createUpdateChargeType()";
		    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		    Map<String, Object> responseMap = new HashMap<>();

		    try {
		        Map<String, Object> serviceResponse = masterService.createUpdateChargeType(chargeTypeDTO);

		        responseMap.put("message", serviceResponse.get("message"));
		        responseMap.put("chargeTypeVO", serviceResponse.get("chargeTypeVO"));

		        ResponseDTO responseDTO = createServiceResponse(responseMap);
		        return ResponseEntity.ok(responseDTO);

		    } catch (Exception e) {

		        LOGGER.error("{} - Unexpected Error: {}", methodName, e.getMessage(), e);

		        ResponseDTO errorDTO = createServiceResponseError(
		                responseMap,
		                "Unexpected Error",
		                e.getMessage()
		        );
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDTO);
		    }
		}
		
		
		@GetMapping("/getChargeTypeById")
		public ResponseEntity<ResponseDTO> getChargeTypeById(@RequestParam Long id) {

		    String methodName = "getChargeTypeById()";
		    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		    Map<String, Object> responseObjectsMap = new HashMap<>();
		    ResponseDTO responseDTO;

		    try {
		        ChargeTypeVO chargeTypeVO = masterService.getChargeTypeById(id);

		        if (chargeTypeVO == null) {
		            String errorMsg = "chargeType not found for ID: " + id;
		            responseDTO = createServiceResponseError(responseObjectsMap, "Not Found", errorMsg);
		            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
		        }

		        responseObjectsMap.put("message", "chargeType found successfully");
		        responseObjectsMap.put("chargeTypeVO", chargeTypeVO);

		        responseDTO = createServiceResponse(responseObjectsMap);

		    } catch (Exception e) {
		        LOGGER.error("{} - Unexpected Error: {}", methodName, e.getMessage(), e);
		        responseDTO = createServiceResponseError(responseObjectsMap, "Unexpected Error", e.getMessage());
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
		    }

		    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		    
		    return ResponseEntity.ok(responseDTO);
		}
		
		@GetMapping("/getChargeTypeByOrgId")
		public ResponseEntity<ResponseDTO> getChargeTypeByOrgId(
		        @RequestParam(required = false) String branchCode,
		        @RequestParam Long orgId,
		        @RequestParam(defaultValue = "") String search,
		        @RequestParam(defaultValue = "1") int page,
		        @RequestParam(defaultValue = "10") int count
		) {
		    String methodName = "getChargeTypeByOrgId()";
		    LOGGER.debug("Starting {}", methodName);

		    Map<String, Object> responseMap = new HashMap<>();
		    ResponseDTO responseDTO;

		    try {
		        Map<String, Object> chargeTypeVO = masterService.getChargeTypeByOrgId( branchCode,orgId, search, page, count);
		        responseMap.put("message", "chargeType retrieved successfully");
		        responseMap.put("chargeTypeVO", chargeTypeVO);
		        responseDTO = createServiceResponse(responseMap);
		    } catch (Exception e) {
		        LOGGER.error("Error in {}: {}", methodName, e.getMessage());
		        responseDTO = createServiceResponseError(responseMap, "Error fetching users", e.getMessage());
		    }
		    
		    LOGGER.debug("Ending {}", methodName);
		    return ResponseEntity.ok(responseDTO);
		}
		
		
		@GetMapping("/getChargeTypeList")
		public ResponseEntity<ResponseDTO> getChargeTypeList(
		        @RequestParam(required = false) String branchCode,
		        @RequestParam Long orgId,
		        @RequestParam(defaultValue = "") String search,
		        @RequestParam(defaultValue = "1") int page,
		        @RequestParam(defaultValue = "10") int count
		) {
		    String methodName = "getChargeTypeByOrgId()";
		    LOGGER.debug("Starting {}", methodName);

		    Map<String, Object> responseMap = new HashMap<>();
		    ResponseDTO responseDTO;

		    try {
		        Map<String, Object> chargeTypeVO = masterService.getChargeTypeList( branchCode,orgId, search, page, count);
		        responseMap.put("message", "chargeType List retrieved successfully");
		        responseMap.put("chargeTypeVO", chargeTypeVO);
		        responseDTO = createServiceResponse(responseMap);
		    } catch (Exception e) {
		        LOGGER.error("Error in {}: {}", methodName, e.getMessage());
		        responseDTO = createServiceResponseError(responseMap, "Error fetching users", e.getMessage());
		    }
		    
		    LOGGER.debug("Ending {}", methodName);
		    return ResponseEntity.ok(responseDTO);
		}
		
		
		// ListOfValues

		@GetMapping("/getListOfValuesById")
		public ResponseEntity<ResponseDTO> getListOfValuesById(@RequestParam(required = false) Long id) {
			String methodName = "getListOfValuesById()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;
			List<ListOfValuesVO> listOfValuesVO = new ArrayList<>();
			try {
				listOfValuesVO = masterService.getListOfValuesById(id);
			} catch (Exception e) {
				errorMsg = e.getMessage();
				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			}
			if (StringUtils.isBlank(errorMsg)) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "listOfValues information get successfully By Id");
				responseObjectsMap.put("listOfValuesVO", listOfValuesVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				responseDTO = createServiceResponseError(responseObjectsMap,
						"listOfValues information receive failed By Id", errorMsg);
			}
			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
			return ResponseEntity.ok().body(responseDTO);
		}

		@GetMapping("/getListOfValuesByOrgId")
		public ResponseEntity<ResponseDTO> getListOfValuesByOrgId(@RequestParam(required = false) Long orgid) {
			String methodName = "getListOfValuesByOrgId()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;
			List<ListOfValuesVO> listOfValuesVO = new ArrayList<>();
			try {
				listOfValuesVO = masterService.getListOfValuesByOrgId(orgid);
			} catch (Exception e) {
				errorMsg = e.getMessage();
				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			}
			if (StringUtils.isBlank(errorMsg)) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "listOfValues information get successfully By OrgId");
				responseObjectsMap.put("listOfValuesVO", listOfValuesVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				responseDTO = createServiceResponseError(responseObjectsMap,
						"listOfValues information receive failed By OrgId", errorMsg);
			}
			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
			return ResponseEntity.ok().body(responseDTO);
		}

		@PutMapping("/updateCreateListOfValues")
		public ResponseEntity<ResponseDTO> updateCreateListOfValues(@Valid @RequestBody ListOfValuesDTO listOfValuesDTO) {
			String methodName = "updateCreateListOfValues()";

			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;

			try {
				ListOfValuesVO listOfValuesVO = masterService.updateCreateListOfValues(listOfValuesDTO);
				boolean isUpdate = listOfValuesDTO.getId() != null;
				if (listOfValuesVO != null) {
					responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
							isUpdate ? "ListOfValues updated successfully" : "ListOfValues created successfully");
					responseObjectsMap.put("listOfValuesVO", listOfValuesVO);
					responseDTO = createServiceResponse(responseObjectsMap);
				} else {
					errorMsg = isUpdate ? "ListOfValues not found for ID: " + listOfValuesDTO.getId()
							: "ListOfValues creation failed";
					responseDTO = createServiceResponseError(responseObjectsMap,
							isUpdate ? "ListOfValues update failed" : "ListOfValues creation failed", errorMsg);
				}
			} catch (Exception e) {
				errorMsg = e.getMessage();
				boolean isUpdate = listOfValuesDTO.getId() != null;
				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
				responseDTO = createServiceResponseError(responseObjectsMap,
						isUpdate ? "ListOfValues update failed" : "ListOfValues creation failed", errorMsg);
			}
			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
			return ResponseEntity.ok().body(responseDTO);
		}

		
		@GetMapping("/getValueDescriptionByListOfValues")
		public ResponseEntity<ResponseDTO> getValueDescriptionByListOfValues(
		        @RequestParam Long orgId,
		        @RequestParam String listDescription

		) {
		    String methodName = "getValueDescriptionByListOfValues()";
		    LOGGER.debug("Starting {}", methodName);

		    Map<String, Object> responseMap = new HashMap<>();
		    ResponseDTO responseDTO;

		    try {
		    	List<Map<String, Object>> listOfValuesVO = masterService.getValueDescriptionByListOfValues( orgId,listDescription);
		        responseMap.put("message", "listOfValuesVO retrieved successfully");
		        responseMap.put("listOfValuesVO", listOfValuesVO);
		        responseDTO = createServiceResponse(responseMap);
		    } catch (Exception e) {
		        LOGGER.error("Error in {}: {}", methodName, e.getMessage());
		        responseDTO = createServiceResponseError(responseMap, "Error fetching users", e.getMessage());
		    }
		    
		    LOGGER.debug("Ending {}", methodName);
		    return ResponseEntity.ok(responseDTO);
		}
		
		
		
}


