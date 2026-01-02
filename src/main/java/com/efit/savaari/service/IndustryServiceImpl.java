package com.efit.savaari.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.efit.savaari.dto.IndustryDTO;
import com.efit.savaari.entity.IndustryVO;
import com.efit.savaari.entity.UserVO;
import com.efit.savaari.entity.VendorVO;
import com.efit.savaari.exception.ApplicationException;
import com.efit.savaari.repo.IndustryRepo;
import com.efit.savaari.repo.UserRepo;
import com.efit.savaari.repo.VendorRepo;

@Service

public class IndustryServiceImpl implements IndustryService {

	public static final Logger LOGGER = LoggerFactory.getLogger(IndustryServiceImpl.class);

	@Autowired
	IndustryRepo industryRepo;

	@Autowired
	PaginationService paginationService;

	@Autowired
	VendorRepo vendorRepo;

	@Autowired
	UserRepo userRepo;

	@Override
	public Map<String, Object> getAllIndustryByOrgId(Long orgId, String search, int page, int size) {

		if (search != null) {
			search = search.trim();
			if (search.isEmpty()) {
				search = null;
			}
		}

		Pageable pageable = PageRequest.of(page - 1, size, Sort.by("industryname").ascending());
		Page<IndustryVO> customerPage = industryRepo.getAllIndustryByOrgId(orgId, search, pageable);

		return paginationService.buildResponse(customerPage);

	}

	@Override
	public IndustryVO getIndustryById(Long id) {

		return industryRepo.getIndustryById(id);
	}

	@Override
	@Transactional
	public Map<String, Object> updateCreateIndustry(IndustryDTO industryDTO) throws ApplicationException {

		IndustryVO industryVO = new IndustryVO();

		String message;

		if (ObjectUtils.isNotEmpty(industryDTO.getId())) {

			industryVO = industryRepo.findById(industryDTO.getId())
					.orElseThrow(() -> new ApplicationException("Industry Not Found!"));
			industryVO.setUpdatedBy(industryDTO.getCreatedBy());

			message = "Industry Updated Successfully";
		} else {

			industryVO.setUpdatedBy(industryDTO.getCreatedBy());
			industryVO.setCreatedBy(industryDTO.getCreatedBy());
			message = "Industry Created Successfully";
		}

		createUpdateIndustryVOByIndustryDTO(industryDTO, industryVO);
		industryRepo.save(industryVO);
		Map<String, Object> response = new HashMap<>();
		response.put("industryVO", industryVO);
		response.put("message", message);
		return response;
	}

	private void createUpdateIndustryVOByIndustryDTO(@Valid IndustryDTO industryDTO, IndustryVO industryVO)
			throws ApplicationException {
		industryVO.setIndustryCode(industryDTO.getIndustryCode());
		industryVO.setIndustryName(industryDTO.getIndustryName());
		industryVO.setPhoneNumber(industryDTO.getPhoneNumber());
		industryVO.setEmail(industryDTO.getEmail());
		industryVO.setState(industryDTO.getState());
		industryVO.setGstNumber(industryDTO.getGstNumber());
		industryVO.setPanNumber(industryDTO.getPanNumber());
		industryVO.setPrimaryAddress(industryDTO.getPrimaryAddress());
		industryVO.setAdditionalAddress(industryDTO.getAdditionalAddress());
		industryVO.setCity(industryDTO.getCity());
		industryVO.setIndustryType(industryDTO.getIndustryType());
		industryVO.setPincode(industryDTO.getPincode());
		industryVO.setPocName(industryDTO.getPocName());
		industryVO.setPocEmail(industryDTO.getPocEmail());
		industryVO.setPocNumber(industryDTO.getPocNumber());
		industryVO.setCreatedBy(industryDTO.getCreatedBy());
		industryVO.setUserId(industryDTO.getUserId());
		industryVO.setUserName(industryDTO.getUserName());
		industryVO.setUserPassword(industryDTO.getUserPassword());

	}

