package com.efit.savaari.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.efit.savaari.dto.AuctionsDTO;
import com.efit.savaari.dto.CustomerBookingRequestDTO;
import com.efit.savaari.dto.DocumentDTO;
import com.efit.savaari.dto.PayoutsDTO;
import com.efit.savaari.dto.QuoteDTO;
import com.efit.savaari.dto.RequestforQuotesDTO;
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
import com.efit.savaari.exception.ApplicationException;

@Service
public interface TransactionService {

	Map<String, Object> createUpdateTrips(TripsDTO tripsDTO) throws Exception;

	TripsVO getTripsById(Long id) throws ApplicationException;

	Map<String, Object> getTripsByOrgId(String branchCode, Long orgId, String search, int page, int count);

	Map<String, Object> createUpdateCustomerBookingRequest(CustomerBookingRequestDTO customerBookingRequestDTO)
			throws Exception;

	CustomerBookingRequestVO getCustomerBookingRequestById(Long id) throws ApplicationException;

	Map<String, Object> getCustomerBookingRequestByOrgId(String branchCode, Long orgId, String search, int page, int count);

//	Map<String, Object> createUpdatePayouts(PayoutsDTO payoutsDTO) throws Exception;

	PayoutsVO getPayoutsById(Long id) throws ApplicationException;

	Map<String, Object> getPayoutsByOrgId(String branchCode, Long orgId, String search, int page, int count);

	Map<String, Object> createUpdatePayouts(PayoutsDTO payoutsDTO, MultipartFile invoiceFile,
			List<MultipartFile> documents) throws Exception;

	Map<String, Object> createUpdateRequestforQuotes(RequestforQuotesDTO requestforQuotesDTO, MultipartFile documents)
			throws Exception;

	RequestforQuotesVO getRequestforQuotesById(Long id) throws ApplicationException;

	Map<String, Object> getRequestforQuotesByOrgId(String branchCode, String search, int page, int count);

	Map<String, Object> uploadLineItemsExcel(Long rfqId, MultipartFile file, String createdBy) throws Exception;

	Map<String, Object> createUpdateAuctions(AuctionsDTO auctionsDTO, MultipartFile documents) throws Exception;

	AuctionsVO getAuctionsById(Long id) throws ApplicationException;

	Map<String, Object> getAuctionsByOrgId(String branchCode,Long orgId, String search, int page, int count);

	Map<String, Object> createUpdateTagging(TaggingDTO taggingDTO) throws Exception;

	TaggingVO getTaggingById(Long id) throws ApplicationException;

	Map<String, Object> getTaggingByOrgId(String branchCode, String search, int page, int count);

	Map<String, Object> createUpdateTripReportMis(TripReportMisDTO tripReportMisDTO) throws Exception;

	TripReportMisVO getTripReportMisById(Long id) throws ApplicationException;

	Map<String, Object> getTripReportMisByOrgId(String branchCode, String search, int page, int count);

	Map<String, Object> createUpdateTripGeofenceAlerts(TripGeofenceAlertsDTO dto) throws Exception;

	TripGeofenceAlertsVO getTripGeofenceAlertsById(Long id) throws ApplicationException;

	Map<String, Object> getTripGeofenceAlertsByOrgId(String branchCode,Long orgId, String search, int page, int count);

	Map<String, Object> createUpdateTripAlerts(TripAlertsDTO dto) throws Exception;

	TripAlertsVO getTripAlertsById(Long id) throws ApplicationException;

	Map<String, Object> getTripAlertsByOrgId(String branchCode,Long orgId, String search, int page, int count);

	Map<String, Object> getAuctionsReportByOrgId(Long userId, String search, int page, int count);

	Map<String, Object> createUpdateQuote(QuoteDTO dto) throws Exception;

	QuoteVO getQuoteById(Long id) throws ApplicationException;

	Map<String, Object> getQuoteByUserId(Long userId, String search, int page, int count);

//	Map<String, Object> createUpdateTvehicle( @Valid TvehicleDTO dto, List<MultipartFile> documents) throws Exception;

	Map<String, Object> createUpdateTvehicle(@Valid TvehicleDTO dto,MultipartFile[] rcFiles,
			MultipartFile[] insuranceFiles,
			MultipartFile[] fcFiles,
			MultipartFile[] permitFiles,
			MultipartFile[] pucFiles,
			MultipartFile[] otherFiles) throws Exception;

	TvehicleVO getTvehiclesById(Long id) throws ApplicationException;

	Map<String, Object> getTvehiclesByOrgId(String branchCode,Long userId, String search, int page, int count);


	TdriverVO getTdriverById(Long id) throws ApplicationException;

	Map<String, Object> getTdriverByOrgId(String branchCode,Long userId, String search, int page, int count);

	List<TvehicleVO> uploadTVehicleExcel(MultipartFile file, String createdBy, Long orgId) throws Exception;

	List<TdriverVO> uploadTDriverExcel(MultipartFile file, String createdBy, Long orgId) throws Exception;

	Map<String, Object> createApprovalQuote(Long id, String action, String actionBy, Long auctionId)
			throws ApplicationException;

	Map<String, Object> getUserAuctionsQuoteByOrgId(Long userId, String search, int page, int count);

	Map<String, Object> createUpdateVendorInvoice(VendorInvoiceDTO dto, MultipartFile invoiceFile,
			List<MultipartFile> tripDocuments) throws Exception;

	VendorInvoiceVO getVendorInvoiceById(Long id) throws ApplicationException;

	Map<String, Object> getVendorInvoiceByOrgId(String branchCode, Long orgId, String search, int page, int count);

	// Document

	Map<String, Object> getAllDocumentByUserId(Long orgId, Long userId, String search, int page, int size);

	DocumentVO getDocumentById(Long id);

	Map<String, Object> updateCreateDocument(DocumentDTO documentDTO, MultipartFile invoiceFile)
			throws ApplicationException, IOException;

	List<Map<String, Object>> getDocumentCount(Long orgId, String branchCode, Long userId);

	List<Map<String, Object>> getDocumentTypeCount(Long orgId, String branchCode, Long userId);

	List<DocumentVO> getAllDocumentList(Long orgId, String branchCode, Long userId, String search, String status,
			String type);

	List<Map<String, Object>> getTripDetailsListByOrgId(String branchCode, Long orgId);

	Map<String, Object> getApprovedQuotesByOrgId(Long orgId, int page, int count);
	
	ResponseEntity<byte[]> viewFile(HttpServletRequest request) throws IOException;

	Map<String, Object> createUpdateTdriver(TdriverDTO dto, MultipartFile[] dlFiles, MultipartFile[] aadharFiles,
			MultipartFile[] panFiles, MultipartFile[] photoFiles, MultipartFile[] expFiles,
			MultipartFile[] medicalFiles, MultipartFile[] otherFiles) throws ApplicationException;

	ResponseEntity<byte[]> viewDriverFile(HttpServletRequest request) throws IOException;

}
