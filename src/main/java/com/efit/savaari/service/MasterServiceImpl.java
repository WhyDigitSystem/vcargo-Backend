package com.efit.savaari.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.efit.savaari.dto.BranchDTO;
import com.efit.savaari.dto.ChargeTypeDTO;
import com.efit.savaari.dto.CompanyProfileDTO;
import com.efit.savaari.dto.CustomerAddressDTO;
import com.efit.savaari.dto.CustomerDTO;
import com.efit.savaari.dto.CustomerRateDTO;
import com.efit.savaari.dto.IndentsDTO;
import com.efit.savaari.dto.ListOfValues1DTO;
import com.efit.savaari.dto.ListOfValuesDTO;
import com.efit.savaari.dto.PlaceDetailsDTO;
import com.efit.savaari.dto.RoutesDTO;
import com.efit.savaari.dto.RoutesDetailsDTO;
import com.efit.savaari.dto.RoutesPetrolPumpsDTO;
import com.efit.savaari.dto.RoutesPitstopDTO;
import com.efit.savaari.dto.VendorRateDTO;
import com.efit.savaari.entity.BranchVO;
import com.efit.savaari.entity.ChargeTypeVO;
import com.efit.savaari.entity.CompanyAddressVO;
import com.efit.savaari.entity.CompanyBankDetailsVO;
import com.efit.savaari.entity.CompanyProfileVO;
import com.efit.savaari.entity.CustomerAddressVO;
import com.efit.savaari.entity.CustomerRateVO;
import com.efit.savaari.entity.CustomerVO;
import com.efit.savaari.entity.IndentsParticipantsVO;
import com.efit.savaari.entity.IndentsPitstopVO;
import com.efit.savaari.entity.IndentsVO;
import com.efit.savaari.entity.ListOfValues1VO;
import com.efit.savaari.entity.ListOfValuesVO;
import com.efit.savaari.entity.PlaceDetailsVO;
import com.efit.savaari.entity.RoutesDetailsVO;
import com.efit.savaari.entity.RoutesPetrolPumpsVO;
import com.efit.savaari.entity.RoutesPitstopVO;
import com.efit.savaari.entity.RoutesVO;
import com.efit.savaari.entity.TimeLineVO;
import com.efit.savaari.entity.TripsDocumentsVO;
import com.efit.savaari.entity.TripsLinkedVO;
import com.efit.savaari.entity.VendorRateVO;
import com.efit.savaari.entity.VendorResponseVO;
import com.efit.savaari.exception.ApplicationException;
import com.efit.savaari.repo.BranchRepo;
import com.efit.savaari.repo.ChargeTypeRepo;
import com.efit.savaari.repo.CompanyAddressRepo;
import com.efit.savaari.repo.CompanyBankDetailsRepo;
import com.efit.savaari.repo.CompanyProfileRepo;
import com.efit.savaari.repo.CustomerAddressRepo;
import com.efit.savaari.repo.CustomerRateRepo;
import com.efit.savaari.repo.CustomerRepo;
import com.efit.savaari.repo.DepartmentRepo;
import com.efit.savaari.repo.DesignationLeaveRepo;
import com.efit.savaari.repo.DesignationRepo;
import com.efit.savaari.repo.IndentsParticipantsRepo;
import com.efit.savaari.repo.IndentsPitstopRepo;
import com.efit.savaari.repo.IndentsRepo;
import com.efit.savaari.repo.IndustryRepo;
import com.efit.savaari.repo.ListOfValues1Repo;
import com.efit.savaari.repo.ListOfValuesRepo;
import com.efit.savaari.repo.PlaceDetailsRepo;
import com.efit.savaari.repo.RoutesDetailsRepo;
import com.efit.savaari.repo.RoutesPetrolPumpsRepo;
import com.efit.savaari.repo.RoutesPitstopRepo;
import com.efit.savaari.repo.RoutesRepo;
import com.efit.savaari.repo.TimeLineRepo;
import com.efit.savaari.repo.TripsDocumentsRepo;
import com.efit.savaari.repo.TripsLinkedRepo;
import com.efit.savaari.repo.UserLoginRolesRepo;
import com.efit.savaari.repo.UserRepo;
import com.efit.savaari.repo.VehicleTypeRepo;
import com.efit.savaari.repo.VendorRateRepo;
import com.efit.savaari.repo.VendorResponseRepo;
import com.efit.savaari.responseDTO.CompanyAddressResponseDTO;
import com.efit.savaari.responseDTO.CompanyBankDetailsResponseDTO;
import com.efit.savaari.responseDTO.CompanyProfileResponseDTO;

@Service
public class MasterServiceImpl implements MasterService {

	private final OtpAsyncService otpAsyncService;
	public static final Logger LOGGER = LoggerFactory.getLogger(MasterServiceImpl.class);

	@Autowired
	BranchRepo branchRepo;

	@Autowired
	DesignationLeaveRepo designationLeaveRepo;

	@Autowired
	UserLoginRolesRepo userLoginRolesRepo;

	@Autowired
	UserRepo userRepo;;

	@Autowired
	CustomerRepo customerRepo;

	@Autowired
	DepartmentRepo departmentRepo;

	@Autowired
	DesignationRepo designationRepo;

	@Autowired
	PaginationService paginationService;

	@Autowired
	IndentsRepo indentsRepo;

	@Autowired
	TimeLineRepo timeLineRepo;

	@Autowired
	VendorResponseRepo vendorResponseRepo;

	@Autowired
	TripsLinkedRepo tripsLinkedRepo;

	@Autowired
	TripsDocumentsRepo tripsDocumentsRepo;

	@Autowired
	PlaceDetailsRepo placeDetailsRepo;

	@Autowired
	VendorRateRepo vendorRateRepo;

	@Autowired
	CustomerRateRepo customerRateRepo;

	@Autowired
	RoutesRepo routesRepo;

	@Autowired
	RoutesPitstopRepo routesPitstopRepo;

	@Autowired
	RoutesPetrolPumpsRepo routesPetrolPumpsRepo;

	@Autowired
	RoutesDetailsRepo routesDetailsRepo;

	@Autowired
	VehicleTypeRepo vehicleTypeRepo;

	@Autowired
	IndentsPitstopRepo indentsPitstopRepo;

	@Autowired
	IndentsParticipantsRepo indentsParticipantsRepo;

	@Autowired
	ChargeTypeRepo chargeTypeRepo;
	
	@Autowired
	IndustryRepo industryRepo;
	
	@Autowired
	ListOfValuesRepo listOfValuesRepo;

	@Autowired
	ListOfValues1Repo listOfValues1Repo;
	
	@Autowired
	CustomerAddressRepo customerAddressRepo;

	
	@Autowired
	CompanyProfileRepo companyProfileRepo;
	
	@Autowired
	CompanyAddressRepo companyAddressRepo;

	@Autowired
	CompanyBankDetailsRepo companyBankDetailsRepo;
	
	MasterServiceImpl(OtpAsyncService otpAsyncService) {
		this.otpAsyncService = otpAsyncService;
	}

	// Branch

	@Override
	public List<BranchVO> getAllBranch(Long orgid) {
		return branchRepo.findAll(orgid);
	}

	@Override
	public Optional<BranchVO> getBranchById(Long branchid) {

		return branchRepo.findById(branchid);
	}

