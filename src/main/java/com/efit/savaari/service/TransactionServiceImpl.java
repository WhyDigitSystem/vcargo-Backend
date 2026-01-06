package com.efit.savaari.service;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet; // RIGHT ✔ (SS = Spreadsheet)
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.efit.savaari.dto.AuctionsDTO;
import com.efit.savaari.dto.CustomerBookingRequestDTO;
import com.efit.savaari.dto.DocumentDTO;
import com.efit.savaari.dto.PayoutsDTO;
import com.efit.savaari.dto.PayoutsDocumentDTO;
import com.efit.savaari.dto.QuoteDTO;
import com.efit.savaari.dto.RequestforQuotesDTO;
import com.efit.savaari.dto.TaggingDTO;
import com.efit.savaari.dto.TdriverDTO;
import com.efit.savaari.dto.TdriverDocumentResponseDTO;
import com.efit.savaari.dto.TdriverResponseDTO;
import com.efit.savaari.dto.TripAlertsDTO;
import com.efit.savaari.dto.TripGeofenceAlertsDTO;
import com.efit.savaari.dto.TripReportMisDTO;
import com.efit.savaari.dto.TripsDTO;
import com.efit.savaari.dto.TvehicleDTO;
import com.efit.savaari.dto.TvehicleDocumentResponseDTO;
import com.efit.savaari.dto.TvehicleResponseDTO;
import com.efit.savaari.dto.VendorInvoiceChargesDTO;
import com.efit.savaari.dto.VendorInvoiceDTO;
import com.efit.savaari.dto.VendorInvoiceTripsDetailsDTO;
import com.efit.savaari.dto.VendorInvoiceTripsDocumentDTO;
import com.efit.savaari.entity.ApprovedQuoteVO;
import com.efit.savaari.entity.AuctionsVO;
import com.efit.savaari.entity.CustomerBookingRequestVO;
import com.efit.savaari.entity.DocumentVO;
import com.efit.savaari.entity.DriverVO;
import com.efit.savaari.entity.LineItemsVO;
import com.efit.savaari.entity.NotificationVO;
import com.efit.savaari.entity.PayoutTripsDetailsVO;
import com.efit.savaari.entity.PayoutsDocumentVO;
import com.efit.savaari.entity.PayoutsTimeLineVO;
import com.efit.savaari.entity.PayoutsVO;
import com.efit.savaari.entity.PayoutsVendorDetailsVO;
import com.efit.savaari.entity.QuoteVO;
import com.efit.savaari.entity.RequestforQuotesVO;
import com.efit.savaari.entity.TaggingVO;
import com.efit.savaari.entity.TdriverDocumentsVO;
import com.efit.savaari.entity.TdriverVO;
import com.efit.savaari.entity.TripAlertsVO;
import com.efit.savaari.entity.TripGeofenceAlertsVO;
import com.efit.savaari.entity.TripReportMisRolesVO;
import com.efit.savaari.entity.TripReportMisVO;
import com.efit.savaari.entity.TripsDLVerificationVO;
import com.efit.savaari.entity.TripsPitStopVO;
import com.efit.savaari.entity.TripsVO;
import com.efit.savaari.entity.TvehicleDocumentsVO;
import com.efit.savaari.entity.TvehicleVO;
import com.efit.savaari.entity.UserVO;
import com.efit.savaari.entity.VehicleVO;
import com.efit.savaari.entity.VendorInvoiceChargesVO;
import com.efit.savaari.entity.VendorInvoiceTripsDetailsVO;
import com.efit.savaari.entity.VendorInvoiceTripsDocumentVO;
import com.efit.savaari.entity.VendorInvoiceVO;
import com.efit.savaari.entity.VendorVO;
import com.efit.savaari.exception.ApplicationException;
import com.efit.savaari.repo.ApprovedQuoteRepo;
import com.efit.savaari.repo.AuctionsRepo;
import com.efit.savaari.repo.CustomerBookingRequestRepo;
import com.efit.savaari.repo.DocumentRepo;
import com.efit.savaari.repo.DriverRepo;
import com.efit.savaari.repo.LineItemsRepo;
import com.efit.savaari.repo.NotificationRepo;
import com.efit.savaari.repo.PayoutsDocumentRepo;
import com.efit.savaari.repo.PayoutsRepo;
import com.efit.savaari.repo.PayoutsTimeLineRepo;
import com.efit.savaari.repo.PayoutsTripsDetailsRepo;
import com.efit.savaari.repo.PayoutsVendorDetailsRepo;
import com.efit.savaari.repo.QuoteRepo;
import com.efit.savaari.repo.RequestforQuotesRepo;
import com.efit.savaari.repo.TaggingRepo;
import com.efit.savaari.repo.TdriverDocumentsRepo;
import com.efit.savaari.repo.TdriverRepo;
import com.efit.savaari.repo.TripAlertsRepo;
import com.efit.savaari.repo.TripGeofenceAlertsRepo;
import com.efit.savaari.repo.TripReportMisRepo;
import com.efit.savaari.repo.TripReportMisRolesRepo;
import com.efit.savaari.repo.TripsDLVerificationRepo;
import com.efit.savaari.repo.TripsPitStopRepo;
import com.efit.savaari.repo.TripsRepo;
import com.efit.savaari.repo.TvehicleDocumentsRepo;
import com.efit.savaari.repo.TvehicleRepo;
import com.efit.savaari.repo.UserRepo;
import com.efit.savaari.repo.VehicleRepo;
import com.efit.savaari.repo.VendorInvoiceChargesRepo;
import com.efit.savaari.repo.VendorInvoiceRepo;
import com.efit.savaari.repo.VendorInvoiceTripsDetailsRepo;
import com.efit.savaari.repo.VendorInvoiceTripsDocumentRepo;
import com.efit.savaari.repo.VendorRepo;

@Service
public class TransactionServiceImpl implements TransactionService {

	public static final Logger LOGGER = LoggerFactory.getLogger(TransactionServiceImpl.class);

	@Autowired
	TripsRepo tripsRepo;

	@Autowired
	PaginationService paginationService;

	@Autowired
	CustomerBookingRequestRepo customerBookingRequestRepo;

	@Autowired
	PayoutsVendorDetailsRepo payoutsVendorDetailsRepo;

	@Autowired
	PayoutsRepo payoutsRepo;

	@Autowired
	PayoutsTripsDetailsRepo payoutsTripsDetailsRepo;

	@Autowired
	PayoutsTimeLineRepo payoutsTimeLineRepo;

	@Autowired
	PayoutsDocumentRepo payoutsDocumentRepo;

	@Autowired
	RequestforQuotesRepo requestforQuotesRepo;

	@Autowired
	LineItemsRepo lineItemsRepo;

	@Autowired
	TripsDLVerificationRepo tripsDLVerificationRepo;

	@Autowired
	TripsPitStopRepo tripsPitStopRepo;

	@Autowired
	AuctionsRepo auctionsRepo;

	@Autowired
	TripReportMisRepo tripReportMisRepo;

	@Autowired
	TaggingRepo taggingRepo;

	@Autowired
	TripReportMisRolesRepo tripReportMisRolesRepo;

	@Autowired
	TripGeofenceAlertsRepo tripGeofenceAlertsRepo;

	@Autowired
	TripAlertsRepo tripAlertsRepo;

	@Autowired
	VehicleRepo vehicleRepo;

	@Autowired
	DriverRepo driverRepo;

	@Autowired
	QuoteRepo quoteRepo;

	@Autowired
	VendorRepo vendorRepo;

	@Autowired
	TvehicleRepo tvehicleRepo;

	@Autowired
	TvehicleDocumentsRepo tvehicleDocumentsRepo;

	@Autowired
	TdriverRepo tdriverRepo;

	@Autowired
	TdriverDocumentsRepo tdriverDocumentsRepo;

	@Autowired
	UserRepo userRepo;

	@Autowired
	ApprovedQuoteRepo approvedQuoteRepo;

	@Autowired
	VendorInvoiceTripsDocumentRepo vendorInvoiceTripsDocumentRepo;

	@Autowired
	VendorInvoiceRepo vendorInvoiceRepo;

	@Autowired
	VendorInvoiceChargesRepo vendorInvoiceChargesRepo;

	@Autowired
	VendorInvoiceTripsDetailsRepo vendorInvoiceTripsDetailsRepo;

	@Autowired
	DocumentRepo documentRepo;

	@Autowired
	NotificationService notificationService;

	@Autowired
	NotificationRepo notificationRepo;

	@Autowired
	EmailService emailService;