	// Industry

	public IndustryVO uploadImageFileIndustry(MultipartFile file, Long id) throws IOException {
		String contentType = file.getContentType();
		if (!isSupportedFileType(contentType)) {
			throw new IllegalArgumentException("Only PDF or image files are allowed.");
		}

		IndustryVO industryVO = industryRepo.findById(id)
				.orElseThrow(() -> new RuntimeException("Industry not found with ID: " + id));
		industryVO.setImage(file.getBytes());

		return industryRepo.save(industryVO);
	}

	private boolean isSupportedFileType(String contentType) {
		return contentType != null && (contentType.equals("application/pdf") || contentType.startsWith("image/"));
	}

	public VendorVO uploadImageFileVendor(MultipartFile file, Long id) throws IOException {
		String contentType = file.getContentType();
		if (!isSupportedFileType(contentType)) {
			throw new IllegalArgumentException("Only PDF or image files are allowed.");
		}

		VendorVO vendorVO = vendorRepo.findById(id)
				.orElseThrow(() -> new RuntimeException("Vendor not found with ID: " + id));
		vendorVO.setImage(file.getBytes());

		return vendorRepo.save(vendorVO);
	}

	@Override
	public void approveUserAdmin(Long id, String action, String actionBy, String type) throws ApplicationException {

		IndustryVO industryVO = industryRepo.findByIndustryTypeAndId(type,id);
		VendorVO vendorVO = vendorRepo.findByVendorTypeAndId(type,id);

		if (industryVO == null && vendorVO == null) {
			throw new ApplicationException("No Industry or Vendor details found for approval.");
		}

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss a");

		if (industryVO != null) {

			if ("Approved".equalsIgnoreCase(industryVO.getVerification())) {
				throw new ApplicationException("This IndustryDetails is already Approved.");
			}
			if ("Rejected".equalsIgnoreCase(industryVO.getVerification())) {
				throw new ApplicationException("This IndustryDetails is already Rejected.");
			}

			industryVO.setVerification(action);
			industryVO.setApproveBy(actionBy);
			industryVO.setApproveOn(LocalDateTime.now().format(formatter).toUpperCase());
			industryRepo.save(industryVO);

			UserVO userVO = new UserVO();
			userVO.setActive(true);
			userVO.setEmail(industryVO.getEmail());
			userVO.setType(industryVO.getIndustryType());
			userVO.setOrganizationName(industryVO.getIndustryName());
			userVO.setUserName(industryVO.getUserName());
			userVO.setPassword(industryVO.getUserPassword());
			userVO.setMobileNo(industryVO.getPhoneNumber());
			userVO.setOrgId(industryVO.getId());
			
			userRepo.save(userVO);

			return;
		}

		if (vendorVO != null) {

			if ("Approved".equalsIgnoreCase(vendorVO.getVerification())) {
				throw new ApplicationException("This VendorDetails is already Approved.");
			}
			if ("Rejected".equalsIgnoreCase(vendorVO.getVerification())) {
				throw new ApplicationException("This VendorDetails is already Rejected.");
			}

			vendorVO.setVerification(action);
			vendorVO.setApproveBy(actionBy);
			vendorVO.setApproveOn(LocalDateTime.now().format(formatter).toUpperCase());
			vendorRepo.save(vendorVO);

			UserVO userVO = new UserVO();
			userVO.setActive(true);
			userVO.setEmail(vendorVO.getPrimaryEmail());
			userVO.setType(vendorVO.getVendorType());
			userVO.setOrganizationName(vendorVO.getVendorName());
			userVO.setUserName(vendorVO.getUserName());
			userVO.setPassword(vendorVO.getUserPassword());
			userVO.setMobileNo(vendorVO.getPrimaryPhoneNumber());
			userRepo.save(userVO);
		}
	}

}