	@Override
	@Transactional
	public Map<String, Object> createUpdateBranch(BranchDTO branchDTO) throws Exception {
		BranchVO branchVO;
		String message = null;

		if (ObjectUtils.isEmpty(branchDTO.getId())) {
			// Check if the branch already exists for creation
			if (branchRepo.existsByBranchAndOrgId(branchDTO.getBranch(), branchDTO.getOrgId())) {
				String errorMessage = String.format("This Branch: %s Already Exists in This Organization",
						branchDTO.getBranch());
				throw new ApplicationException(errorMessage);
			}

			if (branchRepo.existsByBranchCodeAndOrgId(branchDTO.getBranchCode(), branchDTO.getOrgId())) {
				String errorMessage = String.format("This BranchCode: %s Already Exists in This Organization",
						branchDTO.getBranchCode());
				throw new ApplicationException(errorMessage);
			}

			// Create new branch
			branchVO = new BranchVO();
			branchVO.setCreatedBy(branchDTO.getCreatedBy());
			branchVO.setUpdatedBy(branchDTO.getCreatedBy());
			message = "Branch Created Successfully";
		} else {
			// Update existing branch
			branchVO = branchRepo.findById(branchDTO.getId())
					.orElseThrow(() -> new ApplicationException("Branch not found with id: " + branchDTO.getId()));

			branchVO.setUpdatedBy(branchDTO.getCreatedBy());

			if (!branchVO.getBranch().equalsIgnoreCase(branchDTO.getBranch())) {
				if (branchRepo.existsByBranchAndOrgId(branchDTO.getBranch(), branchDTO.getOrgId())) {
					String errorMessage = String.format("This Branch: %s Already Exists in This Organization",
							branchDTO.getBranch());
					throw new ApplicationException(errorMessage);
				}
				branchVO.setBranch(branchDTO.getBranch().toUpperCase());
			}

			if (!branchVO.getBranchCode().equalsIgnoreCase(branchDTO.getBranchCode())) {
				if (branchRepo.existsByBranchCodeAndOrgId(branchDTO.getBranchCode(), branchDTO.getOrgId())) {
					String errorMessage = String.format("This BranchCode: %s Already Exists in This Organization",
							branchDTO.getBranchCode());
					throw new ApplicationException(errorMessage);
				}
				branchVO.setBranchCode(branchDTO.getBranchCode().toUpperCase());
			}

			message = "Branch Updated Successfully";
		}

		getBranchVOFromBranchDTO(branchVO, branchDTO);
		branchRepo.save(branchVO);

		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("branchVO", branchVO);
		return response;
	}

	private void getBranchVOFromBranchDTO(BranchVO branchVO, BranchDTO branchDTO) {
		branchVO.setBranch(branchDTO.getBranch().toUpperCase());
		branchVO.setBranchCode(branchDTO.getBranchCode().toUpperCase());
		branchVO.setOrgId(branchDTO.getOrgId());
		branchVO.setAddressLine1(branchDTO.getAddressLine1());
		// branchVO.setAddressLine2(branchDTO.getAddressLine2());
		// branchVO.setPan(branchDTO.getPan());
		branchVO.setGstIn(branchDTO.getGstIn());
		branchVO.setContactPerson(branchDTO.getContactPerson());
		branchVO.setEmail(branchDTO.getEmail());
		branchVO.setPhone(branchDTO.getPhone());
		branchVO.setState(branchDTO.getState().toUpperCase());
		branchVO.setCity(branchDTO.getCity().toUpperCase());
		branchVO.setPinCode(branchDTO.getPinCode());
		branchVO.setCountry(branchDTO.getCountry().toUpperCase());
		// branchVO.setStateNo(branchDTO.getStateNo().toUpperCase());
		// branchVO.setStateCode(branchDTO.getStateCode().toUpperCase());
		// branchVO.setLccurrency(branchDTO.getLccurrency());
		branchVO.setCancelRemarks(branchDTO.getCancelRemarks());
		branchVO.setActive(branchDTO.isActive());
	}

	@Override
	public void deleteBranch(Long branchid) {
		branchRepo.deleteById(branchid);
	}

	@Override
	@Transactional
	public Map<String, Object> createUpdateCustomer(CustomerDTO dto) throws Exception {

	    CustomerVO vo;
	    String message;

	    // -------- UPDATE --------
	    if (dto.getId() != null) {

	        vo = customerRepo.findById(dto.getId())
	                .orElseThrow(() -> new ApplicationException("Customer not found"));

	        vo.setUpdatedBy(dto.getCreatedBy());
	        message = "Customer Updated Successfully";

	        // Remove old children
	        customerAddressRepo.deleteAll(vo.getCustomerAddressVO());
	        vo.getCustomerAddressVO().clear(); 

	    } else {
	        // -------- CREATE --------
	        vo = new CustomerVO();
	        vo.setCreatedBy(dto.getCreatedBy());
	        vo.setUpdatedBy(dto.getCreatedBy());
	        message = "Customer Created Successfully";
	    }

	    // Main field mapping
	    getCustomerVOFromCustomerDTO(vo, dto);

	    // Save main table first
	    CustomerVO saved = customerRepo.save(vo);

	    // -------- CHILD SAVE --------
	    if (dto.getCustomerAddressDTO() != null) {

	        List<CustomerAddressVO> list = new ArrayList<>();

	        for (CustomerAddressDTO a : dto.getCustomerAddressDTO()) {

	            CustomerAddressVO ch = new CustomerAddressVO();
	            ch.setPrimaryAddress(a.getPrimaryAddress());
	            ch.setAdditionalAddress(a.getAdditionalAddress());
	            ch.setCity(a.getCity());
	            ch.setState(a.getState());
	            ch.setType(a.getType());
	            ch.setPincode(a.getPincode());
	            ch.setCustomerVO(saved);

	            list.add(ch);
	        }

	        customerAddressRepo.saveAll(list);
	    }

	    // Reload child for response
	    saved.setCustomerAddressVO(customerAddressRepo.findByCustomerVO(saved));

	    // Response
	    Map<String, Object> response = new HashMap<>();
	    response.put("customerVO", saved);
	    response.put("message", message);

	    return response;
	}


	private void getCustomerVOFromCustomerDTO(CustomerVO vo, CustomerDTO dto) {
		vo.setCustomerCode(dto.getCustomerCode());
		vo.setCustomerName(dto.getCustomerName());
		vo.setPhoneNumber(dto.getPhoneNumber());
		vo.setEmail(dto.getEmail());
		vo.setGstNumber(dto.getGstNumber());
		vo.setPanNumber(dto.getPanNumber());
		vo.setSalesPerson(dto.getSalesPerson());
		vo.setCustomerType(dto.getCustomerType());

//	    vo.setAccountNumber(dto.getAccountNumber());
		vo.setPocName(dto.getPocName());
		vo.setPocEmail(dto.getPocEmail());
		vo.setPocNumber(dto.getPocNumber());

		vo.setActive(dto.isActive());
		vo.setCreatedBy(dto.getCreatedBy());
		vo.setOrgId(dto.getOrgId());

		// Convert to uppercase if needed
		vo.setBranchCode(dto.getBranchCode());
		vo.setBranch(dto.getBranch());
	}

	@Override
	public CustomerVO getCustomerById(Long id) throws ApplicationException {
		return customerRepo.findById(id).orElseThrow(() -> new ApplicationException("customer not found"));
	}

