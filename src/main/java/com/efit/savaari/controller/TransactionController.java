package com.efit.savaari.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import com.efit.savaari.common.UserConstants;
import com.efit.savaari.dto.AuctionsDTO;
import com.efit.savaari.dto.CustomerBookingRequestDTO;
import com.efit.savaari.dto.DocumentDTO;
import com.efit.savaari.dto.PayoutsDTO;
import com.efit.savaari.dto.QuoteDTO;
import com.efit.savaari.dto.RequestforQuotesDTO;
import com.efit.savaari.dto.ResponseDTO;
import com.efit.savaari.dto.TaggingDTO;
import com.efit.savaari.dto.TdriverDTO;
import com.efit.savaari.dto.TripAlertsDTO;
import com.efit.savaari.dto.TripGeofenceAlertsDTO;
import com.efit.savaari.dto.TripReportMisDTO;
import com.efit.savaari.dto.TripsDTO;
import com.efit.savaari.dto.TvehicleDTO;
import com.efit.savaari.dto.VendorInvoiceDTO;
import com.efit.savaari.entity.AuctionsVO;
import com.efit.savaari.entity.CustomerBookingRequestVO;
import com.efit.savaari.entity.DocumentVO;
import com.efit.savaari.entity.PayoutsVO;
import com.efit.savaari.entity.QuoteVO;
import com.efit.savaari.entity.RequestforQuotesVO;
import com.efit.savaari.entity.TaggingVO;
import com.efit.savaari.entity.TdriverVO;
import com.efit.savaari.entity.TripAlertsVO;
import com.efit.savaari.entity.TripGeofenceAlertsVO;
import com.efit.savaari.entity.TripReportMisVO;
import com.efit.savaari.entity.TripsVO;
import com.efit.savaari.entity.TvehicleVO;
import com.efit.savaari.entity.VendorInvoiceVO;
import com.efit.savaari.service.TransactionService;

@CrossOrigin
@RestController
@RequestMapping("/api/transaction")
public class TransactionController extends BaseController {

	public static final Logger LOGGER = LoggerFactory.getLogger(TransactionController.class);

	@Autowired
	TransactionService transactionService;

	@PutMapping("/createUpdateTrips")
	public ResponseEntity<ResponseDTO> createUpdateTrips(@RequestBody TripsDTO tripsDTO) {

		String methodName = "createUpdateTrips()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		try {
			Map<String, Object> Response = transactionService.createUpdateTrips(tripsDTO);

			ResponseDTO responseDTO = createServiceResponse(Response);
			return ResponseEntity.ok(responseDTO);

		} catch (Exception e) {
			LOGGER.error("{} - Unexpected Error: {}", methodName, e.getMessage(), e);
			ResponseDTO responseDTO = createServiceResponseError(new HashMap<>(), "Unexpected Error", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
		}
	}