	@Override
	public Map<String, Object> createUpdateTrips(TripsDTO tripsDTO) throws Exception {

		TripsVO tripsVO;
		String message;

		if (tripsDTO.getId() != null) {
			tripsVO = tripsRepo.findById(tripsDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid Trips ID"));

//	        timeLineRepo.deleteAll(timeLineRepo.findByIndentsVO(indentsVO));
			tripsDLVerificationRepo.deleteAll(tripsDLVerificationRepo.findByTripsVO(tripsVO));
			tripsPitStopRepo.deleteAll(tripsPitStopRepo.findByTripsVO(tripsVO));

			tripsVO.setUpdatedBy(tripsDTO.getCreatedBy());
			message = "Trips Updated Successfully";

		} else {

			tripsVO = new TripsVO();
			tripsVO.setCreatedBy(tripsDTO.getCreatedBy());
			tripsVO.setUpdatedBy(tripsDTO.getCreatedBy());
			message = "Trips Created Successfully";
		}

		// map DTO to VO
		createUpdateTripsDTOByTripsVO(tripsDTO, tripsVO);

		tripsRepo.save(tripsVO);

		Map<String, Object> response = new HashMap<>();
		response.put("tripsVO", tripsVO);
		response.put("message", message);

		return response;
	}

	private void createUpdateTripsDTOByTripsVO(TripsDTO dto, TripsVO vo) {

		vo.setVendor(dto.getVendor());
		vo.setCustomer(dto.getCustomer());
		vo.setLrNumber(dto.getLrNumber());
		vo.setVehicleNumber(dto.getVehicleNumber());
		vo.setRoute(dto.getRoute());
		vo.setRouteTrip(dto.isRouteTrip());
		vo.setPitStop(dto.isPitStop());
		vo.setOrigin(dto.getOrigin());
		vo.setDestination(dto.getDestination());
		vo.setDriverNumber(dto.getDriverNumber());
		vo.setDriverName(dto.getDriverName());
		vo.setEta(dto.getEta());
		vo.setTatDays(dto.getTatDays());
//		vo.setStatus(dto.getStatus());
		vo.setMaterialType(dto.getMaterialType());
		vo.setVehicleType(dto.getVehicleType());
//		vo.setOverTimeHours(dto.getOverTimeHours());
		vo.setMaterialSqFt(dto.getMaterialSqFt());
		vo.setWeight(dto.getWeight());
		vo.setCreatedBy(dto.getCreatedBy());
		vo.setOrgId(dto.getOrgId());
		vo.setBranchCode(dto.getBranchCode());
		vo.setBranch(dto.getBranch());

		// ----------- TimeLine Mapping -----------
		List<TripsDLVerificationVO> tripsDLVerificationList = new ArrayList<>();

		if (dto.getTripsDLVerificationDTO() != null) {

			dto.getTripsDLVerificationDTO().forEach(u -> {

				TripsDLVerificationVO t = new TripsDLVerificationVO();

				t.setField(u.getField());
				t.setValue(u.getValue());

				t.setTripsVO(vo); // parent link

				tripsDLVerificationList.add(t);
			});
		}

		List<TripsPitStopVO> tripsPitStopList = new ArrayList<>();

		if (dto.getTripsPitStopDTO() != null) {

			dto.getTripsPitStopDTO().forEach(u -> {

				TripsPitStopVO t = new TripsPitStopVO();

				t.setPitstop(u.getPitstop());

				t.setTripsVO(vo); // parent link

				tripsPitStopList.add(t);
			});
		}

		// ----------- SET CHILD LISTS TO PARENT ENTITY -----------
		vo.setTripsDLVerificationVO(tripsDLVerificationList);
		vo.setTripsPitStopVO(tripsPitStopList);

	}

	@Override
	public TripsVO getTripsById(Long id) throws ApplicationException {
		return tripsRepo.findById(id).orElseThrow(() -> new ApplicationException("Trips not found"));
	}

	@Override
	public Map<String, Object> getTripsByOrgId(String branchCode, Long orgId, String search, int page, int count) {

		if (search != null) {
			search = search.trim();
			if (search.isEmpty()) {
				search = null;
			}
		}

		Pageable pageable = PageRequest.of(page - 1, count, Sort.by("customer").ascending());
		Page<TripsVO> indentsPage = tripsRepo.getTripsByOrgId(branchCode, orgId, search, pageable);

		// return paginated response
		return paginationService.buildResponse(indentsPage);

	}

	@Override
	public Map<String, Object> createUpdateCustomerBookingRequest(CustomerBookingRequestDTO customerBookingRequestDTO)
			throws Exception {

		CustomerBookingRequestVO customerBookingRequestVO;
		String message;

		if (customerBookingRequestDTO.getId() != null) {
			customerBookingRequestVO = customerBookingRequestRepo.findById(customerBookingRequestDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid customerBookingRequest ID"));

//	        timeLineRepo.deleteAll(timeLineRepo.findByIndentsVO(indentsVO));

			customerBookingRequestVO.setUpdatedBy(customerBookingRequestDTO.getCreatedBy());
			message = "CustomerBookingRequest Updated Successfully";

		} else {

			customerBookingRequestVO = new CustomerBookingRequestVO();
			customerBookingRequestVO.setCreatedBy(customerBookingRequestDTO.getCreatedBy());
			customerBookingRequestVO.setUpdatedBy(customerBookingRequestDTO.getCreatedBy());
			message = "customerBookingRequest Created Successfully";
		}

		// map DTO to VO
		createUpdateCustomerBookingRequestDTOByCustomerBookingRequestVO(customerBookingRequestDTO,
				customerBookingRequestVO);

		customerBookingRequestRepo.save(customerBookingRequestVO);

		Map<String, Object> response = new HashMap<>();
		response.put("customerBookingRequestVO", customerBookingRequestVO);
		response.put("message", message);

		return response;
	}

	private void createUpdateCustomerBookingRequestDTOByCustomerBookingRequestVO(CustomerBookingRequestDTO dto,
			CustomerBookingRequestVO vo) {

		vo.setCustomer(dto.getCustomer());
		vo.setOrigin(dto.getOrigin());
		vo.setDestination(dto.getDestination());
		vo.setStatus(dto.getStatus());
		vo.setVehicleType(dto.getVehicleType());
		vo.setNoOfVehicles(dto.getNoOfVehicles());
		vo.setPlacementDate(dto.getPlacementDate());
		vo.setOrderType(dto.getOrderType());
		vo.setServiceType(dto.getServiceType());
		vo.setOrderingParty(dto.getOrderingParty());
		vo.setMaterialType(dto.getMaterialType());
		vo.setBillToParty(dto.getBillToParty());
		vo.setDocketNo(dto.getDocketNo());
		vo.setOrgId(dto.getOrgId());
		vo.setBranchCode(dto.getBranchCode());
		vo.setBranchName(dto.getBranchName());
		vo.setActive(dto.isActive());
		vo.setRemarks(dto.getRemarks());
	}

	@Override
	public CustomerBookingRequestVO getCustomerBookingRequestById(Long id) throws ApplicationException {
		return customerBookingRequestRepo.findById(id)
				.orElseThrow(() -> new ApplicationException("CustomerBookingRequest not found"));
	}

	@Override
	public Map<String, Object> getCustomerBookingRequestByOrgId(String branchCode, Long orgId, String search, int page,
			int count) {

		if (search != null) {
			search = search.trim();
			if (search.isEmpty()) {
				search = null;
			}
		}

		Pageable pageable = PageRequest.of(page - 1, count, Sort.by("customer").ascending());
		Page<CustomerBookingRequestVO> customerBookingRequestPage = customerBookingRequestRepo
				.getCustomerBookingRequestByOrgId(branchCode, orgId, search, pageable);

		// return paginated response
		return paginationService.buildResponse(customerBookingRequestPage);
	}

	@Override
	public Map<String, Object> createUpdatePayouts(PayoutsDTO dto, MultipartFile invoiceFile,
			List<MultipartFile> documents) throws Exception {

		PayoutsVO vo;
		String message;

		// =========== UPDATE MODE ===========
		if (dto.getId() != null) {

			vo = payoutsRepo.findById(dto.getId()).orElseThrow(() -> new ApplicationException("Invalid Payouts ID"));

			// Delete child records
			payoutsVendorDetailsRepo.deleteAll(payoutsVendorDetailsRepo.findByPayoutsVO(vo));
			payoutsTimeLineRepo.deleteAll(payoutsTimeLineRepo.findByPayoutsVO(vo));
			payoutsDocumentRepo.deleteAll(payoutsDocumentRepo.findByPayoutsVO(vo));
			payoutsTripsDetailsRepo.deleteAll(payoutsTripsDetailsRepo.findByPayoutsVO(vo));

			vo.setUpdatedBy(dto.getCreatedBy());
			message = "Payouts Updated Successfully";

		} else {

			// =========== CREATE MODE ===========
			vo = new PayoutsVO();
			vo.setCreatedBy(dto.getCreatedBy());
			vo.setUpdatedBy(dto.getCreatedBy());
			message = "Payouts Created Successfully";
		}

		// Map main table fields
		createUpdatePayoutsDTOByPayoutsVO(dto, vo);

		// =========== MAIN TABLE FILE UPLOAD ===========
		if (invoiceFile != null && !invoiceFile.isEmpty()) {
			vo.setInvoiceFile(invoiceFile.getBytes());
		}

		// =========== CHILD TABLE DOCUMENT (file + header) ===========
		List<PayoutsDocumentVO> docList = new ArrayList<>();

		List<PayoutsDocumentDTO> headerList = dto.getPayoutsDocumentDTO(); // headers from JSON

		if (documents != null && headerList != null) {

			if (documents.size() != headerList.size()) {
				throw new ApplicationException("Document count mismatch");
			}

			for (int i = 0; i < documents.size(); i++) {

				MultipartFile file = documents.get(i);
				PayoutsDocumentDTO header = headerList.get(i);

				PayoutsDocumentVO doc = new PayoutsDocumentVO();

				// File
				doc.setDocument(file.getBytes());

				// Header fields
				doc.setDocumentType(header.getDocumentType());
				doc.setRemarks(header.getRemarks());
				doc.setTrip(header.getTrip());
				doc.setVehicleNumber(header.getVehicleNumber());

				doc.setPayoutsVO(vo);
				docList.add(doc);
			}
		}

		vo.setPayoutsDocumentVO(docList);

		// =========== SAVE MAIN + CHILD TABLES ===========
		payoutsRepo.save(vo);

		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("payoutsVO", vo);

		return response;
	}

	private void createUpdatePayoutsDTOByPayoutsVO(PayoutsDTO dto, PayoutsVO vo) {

		vo.setNamingSeries(dto.getNamingSeries());
		vo.setPayoutStatus(dto.getPayoutStatus());
		vo.setVendor(dto.getVendor());
		vo.setPurpose(dto.getPurpose());
		vo.setApproveBy(dto.getApproveBy());
		vo.setApproveOn(dto.getApproveOn());
		vo.setInvoiceAmount(dto.getInvoiceAmount());
		vo.setQuantity(dto.getQuantity());
		vo.setTotalAdditionalCharges(dto.getTotalAdditionalCharges());
		vo.setTotalTripCost(dto.getTotalTripCost());
		vo.setPayoutReference(dto.getPayoutReference());
		vo.setInvoice(dto.getInvoice());
		vo.setInvoiceType(dto.getInvoiceType());
		vo.setFromDate(dto.getFromDate());
		vo.setToDate(dto.getToDate());
		vo.setCreatedBy(dto.getCreatedBy());
		vo.setOrgId(dto.getOrgId());
		vo.setBranchCode(dto.getBranchCode());
		vo.setBranch(dto.getBranch());

		// ===== Vendor Details =====
		List<PayoutsVendorDetailsVO> vendorList = new ArrayList<>();
		if (dto.getPayoutsVendorDetailsDTO() != null) {
			dto.getPayoutsVendorDetailsDTO().forEach(d -> {
				PayoutsVendorDetailsVO v = new PayoutsVendorDetailsVO();
				v.setPurpose(d.getPurpose());
				v.setAmount(d.getAmount());
				v.setRemarks(d.getRemarks());
				v.setTrip(d.getTrip());
				v.setStatus(d.getStatus());
				v.setCreatePayouts(d.isCreatePayouts());
				v.setPayoutsVO(vo);
				vendorList.add(v);
			});
		}
		vo.setPayoutsVendorDetailsVO(vendorList);

		// ===== Timeline =====
		List<PayoutsTimeLineVO> timeLineList = new ArrayList<>();
		if (dto.getPayoutsTimeLineDTO() != null) {
			dto.getPayoutsTimeLineDTO().forEach(t -> {
				PayoutsTimeLineVO tl = new PayoutsTimeLineVO();
				tl.setStatus(t.getStatus());
				tl.setTimeRecorded(t.getTimeRecorded());
				tl.setPayoutsVO(vo);
				timeLineList.add(tl);
			});
		}
		vo.setPayoutsTimeLineVO(timeLineList);

		// ===== Trips =====
		List<PayoutTripsDetailsVO> tripList = new ArrayList<>();
		if (dto.getPayoutTripsDetailsDTO() != null) {
			dto.getPayoutTripsDetailsDTO().forEach(t -> {
				PayoutTripsDetailsVO trip = new PayoutTripsDetailsVO();
				trip.setTrips(t.getTrips());
				trip.setOrigin(t.getOrigin());
				trip.setDestination(t.getDestination());
				trip.setVehicle(t.getVehicle());
				trip.setStatus(t.getStatus());
				trip.setPayoutsVO(vo);
				tripList.add(trip);
			});
		}
		vo.setPayoutTripsDetailsVO(tripList);
	}

	@Override
	public PayoutsVO getPayoutsById(Long id) throws ApplicationException {
		return payoutsRepo.findById(id).orElseThrow(() -> new ApplicationException("Payouts not found"));
	}

	@Override
	public Map<String, Object> getPayoutsByOrgId(String branchCode, Long orgId, String search, int page, int count) {

		if (search != null) {
			search = search.trim();
			if (search.isEmpty()) {
				search = null;
			}
		}

		Pageable pageable = PageRequest.of(page - 1, count, Sort.by("namingseries").ascending());
		Page<PayoutsVO> payoutsPage = payoutsRepo.getPayoutsByOrgId(branchCode, orgId, search, pageable);

		// return paginated response
		return paginationService.buildResponse(payoutsPage);

	}

	@Override
	@Transactional
	public Map<String, Object> createUpdateRequestforQuotes(RequestforQuotesDTO dto, MultipartFile documents)
			throws Exception {

		RequestforQuotesVO vo;
		String message;

		// ====================== UPDATE ======================
		if (dto.getId() != null) {

			vo = requestforQuotesRepo.findById(dto.getId())
					.orElseThrow(() -> new ApplicationException("Invalid RFQ ID"));

			// Delete old child rows
			lineItemsRepo.deleteAll(lineItemsRepo.findByRequestforQuotesVO(vo));

			vo.setUpdatedBy(dto.getCreatedBy());
			message = "RFQ Updated Successfully";

		} else {

			// ====================== CREATE ======================
			vo = new RequestforQuotesVO();
			vo.setCreatedBy(dto.getCreatedBy());
			vo.setUpdatedBy(dto.getCreatedBy());
			message = "RFQ Created Successfully";
		}

		// ======== MAP BASIC FIELDS ========
		mapRFQBasicFields(dto, vo);

		// ======== SAVE MAIN DOCUMENT (single file) ========
		if (documents != null && !documents.isEmpty()) {
			vo.setDocuments(documents.getBytes());
		}

		// ======== CHILD TABLE — LINE ITEMS ========
		List<LineItemsVO> lineItemsList = new ArrayList<>();

		if (dto.getLineItemsDTO() != null) {
			dto.getLineItemsDTO().forEach(li -> {

				LineItemsVO item = new LineItemsVO();
				item.setOrigin(li.getOrigin());
				item.setDestination(li.getDestination());
				item.setVechileType(li.getVechileType());
				item.setRound(li.getRound());
				item.setZone(li.getZone());
				item.setWay(li.getWay());

				item.setRequestforQuotesVO(vo);
				lineItemsList.add(item);
			});
		}

		vo.setLineItemsVO(lineItemsList);

		// ======== SAVE MASTER + CHILD ========
		requestforQuotesRepo.save(vo);

		// ======== RESPONSE ========
		Map<String, Object> response = new HashMap<>();
		response.put("requestforQuotesVO", vo);
		response.put("message", message);

		return response;
	}

	private void mapRFQBasicFields(RequestforQuotesDTO dto, RequestforQuotesVO vo) {

		vo.setRfqDetails(dto.getRfqDetails());
		vo.setNatureOfContract(dto.getNatureOfContract());
		vo.setMaterial(dto.getMaterial());
		vo.setContractStartDate(dto.getContractStartDate());
		vo.setContractDuration(dto.getContractDuration());
		vo.setTermsAndConditions(dto.getTermsAndConditions());
		vo.setVendorTags(dto.getVendorTags());
		vo.setNumTransporter(dto.getNumTransporter());
		vo.setAdditionalCharges(dto.getAdditionalCharges());

		vo.setBranch(dto.getBranch());
		vo.setBranchCode(dto.getBranchCode());
		vo.setOrgId(dto.getOrgId());
	}

	@Override
	public RequestforQuotesVO getRequestforQuotesById(Long id) throws ApplicationException {
		return requestforQuotesRepo.findById(id)
				.orElseThrow(() -> new ApplicationException("RequestforQuotes not found"));
	}

	@Override
	public Map<String, Object> getRequestforQuotesByOrgId(String branchCode, String search, int page, int count) {

		if (search != null) {
			search = search.trim();
			if (search.isEmpty()) {
				search = null;
			}
		}

		Pageable pageable = PageRequest.of(page - 1, count, Sort.by("rfqdetails").ascending());
		Page<RequestforQuotesVO> requestforQuotesPage = requestforQuotesRepo.getRequestforQuotesByOrgId(branchCode,
				search, pageable);

		// return paginated response
		return paginationService.buildResponse(requestforQuotesPage);

	}

	@Override
	@Transactional
	public Map<String, Object> uploadLineItemsExcel(Long rfqId, MultipartFile file, String createdBy) throws Exception {

		if (file == null || file.isEmpty()) {
			throw new ApplicationException("Uploaded Excel is empty");
		}

		RequestforQuotesVO rfqVO = requestforQuotesRepo.findById(rfqId)
				.orElseThrow(() -> new ApplicationException("Invalid RFQ ID"));

		// Delete old line items
//	    lineItemsRepo.deleteAll(lineItemsRepo.findByRequestforQuotesVO(rfqVO));

		List<LineItemsVO> lineItemsList = new ArrayList<>();

		// ========== Read Excel File ==========
		try (InputStream is = file.getInputStream(); Workbook workbook = WorkbookFactory.create(is)) {

			Sheet sheet = workbook.getSheetAt(0);

			int rowNumber = 0;

			for (Row row : sheet) {

				// Skip header row
				if (rowNumber++ == 0)
					continue;

				LineItemsVO item = new LineItemsVO();

				item.setOrigin(getCellValue(row.getCell(0)));
				item.setDestination(getCellValue(row.getCell(1)));
				item.setVechileType(getCellValue(row.getCell(2)));
				item.setRound(Integer.parseInt(getCellValue(row.getCell(3))));
				item.setZone(getCellValue(row.getCell(4)));
				item.setWay(getCellValue(row.getCell(5)));

				item.setRequestforQuotesVO(rfqVO);

				lineItemsList.add(item);
			}
		}

		// Attach to parent
		rfqVO.setLineItemsVO(lineItemsList);

		// Save
		requestforQuotesRepo.save(rfqVO);

		Map<String, Object> response = new HashMap<>();
		response.put("message", "Line Items uploaded successfully");
		response.put("uploadedCount", lineItemsList.size());
		response.put("rfqVO", rfqVO);

		return response;
	}

	private String getCellValue(Cell cell) {
		if (cell == null)
			return "";
		switch (cell.getCellType()) {
		case STRING:
			return cell.getStringCellValue().trim();
		case NUMERIC:
			return String.valueOf((int) cell.getNumericCellValue());
		case BOOLEAN:
			return String.valueOf(cell.getBooleanCellValue());
		default:
			return "";
		}
	}

	@Override
	@Transactional
	public Map<String, Object> createUpdateAuctions(AuctionsDTO dto, MultipartFile documents) throws Exception {

		AuctionsVO vo;
		String message;

		// ===================================== UPDATE
		// =====================================
		if (dto.getId() != null) {

			vo = auctionsRepo.findById(dto.getId()).orElseThrow(() -> new ApplicationException("Invalid Auctions ID"));

			vo.setUpdatedBy(dto.getCreatedBy());
			message = "Auctions Updated Successfully";

		} else {

			// ===================================== CREATE
			// =====================================
			vo = new AuctionsVO();
			vo.setCreatedBy(dto.getCreatedBy());
			vo.setUpdatedBy(dto.getCreatedBy());
			message = "Auctions Created Successfully";
		}

		// ===================================== MAP BASIC FIELDS
		// =====================================
		mapAuctionsDTOFromAuctionsVO(dto, vo);

		// ===================================== FILE UPLOAD
		// =====================================
		if (documents != null && !documents.isEmpty()) {
			vo.setDocuments(documents.getBytes());
		}
		if (dto.getUserId() != null) {
			UserVO user = userRepo.findById(dto.getUserId())
					.orElseThrow(() -> new ApplicationException("Invalid User ID"));
			vo.setUser(user);
		}

		// ===================================== SAVE
		// =====================================
		auctionsRepo.save(vo);

		List<Long> id = userRepo.findEligibleUserForQuotes(vo.getOrgId());
		List<NotificationVO> n = new ArrayList<>();

		StringBuilder bccEmailIds = new StringBuilder();

		for (Long userId : id) {
			NotificationVO n1 = new NotificationVO();
			Optional<UserVO> uvo = userRepo.findById(userId);
			if (!uvo.isPresent()) {
				continue; // safety
			}

			UserVO vo1 = uvo.get();

			// ---- collect BCC emails ----
			if (bccEmailIds.length() > 0) {
				bccEmailIds.append(",");
			}
			bccEmailIds.append(vo1.getUserName());
			n1.setUserid(userId);
			n1.setAuctionsid(vo.getId());
			n1.setMessage(vo.getOrganizationName() + " ,Auction ID: " + vo.getId() + " Bid and Win...");
			n1.setNotificationType("New Auction");
			n.add(n1);
		}
		notificationRepo.saveAll(n);

		String htmlBody = buildAuctionEmailHtml(vo);
		emailService.sendAuctionMail(bccEmailIds.toString(), htmlBody);

		// ===================================== RESPONSE
		// =====================================
		Map<String, Object> response = new HashMap<>();
		response.put("auctionsVO", vo);
		response.put("message", message);

		return response;
	}

	private String buildAuctionEmailHtml(AuctionsVO vo) {
		try {
			ClassPathResource resource = new ClassPathResource("templates/New_Auction.html");

			String html = new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a");

			String formattedStartDate = vo.getStartDate().formatted(formatter);
			String loadDate = vo.getLoadingDate().toString();
			DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");

			String formattedLoadingTime = vo.getLoadingTime() != null ? vo.getLoadingTime().formatted(timeFormatter)
					: "";

			return html.replace("${organizationName}", vo.getOrganizationName())
					.replace("${auctionType}", vo.getAuctionsType()).replace("${bidType}", vo.getBidType())
					.replace("${loadingDate}", loadDate).replace("${loading}", vo.getLoading())
					.replace("${loadingTime}", formattedLoadingTime).replace("${unloading}", vo.getUnloading())
					.replace("${material}", vo.getMaterial())
					.replace("${materialQuantity}", String.valueOf(vo.getMaterialQuantity()))
					.replace("${vehicle}", vo.getVehicle())
					.replace("${vehicleQuantity}", String.valueOf(vo.getVehicleQuantity()))
					.replace("${minPrice}", String.valueOf(vo.getMinPrice()))
					.replace("${maxPrice}", String.valueOf(vo.getMaxPrice()))
					.replace("${startDate}", formattedStartDate);

		} catch (Exception e) {
			throw new RuntimeException("Failed to load auction email template", e);
		}
	}

	private void mapAuctionsDTOFromAuctionsVO(AuctionsDTO dto, AuctionsVO vo) {

		vo.setAuctionsType(dto.getAuctionsType());
		vo.setRoundTrip(dto.isRoundTrip());
		vo.setCustomGeofence(dto.isCustomGeofence());
		vo.setLoading(dto.getLoading());
		vo.setUnloading(dto.getUnloading());
		vo.setVehicle(dto.getVehicle());
		vo.setVehicleQuantity(dto.getVehicleQuantity());
		vo.setOrganizationName(dto.getOrganizationName());
		vo.setLoadingDate(dto.getLoadingDate());
		vo.setLoadingTime(dto.getLoadingTime());
		vo.setUnloadingDate(dto.getUnloadingDate());
		vo.setDuration(dto.getDuration());
		vo.setStartDate(dto.getStartDate());
		vo.setEndDate(dto.getEndDate());
		vo.setMaterial(dto.getMaterial());
		vo.setMaterialQuantity(dto.getMaterialQuantity());
		vo.setMaterialWeight(dto.getMaterialWeight());
		vo.setWeightUnit(dto.getWeightUnit());
		vo.setDimension(dto.getDimension());
		vo.setTransporterTag(dto.getTransporterTag());
		vo.setNumTransporter(dto.getNumTransporter());
		vo.setExcludeTransporters(dto.getExcludeTransporters());
		vo.setShortCuts(dto.getShortCuts());
		vo.setBidType(dto.getBidType());
		vo.setMinBidDifferent(dto.getMinBidDifferent());
		vo.setAllowedBits(dto.getAllowedBits());
		vo.setMaxPrice(dto.getMaxPrice());
		vo.setMinPrice(dto.getMinPrice());
		vo.setAdditionalCharges(dto.getAdditionalCharges());
		vo.setTerms(dto.getTerms());
		vo.setBranch(dto.getBranch());
		vo.setBranchCode(dto.getBranchCode());
		vo.setOrgId(dto.getOrgId());
	}

	@Override
	public AuctionsVO getAuctionsById(Long id) throws ApplicationException {
		return auctionsRepo.findById(id).orElseThrow(() -> new ApplicationException("Auctions not found"));
	}

	@Override
	public Map<String, Object> getAuctionsByOrgId(String branchCode, Long orgId, String search, int page, int count) {

		if (search != null) {
			search = search.trim();
			if (search.isEmpty()) {
				search = null;
			}
		}

		Pageable pageable = PageRequest.of(page - 1, count, Sort.by("auctionstype").ascending());
		Page<AuctionsVO> auctionsPage = auctionsRepo.getAuctionsByOrgId(branchCode, orgId, search, pageable);

		// return paginated response
		return paginationService.buildResponse(auctionsPage);

	}

	@Override
	@Transactional
	public Map<String, Object> createUpdateTagging(TaggingDTO dto) throws Exception {

		TaggingVO vo;
		String message;

		// ================== UPDATE ==================
		if (dto.getId() != null) {

			vo = taggingRepo.findById(dto.getId()).orElseThrow(() -> new ApplicationException("Invalid Tagging ID"));

			vo.setUpdatedBy(dto.getCreatedBy());
			message = "Tagging Updated Successfully";

		} else {

			// ================== CREATE ==================
			vo = new TaggingVO();
			vo.setCreatedBy(dto.getCreatedBy());
			vo.setUpdatedBy(dto.getCreatedBy());
			message = "Tagging Created Successfully";
		}

		// ================== MAP FIELDS ==================
		mapTaggingDtoToVo(dto, vo);

		// ================== SAVE ==================
		taggingRepo.save(vo);

		// ================== RESPONSE ==================
		Map<String, Object> response = new HashMap<>();
		response.put("taggingVO", vo);
		response.put("message", message);

		return response;
	}

	private void mapTaggingDtoToVo(TaggingDTO dto, TaggingVO vo) {
		vo.setName(dto.getName());
		vo.setDescription(dto.getDescription());
		vo.setOrgId(dto.getOrgId());
		vo.setBranch(dto.getBranch());
		vo.setBranchCode(dto.getBranchCode());
	}

	@Override
	public TaggingVO getTaggingById(Long id) throws ApplicationException {
		return taggingRepo.findById(id).orElseThrow(() -> new ApplicationException("Tagging not found"));
	}

	@Override
	public Map<String, Object> getTaggingByOrgId(String branchCode, String search, int page, int count) {

		if (search != null) {
			search = search.trim();
			if (search.isEmpty()) {
				search = null;
			}
		}

		Pageable pageable = PageRequest.of(page - 1, count, Sort.by("name").ascending());
		Page<TaggingVO> taggingPage = taggingRepo.getTaggingByOrgId(branchCode, search, pageable);

		// return paginated response
		return paginationService.buildResponse(taggingPage);

	}

	// tripreportmis

	@Override
	@Transactional
	public Map<String, Object> createUpdateTripReportMis(TripReportMisDTO dto) throws Exception {

		TripReportMisVO vo;
		String message;

		// ====================== UPDATE ======================
		if (dto.getId() != null) {

			vo = tripReportMisRepo.findById(dto.getId())
					.orElseThrow(() -> new ApplicationException("Invalid TripReportMis ID"));

			// Delete old child roles
			tripReportMisRolesRepo.deleteAll(tripReportMisRolesRepo.findByTripReportMisVO(vo));

			vo.setUpdatedBy(dto.getCreatedBy());
			message = "Trip Report MIS Updated Successfully";

		} else {

			// ====================== CREATE ======================
			vo = new TripReportMisVO();
			vo.setCreatedBy(dto.getCreatedBy());
			vo.setUpdatedBy(dto.getCreatedBy());
			message = "Trip Report MIS Created Successfully";
		}

		// ======== MAP MAIN FIELDS ========
		mapTripReportMisFields(dto, vo);

		// ======== CHILD – ROLES ========
		List<TripReportMisRolesVO> roleList = new ArrayList<>();

		if (dto.getTripReportMisRolesDTO() != null) {

			dto.getTripReportMisRolesDTO().forEach(roleDTO -> {

				TripReportMisRolesVO role = new TripReportMisRolesVO();
				role.setRole(roleDTO.getRole());
				role.setTripReportMisVO(vo);

				roleList.add(role);
			});
		}

		vo.setTripReportMisRolesVO(roleList);

		// ======== SAVE MASTER + CHILD ========
		tripReportMisRepo.save(vo);

		// ======== RESPONSE ========
		Map<String, Object> response = new HashMap<>();
		response.put("tripReportMisVO", vo);
		response.put("message", message);

		return response;
	}

	private void mapTripReportMisFields(TripReportMisDTO dto, TripReportMisVO vo) {

		vo.setRefDocType(dto.getRefDocType());
		vo.setReportType(dto.getReportType());
		vo.setReferenceReport(dto.getReferenceReport());
		vo.setIsStandard(dto.getIsStandard());
		vo.setModule(dto.getModule());
		vo.setAddTotalRow(dto.isAddTotalRow());
		vo.setDisabled(dto.isDisabled());
		vo.setComment(dto.getComment());

		vo.setOrgId(dto.getOrgId());
		vo.setBranchCode(dto.getBranchCode());
		vo.setBranch(dto.getBranch());
	}

	@Override
	public TripReportMisVO getTripReportMisById(Long id) throws ApplicationException {
		return tripReportMisRepo.findById(id).orElseThrow(() -> new ApplicationException("TripReportMis not found"));
	}

	@Override
	public Map<String, Object> getTripReportMisByOrgId(String branchCode, String search, int page, int count) {

		if (search != null) {
			search = search.trim();
			if (search.isEmpty()) {
				search = null;
			}
		}

		Pageable pageable = PageRequest.of(page - 1, count, Sort.by("refdoctype").ascending());
		Page<TripReportMisVO> tripReportMisPage = tripReportMisRepo.getTripReportMisByOrgId(branchCode, search,
				pageable);

		// return paginated response
		return paginationService.buildResponse(tripReportMisPage);

	}

	@Override
	@Transactional
	public Map<String, Object> createUpdateTripGeofenceAlerts(TripGeofenceAlertsDTO dto) throws Exception {

		TripGeofenceAlertsVO vo;
		String message;

		if (dto.getId() != null) {

			vo = tripGeofenceAlertsRepo.findById(dto.getId())
					.orElseThrow(() -> new ApplicationException("Invalid Trip Geofence Alert ID"));

			vo.setUpdatedBy(dto.getCreatedBy());
			message = "Trip Geofence Alert Updated Successfully";

		} else {

			vo = new TripGeofenceAlertsVO();
			vo.setCreatedBy(dto.getCreatedBy());
			vo.setUpdatedBy(dto.getCreatedBy());
			message = "Trip Geofence Alert Created Successfully";
		}

		mapTripGeofenceAlertsFields(dto, vo);

		tripGeofenceAlertsRepo.save(vo);

		Map<String, Object> response = new HashMap<>();
		response.put("tripGeofenceAlertsVO", vo);
		response.put("message", message);

		return response;
	}

	private void mapTripGeofenceAlertsFields(TripGeofenceAlertsDTO dto, TripGeofenceAlertsVO vo)
			throws ApplicationException {

		// ===== TRIP FOREIGN KEY =====
		if (dto.getTrip() != null) {
			TripsVO trip = tripsRepo.findById(dto.getTrip())
					.orElseThrow(() -> new ApplicationException("Invalid Trips ID"));
			vo.setTrip(trip);
		}

		// ===== NORMAL FIELDS =====
		vo.setTag(dto.getTag());
		vo.setVehicle(dto.getVehicle());
		vo.setInvoiceType(dto.getInvoiceType());
		vo.setGeofence(dto.getGeofence());
		vo.setTimestamp(dto.getTimestamp());
		vo.setRemarks(vo.getRemarks());

		vo.setOrgId(dto.getOrgId());
		vo.setBranchCode(dto.getBranchCode());
		vo.setBranchName(dto.getBranchName());
		vo.setCancel(dto.isCancel());
		vo.setActive(dto.isActive());
	}

	@Override
	public TripGeofenceAlertsVO getTripGeofenceAlertsById(Long id) throws ApplicationException {
		return tripGeofenceAlertsRepo.findById(id)
				.orElseThrow(() -> new ApplicationException("TripGeofenceAlerts not found"));
	}

	@Override
	public Map<String, Object> getTripGeofenceAlertsByOrgId(String branchCode, Long orgId, String search, int page,
			int count) {

		if (search != null) {
			search = search.trim();
			if (search.isEmpty()) {
				search = null;
			}
		}

		Pageable pageable = PageRequest.of(page - 1, count, Sort.by("tag").ascending());
		Page<TripGeofenceAlertsVO> tripGeofenceAlertsPage = tripGeofenceAlertsRepo
				.getTripGeofenceAlertsByOrgId(branchCode, orgId, search, pageable);

		// return paginated response
		return paginationService.buildResponse(tripGeofenceAlertsPage);

	}

	@Override
	@Transactional
	public Map<String, Object> createUpdateTripAlerts(TripAlertsDTO dto) throws Exception {

		TripAlertsVO vo;
		String message;

		// ================= UPDATE =================
		if (dto.getId() != null) {

			vo = tripAlertsRepo.findById(dto.getId())
					.orElseThrow(() -> new ApplicationException("Invalid TripAlerts ID"));

			vo.setUpdatedBy(dto.getCreatedBy());
			message = "Trip Alerts Updated Successfully";

		} else {

			// ================= CREATE =================
			vo = new TripAlertsVO();
			vo.setCreatedBy(dto.getCreatedBy());
			vo.setUpdatedBy(dto.getCreatedBy());
			message = "Trip Alerts Created Successfully";
		}

		// =========== MAP FIELDS ===========
		mapTripAlertsDTOFromTripAlertsVO(dto, vo);

		// =========== SET FOREIGN KEYS ===========
		if (dto.getTrip() != null) {
			TripsVO trip = tripsRepo.findById(dto.getTrip())
					.orElseThrow(() -> new ApplicationException("Invalid Trips ID"));
			vo.setTrip(trip);
		}

		if (dto.getVehicle() != null) {
			VehicleVO vehicle = vehicleRepo.findById(dto.getVehicle())
					.orElseThrow(() -> new ApplicationException("Invalid Vehicle ID"));
			vo.setVehicle(vehicle);
		}

		if (dto.getDriver() != null) {
			DriverVO driver = driverRepo.findById(dto.getDriver())
					.orElseThrow(() -> new ApplicationException("Invalid Driver ID"));
			vo.setDriver(driver);
		}

		// =========== SAVE ===========
		tripAlertsRepo.save(vo);

		// =========== RESPONSE ===========
		Map<String, Object> response = new HashMap<>();
		response.put("tripAlertsVO", vo);
		response.put("message", message);

		return response;
	}

	private void mapTripAlertsDTOFromTripAlertsVO(TripAlertsDTO dto, TripAlertsVO vo) {

		vo.setFromDate(dto.getFromDate());
		vo.setToDate(dto.getToDate());
		vo.setType(dto.getType());
		vo.setExtra(dto.getExtra());
		vo.setActive(dto.isActive());
		vo.setOrgId(dto.getOrgId());
		vo.setBranchCode(dto.getBranchCode());
		vo.setBranch(dto.getBranch());
	}

	@Override
	public TripAlertsVO getTripAlertsById(Long id) throws ApplicationException {
		return tripAlertsRepo.findById(id).orElseThrow(() -> new ApplicationException("TripAlerts not found"));
	}

	@Override
	public Map<String, Object> getTripAlertsByOrgId(String branchCode, Long orgId, String search, int page, int count) {

		if (search != null) {
			search = search.trim();
			if (search.isEmpty()) {
				search = null;
			}
		}

		Pageable pageable = PageRequest.of(page - 1, count, Sort.by("type").ascending());
		Page<TripAlertsVO> tripAlertsPage = tripAlertsRepo.getTripAlertsByOrgId(branchCode, orgId, search, pageable);

		// return paginated response
		return paginationService.buildResponse(tripAlertsPage);

	}

	// AuctionsReport
	@Override
	public Map<String, Object> getAuctionsReportByOrgId(Long userId, String search, int page, int count) {

		Pageable pageable = PageRequest.of(page - 1, count);
		Page<Object[]> pageData = auctionsRepo.getAuctionsReportByOrgId(userId, search, pageable);

		List<AuctionsVO> finalList = new ArrayList<>();

		for (Object[] row : pageData.getContent()) {

			AuctionsVO vo = new AuctionsVO();

			vo.setId(getLong(row[0]));
			vo.setAuctionsType((String) row[1]);
			vo.setRoundTrip(getBoolean(row[2]));
			vo.setCustomGeofence(getBoolean(row[3]));
			vo.setLoading((String) row[4]);
			vo.setUnloading((String) row[5]);
			vo.setVehicle((String) row[6]);
			vo.setVehicleQuantity(getInt(row[7]));
			vo.setLoadingDate(toLocalDate(row[8]));
			vo.setLoadingTime((String) row[9]);
			vo.setUnloadingDate(toLocalDate(row[10]));
			vo.setDuration(getInt(row[11]));
			vo.setStartDate((String) row[12]);
			vo.setEndDate((String) row[13]);
			vo.setMaterial((String) row[14]);
			vo.setMaterialQuantity(getInt(row[15]));
			vo.setMaterialWeight((BigDecimal) row[16]);
			vo.setWeightUnit((String) row[17]);
			vo.setDimension(getDouble(row[18]));
			vo.setTransporterTag((String) row[19]);
			vo.setNumTransporter(getInt(row[20]));
			vo.setExcludeTransporters((String) row[21]);
			vo.setShortCuts((String) row[22]);
			vo.setTerms((String) row[23]);
			vo.setDocuments((byte[]) row[24]);
			vo.setOrganizationName((String) row[25]);
			vo.setBidType((String) row[26]);
			vo.setMinBidDifferent(getInt(row[27]));
			vo.setAllowedBits((String) row[28]);
			vo.setMaxPrice((String) row[29]);
			vo.setMinPrice((String) row[30]);
			vo.setAdditionalCharges((String) row[31]);
			vo.setActive(getBoolean(row[32]));
			vo.setCreatedBy((String) row[33]);
			vo.setUpdatedBy((String) row[34]);
			vo.setOrgId(getLong(row[35]));
			vo.setBranchCode((String) row[36]);
			vo.setBranch((String) row[37]);
			vo.setScreenCode((String) row[38]);
			vo.setScreenName((String) row[39]);

			// ⭐ STATUS
			vo.setStatus(getInt(row[40]));
			vo.setAuctionStatus(getInt(row[41]));

			finalList.add(vo);
		}

		// 🔥 Convert to PageImpl so PaginationService works
		Page<AuctionsVO> convertedPage = new PageImpl<>(finalList, pageable, pageData.getTotalElements());

		// ✔ Now use your own service
		return paginationService.buildResponse(convertedPage);
	}

	private LocalDate toLocalDate(Object obj) {
		if (obj == null)
			return null;
		if (obj instanceof java.sql.Date)
			return ((java.sql.Date) obj).toLocalDate();
		return null;
	}

	private Long getLong(Object o) {
		return o == null ? null : ((Number) o).longValue();
	}

	private Integer getInt(Object o) {
		return o == null ? null : ((Number) o).intValue();
	}

	private Double getDouble(Object o) {
		return o == null ? null : ((Number) o).doubleValue();
	}

	private Boolean getBoolean(Object o) {
		if (o == null)
			return false;
		if (o instanceof Boolean)
			return (Boolean) o;
		return ((Number) o).intValue() == 1;
	}

	// quote
	@Override
	@Transactional
	public Map<String, Object> createUpdateQuote(QuoteDTO dto) throws Exception {

		QuoteVO vo;
		String message;

		if (dto.getId() != null) {

			vo = quoteRepo.findById(dto.getId()).orElseThrow(() -> new ApplicationException("Invalid Quote ID"));

			vo.setUpdatedBy(dto.getCreatedBy());
			message = "Quote Updated Successfully";

		} else {

			vo = new QuoteVO();
			vo.setCreatedBy(dto.getCreatedBy());
			vo.setUpdatedBy(dto.getCreatedBy());
			message = "Quote Created Successfully";
		}

		mapQuoteDTOAndQuoteVO(dto, vo);

//		if (dto.getVendorCode() != null) {
//			VendorVO vendor = vendorRepo.findByVendorCode(dto.getVendorCode())
//					.orElseThrow(() -> new ApplicationException("Invalid Vendor ID"));
//			vo.setVendor(vendor);
//		}

		if (dto.getUserCode() != null) {
			UserVO user = userRepo.findById(dto.getUserCode())
					.orElseThrow(() -> new ApplicationException("Invalid User ID"));
			vo.setUser(user);
		}

		if (dto.getAuction() != null) {

			// 1️⃣ Validate auction with custom query
			AuctionsVO auctionVO = auctionsRepo.validateAuctionActiveAndNotExpired(dto.getAuction());

			// 2️⃣ If query returns null -> invalid or expired
			if (auctionVO == null) {
				throw new ApplicationException("Auction is expired or inactive");
			}

			// 3️⃣ Set auction to VO
			vo.setAuction(auctionVO);
		}

		quoteRepo.save(vo);

		Optional<AuctionsVO> optionalAuction = auctionsRepo.findById(dto.getAuction());
		AuctionsVO auction = optionalAuction.get();
		Optional<UserVO> userVO = userRepo.findById(dto.getUserCode());

		UserVO uv = userVO.get();
		NotificationVO n1 = new NotificationVO();
		n1.setAuctionsid(auction.getId());
		n1.setUserid(auction.getUser().getId());
		n1.setMessage("A new quote has been submitted by " + uv.getOrganizationName() + " for your auction "
				+ auction.getId() + " . Please review the quote details.");
		n1.setNotificationType("Quote Received");
		notificationRepo.save(n1);

		Map<String, Object> response = new HashMap<>();
		response.put("quoteVO", vo);
		response.put("message", message);

		return response;
	}

	private void mapQuoteDTOAndQuoteVO(QuoteDTO dto, QuoteVO vo) {

		vo.setQuoteAmount(dto.getQuoteAmount());
		vo.setAdditionalNotes(dto.getAdditionalNotes());
		vo.setTermsAndConditions(dto.getTermsAndConditions());
		vo.setOrgId(dto.getOrgId());
		vo.setBranchCode(dto.getBranchCode());
		vo.setBranch(dto.getBranch());
		vo.setEstimatedDeliveryDate(dto.getEstimatedDeliveryDate());
//		vo.setVendor(dto.getVendorCode();

	}

	@Override
	public QuoteVO getQuoteById(Long id) throws ApplicationException {
		return quoteRepo.findById(id).orElseThrow(() -> new ApplicationException("Quote not found"));
	}

	@Override
	public Map<String, Object> getQuoteByUserId(Long userId, String search, int page, int count) {

		if (search != null) {
			search = search.trim();
			if (search.isEmpty()) {
				search = null;
			}
		}

		Pageable pageable = PageRequest.of(page - 1, count, Sort.by("quoteamount").ascending());
		Page<QuoteVO> quotePage = quoteRepo.getQuoteByUserId(userId, search, pageable);

		// return paginated response
		return paginationService.buildResponse(quotePage);

	}

	@Override
	public Map<String, Object> getUserAuctionsQuoteByOrgId(Long userId, String search, int page, int count) {

		if (search != null) {
			search = search.trim();
			if (search.isEmpty()) {
				search = null;
			}
		}

		Pageable pageable = PageRequest.of(page - 1, count, Sort.by("quoteamount").ascending());
		Page<QuoteVO> quotePage = quoteRepo.getUserAuctionsQuoteByOrgId(userId, search, pageable);

		// return paginated response
		return paginationService.buildResponse(quotePage);

	}

//	TVehicle

//	@Override
//	@Transactional
//	public Map<String, Object> createUpdateTvehicle(TvehicleDTO dto, List<MultipartFile> documents)
//			throws ApplicationException {

	@Value("${file.upload.path}")
	private String uploadBasePath;

	@Override
	@Transactional
	public Map<String, Object> createUpdateTvehicle(TvehicleDTO dto, MultipartFile[] rcFiles,
			MultipartFile[] insuranceFiles, MultipartFile[] fcFiles, MultipartFile[] permitFiles,
			MultipartFile[] pucFiles, MultipartFile[] otherFiles) throws ApplicationException {
		TvehicleVO vo;
		String message;

		// UPDATE
		if (ObjectUtils.isNotEmpty(dto.getId())) {

			vo = tvehicleRepo.findById(dto.getId()).orElseThrow(() -> new ApplicationException("Invalid Vehicle ID"));

//			tvehicleDocumentsRepo.deleteAll(tvehicleDocumentsRepo.findByTvehicleVO(vo));

			vo.setUpdatedBy(dto.getCreatedBy());
			message = "Vehicle Updated Successfully";

		} else {

			// CREATE
			vo = new TvehicleVO();
			vo.setCreatedBy(dto.getCreatedBy());
			vo.setUpdatedBy(dto.getCreatedBy());
			message = "Vehicle Created Successfully";
		}

		if (dto.getUserId() != null) {
			UserVO user = userRepo.findById(dto.getUserId())
					.orElseThrow(() -> new ApplicationException("Invalid User ID"));
			vo.setUser(user);
		}

		// BASIC MAPPING
		mapTvehicleDTOtoVO(dto, vo);
		vo = tvehicleRepo.save(vo);
		String vehicleNo = vo.getVehicleNumber();

		// 2️⃣ Base vehicle folder
		Path vehicleFolder = Paths.get(uploadBasePath, vehicleNo);
		createDirectory(vehicleFolder);

		replaceDocuments(vo, "RC", rcFiles, vehicleFolder);
		replaceDocuments(vo, "INSURANCE", insuranceFiles, vehicleFolder);
		replaceDocuments(vo, "FC", fcFiles, vehicleFolder);
		replaceDocuments(vo, "PERMIT", permitFiles, vehicleFolder);
		replaceDocuments(vo, "PUC", pucFiles, vehicleFolder);
		replaceDocuments(vo, "OTHER", otherFiles, vehicleFolder);
		// RESPONSE
		TvehicleResponseDTO tvehicleResponseDTO = mapToResponseDTO(vo);
		Map<String, Object> response = new HashMap<>();
		response.put("tvehicleVO", tvehicleResponseDTO);
		response.put("message", message);

		return response;
	}

	public TvehicleResponseDTO mapToResponseDTO(TvehicleVO vehicle) {

		TvehicleResponseDTO dto = new TvehicleResponseDTO();

		dto.setId(vehicle.getId());
		dto.setVehicleNumber(vehicle.getVehicleNumber());
		dto.setType(vehicle.getType());
		dto.setModel(vehicle.getModel());
		dto.setCapacity(vehicle.getCapacity());

		if (vehicle.getUser() != null) {
			dto.setUser(vehicle.getUser().getId());
		}

		dto.setDriver(vehicle.getDriver());
		dto.setDriverPhone(vehicle.getDriverPhone());
		dto.setCurrentLocation(vehicle.getCurrentLocation());

		dto.setFuelEfficiency(vehicle.getFuelEfficiency());
		dto.setMaintenanceRequired(vehicle.isMaintenanceRequired());

		dto.setYear(vehicle.getYear());
		dto.setChassisNumber(vehicle.getChassisNumber());
		dto.setEngineNumber(vehicle.getEngineNumber());
		dto.setPermitType(vehicle.getPermitType());
		dto.setOwnerName(vehicle.getOwnerName());

		dto.setInsuranceExpiry(vehicle.getInsuranceExpiry());
		dto.setFitnessExpiry(vehicle.getFitnessExpiry());
		dto.setLastService(vehicle.getLastService());
		dto.setNextService(vehicle.getNextService());

		dto.setActive(vehicle.isActive());
		dto.setCancel(vehicle.isCancel());

		dto.setOrgId(vehicle.getOrgId());
		dto.setBranchCode(vehicle.getBranchCode());
		dto.setBranchName(vehicle.getBranchName());

		if (vehicle.getDocuments() == null) {
			dto.setDocuments(null);
		} else {
			dto.setDocuments(vehicle.getDocuments().stream().map(doc -> {
				TvehicleDocumentResponseDTO d = new TvehicleDocumentResponseDTO();
				d.setId(doc.getId());
				d.setDocumentType(doc.getDocumentType());
				d.setFileName(doc.getFileName());

				String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/transaction/files")
						.toUriString();

				d.setFilePath(baseUrl + doc.getFilePath());
				d.setFileType(doc.getFileType());
				d.setFileSize(doc.getFileSize());
				d.setUploadedOn(doc.getUploadedOn());
				return d;
			}).toList());
		}

		return dto;
	}

	private void replaceDocuments(TvehicleVO vehicle, String documentType, MultipartFile[] files, Path vehicleFolder) {

		if (files == null || files.length == 0)
			return;

		// 1️⃣ Fetch old docs
		List<TvehicleDocumentsVO> oldDocs = tvehicleDocumentsRepo.findByTvehicleAndDocumentType(vehicle, documentType);

		// 2️⃣ Delete physical files
		for (TvehicleDocumentsVO doc : oldDocs) {
			deleteFileSafely(doc.getFilePath());
		}

		// 3️⃣ Delete DB rows
		tvehicleDocumentsRepo.deleteAll(oldDocs);

		// 4️⃣ Remove from persistence context
		if (vehicle.getDocuments() != null) {
			vehicle.getDocuments().removeIf(d -> documentType.equals(d.getDocumentType()));
		}

		// 5️⃣ Save new files
		saveFiles(vehicle, documentType, files, vehicleFolder);
	}

	private void deleteFileSafely(String path) {
		try {
			Path filePath = Paths.get(path);
			if (Files.exists(filePath)) {
				Files.delete(filePath);
			}
		} catch (Exception e) {
			// log only – don't break transaction
			System.err.println("Unable to delete file: " + path);
		}
	}

	private void saveFiles(TvehicleVO vehicle, String documentType, MultipartFile[] files, Path vehicleFolder) {
		if (files == null || files.length == 0)
			return;

		try {
			Path docFolder = vehicleFolder.resolve(documentType);
			createDirectory(docFolder);

			for (MultipartFile file : files) {

				String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
				Path filePath = docFolder.resolve(fileName);

				// ✅ IMPORTANT: close stream
				try (InputStream is = file.getInputStream()) {
					Files.copy(is, filePath, StandardCopyOption.REPLACE_EXISTING);
				}

				// Save DB entry
				TvehicleDocumentsVO doc = new TvehicleDocumentsVO();
				doc.setTvehicle(vehicle);
				doc.setDocumentType(documentType);
				doc.setFileName(fileName);
				doc.setFilePath(filePath.toString().replace("\\", "/"));
				doc.setFileType(file.getContentType());
				doc.setFileSize(file.getSize());
				doc.setUploadedOn(LocalDateTime.now());

				tvehicleDocumentsRepo.save(doc);
				if (vehicle.getDocuments() == null) {
					vehicle.setDocuments(new ArrayList<>());
				}
				vehicle.getDocuments().add(doc);
			}

		} catch (IOException e) {
			throw new RuntimeException("File upload failed for " + documentType, e);
		}
	}

	private void createDirectory(Path path) {
		try {
			if (!Files.exists(path)) {
				Files.createDirectories(path);
			}
		} catch (IOException e) {
			throw new RuntimeException("Could not create directory: " + path);
		}
	}

	private void mapTvehicleDTOtoVO(TvehicleDTO dto, TvehicleVO vo) {

		vo.setVehicleNumber(dto.getVehicleNumber());
		vo.setType(dto.getType());
		vo.setModel(dto.getModel());
		vo.setCapacity(dto.getCapacity());
		vo.setInsuranceExpiry(dto.getInsuranceExpiry());
		vo.setFitnessExpiry(dto.getFitnessExpiry());
		vo.setLastService(dto.getLastService());
		vo.setNextService(dto.getNextService());
		vo.setDriver(dto.getDriver());
		vo.setDriverPhone(dto.getDriverPhone());
		vo.setCurrentLocation(dto.getCurrentLocation());
		vo.setFuelEfficiency(dto.getFuelEfficiency());
		vo.setMaintenanceRequired(dto.isMaintenanceRequired());
		vo.setYear(dto.getYear());
		vo.setChassisNumber(dto.getChassisNumber());
		vo.setEngineNumber(dto.getEngineNumber());
		vo.setPermitType(dto.getPermitType());
		vo.setOwnerName(dto.getOwnerName());

		vo.setActive(dto.isActive());
		vo.setOrgId(dto.getOrgId());
		vo.setBranchCode(dto.getBranchCode());
		vo.setBranchName(dto.getBranchName());
	}

	@Override
	public ResponseEntity<byte[]> viewFile(HttpServletRequest request) throws IOException {

		return serveFile(request, "/api/transaction/files/", uploadBasePath, "uploads/vehicles/");
	}

	@Override
	public TvehicleVO getTvehiclesById(Long id) throws ApplicationException {
		return tvehicleRepo.findById(id).orElseThrow(() -> new ApplicationException("Tvehicle not found"));
	}

	@Override
	public Map<String, Object> getTvehiclesByOrgId(String branchCode, Long userId, String search, int page, int count) {

		if (search != null) {
			search = search.trim();
			if (search.isEmpty()) {
				search = null;
			}
		}

		Pageable pageable = PageRequest.of(page - 1, count, Sort.by("vehiclenumber").ascending());
		Page<TvehicleVO> TvehiclePage = tvehicleRepo.getTvehiclesByOrgId(branchCode, userId, search, pageable);

		Page<TvehicleResponseDTO> dtoPage = TvehiclePage.map(this::mapToResponseDTO);

		// return paginated response
		return paginationService.buildResponse(dtoPage);

	}

	@Value("${driver.file.upload.path}")
	private String uploadDriverBasePath;

	@Override
	@Transactional
	public Map<String, Object> createUpdateTdriver(TdriverDTO dto, MultipartFile[] dlFiles, MultipartFile[] aadharFiles,
			MultipartFile[] panFiles, MultipartFile[] photoFiles, MultipartFile[] expFiles,
			MultipartFile[] medicalFiles, MultipartFile[] otherFiles) throws ApplicationException {

		TdriverVO vo;
		String message;

		// UPDATE
		if (ObjectUtils.isNotEmpty(dto.getId())) {
			vo = tdriverRepo.findById(dto.getId()).orElseThrow(() -> new ApplicationException("Invalid Driver ID"));
			vo.setUpdatedBy(dto.getCreatedBy());
			message = "Driver Updated Successfully";
		}
		// CREATE
		else {
			vo = new TdriverVO();
			vo.setCreatedBy(dto.getCreatedBy());
			vo.setUpdatedBy(dto.getCreatedBy());
			message = "Driver Created Successfully";
		}

		if (dto.getUserId() != null) {
			UserVO user = userRepo.findById(dto.getUserId())
					.orElseThrow(() -> new ApplicationException("Invalid User ID"));
			vo.setUser(user);
		}

		mapTdriverDTOtoVO(dto, vo);
		vo = tdriverRepo.save(vo);

		String licenceNo = vo.getLicenseNumber();

		// Base driver folder
		Path driverFolder = Paths.get(uploadDriverBasePath, licenceNo);
		createDirectory(driverFolder);

		replaceDriverDocuments(vo, "DL", dlFiles, driverFolder);
		replaceDriverDocuments(vo, "AADHAR", aadharFiles, driverFolder);
		replaceDriverDocuments(vo, "PAN", panFiles, driverFolder);
		replaceDriverDocuments(vo, "PHOTO", photoFiles, driverFolder);
		replaceDriverDocuments(vo, "EXP", expFiles, driverFolder);
		replaceDriverDocuments(vo, "MEDICAL", medicalFiles, driverFolder);
		replaceDriverDocuments(vo, "OTHER", otherFiles, driverFolder);

		Map<String, Object> response = new HashMap<>();
		response.put("tdriverVO", mapToDriverResponseDTO(vo));
		response.put("message", message);

		return response;
	}

	public TdriverResponseDTO mapToDriverResponseDTO(TdriverVO driver) {

		TdriverResponseDTO dto = new TdriverResponseDTO();

		dto.setId(driver.getId());
		dto.setName(driver.getName());
		dto.setPhone(driver.getPhone());
		dto.setEmail(driver.getEmail());

		if (driver.getUser() != null) {
			dto.setUserId(driver.getUser().getId());
		}

		dto.setLicenseNumber(driver.getLicenseNumber());
		dto.setActive(driver.isActive());
		dto.setOrgId(driver.getOrgId());
		dto.setBranchCode(driver.getBranchCode());
		dto.setBranchName(driver.getBranchName());

		if (driver.getTdriverDocumentsVO() == null) {
			dto.setDocuments(null);
		} else {
			dto.setDocuments(driver.getTdriverDocumentsVO().stream().map(doc -> {
				TdriverDocumentResponseDTO d = new TdriverDocumentResponseDTO();
				d.setId(doc.getId());
				d.setDocumentType(doc.getDocumentType());
				d.setFileName(doc.getFileName());

				String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
						.path("/api/transaction/driverFiles/").toUriString();

				d.setFilePath(baseUrl + doc.getFilePath());
				d.setFileType(doc.getFileType());
				d.setFileSize(doc.getFileSize());
				d.setUploadedOn(doc.getUploadedOn());
				return d;
			}).toList());
		}

		return dto;
	}

	private void replaceDriverDocuments(TdriverVO driver, String documentType, MultipartFile[] files,
			Path driverFolder) {
		if (files == null || files.length == 0)
			return;

		// 1️⃣ Fetch old docs
		List<TdriverDocumentsVO> oldDocs = tdriverDocumentsRepo.findByTdriverVOAndDocumentType(driver, documentType);

		// 2️⃣ Delete physical files
		for (TdriverDocumentsVO doc : oldDocs) {
			deleteFileSafely(doc.getFilePath());
		}

		// 3️⃣ Delete DB rows
		tdriverDocumentsRepo.deleteAll(oldDocs);

		// 4️⃣ Remove from persistence context
		if (driver.getTdriverDocumentsVO() != null) {
			driver.getTdriverDocumentsVO().removeIf(d -> documentType.equals(d.getDocumentType()));
		}

		// 5️⃣ Save new files
		saveDriverFiles(driver, documentType, files, driverFolder);

	}

	private void saveDriverFiles(TdriverVO driver, String documentType, MultipartFile[] files, Path driverFolder) {
		if (files == null || files.length == 0)
			return;

		try {
			Path docFolder = driverFolder.resolve(documentType);
			createDirectory(docFolder);

			for (MultipartFile file : files) {

				String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
				Path filePath = docFolder.resolve(fileName);

				// ✅ IMPORTANT: close stream
				try (InputStream is = file.getInputStream()) {
					Files.copy(is, filePath, StandardCopyOption.REPLACE_EXISTING);
				}

				// Save DB entry
				TdriverDocumentsVO doc = new TdriverDocumentsVO();
				doc.setTdriverVO(driver);
				doc.setDocumentType(documentType);
				doc.setFileName(fileName);
				doc.setFilePath(filePath.toString().replace("\\", "/"));
				doc.setFileType(file.getContentType());
				doc.setFileSize(file.getSize());
				doc.setUploadedOn(LocalDateTime.now());

				tdriverDocumentsRepo.save(doc);
				if (driver.getTdriverDocumentsVO() == null) {
					driver.setTdriverDocumentsVO(new ArrayList<>());
				}
				driver.getTdriverDocumentsVO().add(doc);
			}

		} catch (IOException e) {
			throw new RuntimeException("File upload failed for " + documentType, e);
		}

	}

	private void mapTdriverDTOtoVO(TdriverDTO dto, TdriverVO vo) {

		vo.setName(dto.getName());
		vo.setPhone(dto.getPhone());
		vo.setEmail(dto.getEmail());
		vo.setLicenseNumber(dto.getLicenseNumber());
		vo.setLicenseExpiry(dto.getLicenseExpiry());
		vo.setAadharNumber(dto.getAadharNumber());
		vo.setAddress(dto.getAddress());
		vo.setStatus(dto.getStatus());
		vo.setExperience(dto.getExperience());
		vo.setSalary(dto.getSalary());
		vo.setAssignedVehicle(dto.getAssignedVehicle());
		vo.setCurrentLocation(dto.getCurrentLocation());
		vo.setBloodGroup(dto.getBloodGroup());
		vo.setEmergencyContact(dto.getEmergencyContact());
		vo.setPerformance(dto.getPerformance());
		vo.setJoinedDate(dto.getJoinedDate());
		vo.setLastTrip(dto.getLastTrip());
		vo.setActive(dto.isActive());
		vo.setOrgId(dto.getOrgId());
		vo.setBranchCode(dto.getBranchCode());
		vo.setBranchName(dto.getBranchName());
	}

	@Override
	public ResponseEntity<byte[]> viewDriverFile(HttpServletRequest request) throws IOException {

		return serveFile(request, "/api/transaction/driverFiles/", uploadDriverBasePath,"uploads/driver/");
	}

	private ResponseEntity<byte[]> serveFile(HttpServletRequest request, String apiPrefix, String uploadBasePath,
			String uploadFolderPrefix) throws IOException {

		String uri = request.getRequestURI();

		// Remove API prefix
		String relativePath = uri.replace(apiPrefix, "");

		// Decode URL (%20 → space)
		relativePath = URLDecoder.decode(relativePath, StandardCharsets.UTF_8);

		// Remove logical upload folder from URL path
		if (relativePath.startsWith(uploadFolderPrefix)) {
			relativePath = relativePath.substring(uploadFolderPrefix.length());
		}

		Path baseDir = Paths.get(uploadBasePath).toAbsolutePath().normalize();
		Path filePath = baseDir.resolve(relativePath).normalize();

		// 🔐 Security check
		if (!filePath.startsWith(baseDir)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		if (!Files.exists(filePath)) {
			return ResponseEntity.notFound().build();
		}

		String contentType = Files.probeContentType(filePath);
		if (contentType == null) {
			contentType = "application/octet-stream";
		}

		byte[] data = Files.readAllBytes(filePath);

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "inline").body(data);
	}

	@Override
	public TdriverVO getTdriverById(Long id) throws ApplicationException {
		return tdriverRepo.findById(id).orElseThrow(() -> new ApplicationException("Tdriver not found"));
	}

	@Override
	public Map<String, Object> getTdriverByOrgId(String branchCode, Long userId, String search, int page, int count) {

		if (search != null) {
			search = search.trim();
			if (search.isEmpty()) {
				search = null;
			}
		}

		Pageable pageable = PageRequest.of(page - 1, count, Sort.by("name").ascending());
		Page<TdriverVO> quotePage = tdriverRepo.getTdriverByOrgId(branchCode, userId, search, pageable);

		// return paginated response
		return paginationService.buildResponse(quotePage);

	}

	// Bulk upload api TVehicles

	@Override
	public List<TvehicleVO> uploadTVehicleExcel(MultipartFile file, String createdBy, Long orgId) throws Exception {

		List<TvehicleDTO> excelList = new ArrayList<>();

		Workbook workbook = WorkbookFactory.create(file.getInputStream());
		Sheet sheet = workbook.getSheetAt(0);

		int rowIndex = 0;

		for (Row row : sheet) {

			if (rowIndex == 0) {
				rowIndex++;
				continue;
			}

			if (row == null || row.getCell(0) == null)
				continue;

			TvehicleDTO dto = new TvehicleDTO();

			dto.setVehicleNumber(getString(row, 0));
			dto.setType(getString(row, 1));
			dto.setModel(getString(row, 2));
			dto.setCapacity(getString(row, 3));
			dto.setInsuranceExpiry(getDate(row, 4));
			dto.setFitnessExpiry(getDate(row, 5));
			dto.setLastService(getDate(row, 6));
			dto.setNextService(getDate(row, 7));
			dto.setDriver(getString(row, 8));
			dto.setDriverPhone(getString(row, 9));
			dto.setCurrentLocation(getString(row, 10));
			dto.setFuelEfficiency(getString(row, 11));
			dto.setMaintenanceRequired(getBoolean(row, 12));
			dto.setYear(getInt(row, 13));
			dto.setChassisNumber(getString(row, 14));
			dto.setEngineNumber(getString(row, 15));
			dto.setPermitType(getString(row, 16));
			dto.setOwnerName(getString(row, 17));
			dto.setActive(getBoolean(row, 18));
			dto.setCreatedBy(createdBy);
			dto.setOrgId(orgId);
			dto.setBranchCode(getString(row, 19));
			dto.setBranchName(getString(row, 20));

			excelList.add(dto);
		}

		workbook.close();

		// RETURN the saved VO list
		return saveToDB(excelList);
	}

	private List<TvehicleVO> saveToDB(List<TvehicleDTO> list) {

		List<TvehicleVO> vos = new ArrayList<>();

		for (TvehicleDTO dto : list) {

			TvehicleVO vo = new TvehicleVO();

			vo.setVehicleNumber(dto.getVehicleNumber());
			vo.setType(dto.getType());
			vo.setModel(dto.getModel());
			vo.setCapacity(dto.getCapacity());
			vo.setInsuranceExpiry(dto.getInsuranceExpiry());
			vo.setFitnessExpiry(dto.getFitnessExpiry());
			vo.setLastService(dto.getLastService());
			vo.setNextService(dto.getNextService());
			vo.setDriver(dto.getDriver());
			vo.setDriverPhone(dto.getDriverPhone());
			vo.setCurrentLocation(dto.getCurrentLocation());
			vo.setFuelEfficiency(dto.getFuelEfficiency());
			vo.setMaintenanceRequired(dto.isMaintenanceRequired());
			vo.setYear(dto.getYear());
			vo.setChassisNumber(dto.getChassisNumber());
			vo.setEngineNumber(dto.getEngineNumber());
			vo.setPermitType(dto.getPermitType());
			vo.setOwnerName(dto.getOwnerName());
			vo.setActive(dto.isActive());
			vo.setCreatedBy(dto.getCreatedBy());
			vo.setUpdatedBy(dto.getCreatedBy());
			vo.setOrgId(dto.getOrgId());
			vo.setBranchCode(dto.getBranchCode());
			vo.setBranchName(dto.getBranchName());

			vos.add(vo);
		}

		// This returns entities WITH ID generated
		return tvehicleRepo.saveAll(vos);
	}

	private String getString(Row row, int col) {
		try {
			Cell cell = row.getCell(col);
			if (cell == null)
				return "";
			return cell.toString().trim();
		} catch (Exception e) {
			return "";
		}
	}

	private int getInt(Row row, int col) {
		try {
			Cell cell = row.getCell(col);
			if (cell == null)
				return 0;

			if (cell.getCellType() == CellType.NUMERIC) {
				return (int) cell.getNumericCellValue();
			}

			if (cell.getCellType() == CellType.STRING) {
				String v = cell.getStringCellValue().trim();
				if (v.isEmpty())
					return 0;
				return Integer.parseInt(v);
			}

			return 0;

		} catch (Exception e) {
			return 0;
		}
	}

	private boolean getBoolean(Row row, int col) {
		try {
			Cell cell = row.getCell(col);
			if (cell == null)
				return false;

			if (cell.getCellType() == CellType.BOOLEAN) {
				return cell.getBooleanCellValue();
			}

			if (cell.getCellType() == CellType.STRING) {
				String v = cell.getStringCellValue().trim().toLowerCase();
				return v.equals("true") || v.equals("yes") || v.equals("1");
			}

			return false;

		} catch (Exception e) {
			return false;
		}
	}

	private LocalDate getDate(Row row, int col) {
		try {
			Cell cell = row.getCell(col);
			if (cell == null)
				return null;

			// CASE 1: Excel numeric date
			if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
				return cell.getLocalDateTimeCellValue().toLocalDate();
			}

			// CASE 2: Text-based date
			if (cell.getCellType() == CellType.STRING) {
				String value = cell.getStringCellValue().trim();
				if (value.isEmpty())
					return null;

				// yyyy-MM-dd
				if (value.matches("\\d{4}-\\d{2}-\\d{2}")) {
					return LocalDate.parse(value);
				}

				// dd-MM-yyyy
				if (value.matches("\\d{2}-\\d{2}-\\d{4}")) {
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
					return LocalDate.parse(value, formatter);
				}
			}

			return null;

		} catch (Exception e) {
			return null;
		}
	}

	// Tdriver bulk upload

	@Override
	public List<TdriverVO> uploadTDriverExcel(MultipartFile file, String createdBy, Long orgId) throws Exception {

		List<TdriverDTO> driverList = new ArrayList<>();

		Workbook workbook = WorkbookFactory.create(file.getInputStream());
		Sheet sheet = workbook.getSheetAt(0);

		int rowIndex = 0;

		for (Row row : sheet) {

			if (rowIndex == 0) {
				rowIndex++;
				continue;
			}

			if (row == null || row.getCell(0) == null)
				continue;

			TdriverDTO dto = new TdriverDTO();

			dto.setName(getString(row, 0));
			dto.setPhone(getString(row, 1));
			dto.setEmail(getString(row, 2));
			dto.setLicenseNumber(getString(row, 3));
			dto.setLicenseExpiry(getDate(row, 4));
			dto.setAadharNumber(getString(row, 5));
			dto.setAddress(getString(row, 6));
			dto.setStatus(getString(row, 7));
			dto.setExperience(getString(row, 8));
			dto.setSalary(getString(row, 9));
			dto.setAssignedVehicle(getString(row, 10));
			dto.setCurrentLocation(getString(row, 11));
			dto.setBloodGroup(getString(row, 12));
			dto.setEmergencyContact(getString(row, 13));
			dto.setPerformance(getString(row, 14));
			dto.setJoinedDate(getDate(row, 15));
			dto.setLastTrip(getDate(row, 16));
			dto.setActive(getBoolean(row, 17));
			dto.setCreatedBy(createdBy);
			dto.setOrgId(orgId);
			dto.setBranchCode(getString(row, 18));
			dto.setBranchName(getString(row, 19));

			driverList.add(dto);
		}

		workbook.close();

		return saveTDriverToDB(driverList);
	}

	private List<TdriverVO> saveTDriverToDB(List<TdriverDTO> list) {

		List<TdriverVO> vos = new ArrayList<>();

		for (TdriverDTO dto : list) {

			TdriverVO vo = new TdriverVO();

			vo.setName(dto.getName());
			vo.setPhone(dto.getPhone());
			vo.setEmail(dto.getEmail());
			vo.setLicenseNumber(dto.getLicenseNumber());
			vo.setLicenseExpiry(dto.getLicenseExpiry());
			vo.setAadharNumber(dto.getAadharNumber());
			vo.setAddress(dto.getAddress());
			vo.setStatus(dto.getStatus());
			vo.setExperience(dto.getExperience());
			vo.setSalary(dto.getSalary());
			vo.setAssignedVehicle(dto.getAssignedVehicle());
			vo.setCurrentLocation(dto.getCurrentLocation());
			vo.setBloodGroup(dto.getBloodGroup());
			vo.setEmergencyContact(dto.getEmergencyContact());
			vo.setPerformance(dto.getPerformance());
			vo.setJoinedDate(dto.getJoinedDate());
			vo.setLastTrip(dto.getLastTrip());
			vo.setActive(dto.isActive());
			vo.setCreatedBy(dto.getCreatedBy());
			vo.setUpdatedBy(dto.getCreatedBy());
			vo.setOrgId(dto.getOrgId());
			vo.setBranchCode(dto.getBranchCode());
			vo.setBranchName(dto.getBranchName());
			vo.setCancel(false);

			vos.add(vo);
		}

		return tdriverRepo.saveAll(vos); // Returns VO with ID
	}

	// Approve quote

	@Override
	public Map<String, Object> createApprovalQuote(Long id, String action, String actionBy, Long auctionId)
			throws ApplicationException {

		QuoteVO quoteVO = quoteRepo.findByIdAndAuctionId(id, auctionId);
		String message = "";

		if (quoteVO.getApprovedStatus() == null || (!quoteVO.getApprovedStatus().equalsIgnoreCase("Approved")
				&& !quoteVO.getApprovedStatus().equalsIgnoreCase("Rejected"))) {

			if ("Approved".equalsIgnoreCase(action)) {

				UserVO userVO = quoteVO.getUser();
				VendorVO vendor = vendorRepo.findById(userVO.getVendorId())
						.orElseThrow(() -> new RuntimeException("Invalid Vendor"));

				AuctionsVO auctionVO = auctionsRepo.findById(auctionId).get();
				auctionVO.setQuotes(true);
				auctionVO.setApprovedVendor(vendor);
				auctionsRepo.save(auctionVO);
				// Get Max & Min quotes
				QuoteVO maxQuote = quoteRepo.findTopByAuctionIdOrderByQuoteAmountDesc(auctionId);
				QuoteVO minQuote = quoteRepo.findTopByAuctionIdOrderByQuoteAmountAsc(auctionId);

				// Save Approved Quote
				ApprovedQuoteVO approvedQuoteVO = new ApprovedQuoteVO();
				approvedQuoteVO.setAuction(quoteVO.getAuction());
				approvedQuoteVO.setAdditionalNotes(quoteVO.getAdditionalNotes());
				approvedQuoteVO.setEstimatedDeliveryDate(quoteVO.getEstimatedDeliveryDate());
				approvedQuoteVO.setTermsAndConditions(quoteVO.getTermsAndConditions());
				approvedQuoteVO.setApprovedAmount(quoteVO.getQuoteAmount());
				approvedQuoteVO.setApprovedUser(quoteVO.getUser());
				approvedQuoteVO.setCreatedBy(actionBy);
				approvedQuoteVO.setUpdatedBy(actionBy);
				approvedQuoteVO.setOrgId(auctionVO.getOrgId());
				// NEW FIELDS
				approvedQuoteVO.setMaxQuotedAmount(maxQuote.getQuoteAmount());
				approvedQuoteVO.setMinQuotedAmount(minQuote.getQuoteAmount());
				approvedQuoteVO.setMaxQuotedUser(maxQuote.getUser());
				approvedQuoteVO.setMinQuotedUser(minQuote.getUser());

				approvedQuoteRepo.save(approvedQuoteVO);

				String msg = "Your Quote is Accepted";
				notificationService.createNotification(userVO.getId(), auctionId, msg, "Quote Accepted");

				List<QuoteVO> quotes = quoteRepo.findAllByAuctionAndUserNot(auctionVO, userVO);

				List<NotificationVO> n = new ArrayList<>();
				for (QuoteVO quoteVO2 : quotes) {
					QuoteVO quo = quoteVO2;
					UserVO userVO1 = quoteVO2.getUser();
					NotificationVO n1 = new NotificationVO();
					n1.setUserid(userVO1.getId());
					n1.setAuctionsid(auctionId);
					n1.setMessage("Your Quote is Rejected");
					n1.setNotificationType("Quote Rejected");
					n.add(n1);
					quo.setApproveBy(actionBy);
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss a");
					quo.setApproveOn(LocalDateTime.now().format(formatter).toUpperCase());
					quo.setApprovedStatus("Rejected");
					quoteRepo.save(quo);
				}
				notificationRepo.saveAll(n);
			}

			quoteVO.setApprovedStatus(action);
			quoteVO.setApproveBy(actionBy);

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss a");
			quoteVO.setApproveOn(LocalDateTime.now().format(formatter).toUpperCase());
			quoteRepo.save(quoteVO);

			message = action.equalsIgnoreCase("Approved") ? "Approved Successfully" : "Rejected Successfully";

		} else if (quoteVO.getApprovedStatus().equalsIgnoreCase("Approved")) {
			throw new ApplicationException("This Quote Already Approved");
		} else if (quoteVO.getApprovedStatus().equalsIgnoreCase("Rejected")) {
			throw new ApplicationException("This Quote Already Rejected");
		}

		Map<String, Object> response = new HashMap<>();
		response.put("quoteVO", quoteVO);
		response.put("message", message);

		return response;
	}

	@Override
	@Transactional
	public Map<String, Object> createUpdateVendorInvoice(VendorInvoiceDTO dto, MultipartFile invoiceFile,
			List<MultipartFile> tripDocuments) throws Exception {

		VendorInvoiceVO vo;
		String message;

		// ================= UPDATE =================
		if (dto.getId() != null) {

			vo = vendorInvoiceRepo.findById(dto.getId())
					.orElseThrow(() -> new ApplicationException("Invalid VendorInvoice ID"));

			// Delete Old children
			vendorInvoiceChargesRepo.deleteAll(vo.getVendorInvoiceChargesVO());
			vendorInvoiceTripsDetailsRepo.deleteAll(vo.getVendorInvoiceTripsDetailsVO());
			vendorInvoiceTripsDocumentRepo.deleteAll(vo.getVendorInvoiceTripsDocumentVO());

			vo.getVendorInvoiceChargesVO().clear();
			vo.getVendorInvoiceTripsDetailsVO().clear();
			vo.getVendorInvoiceTripsDocumentVO().clear();

			vo.setUpdatedBy(dto.getCreatedBy());
			message = "Vendor Invoice Updated Successfully";

		} else {
			// ================= CREATE =================
			vo = new VendorInvoiceVO();
			vo.setCreatedBy(dto.getCreatedBy());
			vo.setUpdatedBy(dto.getCreatedBy());
			message = "Vendor Invoice Created Successfully";
		}

		// ================= Vendor Attach =================
		if (dto.getVendorId() != null) {
			VendorVO vendor = vendorRepo.findById(dto.getVendorId())
					.orElseThrow(() -> new ApplicationException("Invalid Vendor ID"));
			vo.setVendor(vendor);
		}

		// ================= Main Table Mapping =================
		setInvoiceData(dto, vo);

		// ================= Invoice File =================
		if (invoiceFile != null && !invoiceFile.isEmpty()) {
			vo.setInvoiceFile(invoiceFile.getBytes());
		}

		// Save main table first
		vendorInvoiceRepo.save(vo);

		// ================= CHILD – Charges =================
		List<VendorInvoiceChargesVO> chargeList = new ArrayList<VendorInvoiceChargesVO>();

		if (dto.getVendorInvoiceChargesDTO() != null) {
			for (VendorInvoiceChargesDTO c : dto.getVendorInvoiceChargesDTO()) {

				VendorInvoiceChargesVO ch = new VendorInvoiceChargesVO();
				ch.setPurpose(c.getPurpose());
				ch.setAmount(c.getAmount());
				ch.setRemarks(c.getRemarks());
				ch.setTrip(c.getTrip());
				ch.setStatus(c.getStatus());
				ch.setVendorInvoiceVO(vo);
				chargeList.add(ch);
			}
		}
		vo.setVendorInvoiceChargesVO(chargeList);

		// ================= CHILD – Trip Details =================
		List<VendorInvoiceTripsDetailsVO> tripsList = new ArrayList<VendorInvoiceTripsDetailsVO>();

		if (dto.getVendorInvoiceTripsDetailsDTO() != null) {
			for (VendorInvoiceTripsDetailsDTO t : dto.getVendorInvoiceTripsDetailsDTO()) {

				VendorInvoiceTripsDetailsVO td = new VendorInvoiceTripsDetailsVO();
				td.setTrips(t.getTrips());
				td.setOrigin(t.getOrigin());
				td.setDestination(t.getDestination());
				td.setVehicle(t.getVehicle());
				td.setStatus(t.getStatus());
				td.setVendorInvoiceVO(vo);
				tripsList.add(td);
			}
		}
		vo.setVendorInvoiceTripsDetailsVO(tripsList);

		// ================= CHILD – Trip Documents (file + JSON header)
		// =================
		List<VendorInvoiceTripsDocumentVO> docList = new ArrayList<VendorInvoiceTripsDocumentVO>();

		if (dto.getVendorInvoiceTripsDocumentDTO() != null && tripDocuments != null) {

//			if (tripDocuments.size() != dto.getVendorInvoiceTripsDocumentDTO().size()) {
//				throw new ApplicationException("Document count mismatch");
//			}

			for (int i = 0; i < tripDocuments.size(); i++) {

				MultipartFile f = tripDocuments.get(i);
				VendorInvoiceTripsDocumentDTO d = dto.getVendorInvoiceTripsDocumentDTO().get(i);

				VendorInvoiceTripsDocumentVO dv = new VendorInvoiceTripsDocumentVO();
				dv.setDocType(d.getDocType());
				dv.setRemarks(d.getRemarks());
				dv.setTrip(d.getTrip());
				dv.setVehicleNumber(d.getVehicleNumber());
				dv.setVendorInvoiceVO(vo);

				if (f != null && !f.isEmpty()) {
					dv.setDocuments(f.getBytes());
				}

				docList.add(dv);
			}
		}
		vo.setVendorInvoiceTripsDocumentVO(docList);

		// ================= FINAL SAVE =================
		vendorInvoiceRepo.save(vo);

		Map<String, Object> response = new HashMap<String, Object>();
		response.put("message", message);
		response.put("vendorInvoice", vo);

		return response;
	}

	private void setInvoiceData(VendorInvoiceDTO dto, VendorInvoiceVO vo) {

		vo.setInvoiceType(dto.getInvoiceType());
		vo.setTripCost(dto.getTripCost());
		vo.setTotalAdditionalCharges(dto.getTotalAdditionalCharges());
		vo.setSubTotal(dto.getSubTotal());
		vo.setTotalAmount(dto.getTotalAmount());
		vo.setTds(dto.getTds());
		vo.setPayableAmount(dto.getPayableAmount());
		vo.setInvoiceNumber(dto.getInvoiceNumber());
		vo.setDueDate(dto.getDueDate());
		vo.setInvoiceDate(dto.getInvoiceDate());
		vo.setDescription(dto.getDescription());
		vo.setFromDate(dto.getFromDate());
		vo.setToDate(dto.getToDate());
		vo.setOrgId(dto.getOrgId());
		vo.setBranchCode(dto.getBranchCode());
		vo.setBranch(dto.getBranch());
	}

	@Override
	public VendorInvoiceVO getVendorInvoiceById(Long id) throws ApplicationException {
		return vendorInvoiceRepo.findById(id).orElseThrow(() -> new ApplicationException("VendorInvoice not found"));
	}

	@Override
	public Map<String, Object> getVendorInvoiceByOrgId(String branchCode, Long orgId, String search, int page,
			int count) {

		if (search != null) {
			search = search.trim();
			if (search.isEmpty()) {
				search = null;
			}
		}

		Pageable pageable = PageRequest.of(page - 1, count, Sort.by("invoicetype").ascending());
		Page<VendorInvoiceVO> vendorInvoicePage = vendorInvoiceRepo.getVendorInvoiceByOrgId(branchCode, orgId, search,
				pageable);

		// return paginated response
		return paginationService.buildResponse(vendorInvoicePage);

	}

	// Document

	@Override
	public Map<String, Object> getAllDocumentByUserId(Long orgId, Long userId, String search, int page, int size) {

		if (search != null) {
			search = search.trim();
			if (search.isEmpty()) {
				search = null;
			}
		}

		Pageable pageable = PageRequest.of(page - 1, size, Sort.by("name").ascending());
		Page<DocumentVO> customerPage = documentRepo.getAllDocumentByUserId(orgId, userId, search, pageable);

		return paginationService.buildResponse(customerPage);

	}

	@Override
	public DocumentVO getDocumentById(Long id) {

		return documentRepo.getDocumentById(id);
	}

	@Override
	@Transactional
	public Map<String, Object> updateCreateDocument(DocumentDTO documentDTO, MultipartFile invoiceFile)
			throws ApplicationException, IOException {

		DocumentVO documentVO = new DocumentVO();

		String message;

		if (ObjectUtils.isNotEmpty(documentDTO.getId())) {

			documentVO = documentRepo.findById(documentDTO.getId())
					.orElseThrow(() -> new ApplicationException("Document Not Found!"));
			documentVO.setUpdatedBy(documentDTO.getCreatedBy());

			if (!documentVO.getDocumentNo().equalsIgnoreCase(documentDTO.getDocumentNo())) {
				if (documentRepo.existsByDocumentNoAndOrgId(documentDTO.getDocumentNo(), documentDTO.getOrgId())) {
					String errorMessage = String.format("This DocumentNo: %s Already Exists in This Organization",
							documentDTO.getDocumentNo());
					throw new ApplicationException(errorMessage);
				}
				documentVO.setDocumentNo(documentDTO.getDocumentNo().toUpperCase());
			}

			message = "Document Updated Successfully";
		} else {

			if (documentRepo.existsByDocumentNoAndOrgId(documentDTO.getDocumentNo(), documentDTO.getOrgId())) {
				String errorMessage = String.format("This DocumentNo: %s Already Exists in This Organization",
						documentDTO.getDocumentNo());
				throw new ApplicationException(errorMessage);
			}

			documentVO.setUpdatedBy(documentDTO.getCreatedBy());
			documentVO.setCreatedBy(documentDTO.getCreatedBy());
			message = "Document Created Successfully";
		}

		if (invoiceFile != null && !invoiceFile.isEmpty()) {
			documentVO.setFileUpload(invoiceFile.getBytes());
		}

		createUpdateDocumentVOByDocumentDTO(documentDTO, documentVO);
		documentRepo.save(documentVO);
		Map<String, Object> response = new HashMap<>();
		response.put("documentVO", documentVO);
		response.put("message", message);
		return response;
	}

	private void createUpdateDocumentVOByDocumentDTO(@Valid DocumentDTO documentDTO, DocumentVO documentVO)
			throws ApplicationException {
		documentVO.setName(documentDTO.getName());
		documentVO.setBranchCode(documentDTO.getBranchCode());
		documentVO.setType(documentDTO.getType());
		documentVO.setTags(documentDTO.getTags());
		documentVO.setCreatedBy(documentDTO.getCreatedBy());
		documentVO.setAssociatedWith(documentDTO.getAssociatedWith());
		documentVO.setAssociationType(documentDTO.getAssociationType());
		documentVO.setDocumentNo(documentDTO.getDocumentNo());
		documentVO.setStatus(documentDTO.getStatus());
		documentVO.setIssueDate(documentDTO.getIssueDate());
		documentVO.setExpiryDate(documentDTO.getExpiryDate());
		documentVO.setDescription(documentDTO.getDescription());
		documentVO.setOrgId(documentDTO.getOrgId());
		documentVO.setUserId(documentDTO.getUserId());
		documentVO.setVarifiedStatus(documentDTO.getVarifiedStatus());
	}

	@Override
	public List<Map<String, Object>> getDocumentCount(Long orgId, String branchCode, Long userId) {
		Set<Object[]> chType = documentRepo.getDocumentCount(orgId, branchCode, userId);
		return getDocumentCount(chType);
	}

	private List<Map<String, Object>> getDocumentCount(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("totalDocument", ch[0] != null ? Integer.parseInt(ch[0].toString()) : 0);
			map.put("Active", ch[1] != null ? Integer.parseInt(ch[1].toString()) : 0);
			map.put("expiringoon", ch[2] != null ? Integer.parseInt(ch[2].toString()) : 0);
			map.put("expired", ch[3] != null ? Integer.parseInt(ch[3].toString()) : 0);
			map.put("verified", ch[4] != null ? Integer.parseInt(ch[4].toString()) : 0);
			map.put("pending", ch[5] != null ? Integer.parseInt(ch[5].toString()) : 0);

			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getDocumentTypeCount(Long orgId, String branchCode, Long userId) {
		Set<Object[]> chType = documentRepo.getDocumentTypeCount(orgId, branchCode, userId);
		return getDocumentTypeCount(chType);
	}

	private List<Map<String, Object>> getDocumentTypeCount(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("vehicleDocument", ch[0] != null ? Integer.parseInt(ch[0].toString()) : 0);
			map.put("driverDocument", ch[1] != null ? Integer.parseInt(ch[1].toString()) : 0);
			map.put("invoiceDocument", ch[2] != null ? Integer.parseInt(ch[2].toString()) : 0);
			map.put("insuranceDocument", ch[3] != null ? Integer.parseInt(ch[3].toString()) : 0);
			map.put("taxDocument", ch[4] != null ? Integer.parseInt(ch[4].toString()) : 0);
			map.put("routeDocument", ch[5] != null ? Integer.parseInt(ch[5].toString()) : 0);

			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<DocumentVO> getAllDocumentList(Long orgId, String branchCode, Long userId, String search, String status,
			String type) {

		return documentRepo.getAllDocumentList(orgId, branchCode, userId, search, status, type);
	}

	@Override
	public List<Map<String, Object>> getTripDetailsListByOrgId(String branchCode, Long orgId) {

		if (branchCode != null && branchCode.trim().isEmpty()) {
			branchCode = null;
		}

		List<Object[]> results = tripsRepo.getTripDetailsListByOrgId(branchCode, orgId);
		List<Map<String, Object>> list = new ArrayList<>();

		for (Object[] row : results) {
			Map<String, Object> map = new HashMap<>();

			map.put("id", row[0] == null ? "" : row[0].toString());
			map.put("lrNumber", row[1] == null ? "" : row[1].toString());
			map.put("vehicleNumber", row[2] == null ? "" : row[2].toString());
			map.put("orgin", row[3] == null ? "" : row[3].toString());
			map.put("destination", row[4] == null ? "" : row[4].toString());
			map.put("status", row[5] == null ? "" : row[5].toString());

			list.add(map);
		}

		return list;
	}

	@Override
	public Map<String, Object> getApprovedQuotesByOrgId(Long orgId, int page, int count) {

		Pageable pageable = PageRequest.of(page - 1, count);
		Page<Map<String, Object>> approvedQuotes = approvedQuoteRepo.getApprovedQuotesByOrg(orgId, pageable);
		return paginationService.buildResponse(approvedQuotes);
	}

}