	@Override
	public Map<String, Object> getCustomerByOrgId(String branchCode,Long orgId, String search, int page, int count) {

		if (search != null) {
			search = search.trim();
			if (search.isEmpty()) {
				search = null;
			}
		}

		Pageable pageable = PageRequest.of(page - 1, count, Sort.by("customerName").ascending());
		Page<CustomerVO> customerPage = customerRepo.getCustomerByOrgId(branchCode,orgId, search, pageable);

		// return paginated response
		return paginationService.buildResponse(customerPage);

	}

	@Override
	public Map<String, Object> createUpdateIndents(IndentsDTO indentsDTO, List<MultipartFile> tripFiles)
			throws Exception {

		IndentsVO indentsVO;
		String message;

		if (indentsDTO.getId() != null) {
			indentsVO = indentsRepo.findById(indentsDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid Indents ID"));

			timeLineRepo.deleteAll(timeLineRepo.findByIndentsVO(indentsVO));
			vendorResponseRepo.deleteAll(vendorResponseRepo.findByIndentsVO(indentsVO));
			tripsLinkedRepo.deleteAll(tripsLinkedRepo.findByIndentsVO(indentsVO));
			tripsDocumentsRepo.deleteAll(tripsDocumentsRepo.findByIndentsVO(indentsVO));
			indentsPitstopRepo.deleteAll(indentsPitstopRepo.findByIndentsVO(indentsVO));
			indentsParticipantsRepo.deleteAll(indentsParticipantsRepo.findByIndentsVO(indentsVO));

			indentsVO.setUpdatedBy(indentsDTO.getCreatedBy());
			message = "Indents Updated Successfully";

		} else {

			indentsVO = new IndentsVO();
			indentsVO.setCreatedBy(indentsDTO.getCreatedBy());
			indentsVO.setUpdatedBy(indentsDTO.getCreatedBy());
			message = "Indents Created Successfully";
		}

		// map DTO to VO
		createUpdateIndentsDTOByIndentsVO(indentsDTO, indentsVO);

		// ---------------------- FILE MAPPING -----------------------
		if (tripFiles != null && indentsVO.getTripsDocumentsVO() != null) {

			List<TripsDocumentsVO> docs = indentsVO.getTripsDocumentsVO();

			for (int i = 0; i < docs.size(); i++) {
				if (i < tripFiles.size()) { // ensure matching index
					MultipartFile file = tripFiles.get(i);
					if (!file.isEmpty()) {
						docs.get(i).setContractAttachment(file.getBytes());
					}
				}
			}
		}
		// -----------------------------------------------------------

		indentsRepo.save(indentsVO);

		Map<String, Object> response = new HashMap<>();
		response.put("indentsVO", indentsVO);
		response.put("message", message);

		return response;
	}

	private void createUpdateIndentsDTOByIndentsVO(IndentsDTO dto, IndentsVO vo) {

		// --- Simple Fields ---
		vo.setStatus(dto.getStatus());
		vo.setCustomer(dto.getCustomer());
		vo.setOrigin(dto.getOrigin());
		vo.setDestination(dto.getDestination());
		vo.setVechicleType(dto.getVechicleType());
		vo.setWeight(dto.getWeight());
		vo.setRoute(dto.getRoute());
		vo.setPitStop(dto.isPitStop());
		vo.setNumberOfVechicles(dto.getNumberOfVechicles());
		vo.setCustomerRate(dto.getCustomerRate());
		vo.setVendorRateVehicles(dto.getVendorRateVehicles());
		vo.setExtraInfo(dto.getExtraInfo());
		vo.setPlacementDate(dto.getPlacementDate());
		vo.setOrderType(dto.getOrderType());
		vo.setDockerNo(dto.getDockerNo());
		vo.setTripType(dto.getTripType());
		vo.setMaterialType(dto.getMaterialType());
		vo.setOverTimeHours(dto.getOverTimeHours());
		vo.setVendorRank(dto.getVendorRank());
		vo.setOrgId(dto.getOrgId());
		vo.setOriginPoc(dto.getOriginPoc());
		vo.setDestinationPoc(dto.getDestinationPoc());

		// ----------- TimeLine Mapping -----------
		List<TimeLineVO> timeLineList = new ArrayList<>();

		if (dto.getTimeLineDTO() != null) {

			dto.getTimeLineDTO().forEach(u -> {

				TimeLineVO t = new TimeLineVO();

				t.setEvent(u.getEvent());
				t.setTime(u.getTime());
				t.setUser(u.getUser());

				t.setIndentsVO(vo); // parent link

				timeLineList.add(t);
			});
		}

		List<IndentsParticipantsVO> indentsParticipantsList = new ArrayList<>();

		if (dto.getIndentsParticipantsDTO() != null) {

			dto.getIndentsParticipantsDTO().forEach(u -> {

				IndentsParticipantsVO t = new IndentsParticipantsVO();

				t.setVendor(u.getVendor());
				t.setVendorRate(u.getVendorRate());
				t.setRanks(u.getRanks());
				t.setVendorResponse(u.getVendorResponse());

				t.setIndentsVO(vo); // parent link

				indentsParticipantsList.add(t);
			});
		}

		List<IndentsPitstopVO> indentsPitstopVOList = new ArrayList<>();

		if (dto.getIndentsPitstopDTO() != null) {

			dto.getIndentsPitstopDTO().forEach(u -> {

				IndentsPitstopVO t = new IndentsPitstopVO();

				t.setPitstop(u.getPitstop());

				t.setIndentsVO(vo); // parent link

				indentsPitstopVOList.add(t);
			});
		}

		// ----------- Vendor Response Mapping -----------
		List<VendorResponseVO> vendorResponseList = new ArrayList<>();

		if (dto.getVendorResponseDTO() != null) {

			dto.getVendorResponseDTO().forEach(r -> {

				VendorResponseVO v = new VendorResponseVO();

				v.setLrNumber(r.getLrNumber());
				v.setVehicleNumber(r.getVehicleNumber());
				v.setDriverNumber(r.getDriverNumber());
				v.setDriverName(r.getDriverName());

				v.setIndentsVO(vo);

				vendorResponseList.add(v);
			});
		}

		// ----------- Trips Linked Mapping -----------
		List<TripsLinkedVO> tripsLinkedList = new ArrayList<>();

		if (dto.getTripsLinkedDTO() != null) {

			dto.getTripsLinkedDTO().forEach(t -> {

				TripsLinkedVO tl = new TripsLinkedVO();

				tl.setTrips(t.getTrips());
				tl.setOrigin(t.getOrigin());
				tl.setDestination(t.getDestination());
				tl.setVechile(t.getVechile());
				tl.setStatus(t.getStatus());

				tl.setIndentsVO(vo);

				tripsLinkedList.add(tl);
			});
		}

		// ----------- Trips Documents Mapping -----------
		List<TripsDocumentsVO> tripsDocumentsList = new ArrayList<>();

		if (dto.getTripsDocumentsDTO() != null) {

			dto.getTripsDocumentsDTO().forEach(d -> {

				TripsDocumentsVO dVO = new TripsDocumentsVO();

				dVO.setDocType(d.getDocType());
				dVO.setRemarks(d.getRemarks());
				dVO.setTrip(d.getTrip());
				dVO.setVechileNumber(d.getVechileNumber());
				dVO.setContractAttachment(d.getContractAttachment()); // updated later if file upload

				dVO.setIndentsVO(vo);

				tripsDocumentsList.add(dVO);
			});
		}

		// ----------- SET CHILD LISTS TO PARENT ENTITY -----------
		vo.setTimeLineVO(timeLineList);
		vo.setVendorResponseVO(vendorResponseList);
		vo.setTripsLinkedVO(tripsLinkedList);
		vo.setTripsDocumentsVO(tripsDocumentsList);
		vo.setIndentsPitstopVO(indentsPitstopVOList);
		vo.setIndentsParticipantsVO(indentsParticipantsList);

	}

	@Override
	public IndentsVO getIndentsById(Long id) throws ApplicationException {
		return indentsRepo.findById(id).orElseThrow(() -> new ApplicationException("Indents not found"));
	}

	@Override
	public Map<String, Object> getIndentsByOrgId(String branchCode,Long orgId, String search, int page, int count) {

		if (search != null) {
			search = search.trim();
			if (search.isEmpty()) {
				search = null;
			}
		}

		Pageable pageable = PageRequest.of(page - 1, count, Sort.by("customer").ascending());
		Page<IndentsVO> indentsPage = indentsRepo.getIndentsByOrgId(branchCode,orgId, search, pageable);

		// return paginated response
		return paginationService.buildResponse(indentsPage);

	}

	@Override
	public Map<String, Object> createUpdatePlaceDetails(PlaceDetailsDTO placeDetailsDTO) throws Exception {

		PlaceDetailsVO placeDetailsVO;
		String message;

		if (placeDetailsDTO.getId() != null) {
			placeDetailsVO = placeDetailsRepo.findById(placeDetailsDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid PlaceDetails ID"));

			if (placeDetailsDTO.getId() != null) {
				PlaceDetailsVO existing = placeDetailsRepo.findByPlaceAndOrgIdAndBranchCode(placeDetailsDTO.getPlace(),
						placeDetailsDTO.getOrgId(), placeDetailsDTO.getBranchCode());

				if (existing != null && !existing.getId().equals(placeDetailsDTO.getId())) {
					throw new ApplicationException("Place already exists for this Branch and Organization.");
				}
			}

//	        timeLineRepo.deleteAll(timeLineRepo.findByIndentsVO(indentsVO));

			placeDetailsVO.setUpdatedBy(placeDetailsDTO.getCreatedBy());
			message = "PlaceDetails Updated Successfully";

		} else {

			boolean isDuplicate = placeDetailsRepo.existsByPlaceAndOrgIdAndBranchCode(placeDetailsDTO.getPlace(),
					placeDetailsDTO.getOrgId(), placeDetailsDTO.getBranchCode());

			// If creating new
			if (placeDetailsDTO.getId() == null && isDuplicate) {
				throw new ApplicationException("Place already exists for this Branch and Organization.");
			}

			placeDetailsVO = new PlaceDetailsVO();
			placeDetailsVO.setCreatedBy(placeDetailsDTO.getCreatedBy());
			placeDetailsVO.setUpdatedBy(placeDetailsDTO.getCreatedBy());
			message = "PlaceDetails Created Successfully";
		}

		// map DTO to VO
		createUpdatePlaceDetailsDTOByPlaceDetailsVO(placeDetailsDTO, placeDetailsVO);

		placeDetailsRepo.save(placeDetailsVO);

		Map<String, Object> response = new HashMap<>();
		response.put("placeDetailsVO", placeDetailsVO);
		response.put("message", message);

		return response;
	}

	private void createUpdatePlaceDetailsDTOByPlaceDetailsVO(PlaceDetailsDTO dto, PlaceDetailsVO vo) {

		vo.setPlace(dto.getPlace());
		vo.setCreatedBy(dto.getCreatedBy());
		vo.setOrgId(dto.getOrgId());
		vo.setBranchCode(dto.getBranchCode());
		vo.setBranch(dto.getBranch());

	}

	@Override
	public PlaceDetailsVO getPlaceDetailsById(Long id) throws ApplicationException {
		return placeDetailsRepo.findById(id).orElseThrow(() -> new ApplicationException("PlaceDetails not found"));
	}

	@Override
	public Map<String, Object> getPlaceDetailsByOrgId(String branchCode, String search, int page, int count) {

		if (search != null) {
			search = search.trim();
			if (search.isEmpty()) {
				search = null;
			}
		}

		Pageable pageable = PageRequest.of(page - 1, count, Sort.by("place").ascending());
		Page<PlaceDetailsVO> placePage = placeDetailsRepo.getPlaceDetailsByOrgId(branchCode, search, pageable);

		// return paginated response
		return paginationService.buildResponse(placePage);

	}

	@Override
	public Map<String, Object> createUpdateVendorRate(VendorRateDTO vendorRateDTO) throws Exception {

		VendorRateVO vendorRateVO;
		String message;

		if (vendorRateDTO.getId() != null) {
			vendorRateVO = vendorRateRepo.findById(vendorRateDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid VendorRate ID"));

			vendorRateVO.setUpdatedBy(vendorRateDTO.getCreatedBy());
			message = "vendorRate Updated Successfully";

		} else {

			vendorRateVO = new VendorRateVO();
			vendorRateVO.setCreatedBy(vendorRateDTO.getCreatedBy());
			vendorRateVO.setUpdatedBy(vendorRateDTO.getCreatedBy());
			message = "vendorRate Created Successfully";
		}

		// map DTO to VO
		createUpdateVendorRateDTOByVendorRateVO(vendorRateDTO, vendorRateVO);

		vendorRateRepo.save(vendorRateVO);

		Map<String, Object> response = new HashMap<>();
		response.put("vendorRateVO", vendorRateVO);
		response.put("message", message);

		return response;
	}

	private void createUpdateVendorRateDTOByVendorRateVO(VendorRateDTO dto, VendorRateVO vo) {

		vo.setState(dto.getState());
		vo.setVendor(dto.getVendor());
		vo.setNamingSeries(dto.getNamingSeries());
		vo.setEffectiveTo(dto.getEffectiveTo());
		vo.setEffectiveFrom(dto.getEffectiveFrom());
		vo.setPriority(dto.getPriority());
		vo.setOrigin(dto.getOrigin());
		vo.setDestination(dto.getDestination());
		vo.setRate(dto.getRate());
		vo.setVehicleType(dto.getVehicleType());
		vo.setRateType(dto.getRateType());
		vo.setWeight(dto.getWeight());
		vo.setDetentioncharge(dto.getDetentioncharge());
		vo.setRank(dto.getRank());
		vo.setUnloadingCharges(dto.getUnloadingCharges());
		vo.setRemarks(dto.getRemarks());
		vo.setExtraCharges(dto.getExtraCharges());
		vo.setActive(dto.isActive());
		vo.setOrgId(dto.getOrgId());
		vo.setBranchCode(dto.getBranchCode());
		vo.setBranch(dto.getBranch());

	}

	@Override
	public VendorRateVO getVendorRateById(Long id) throws ApplicationException {
		return vendorRateRepo.findById(id).orElseThrow(() -> new ApplicationException("VendorRate not found"));
	}

	@Override
	public Map<String, Object> getVendorRateByOrgId(String branchCode,Long orgId, String search, int page, int count) {

		if (search != null) {
			search = search.trim();
			if (search.isEmpty()) {
				search = null;
			}
		}

		Pageable pageable = PageRequest.of(page - 1, count, Sort.by("vendor").ascending());
		Page<VendorRateVO> vendorRatePage = vendorRateRepo.getVendorRateByOrgId(branchCode,orgId, search, pageable);

		// return paginated response
		return paginationService.buildResponse(vendorRatePage);

	}

	@Override
	public Map<String, Object> createUpdateCustomerRate(CustomerRateDTO customerRateDTO) throws Exception {

		CustomerRateVO customerRateVO;
		String message;

		if (customerRateDTO.getId() != null) {
			customerRateVO = customerRateRepo.findById(customerRateDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid CustomerRate ID"));

			customerRateVO.setUpdatedBy(customerRateDTO.getCreatedBy());
			message = "CustomerRate Updated Successfully";

		} else {

			customerRateVO = new CustomerRateVO();
			customerRateVO.setCreatedBy(customerRateDTO.getCreatedBy());
			customerRateVO.setUpdatedBy(customerRateDTO.getCreatedBy());
			message = "CustomerRate Created Successfully";
		}

//		if (customerRateDTO.getIndustryId() != null) {
//			IndustryVO industryVO = industryRepo.findById(customerRateDTO.getIndustryId())
//					.orElseThrow(() -> new ApplicationException("Invalid Industry ID"));
//			customerRateVO.setIndustry(industryVO);
//		}
		
		// map DTO to VO
		createUpdateCustomerRateDTOByCustomerRateVO(customerRateDTO, customerRateVO);

		customerRateRepo.save(customerRateVO);

		Map<String, Object> response = new HashMap<>();
		response.put("customerRateVO", customerRateVO);
		response.put("message", message);

		return response;
	}

	private void createUpdateCustomerRateDTOByCustomerRateVO(CustomerRateDTO dto, CustomerRateVO vo) {

		vo.setCustomer(dto.getCustomer());
		vo.setNamingSeries(dto.getNamingSeries());
		vo.setOrigin(dto.getOrigin());
		vo.setDestination(dto.getDestination());

		vo.setRate(dto.getRate());
		vo.setVehicleType(dto.getVehicleType());
		vo.setRateType(dto.getRateType());
		vo.setWeight(dto.getWeight());

		vo.setActive(dto.isActive());
		vo.setOrgId(dto.getOrgId());
		vo.setBranchCode(dto.getBranchCode());
		vo.setBranch(dto.getBranch());
	}

	@Override
	public CustomerRateVO getCustomerRateById(Long id) throws ApplicationException {
		return customerRateRepo.findById(id).orElseThrow(() -> new ApplicationException("CustomerRate not found"));
	}

	@Override
	public Map<String, Object> getCustomerRateByOrgId(String branchCode,Long orgId, String search, int page, int count) {

		if (search != null) {
			search = search.trim();
			if (search.isEmpty()) {
				search = null;
			}
		}

		Pageable pageable = PageRequest.of(page - 1, count, Sort.by("customer").ascending());
		Page<CustomerRateVO> customerRatePage = customerRateRepo.getCustomerRateByOrgId(branchCode,orgId, search, pageable);

		// return paginated response
		return paginationService.buildResponse(customerRatePage);

	}

	@Override
	public List<Map<String, Object>> getCustomerNameByOrgId(String branchCode,Long orgId) {

		if (branchCode != null && branchCode.trim().isEmpty()) {
			branchCode = null;
		}

		List<Object[]> results = customerRepo.getCustomerNameByOrgId(branchCode,orgId);
		List<Map<String, Object>> list = new ArrayList<>();

		for (Object[] row : results) {
			Map<String, Object> map = new HashMap<>();

			map.put("customercode", row[0] == null ? "" : row[0].toString());
			map.put("customername", row[1] == null ? "" : row[1].toString());

			list.add(map);
		}

		return list;
	}

	@Override
	public Map<String, Object> createUpdateRoutes(RoutesDTO routesDTO) throws Exception {

		RoutesVO routesVO;
		String message;

		if (routesDTO.getId() != null) {
			routesVO = routesRepo.findById(routesDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid Routes ID"));

			routesPitstopRepo.deleteAll(routesPitstopRepo.findByRoutesVO(routesVO));
			routesPetrolPumpsRepo.deleteAll(routesPetrolPumpsRepo.findByRoutesVO(routesVO));
			routesDetailsRepo.deleteAll(routesDetailsRepo.findByRoutesVO(routesVO));

			routesVO.setUpdatedBy(routesDTO.getCreatedBy());
			message = "Routes Updated Successfully";

		} else {

			routesVO = new RoutesVO();
			routesVO.setCreatedBy(routesDTO.getCreatedBy());
			routesVO.setUpdatedBy(routesDTO.getCreatedBy());
			message = "Routes Created Successfully";
		}

		// map DTO to VO
		createUpdateRoutesDTOByRoutesVO(routesDTO, routesVO);

		routesRepo.save(routesVO);

		Map<String, Object> response = new HashMap<>();
		response.put("routesVO", routesVO);
		response.put("message", message);

		return response;
	}

	private void createUpdateRoutesDTOByRoutesVO(RoutesDTO dto, RoutesVO vo) {

		// ====== Parent fields mapping (DTO -> VO)
		vo.setCustomer(dto.getCustomer());
		vo.setOrigin(dto.getOrigin());
		vo.setDestination(dto.getDestination());
		vo.setVehicleType(dto.getVehicleType());
		vo.setMileage(dto.getMileage());
		vo.setFuelRate(dto.getFuelRate());
		vo.setShowpumps(dto.isShowpumps());
		vo.setRoute(dto.getRoute());
		vo.setTat(dto.getTat());
		vo.setOrgId(dto.getOrgId());
		vo.setBranchCode(dto.getBranchCode());
		vo.setBranchName(dto.getBranchName());
		vo.setActive(dto.isActive());

		// ===========================================
		// CHILD TABLE 1: PITSTOPS
		// ===========================================

		if (dto.getRoutesPitstopDTO() != null) {

			List<RoutesPitstopVO> pitstopList = new ArrayList<>();

			for (RoutesPitstopDTO pitDTO : dto.getRoutesPitstopDTO()) {
				RoutesPitstopVO pitVO = new RoutesPitstopVO();
				pitVO.setPitShop(pitDTO.getPitShop());
				pitVO.setRoutesVO(vo); // FK
				pitstopList.add(pitVO);
			}

			vo.setRoutesPitStopVO(pitstopList);
		}

		// ===========================================
		// CHILD TABLE 2: PETROL PUMPS
		// ===========================================

		if (dto.getRoutesPetrolPumpsDTO() != null) {

			List<RoutesPetrolPumpsVO> petrolList = new ArrayList<>();

			for (RoutesPetrolPumpsDTO pDTO : dto.getRoutesPetrolPumpsDTO()) {
				RoutesPetrolPumpsVO pumpVO = new RoutesPetrolPumpsVO();
				pumpVO.setName(pDTO.getName());
				pumpVO.setAddress(pDTO.getAddress());
				pumpVO.setCity(pDTO.getCity());
				pumpVO.setState(pDTO.getState());
				pumpVO.setType(pDTO.getType());
				pumpVO.setRoutesVO(vo);
				petrolList.add(pumpVO);
			}

			vo.setRoutesPetrolPumpsVO(petrolList);
		}

		// ===========================================
		// CHILD TABLE 3: ROUTES DETAILS (NEW)
		// ===========================================

		if (dto.getRoutesDetailsDTO() != null) {

			List<RoutesDetailsVO> detailsList = new ArrayList<>();

			for (RoutesDetailsDTO dDTO : dto.getRoutesDetailsDTO()) {

				RoutesDetailsVO detailsVO = new RoutesDetailsVO();

				detailsVO.setName(dDTO.getName());
				detailsVO.setDistance(dDTO.getDistance());
				detailsVO.setNoOfTolls(dDTO.getNoOfTolls());
				detailsVO.setTollCost(dDTO.getTollCost());
				detailsVO.setFuelCost(dDTO.getFuelCost());
				detailsVO.setTotalCost(dDTO.getTotalCost());
				detailsVO.setSavings(dDTO.getSavings());
				detailsVO.setDuration(dDTO.getDuration());
				detailsVO.setDefaults(dDTO.isDefaults());

				detailsVO.setRoutesVO(vo); // FK

				detailsList.add(detailsVO);
			}

			vo.setRoutesDetailsVO(detailsList);
		}
	}

	@Override
	public RoutesVO getRoutesById(Long id) throws ApplicationException {
		return routesRepo.findById(id).orElseThrow(() -> new ApplicationException("Routes not found"));
	}

	@Override
	public Map<String, Object> getRoutesByOrgId(String branchCode,Long orgId, String search, int page, int count) {

		if (search != null) {
			search = search.trim();
			if (search.isEmpty()) {
				search = null;
			}
		}

		Pageable pageable = PageRequest.of(page - 1, count, Sort.by("customer").ascending());
		Page<RoutesVO> routesPage = routesRepo.getRoutesByOrgId(branchCode,orgId, search, pageable);

		// return paginated response
		return paginationService.buildResponse(routesPage);

	}

	@Override
	public List<Map<String, Object>> getVehicleTypeListByOrgId(String branchCode,Long orgId) {

		if (branchCode != null && branchCode.trim().isEmpty()) {
			branchCode = null;
		}

		List<String> results = vehicleTypeRepo.getVehicleTypeListByOrgId(branchCode,orgId);

		List<Map<String, Object>> list = new ArrayList<>();

		for (String value : results) {

			Map<String, Object> map = new HashMap<>();
			map.put("vehicleType", value == null ? "" : value);

			list.add(map);
		}

		return list;
	}

	@Override
	public List<Map<String, Object>> getOriginAndDestinationList(String branchCode,Long orgId) {

		if (branchCode != null && branchCode.trim().isEmpty()) {
			branchCode = null;
		}

		List<Object[]> results = routesRepo.getOriginAndDestinationList(branchCode,orgId);
		List<Map<String, Object>> list = new ArrayList<>();

		for (Object[] row : results) {
			Map<String, Object> map = new HashMap<>();

			map.put("origin", row[0] == null ? "" : row[0].toString());
			map.put("destination", row[1] == null ? "" : row[1].toString());

			list.add(map);
		}

		return list;
	}

	@Override
	public List<Map<String, Object>> getVendorRateAndOriginList(String branchCode,Long orgId) {

		if (branchCode != null && branchCode.trim().isEmpty()) {
			branchCode = null;
		}

		List<Object[]> results = vendorRateRepo.getVendorRateAndOriginList(branchCode,orgId);
		List<Map<String, Object>> list = new ArrayList<>();

		for (Object[] row : results) {
			Map<String, Object> map = new HashMap<>();

			map.put("vendor", row[0] == null ? "" : row[0].toString());
			map.put("rate", row[1] == null ? "" : row[1].toString());
			map.put("origin", row[2] == null ? "" : row[2].toString());
			map.put("destination", row[3] == null ? "" : row[3].toString());

			list.add(map);
		}

		return list;
	}

	@Override
	public List<Map<String, Object>> getCustomerRateAndOriginList(String branchCode,Long orgId) {

		if (branchCode != null && branchCode.trim().isEmpty()) {
			branchCode = null;
		}

		List<Object[]> results = customerRateRepo.getCustomerRateAndOriginList(branchCode,orgId);
		List<Map<String, Object>> list = new ArrayList<>();

		for (Object[] row : results) {
			Map<String, Object> map = new HashMap<>();

			map.put("customer", row[0] == null ? "" : row[0].toString());
			map.put("rate", row[1] == null ? "" : row[1].toString());
			map.put("origin", row[2] == null ? "" : row[2].toString());
			map.put("destination", row[3] == null ? "" : row[3].toString());

			list.add(map);
		}

		return list;
	}

	// CHARGETYPE

	@Override
	@Transactional
	public Map<String, Object> createUpdateChargeType(ChargeTypeDTO dto) throws ApplicationException {

		ChargeTypeVO vo;
		String message;

		// ========== UPDATE ==========
		if (dto.getId() != null) {

			vo = chargeTypeRepo.findById(dto.getId())
					.orElseThrow(() -> new ApplicationException("Invalid ChargeType ID"));

			vo.setUpdatedBy(dto.getCreatedBy());
			message = "chargeType Updated Successfully";

		} else {
			// ========== CREATE ==========
			vo = new ChargeTypeVO();
			vo.setCreatedBy(dto.getCreatedBy());
			vo.setUpdatedBy(dto.getCreatedBy());
			message = "chargeType Created Successfully";
		}

		// ===== MAP BASIC FIELDS =====
		mapChargeTypeDTOtoVO(dto, vo);

		// ===== SAVE =====
		chargeTypeRepo.save(vo);

		// ===== RESPONSE =====
		Map<String, Object> response = new HashMap<>();
		response.put("chargeTypeVO", vo);
		response.put("message", message);

		return response;
	}

	private void mapChargeTypeDTOtoVO(ChargeTypeDTO dto, ChargeTypeVO vo) {

		vo.setChargeType(dto.getChargeType());
		vo.setUnit(dto.getUnit());
		vo.setActive(dto.isActive());
		vo.setCreatedBy(dto.getCreatedBy());
		vo.setOrgId(dto.getOrgId());
		vo.setBranchCode(dto.getBranchCode());
		vo.setBranch(dto.getBranch());
	}
	
	@Override
	public ChargeTypeVO getChargeTypeById(Long id) throws ApplicationException {
		return chargeTypeRepo.findById(id).orElseThrow(() -> new ApplicationException("ChargeType not found"));
	}
	
	
	@Override
	public Map<String, Object> getChargeTypeByOrgId(String branchCode, Long orgId,String search, int page, int count) {

		if (search != null) {
			search = search.trim();
			if (search.isEmpty()) {
				search = null;
			}
		}

		Pageable pageable = PageRequest.of(page - 1, count, Sort.by("chargetype").ascending());
		Page<ChargeTypeVO> chargeTypePage = chargeTypeRepo.getChargeTypeByOrgId(branchCode,orgId, search, pageable);

		// return paginated response
		return paginationService.buildResponse(chargeTypePage);

	}
	
	@Override
	public Map<String, Object> getChargeTypeList(String branchCode,Long orgId, String search, int page, int count) {

		if (search != null) {
			search = search.trim();
			if (search.isEmpty()) {
				search = null;
			}
		}

		Pageable pageable = PageRequest.of(page - 1, count, Sort.by("chargetype").ascending());
		Page<Object[]> rawPage = chargeTypeRepo.getChargeTypeList(branchCode,orgId, search, pageable);

		// Convert to key:value pair list
		List<Map<String, Object>> mappedList = rawPage.getContent().stream().map(r -> {
			Map<String, Object> map = new HashMap<>();
			map.put("id", r[0]);            // column 1
			map.put("chargeType", r[1]);    // column 2
			return map;
		}).collect(Collectors.toList());
		
		// Convert mapped list back into Page object
		Page<Map<String, Object>> finalPage = new PageImpl<>(
				mappedList,
				pageable,
				rawPage.getTotalElements()
		);

		// pagination service remains untouched
		return paginationService.buildResponse(finalPage);
	}

	
	// ListOfValues

		@Override
		public List<ListOfValuesVO> getListOfValuesById(Long id) {
			List<ListOfValuesVO> listOfValuesVO = new ArrayList<>();
			if (ObjectUtils.isNotEmpty(id)) {
				LOGGER.info("Successfully Received  ListOfValues BY Id : {}", id);
				listOfValuesVO = listOfValuesRepo.getListOfValuesById(id);
			} else {
				LOGGER.info("Successfully Received  ListOfValues For All Id.");
				listOfValuesVO = listOfValuesRepo.findAll();
			}
			return listOfValuesVO;
		}

		@Override
		public List<ListOfValuesVO> getListOfValuesByOrgId(Long orgid) {
			List<ListOfValuesVO> listOfValuesVO = new ArrayList<>();
			if (ObjectUtils.isNotEmpty(orgid)) {
				LOGGER.info("Successfully Received  ListOfValues BY OrgId : {}", orgid);
				listOfValuesVO = listOfValuesRepo.getListOfValuesByOrgId(orgid);
			} else {
				LOGGER.info("Successfully Received  ListOfValues For All OrgId.");
				listOfValuesVO = listOfValuesRepo.findAll();
			}
			return listOfValuesVO;
		}

		@Override
		public ListOfValuesVO updateCreateListOfValues(@Valid ListOfValuesDTO listOfValuesDTO) throws ApplicationException {
			ListOfValuesVO listOfValuesVO = new ListOfValuesVO();
			boolean isUpdate = false;
			if (ObjectUtils.isNotEmpty(listOfValuesDTO.getId())) {
				isUpdate = true;
				listOfValuesVO = listOfValuesRepo.findById(listOfValuesDTO.getId())
						.orElseThrow(() -> new ApplicationException("Invalid ListOfValues details"));
				listOfValuesVO.setUpdatedBy(listOfValuesDTO.getCreatedBy());
			}

			else {
				if (listOfValuesRepo.existsByListCodeAndOrgId(listOfValuesDTO.getListCode(), listOfValuesDTO.getOrgId())) {
					throw new ApplicationException("ListCode already Exists");
				}
				if (listOfValuesRepo.existsByListDescriptionAndOrgId(listOfValuesDTO.getListDescription(),
						listOfValuesDTO.getOrgId())) {
					throw new ApplicationException("ListDescription already Exists");
				}
				listOfValuesVO.setUpdatedBy(listOfValuesDTO.getCreatedBy());
				listOfValuesVO.setCreatedBy(listOfValuesDTO.getCreatedBy());
			}

			if (isUpdate) {
				ListOfValuesVO listOfValues = listOfValuesRepo.findById(listOfValuesDTO.getId()).orElse(null);
				if (!listOfValues.getListCode().equals(listOfValuesDTO.getListCode())) {
					if (listOfValuesRepo.existsByListCodeAndOrgId(listOfValuesDTO.getListCode(),
							listOfValuesDTO.getOrgId())) {
						throw new ApplicationException("ListCode already Exists");
					}
					if (!listOfValues.getListDescription().equals(listOfValuesDTO.getListDescription())) {
						if (listOfValuesRepo.existsByListDescriptionAndOrgId(listOfValuesDTO.getListDescription(),
								listOfValuesDTO.getOrgId())) {
							throw new ApplicationException("ListDescription already Exists");
						}

					}
				}
			}
			getListOfValuesVOFromTypesOfValuesDTO(listOfValuesDTO, listOfValuesVO);
			listOfValuesVO = listOfValuesRepo.save(listOfValuesVO);

			List<ListOfValues1VO> listOfValues1VOList = listOfValues1Repo.findBylistOfValuesVO(listOfValuesVO);
			listOfValues1Repo.deleteAll(listOfValues1VOList);

			List<ListOfValues1VO> listOfValues1VOs = new ArrayList<>();
			if (listOfValuesDTO.getListOfValues1DTO() != null) {
				for (ListOfValues1DTO listOfValues1DTO : listOfValuesDTO.getListOfValues1DTO()) {

					ListOfValues1VO listOfValues1VO = new ListOfValues1VO();
					listOfValues1VO.setValueCode(listOfValues1DTO.getValueCode().toUpperCase());
					listOfValues1VO.setSNo(listOfValues1DTO.getSNo());
					listOfValues1VO.setValueDescription(listOfValues1DTO.getValueDescription().toUpperCase());
					listOfValues1VO.setActive(listOfValues1DTO.isActive());
					listOfValues1VO.setListOfValuesVO(listOfValuesVO);
					listOfValues1VOs.add(listOfValues1VO);
				}
			}

			listOfValuesVO.setListOfValues1VO(listOfValues1VOs);
			return listOfValuesRepo.save(listOfValuesVO);

		}

		private void getListOfValuesVOFromTypesOfValuesDTO(@Valid ListOfValuesDTO listOfValuesDTO,
				ListOfValuesVO listOfValuesVO) {
			listOfValuesVO.setListCode(listOfValuesDTO.getListCode());
			listOfValuesVO.setOrgId(listOfValuesDTO.getOrgId());
			listOfValuesVO.setListDescription(listOfValuesDTO.getListDescription());
			listOfValuesVO.setActive(listOfValuesDTO.isActive());

		}

		
		@Override
		public List<Map<String, Object>> getValueDescriptionByListOfValues(Long orgId,String listDescription) {

			List<Object[]> results = customerRepo.getValueDescriptionByListOfValues(orgId,listDescription);
			List<Map<String, Object>> list = new ArrayList<>();

			for (Object[] row : results) {
				Map<String, Object> map = new HashMap<>();

				map.put("valuedescription", row[0] == null ? "" : row[0].toString());
				map.put("valueCode", row[1] == null ? "" : row[1].toString());

				list.add(map);
			}

			return list;
		}

		
		 @Override
		    public Map<String, Object> createUpdateCompanyProfile(CompanyProfileDTO dto, MultipartFile image) {

		        CompanyProfileVO vo;
		        String message;

		        
		        // ===== FIND OR CREATE =====
		        if (dto.getId() != null) {
		            vo = companyProfileRepo.findById(dto.getId())
		                    .orElseThrow(() -> new RuntimeException("Invalid CompanyProfile ID"));
		            vo.setUpdatedBy(dto.getCreatedBy());
		            List<CompanyAddressVO> companyAddressVO = companyAddressRepo.findByCompanyProfileVO(vo);
		            companyAddressRepo.deleteAll(companyAddressVO);
		            
		            List<CompanyBankDetailsVO> companyBankDetailsVO = companyBankDetailsRepo.findByCompanyProfileVO(vo);
		            companyBankDetailsRepo.deleteAll(companyBankDetailsVO);

		            message = "Company Profile Updated Successfully";
		        } else {
		            vo = new CompanyProfileVO();
		            vo.setCreatedBy(dto.getCreatedBy());
		            vo.setUpdatedBy(dto.getCreatedBy());
		            message = "Company Profile Created Successfully";
		        }

		        // ===== MAP DTO → VO =====
		        mapCompanyProfileDTOtoVO(dto, vo);

		        // ===== IMAGE SAVE =====
		        if (image != null && !image.isEmpty()) {
		            try {
		                vo.setCompanyLogo(image.getBytes());
		            } catch (IOException e) {
		                throw new RuntimeException("Failed to upload company logo", e);
		            }
		        }

		        // ===== SAVE PARENT + CHILD =====
		        vo = companyProfileRepo.save(vo);

		        // ===== MAP VO → RESPONSE DTO =====
		        CompanyProfileResponseDTO responseDTO = mapToCompanyProfileResponseDTO(vo);

		        Map<String, Object> response = new HashMap<>();
		        response.put("companyProfile", responseDTO);
		        response.put("message", message);

		        return response;
		    }

		    // =========================================================
		    // =============== DTO → VO MAPPING =========================
		    // =========================================================
		    private void mapCompanyProfileDTOtoVO(CompanyProfileDTO dto, CompanyProfileVO vo) {

		        vo.setCompanyCode(dto.getCompanyCode());
		        vo.setCompanyName(dto.getCompanyName());
		        vo.setOwnerName(dto.getOwnerName());
		        vo.setEmailAddress(dto.getEmailAddress());
		        vo.setPhoneNo(dto.getPhoneNo());
		        vo.setGstNo(dto.getGstNo());
		        vo.setPanNo(dto.getPanNo());
//		        vo.setAccountHolderName(dto.getAccountHolderName());
//		        vo.setAccountNumber(dto.getAccountNumber());
//		        vo.setBankName(dto.getBankName());
//		        vo.setIfscCode(dto.getIfscCode());
		        vo.setWebsite(dto.getWebsite());
		        vo.setEstablishedYear(dto.getEstablishedYear());
		        vo.setBranch(dto.getBranch());
		        vo.setBranchcode(dto.getBranchcode());
		        vo.setTermsAndConditions(dto.getTermsAndConditions());
		        vo.setOrgId(dto.getOrgId());

		        vo.setCompanyAddressVO(new ArrayList<>());

		        if (dto.getCompanyAddressDTO() != null && !dto.getCompanyAddressDTO().isEmpty()) {

		            dto.getCompanyAddressDTO().forEach(a -> {

		                CompanyAddressVO addr = new CompanyAddressVO();
		                addr.setShippingAddress(a.getShippingAddress());
		                addr.setBillingAddress(a.getBillingAddress());
		                addr.setPrimary(a.isPrimary());
		                addr.setCompanyProfileVO(vo); // link parent

		                vo.getCompanyAddressVO().add(addr);
		            });
		        }
		        
		        
		     // ===== BANK DETAILS LIST =====
		        vo.setCompanyBankDetailsVO(new ArrayList<>());

		        if (dto.getCompanyBankDetailsDTO() != null && !dto.getCompanyBankDetailsDTO().isEmpty()) {

		            dto.getCompanyBankDetailsDTO().forEach(a -> {

		                CompanyBankDetailsVO bank = new CompanyBankDetailsVO();
		                bank.setAccountHolderName(a.getAccountHolderName());
		                bank.setAccountNumber(a.getAccountNumber());
		                bank.setBankName(a.getBankName());
		                bank.setIfscCode(a.getIfscCode());
		                bank.setPrimary(a.isPrimary());
		                bank.setBranch(a.getBranch());
		                bank.setBranchCode(a.getBranchCode());
		                bank.setCompanyProfileVO(vo);

		                vo.getCompanyBankDetailsVO().add(bank);
		            });
		        }
		    }

		    // =========================================================
		    // =============== VO → RESPONSE DTO ========================
		    // =========================================================
		    private CompanyProfileResponseDTO mapToCompanyProfileResponseDTO(CompanyProfileVO vo) {

		        CompanyProfileResponseDTO dto = new CompanyProfileResponseDTO();

		        dto.setId(vo.getId());
		        dto.setCompanyCode(vo.getCompanyCode());
		        dto.setCompanyName(vo.getCompanyName());
		        dto.setOwnerName(vo.getOwnerName());
		        dto.setEmailAddress(vo.getEmailAddress());
		        dto.setPhoneNo(vo.getPhoneNo());
		        dto.setGstNo(vo.getGstNo());
		        dto.setPanNo(vo.getPanNo());
//		        dto.setAccountHolderName(vo.getAccountHolderName());
//		        dto.setAccountNumber(vo.getAccountNumber());
//		        dto.setBankName(vo.getBankName());
//		        dto.setIfscCode(vo.getIfscCode());
		        dto.setWebsite(vo.getWebsite());
		        dto.setEstablishedYear(vo.getEstablishedYear());
		        dto.setCreatedBy(vo.getCreatedBy());
		        dto.setBranch(vo.getBranch());
		        dto.setBranchcode(vo.getBranchcode());
		        dto.setCompanyLogo(vo.getCompanyLogo());
		        dto.setTermsAndConditions(vo.getTermsAndConditions());
		        dto.setOrgId(vo.getOrgId());


		        // ===== CHILD RESPONSE =====
		        if (vo.getCompanyAddressVO() != null && !vo.getCompanyAddressVO().isEmpty()) {

		            List<CompanyAddressResponseDTO> addressList =
		                    vo.getCompanyAddressVO().stream().map(a -> {

		                        CompanyAddressResponseDTO adto = new CompanyAddressResponseDTO();
		                        adto.setId(a.getId());
		                        adto.setShippingAddress(a.getShippingAddress());
		                        adto.setBillingAddress(a.getBillingAddress());
		                        adto.setPrimary(a.isPrimary());
		                        return adto;

		                    }).collect(Collectors.toList());

		            dto.setCompanyAddresses(addressList);
		        }
		        
		        
		        if (vo.getCompanyBankDetailsVO() != null && !vo.getCompanyBankDetailsVO().isEmpty()) {

		            List<CompanyBankDetailsResponseDTO> bankList =
		                    vo.getCompanyBankDetailsVO().stream().map(a -> {

		                    	CompanyBankDetailsResponseDTO bankto = new CompanyBankDetailsResponseDTO();
		                    	bankto.setId(a.getId());
		                    	bankto.setAccountHolderName(a.getAccountHolderName());
		                    	bankto.setAccountNumber(a.getAccountNumber());
		                    	bankto.setBankName(a.getBankName());
		                    	bankto.setIfscCode(a.getIfscCode());
		                    	bankto.setBranch(a.getBranch());
		                    	bankto.setBranchCode(a.getBranchCode());
		                    	bankto.setPrimary(a.isPrimary());

		                        return bankto;

		                    }).collect(Collectors.toList());

		            dto.setCompanyBankDetailsResponseDTO(bankList);
		        }

		        return dto;
		    }
		    
			@Override
			public Map<String, Object> getAllCompanyProfileByOrgId(Long orgId, int page, int count) {
				Pageable pageable = PageRequest.of(page - 1, count);
				Page<CompanyProfileVO> companyPage = companyProfileRepo.getAllCompanyProfileByOrgId(orgId, pageable);

				Page<CompanyProfileResponseDTO> dtoPage = companyPage.map(this::mapToCompanyProfileResponseDTO);
				return paginationService.buildResponse(dtoPage);
			}

			@Override
			public CompanyProfileResponseDTO getCompanyProfileById(Long id) {
				CompanyProfileVO companyProfileVO = companyProfileRepo.findById(id).orElseThrow();
				CompanyProfileResponseDTO companyProfileResponseDTO = mapToCompanyProfileResponseDTO(companyProfileVO);
				return companyProfileResponseDTO;
			}


}