	@GetMapping("/getTripsById")
	public ResponseEntity<ResponseDTO> getTripsById(@RequestParam Long id) {

		String methodName = "getTripsById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {
			TripsVO tripsVO = transactionService.getTripsById(id);

			if (tripsVO == null) {
				String errorMsg = "Trips not found for ID: " + id;
				responseDTO = createServiceResponseError(responseObjectsMap, "Not Found", errorMsg);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
			}

			responseObjectsMap.put("message", "Trips found successfully");
			responseObjectsMap.put("tripsVO", tripsVO);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {
			LOGGER.error("{} - Unexpected Error: {}", methodName, e.getMessage(), e);
			responseDTO = createServiceResponseError(responseObjectsMap, "Unexpected Error", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getTripsByOrgId")
	public ResponseEntity<ResponseDTO> getTripsByOrgId(@RequestParam(required = false) String branchCode,
			@RequestParam Long orgId, @RequestParam(defaultValue = "") String search,
			@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int count) {
		String methodName = "getTripsByOrgId()";
		LOGGER.debug("Starting {}", methodName);

		Map<String, Object> responseMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {
			Map<String, Object> tripsVO = transactionService.getTripsByOrgId(branchCode, orgId, search, page, count);
			responseMap.put("message", "Trips retrieved successfully");
			responseMap.put("tripsVO", tripsVO);
			responseDTO = createServiceResponse(responseMap);
		} catch (Exception e) {
			LOGGER.error("Error in {}: {}", methodName, e.getMessage());
			responseDTO = createServiceResponseError(responseMap, "Error fetching users", e.getMessage());
		}

		LOGGER.debug("Ending {}", methodName);
		return ResponseEntity.ok(responseDTO);
	}

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

	@PutMapping(value = "/createUpdatePayouts", consumes = "multipart/form-data")
	public ResponseEntity<ResponseDTO> createUpdatePayouts(@RequestPart("payoutsDTO") PayoutsDTO payoutsDTO,
			@RequestPart(value = "invoiceFile", required = false) MultipartFile invoiceFile,
			@RequestPart(value = "documents", required = false) List<MultipartFile> documents) {

		String methodName = "createUpdatePayouts()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		try {
			Map<String, Object> Response = transactionService.createUpdatePayouts(payoutsDTO, invoiceFile, documents);

			ResponseDTO responseDTO = createServiceResponse(Response);
			return ResponseEntity.ok(responseDTO);

		} catch (Exception e) {
			LOGGER.error("{} - Unexpected Error: {}", methodName, e.getMessage(), e);
			ResponseDTO responseDTO = createServiceResponseError(new HashMap<>(), "Unexpected Error", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
		}
	}

	@GetMapping("/getPayoutsById")
	public ResponseEntity<ResponseDTO> getPayoutsById(@RequestParam Long id) {

		String methodName = "getPayoutsById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {

			PayoutsVO payoutsVO = transactionService.getPayoutsById(id);

			if (payoutsVO == null) {
				String errorMsg = "Payouts not found for ID: " + id;
				responseDTO = createServiceResponseError(responseObjectsMap, "Not Found", errorMsg);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
			}

			responseObjectsMap.put("message", "Payouts found successfully");
			responseObjectsMap.put("payoutsVO", payoutsVO);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {
			LOGGER.error("{} - Unexpected Error: {}", methodName, e.getMessage(), e);
			responseDTO = createServiceResponseError(responseObjectsMap, "Unexpected Error", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getPayoutsByOrgId")
	public ResponseEntity<ResponseDTO> getPayoutsByOrgId(@RequestParam(required = false) String branchCode,
			@RequestParam Long orgId, @RequestParam(defaultValue = "") String search,
			@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int count) {
		String methodName = "getPayoutsByOrgId()";
		LOGGER.debug("Starting {}", methodName);

		Map<String, Object> responseMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {
			Map<String, Object> payoutsVO = transactionService.getPayoutsByOrgId(branchCode, orgId, search, page,
					count);
			responseMap.put("message", "Payouts retrieved successfully");
			responseMap.put("payoutsVO", payoutsVO);
			responseDTO = createServiceResponse(responseMap);
		} catch (Exception e) {
			LOGGER.error("Error in {}: {}", methodName, e.getMessage());
			responseDTO = createServiceResponseError(responseMap, "Error fetching users", e.getMessage());
		}

		LOGGER.debug("Ending {}", methodName);
		return ResponseEntity.ok(responseDTO);
	}

	@PutMapping(value = "/createUpdateRequestforQuotes", consumes = "multipart/form-data")
	public ResponseEntity<ResponseDTO> createUpdateRequestforQuotes(
			@RequestPart("requestforQuotesDTO") RequestforQuotesDTO requestforQuotesDTO,
			@RequestPart(value = "documents", required = false) MultipartFile documents) {

		String methodName = "createUpdateRFQ()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		try {
			Map<String, Object> Response = transactionService.createUpdateRequestforQuotes(requestforQuotesDTO,
					documents);

			ResponseDTO responseDTO = createServiceResponse(Response);
			return ResponseEntity.ok(responseDTO);

		} catch (Exception e) {
			LOGGER.error("{} - Unexpected Error: {}", methodName, e.getMessage(), e);
			ResponseDTO responseDTO = createServiceResponseError(new HashMap<>(), "Unexpected Error", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
		}
	}

	@GetMapping("/getRequestforQuotesById")
	public ResponseEntity<ResponseDTO> getRequestforQuotesById(@RequestParam Long id) {

		String methodName = "getRFQById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {

			RequestforQuotesVO requestforQuotesVO = transactionService.getRequestforQuotesById(id);

			if (requestforQuotesVO == null) {
				String errorMsg = "RequestforQuotes not found for ID: " + id;
				responseDTO = createServiceResponseError(responseObjectsMap, "Not Found", errorMsg);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
			}

			responseObjectsMap.put("message", "RequestforQuotes found successfully");
			responseObjectsMap.put("requestforQuotesVO", requestforQuotesVO);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {
			LOGGER.error("{} - Unexpected Error: {}", methodName, e.getMessage(), e);
			responseDTO = createServiceResponseError(responseObjectsMap, "Unexpected Error", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getRequestforQuotesByOrgId")
	public ResponseEntity<ResponseDTO> getRequestforQuotesByOrgId(@RequestParam(required = false) String branchCode,
			@RequestParam(defaultValue = "") String search, @RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int count) {
		String methodName = "getRequestforQuotesByOrgId()";
		LOGGER.debug("Starting {}", methodName);

		Map<String, Object> responseMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {
			Map<String, Object> requestforQuotesVO = transactionService.getRequestforQuotesByOrgId(branchCode, search,
					page, count);
			responseMap.put("message", "RequestforQuotes retrieved successfully");
			responseMap.put("requestforQuotesVO", requestforQuotesVO);
			responseDTO = createServiceResponse(responseMap);
		} catch (Exception e) {
			LOGGER.error("Error in {}: {}", methodName, e.getMessage());
			responseDTO = createServiceResponseError(responseMap, "Error fetching users", e.getMessage());
		}

		LOGGER.debug("Ending {}", methodName);
		return ResponseEntity.ok(responseDTO);
	}

	@PostMapping(value = "/uploadLineItems", consumes = "multipart/form-data")
	public ResponseEntity<ResponseDTO> uploadLineItems(@RequestParam Long rfqId,
			@RequestPart("files") MultipartFile file, @RequestPart("createdBy") String createdBy) {

		String methodName = "uploadLineItems()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		try {
			Map<String, Object> response = transactionService.uploadLineItemsExcel(rfqId, file, createdBy);
			ResponseDTO res = createServiceResponse(response);
			return ResponseEntity.ok(res);

		} catch (Exception e) {
			LOGGER.error("{} - Unexpected Error: {}", methodName, e.getMessage());
			ResponseDTO res = createServiceResponseError(new HashMap<>(), "Upload Failed", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
		}
	}

	// AUCTIONS

	@PutMapping(value = "/createUpdateAuctions", consumes = "multipart/form-data")
	public ResponseEntity<ResponseDTO> createUpdateAuctions(@RequestPart("auctionsDTO") AuctionsDTO auctionsDTO,
			@RequestPart(value = "documents", required = false) MultipartFile documents) {
		String methodName = "createUpdateAuctions()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		try {
			Map<String, Object> Response = transactionService.createUpdateAuctions(auctionsDTO, documents);

			ResponseDTO responseDTO = createServiceResponse(Response);
			return ResponseEntity.ok(responseDTO);

		} catch (Exception e) {
			LOGGER.error("{} - Unexpected Error: {}", methodName, e.getMessage(), e);
			ResponseDTO responseDTO = createServiceResponseError(new HashMap<>(), "Unexpected Error", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
		}
	}

	@GetMapping("/getAuctionsById")
	public ResponseEntity<ResponseDTO> getAuctionsById(@RequestParam Long id) {

		String methodName = "getAuctionsById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {

			AuctionsVO auctionsVO = transactionService.getAuctionsById(id);

			if (auctionsVO == null) {
				String errorMsg = "Auctions not found for ID: " + id;
				responseDTO = createServiceResponseError(responseObjectsMap, "Not Found", errorMsg);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
			}

			responseObjectsMap.put("message", "Auctions found successfully");
			responseObjectsMap.put("auctionsVO", auctionsVO);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {
			LOGGER.error("{} - Unexpected Error: {}", methodName, e.getMessage(), e);
			responseDTO = createServiceResponseError(responseObjectsMap, "Unexpected Error", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getAuctionsByOrgId")
	public ResponseEntity<ResponseDTO> getAuctionsByOrgId(@RequestParam(required = false) String branchCode,
			@RequestParam Long orgId, @RequestParam(defaultValue = "") String search,
			@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int count) {
		String methodName = "getAuctionsByOrgId()";
		LOGGER.debug("Starting {}", methodName);

		Map<String, Object> responseMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {
			Map<String, Object> auctionsVO = transactionService.getAuctionsByOrgId(branchCode, orgId, search, page,
					count);
			responseMap.put("message", "Auctions retrieved successfully");
			responseMap.put("auctionsVO", auctionsVO);
			responseDTO = createServiceResponse(responseMap);
		} catch (Exception e) {
			LOGGER.error("Error in {}: {}", methodName, e.getMessage());
			responseDTO = createServiceResponseError(responseMap, "Error fetching users", e.getMessage());
		}

		LOGGER.debug("Ending {}", methodName);
		return ResponseEntity.ok(responseDTO);
	}

	@PutMapping("/createUpdateTagging")
	public ResponseEntity<ResponseDTO> createUpdateTagging(@RequestBody TaggingDTO taggingDTO) {

		String methodName = "createUpdateTagging()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		try {
			Map<String, Object> Response = transactionService.createUpdateTagging(taggingDTO);

			ResponseDTO responseDTO = createServiceResponse(Response);
			return ResponseEntity.ok(responseDTO);

		} catch (Exception e) {
			LOGGER.error("{} - Unexpected Error: {}", methodName, e.getMessage(), e);
			ResponseDTO responseDTO = createServiceResponseError(new HashMap<>(), "Unexpected Error", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
		}
	}

	@GetMapping("/getTaggingById")
	public ResponseEntity<ResponseDTO> getTaggingById(@RequestParam Long id) {

		String methodName = "getTaggingById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {

			TaggingVO taggingVO = transactionService.getTaggingById(id);

			if (taggingVO == null) {
				String errorMsg = "Tagging not found for ID: " + id;
				responseDTO = createServiceResponseError(responseObjectsMap, "Not Found", errorMsg);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
			}

			responseObjectsMap.put("message", "Tagging found successfully");
			responseObjectsMap.put("taggingVO", taggingVO);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {
			LOGGER.error("{} - Unexpected Error: {}", methodName, e.getMessage(), e);
			responseDTO = createServiceResponseError(responseObjectsMap, "Unexpected Error", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getTaggingByOrgId")
	public ResponseEntity<ResponseDTO> getTaggingByOrgId(@RequestParam(required = false) String branchCode,
			@RequestParam(defaultValue = "") String search, @RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int count) {
		String methodName = "getTaggingByOrgId()";
		LOGGER.debug("Starting {}", methodName);

		Map<String, Object> responseMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {
			Map<String, Object> taggingVO = transactionService.getTaggingByOrgId(branchCode, search, page, count);
			responseMap.put("message", "Tagging retrieved successfully");
			responseMap.put("taggingVO", taggingVO);
			responseDTO = createServiceResponse(responseMap);
		} catch (Exception e) {
			LOGGER.error("Error in {}: {}", methodName, e.getMessage());
			responseDTO = createServiceResponseError(responseMap, "Error fetching users", e.getMessage());
		}

		LOGGER.debug("Ending {}", methodName);
		return ResponseEntity.ok(responseDTO);
	}

	// tripreportmis

	@PutMapping("/createUpdateTripReportMis")
	public ResponseEntity<ResponseDTO> createUpdateTripReportMis(@RequestBody TripReportMisDTO tripReportMisDTO) {
		String methodName = "createUpdateTripReportMis()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> tripReportMisVO = transactionService.createUpdateTripReportMis(tripReportMisDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, tripReportMisVO.get("message"));
			responseObjectsMap.put("tripReportMisVO", tripReportMisVO.get("tripReportMisVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getTripReportMisById")
	public ResponseEntity<ResponseDTO> getTripReportMisById(@RequestParam Long id) {

		String methodName = "getTripReportMisById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {

			TripReportMisVO tripReportMisVO = transactionService.getTripReportMisById(id);

			if (tripReportMisVO == null) {
				String errorMsg = "TripReportMis not found for ID: " + id;
				responseDTO = createServiceResponseError(responseObjectsMap, "Not Found", errorMsg);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
			}

			responseObjectsMap.put("message", "TripReportMis found successfully");
			responseObjectsMap.put("tripReportMisVO", tripReportMisVO);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {
			LOGGER.error("{} - Unexpected Error: {}", methodName, e.getMessage(), e);
			responseDTO = createServiceResponseError(responseObjectsMap, "Unexpected Error", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getTripReportMisByOrgId")
	public ResponseEntity<ResponseDTO> getTripReportMisByOrgId(@RequestParam(required = false) String branchCode,
			@RequestParam(defaultValue = "") String search, @RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int count) {
		String methodName = "getTripReportMisByOrgId()";
		LOGGER.debug("Starting {}", methodName);

		Map<String, Object> responseMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {
			Map<String, Object> tripReportMisVO = transactionService.getTripReportMisByOrgId(branchCode, search, page,
					count);
			responseMap.put("message", "TripReportMis retrieved successfully");
			responseMap.put("tripReportMisVO", tripReportMisVO);
			responseDTO = createServiceResponse(responseMap);
		} catch (Exception e) {
			LOGGER.error("Error in {}: {}", methodName, e.getMessage());
			responseDTO = createServiceResponseError(responseMap, "Error fetching users", e.getMessage());
		}

		LOGGER.debug("Ending {}", methodName);
		return ResponseEntity.ok(responseDTO);
	}

	// TripGeofenceAlerts

	@PutMapping("/createUpdateTripGeofenceAlerts")
	public ResponseEntity<ResponseDTO> createUpdateTripGeofenceAlerts(@RequestBody TripGeofenceAlertsDTO dto) {

		String methodName = "createUpdateTripGeofenceAlerts()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {
			Map<String, Object> result = transactionService.createUpdateTripGeofenceAlerts(dto);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, result.get("message"));
			responseObjectsMap.put("tripGeofenceAlertsVO", result.get("tripGeofenceAlertsVO"));

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {
			LOGGER.error("{} - Unexpected Error: {}", methodName, e.getMessage(), e);
			responseDTO = createServiceResponseError(responseObjectsMap, "Unexpected Error", e.getMessage());
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getTripGeofenceAlertsById")
	public ResponseEntity<ResponseDTO> getTripGeofenceAlertsById(@RequestParam Long id) {

		String methodName = "getTripGeofenceAlertsById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {

			TripGeofenceAlertsVO tripGeofenceAlertsVO = transactionService.getTripGeofenceAlertsById(id);

			if (tripGeofenceAlertsVO == null) {
				String errorMsg = "TripGeofenceAlerts not found for ID: " + id;
				responseDTO = createServiceResponseError(responseObjectsMap, "Not Found", errorMsg);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
			}

			responseObjectsMap.put("message", "TripGeofenceAlerts found successfully");
			responseObjectsMap.put("tripGeofenceAlertsVO", tripGeofenceAlertsVO);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {
			LOGGER.error("{} - Unexpected Error: {}", methodName, e.getMessage(), e);
			responseDTO = createServiceResponseError(responseObjectsMap, "Unexpected Error", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getTripGeofenceAlertsByOrgId")
	public ResponseEntity<ResponseDTO> getTripGeofenceAlertsByOrgId(@RequestParam(required = false) String branchCode,
			@RequestParam Long orgId, @RequestParam(defaultValue = "") String search,
			@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int count) {
		String methodName = "getTripGeofenceAlertsByOrgId()";
		LOGGER.debug("Starting {}", methodName);

		Map<String, Object> responseMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {
			Map<String, Object> tripGeofenceAlertsVO = transactionService.getTripGeofenceAlertsByOrgId(branchCode,
					orgId, search, page, count);
			responseMap.put("message", "TripGeofenceAlerts retrieved successfully");
			responseMap.put("tripGeofenceAlertsVO", tripGeofenceAlertsVO);
			responseDTO = createServiceResponse(responseMap);
		} catch (Exception e) {
			LOGGER.error("Error in {}: {}", methodName, e.getMessage());
			responseDTO = createServiceResponseError(responseMap, "Error fetching users", e.getMessage());
		}

		LOGGER.debug("Ending {}", methodName);
		return ResponseEntity.ok(responseDTO);
	}

	@PutMapping("/createUpdateTripAlerts")
	public ResponseEntity<ResponseDTO> createUpdateTripAlerts(@Valid @RequestBody TripAlertsDTO dto) {

		String methodName = "createUpdateTripAlerts()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseMap = new HashMap<>();

		try {
			Map<String, Object> serviceResponse = transactionService.createUpdateTripAlerts(dto);

			responseMap.put("message", serviceResponse.get("message"));
			responseMap.put("tripAlertsVO", serviceResponse.get("tripAlertsVO"));

			ResponseDTO responseDTO = createServiceResponse(responseMap);
			return ResponseEntity.ok(responseDTO);

		} catch (Exception e) {

			LOGGER.error("{} - Unexpected Error: {}", methodName, e.getMessage(), e);

			ResponseDTO errorDTO = createServiceResponseError(responseMap, "Unexpected Error", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDTO);
		}
	}

	@GetMapping("/getTripAlertsById")
	public ResponseEntity<ResponseDTO> getTripAlertsById(@RequestParam Long id) {

		String methodName = "getTripAlertsById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {

			TripAlertsVO tripAlertsVO = transactionService.getTripAlertsById(id);

			if (tripAlertsVO == null) {
				String errorMsg = "TripAlerts not found for ID: " + id;
				responseDTO = createServiceResponseError(responseObjectsMap, "Not Found", errorMsg);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
			}

			responseObjectsMap.put("message", "TripAlerts found successfully");
			responseObjectsMap.put("tripAlertsVO", tripAlertsVO);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {
			LOGGER.error("{} - Unexpected Error: {}", methodName, e.getMessage(), e);
			responseDTO = createServiceResponseError(responseObjectsMap, "Unexpected Error", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getTripAlertsByOrgId")
	public ResponseEntity<ResponseDTO> getTripAlertsByOrgId(@RequestParam(required = false) String branchCode,
			@RequestParam Long orgId, @RequestParam(defaultValue = "") String search,
			@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int count) {
		String methodName = "getTripAlertsByOrgId()";
		LOGGER.debug("Starting {}", methodName);

		Map<String, Object> responseMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {
			Map<String, Object> tripAlertsVO = transactionService.getTripAlertsByOrgId(branchCode, orgId, search, page,
					count);
			responseMap.put("message", "TripAlerts retrieved successfully");
			responseMap.put("tripAlertsVO", tripAlertsVO);
			responseDTO = createServiceResponse(responseMap);
		} catch (Exception e) {
			LOGGER.error("Error in {}: {}", methodName, e.getMessage());
			responseDTO = createServiceResponseError(responseMap, "Error fetching users", e.getMessage());
		}

		LOGGER.debug("Ending {}", methodName);
		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getAuctionsReportByOrgId")
	public ResponseEntity<ResponseDTO> getAuctionsReportByOrgId(@RequestParam Long userId,
			@RequestParam(defaultValue = "") String search, @RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int count) {
		String methodName = "getAuctionsReportByOrgId()";
		LOGGER.debug("Starting {}", methodName);

		Map<String, Object> responseMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {
			Map<String, Object> auctionsReportVO = transactionService.getAuctionsReportByOrgId(userId, search, page,
					count);
			responseMap.put("message", "AuctionsReport retrieved successfully");
			responseMap.put("auctionsReportVO", auctionsReportVO);
			responseDTO = createServiceResponse(responseMap);
		} catch (Exception e) {
			LOGGER.error("Error in {}: {}", methodName, e.getMessage());
			responseDTO = createServiceResponseError(responseMap, "Error fetching users", e.getMessage());
		}

		LOGGER.debug("Ending {}", methodName);
		return ResponseEntity.ok(responseDTO);
	}

	@PutMapping("/createUpdateQuote")
	public ResponseEntity<ResponseDTO> createUpdateQuote(@Valid @RequestBody QuoteDTO dto) {

		String methodName = "createUpdateQuote()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {
			Map<String, Object> result = transactionService.createUpdateQuote(dto);

			responseMap.put("message", result.get("message"));
			responseMap.put("quoteVO", result.get("quoteVO"));

			responseDTO = createServiceResponse(responseMap);

		} catch (Exception e) {

			LOGGER.error("{} - Unexpected Error: {}", methodName, e.getMessage(), e);

			responseDTO = createServiceResponseError(responseMap, "Unexpected Error", e.getMessage());
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getQuoteById")
	public ResponseEntity<ResponseDTO> getQuoteById(@RequestParam Long id) {

		String methodName = "getQuoteById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {

			QuoteVO quoteVO = transactionService.getQuoteById(id);

			if (quoteVO == null) {
				String errorMsg = "Quote not found for ID: " + id;
				responseDTO = createServiceResponseError(responseObjectsMap, "Not Found", errorMsg);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
			}

			responseObjectsMap.put("message", "Quote found successfully");
			responseObjectsMap.put("quoteVO", quoteVO);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {
			LOGGER.error("{} - Unexpected Error: {}", methodName, e.getMessage(), e);
			responseDTO = createServiceResponseError(responseObjectsMap, "Unexpected Error", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getQuoteByUserId")
	public ResponseEntity<ResponseDTO> getQuoteByUserId(@RequestParam Long userId,
			@RequestParam(defaultValue = "") String search, @RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int count) {
		String methodName = "getQuoteByUserId()";
		LOGGER.debug("Starting {}", methodName);

		Map<String, Object> responseMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {
			Map<String, Object> quoteVO = transactionService.getQuoteByUserId(userId, search, page, count);
			responseMap.put("message", "Quote retrieved successfully");
			responseMap.put("quoteVO", quoteVO);
			responseDTO = createServiceResponse(responseMap);
		} catch (Exception e) {
			LOGGER.error("Error in {}: {}", methodName, e.getMessage());
			responseDTO = createServiceResponseError(responseMap, "Error fetching users", e.getMessage());
		}

		LOGGER.debug("Ending {}", methodName);
		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getUserAuctionsQuoteByOrgId")
	public ResponseEntity<ResponseDTO> getUserAuctionsQuoteByOrgId(@RequestParam Long userId,
			@RequestParam(defaultValue = "") String search, @RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int count) {
		String methodName = "getQuoteByOrgId()";
		LOGGER.debug("Starting {}", methodName);

		Map<String, Object> responseMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {
			Map<String, Object> quoteVO = transactionService.getUserAuctionsQuoteByOrgId(userId, search, page, count);
			responseMap.put("message", "Quote retrieved successfully");
			responseMap.put("quoteVO", quoteVO);
			responseDTO = createServiceResponse(responseMap);
		} catch (Exception e) {
			LOGGER.error("Error in {}: {}", methodName, e.getMessage());
			responseDTO = createServiceResponseError(responseMap, "Error fetching users", e.getMessage());
		}

		LOGGER.debug("Ending {}", methodName);
		return ResponseEntity.ok(responseDTO);
	}

	@PutMapping(value = "/createUpdateTvehicle")
	public ResponseEntity<ResponseDTO> createUpdateTvehicle(@RequestPart("tvehicleDTO") TvehicleDTO tvehicleDTO,
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
			@RequestParam Long userId, @RequestParam(defaultValue = "") String search,
			@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int count) {
		String methodName = "getTvehiclesByOrgId()";
		LOGGER.debug("Starting {}", methodName);

		Map<String, Object> responseMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {
			Map<String, Object> tvehiclesVO = transactionService.getTvehiclesByOrgId(branchCode, userId, search, page,
					count);
			responseMap.put("message", "Tvehicles retrieved successfully");
			responseMap.put("tvehiclesVO", tvehiclesVO);
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
	public ResponseEntity<ResponseDTO> createUpdateTdriver(
//	        @RequestPart("tdriverDTO") TdriverDTO tdriverDTO,
			@RequestBody TdriverDTO tdriverDTO
//	        @RequestPart(value = "documents", required = false) List<MultipartFile> documents 
	) {

		String methodName = "createUpdateTvehicle()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseMap = new HashMap<>();

		try {
			Map<String, Object> serviceResponse = transactionService.createUpdateTdriver(tdriverDTO);

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
			@RequestParam Long userId, @RequestParam(defaultValue = "") String search,
			@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int count) {
		String methodName = "getTdriverByOrgId()";
		LOGGER.debug("Starting {}", methodName);

		Map<String, Object> responseMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {
			Map<String, Object> tdriverVO = transactionService.getTdriverByOrgId(branchCode, userId, search, page,
					count);
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

	@PutMapping("/createApprovalQuote")
	public ResponseEntity<ResponseDTO> createApprovalQuote(@RequestParam Long quotesId, @RequestParam String action,
			@RequestParam String actionBy, @RequestParam Long auctionId) {
		String methodName = "createApprovalLeave()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> result = transactionService.createApprovalQuote(quotesId, action, actionBy, auctionId);

			// ✅ Correct keys from the returned map
			responseObjectsMap.put("quoteVO", result.get("quoteVO"));
			responseObjectsMap.put("message", result.get("message"));

			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

//	VendorInvoice

	@PutMapping(value = "/createUpdateVendorInvoice", consumes = "multipart/form-data")
	public ResponseEntity<ResponseDTO> createUpdateVendorInvoice(
			@RequestPart("vendorInvoiceDTO") VendorInvoiceDTO vendorInvoiceDTO,
			@RequestPart(value = "invoiceFile", required = false) MultipartFile invoiceFile,
			@RequestPart(value = "tripDocuments", required = false) List<MultipartFile> tripDocuments) {

		String methodName = "createUpdateVendorInvoice()";
		LOGGER.debug("START {}", methodName);

		Map<String, Object> responseMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {
			Map<String, Object> savedData = transactionService.createUpdateVendorInvoice(vendorInvoiceDTO, invoiceFile,
					tripDocuments);

			responseMap.put("vendorInvoice", savedData.get("vendorInvoice"));
			responseMap.put("message", savedData.get("message"));

			responseDTO = createServiceResponse(responseMap);

		} catch (Exception e) {
			LOGGER.error("{} - Error: {}", methodName, e.getMessage(), e);
			responseDTO = createServiceResponseError(responseMap, "Unexpected Error", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
		}

		LOGGER.debug("END {}", methodName);
		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getVendorInvoiceById")
	public ResponseEntity<ResponseDTO> getVendorInvoiceById(@RequestParam Long id) {

		String methodName = "getVendorInvoiceById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {

			VendorInvoiceVO vendorInvoiceVO = transactionService.getVendorInvoiceById(id);

			if (vendorInvoiceVO == null) {
				String errorMsg = "VendorInvoice not found for ID: " + id;
				responseDTO = createServiceResponseError(responseObjectsMap, "Not Found", errorMsg);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
			}

			responseObjectsMap.put("message", "Tdriver found successfully");
			responseObjectsMap.put("vendorInvoiceVO", vendorInvoiceVO);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {
			LOGGER.error("{} - Unexpected Error: {}", methodName, e.getMessage(), e);
			responseDTO = createServiceResponseError(responseObjectsMap, "Unexpected Error", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getVendorInvoiceByOrgId")
	public ResponseEntity<ResponseDTO> getVendorInvoiceByOrgId(@RequestParam(required = false) String branchCode,
			@RequestParam Long orgId, @RequestParam(defaultValue = "") String search,
			@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int count) {
		String methodName = "getVendorInvoiceByOrgId()";
		LOGGER.debug("Starting {}", methodName);

		Map<String, Object> responseMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {
			Map<String, Object> vendorInvoiceVO = transactionService.getVendorInvoiceByOrgId(branchCode, orgId, search,
					page, count);
			responseMap.put("message", "VendorInvoice retrieved successfully");
			responseMap.put("vendorInvoiceVO", vendorInvoiceVO);
			responseDTO = createServiceResponse(responseMap);
		} catch (Exception e) {
			LOGGER.error("Error in {}: {}", methodName, e.getMessage());
			responseDTO = createServiceResponseError(responseMap, "Error fetching users", e.getMessage());
		}

		LOGGER.debug("Ending {}", methodName);
		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getTripDetailsListByOrgId")
	public ResponseEntity<ResponseDTO> getTripDetailsListByOrgId(@RequestParam(required = false) String branchCode,
			@RequestParam Long orgId

	) {
		String methodName = "getTripDetailsListByOrgId()";
		LOGGER.debug("Starting {}", methodName);

		Map<String, Object> responseMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {
			List<Map<String, Object>> tripVO = transactionService.getTripDetailsListByOrgId(branchCode, orgId);
			responseMap.put("message", "Trips retrieved successfully");
			responseMap.put("tripVO", tripVO);
			responseDTO = createServiceResponse(responseMap);
		} catch (Exception e) {
			LOGGER.error("Error in {}: {}", methodName, e.getMessage());
			responseDTO = createServiceResponseError(responseMap, "Error fetching users", e.getMessage());
		}

		LOGGER.debug("Ending {}", methodName);
		return ResponseEntity.ok(responseDTO);
	}

	@PutMapping(value = "/updateCreateDocument", consumes = "multipart/form-data")
	public ResponseEntity<ResponseDTO> updateCreateDocument(@RequestPart("documentDTO") DocumentDTO documentDTO,
//			@RequestBody DocumentDTO documentDTO,
			@RequestPart(value = "invoiceFile", required = false) MultipartFile invoiceFile) {

		String methodName = "updateCreateDocument()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		try {
			Map<String, Object> Response = transactionService.updateCreateDocument(documentDTO, invoiceFile);

			ResponseDTO responseDTO = createServiceResponse(Response);
			return ResponseEntity.ok(responseDTO);

		} catch (Exception e) {
			LOGGER.error("{} - Unexpected Error: {}", methodName, e.getMessage(), e);
			ResponseDTO responseDTO = createServiceResponseError(new HashMap<>(), "Unexpected Error", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
		}
	}

	@GetMapping("/getAllDocumentByUserId")
	public ResponseEntity<ResponseDTO> getAllDocumentByUserId(@RequestParam Long orgId, @RequestParam Long userId,
			@RequestParam(defaultValue = "") String search, @RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int size) {
		String methodName = "getAllDocumentByUserId()";
		LOGGER.debug("Starting {}", methodName);

		Map<String, Object> responseMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {
			Map<String, Object> documentVO = transactionService.getAllDocumentByUserId(orgId, userId, search, page,
					size);
			responseMap.put("message", "Document retrieved successfully");
			responseMap.put("documentVO", documentVO);
			responseDTO = createServiceResponse(responseMap);
		} catch (Exception e) {
			LOGGER.error("Error in {}: {}", methodName, e.getMessage());
			responseDTO = createServiceResponseError(responseMap, "Error fetching users", e.getMessage());
		}

		LOGGER.debug("Ending {}", methodName);
		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getDocumentById")
	public ResponseEntity<ResponseDTO> getDocumentById(@RequestParam Long id) {
		String methodName = "getDocumentById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		DocumentVO documentVO = new DocumentVO();
		try {
			documentVO = transactionService.getDocumentById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Document get successfully By id");
			responseObjectsMap.put("documentVO", documentVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Document information receive failedByOrgId",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getDocumentCount")
	public ResponseEntity<ResponseDTO> getDocumentCount(@RequestParam Long orgId, @RequestParam String branchCode,
			@RequestParam Long userId) {
		String methodName = "getDocumentCount()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = transactionService.getDocumentCount(orgId, branchCode, userId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Count  retrieved successfully");
			responseObjectsMap.put("mapp", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve  Count", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getDocumentTypeCount")
	public ResponseEntity<ResponseDTO> getDocumentTypeCount(@RequestParam Long orgId, @RequestParam String branchCode,
			@RequestParam Long userId) {
		String methodName = "getDocumentTypeCount()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = transactionService.getDocumentTypeCount(orgId, branchCode, userId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "TypeCount retrieved successfully");
			responseObjectsMap.put("mapp", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve  TypeCount", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getAllDocumentList")
	public ResponseEntity<ResponseDTO> getAllDocumentList(@RequestParam Long orgId, @RequestParam String branchCode,
			@RequestParam Long userId, @RequestParam(required = false) String search, @RequestParam String status,
			@RequestParam String type) {
		String methodName = "getAllDocumentList()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<DocumentVO> documentVO = new ArrayList<>();
		try {
			documentVO = transactionService.getAllDocumentList(orgId, branchCode, userId, search, status, type);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, " successfully By ListAllDocument ");
			responseObjectsMap.put("documentVO", documentVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "ListAllDocument information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getAuctionReportByOrgId")
	public ResponseEntity<ResponseDTO> getApprovedQuotesByOrgId(@RequestParam Long orgId,
			@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int count) {
		String methodName = "getAuctionReportByOrgId()";
		LOGGER.debug("Starting {}", methodName);

		Map<String, Object> responseMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {
			Map<String, Object> approvedQuotes = transactionService.getApprovedQuotesByOrgId(orgId, page, count);
			responseMap.put("message", "Trips retrieved successfully");
			responseMap.put("approvedQuotes", approvedQuotes);
			responseDTO = createServiceResponse(responseMap);
		} catch (Exception e) {
			LOGGER.error("Error in {}: {}", methodName, e.getMessage());
			responseDTO = createServiceResponseError(responseMap, "Error fetching users", e.getMessage());
		}

		LOGGER.debug("Ending {}", methodName);
		return ResponseEntity.ok(responseDTO);
	}

}
