package com.efit.savaari.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.efit.savaari.common.CommonConstant;
import com.efit.savaari.dto.ChargeTypeDTO;
import com.efit.savaari.dto.CompanyProfileDTO;
import com.efit.savaari.dto.CustomerDTO;
import com.efit.savaari.dto.CustomerRateDTO;
import com.efit.savaari.dto.RoutesDTO;
import com.efit.savaari.entity.ChargeTypeVO;
import com.efit.savaari.entity.CustomerRateVO;
import com.efit.savaari.entity.CustomerVO;
import com.efit.savaari.entity.RoutesVO;
import com.efit.savaari.responseDTO.CompanyProfileResponseDTO;
import com.efit.savaari.responseDTO.ResponseDTO;
import com.efit.savaari.service.MasterService;

@CrossOrigin
@RestController
@RequestMapping("/api/master")
public class MasterController extends BaseController {

	@Autowired
	MasterService masterService;
	
	

	public static final Logger LOGGER = LoggerFactory.getLogger(MasterController.class);

		
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
		        @RequestParam Long orgId) {

		    Map<String, Object> responseMap = new HashMap<>();
		    ResponseDTO responseDTO;

		    try {
		        List<CustomerVO> customers =
		                masterService.getCustomerByOrgId(branchCode, orgId);

		        responseMap.put("message", "Customer retrieved successfully");
		        responseMap.put("customerVO", customers);

		        responseDTO = createServiceResponse(responseMap);

		    } catch (Exception e) {

		        responseDTO = createServiceResponseError(
		                responseMap,
		                "Error fetching users",
		                e.getMessage()
		        );
		    }

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
		
		
		
		
		//CompanyProfile
		
//		@PutMapping("/createUpdateCompanyProfile")
//		public ResponseEntity<ResponseDTO> createUpdateCompanyProfile(
//		        @RequestBody CompanyProfileDTO companyProfileDTO	) {
//
//		    String methodName = "createUpdateCompanyProfile()";
//		    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
//
//		    try {
//		        Map<String, Object> Response = masterService.createUpdateCompanyProfile(companyProfileDTO);
//
//		        ResponseDTO responseDTO = createServiceResponse(Response);
//		        return ResponseEntity.ok(responseDTO);
//
//		    } catch (Exception e) {
//		        LOGGER.error("{} - Unexpected Error: {}", methodName, e.getMessage(), e);
//		        ResponseDTO responseDTO = createServiceResponseError(
//		                new HashMap<>(),
//		                "Unexpected Error",
//		                e.getMessage()
//		        );
//		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
//		    }
//		}
//		
		@PutMapping(
			    value = "/createUpdateCompanyProfile",
				    consumes = MediaType.MULTIPART_FORM_DATA_VALUE
				)
				public ResponseEntity<ResponseDTO> createUpdateCompanyProfile(
				        @RequestPart("companyProfileDTO") CompanyProfileDTO companyProfileDTO,
//				        @RequestBody CompanyProfileDTO companyProfileDTO,

			        @RequestPart(value = "image", required = false) MultipartFile image) {

		    String methodName = "createUpdateCompanyProfile()";
		    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		    try {

		        Map<String, Object> response =
		                masterService.createUpdateCompanyProfile(companyProfileDTO, image);

		        ResponseDTO responseDTO = createServiceResponse(response);
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

		
		@GetMapping("/getAllCompanyProfileByOrgId")
		public ResponseEntity<ResponseDTO> getAllCompanyProfileByOrgId(@RequestParam Long orgId,
				@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int count) {
			String methodName = "getAllCompanyProfileByOrgId()";
			LOGGER.debug("Starting {}", methodName);

			Map<String, Object> responseMap = new HashMap<>();
			ResponseDTO responseDTO;

			try {
				Map<String, Object> companyProfile = masterService.getAllCompanyProfileByOrgId(orgId, page, count);
				responseMap.put("message", "CompanyProfile Details retrieved successfully");
				responseMap.put("companyProfile", companyProfile);
				responseDTO = createServiceResponse(responseMap);
			} catch (Exception e) {
				LOGGER.error("Error in {}: {}", methodName, e.getMessage());
				responseDTO = createServiceResponseError(responseMap, "Error fetching users", e.getMessage());
			}

			LOGGER.debug("Ending {}", methodName);
			return ResponseEntity.ok(responseDTO);
		}

		@GetMapping("/getCompanyProfileById")
		public ResponseEntity<ResponseDTO> getCompanyProfileById(@RequestParam Long id) {
			String methodName = "getCompanyProfileById()";
			LOGGER.debug("Starting {}", methodName);

			Map<String, Object> responseMap = new HashMap<>();
			ResponseDTO responseDTO;

			try {
				CompanyProfileResponseDTO companyProfileDTO = masterService.getCompanyProfileById(id);
				responseMap.put("message", "CompanyProfile Details retrieved successfully");
				responseMap.put("companyProfileDTO", companyProfileDTO);
				responseDTO = createServiceResponse(responseMap);
			} catch (Exception e) {
				LOGGER.error("Error in {}: {}", methodName, e.getMessage());
				responseDTO = createServiceResponseError(responseMap, "Error fetching users", e.getMessage());
			}

			LOGGER.debug("Ending {}", methodName);
			return ResponseEntity.ok(responseDTO);
		}

		@GetMapping("/getNextCustomerCode")
		public ResponseEntity<ResponseDTO> getNextCustomerCode() {

		    String methodName = "getNextCustomerCode()";
		    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		    Map<String, Object> responseObjectsMap = new HashMap<>();
		    ResponseDTO responseDTO;

		    try {

		        String customerCode = masterService.getNextCustomerCode();

		        responseObjectsMap.put("message", "Customer Code generated successfully");
		        responseObjectsMap.put("customerCode", customerCode);

		        responseDTO = createServiceResponse(responseObjectsMap);

		    } catch (Exception e) {

		        LOGGER.error("{} - Unexpected Error: {}", methodName, e.getMessage(), e);
		        responseDTO = createServiceResponseError(
		                responseObjectsMap,
		                "Unexpected Error",
		                e.getMessage()
		        );

		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
		    }

		    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		    return ResponseEntity.ok(responseDTO);
		}

		

		
}


