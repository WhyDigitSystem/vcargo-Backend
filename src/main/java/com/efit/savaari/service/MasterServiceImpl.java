package com.efit.savaari.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

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

import com.efit.savaari.dto.ChargeTypeDTO;
import com.efit.savaari.dto.CompanyProfileDTO;
import com.efit.savaari.dto.CustomerAddressDTO;
import com.efit.savaari.dto.CustomerDTO;
import com.efit.savaari.dto.CustomerRateDTO;
import com.efit.savaari.dto.RoutesDTO;
import com.efit.savaari.dto.RoutesDetailsDTO;
import com.efit.savaari.dto.RoutesPetrolPumpsDTO;
import com.efit.savaari.dto.RoutesPitstopDTO;
import com.efit.savaari.entity.ChargeTypeVO;
import com.efit.savaari.entity.CompanyAddressVO;
import com.efit.savaari.entity.CompanyBankDetailsVO;
import com.efit.savaari.entity.CompanyProfileVO;
import com.efit.savaari.entity.CustomerAddressVO;
import com.efit.savaari.entity.CustomerRateVO;
import com.efit.savaari.entity.CustomerVO;
import com.efit.savaari.entity.RoutesDetailsVO;
import com.efit.savaari.entity.RoutesPetrolPumpsVO;
import com.efit.savaari.entity.RoutesPitstopVO;
import com.efit.savaari.entity.RoutesVO;
import com.efit.savaari.exception.ApplicationException;
import com.efit.savaari.repo.ChargeTypeRepo;
import com.efit.savaari.repo.CompanyAddressRepo;
import com.efit.savaari.repo.CompanyBankDetailsRepo;
import com.efit.savaari.repo.CompanyProfileRepo;
import com.efit.savaari.repo.CustomerAddressRepo;
import com.efit.savaari.repo.CustomerRateRepo;
import com.efit.savaari.repo.CustomerRepo;
import com.efit.savaari.repo.RoutesDetailsRepo;
import com.efit.savaari.repo.RoutesPetrolPumpsRepo;
import com.efit.savaari.repo.RoutesPitstopRepo;
import com.efit.savaari.repo.RoutesRepo;
import com.efit.savaari.repo.UserLoginRolesRepo;
import com.efit.savaari.repo.UserRepo;
import com.efit.savaari.repo.VehicleTypeRepo;
import com.efit.savaari.repo.VendorRateRepo;
import com.efit.savaari.responseDTO.CompanyAddressResponseDTO;
import com.efit.savaari.responseDTO.CompanyBankDetailsResponseDTO;
import com.efit.savaari.responseDTO.CompanyProfileResponseDTO;

@Service
public class MasterServiceImpl implements MasterService {

	private final OtpAsyncService otpAsyncService;
	public static final Logger LOGGER = LoggerFactory.getLogger(MasterServiceImpl.class);

	@Autowired
	UserLoginRolesRepo userLoginRolesRepo;

	@Autowired
	UserRepo userRepo;;

	@Autowired
	CustomerRepo customerRepo;

	@Autowired
	PaginationService paginationService;


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
	ChargeTypeRepo chargeTypeRepo;

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

	
	private String generateCustomerCode() {

	    String lastCode = customerRepo.getLastCustomerCode();

	    if (lastCode == null || lastCode.isEmpty()) {
	        return "CUS0001";
	    }

	    // Extract number part
	    String numberPart = lastCode.replaceAll("[^0-9]", "");

	    int nextNumber = Integer.parseInt(numberPart) + 1;

	    return String.format("CUS%03d", nextNumber);
	}
	
	@Override
	public String getNextCustomerCode() {
	    return generateCustomerCode();
	}
	
	@Override
	public CustomerVO getCustomerById(Long id) throws ApplicationException {
		return customerRepo.findById(id).orElseThrow(() -> new ApplicationException("customer not found"));
	}

	@Override
	public List<CustomerVO> getCustomerByOrgId(String branchCode, Long orgId) {
	    return customerRepo.getCustomerByOrgId(branchCode, orgId);
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
