package com.efit.savaari.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.efit.savaari.dto.UserStatus;
import com.efit.savaari.dto.VendorDTO;
import com.efit.savaari.entity.EmailOtpEntity;
import com.efit.savaari.entity.UserVO;
import com.efit.savaari.entity.VendorBankDetailsVO;
import com.efit.savaari.entity.VendorDetailsVO;
import com.efit.savaari.entity.VendorGstVO;
import com.efit.savaari.entity.VendorUsersVO;
import com.efit.savaari.entity.VendorVO;
import com.efit.savaari.exception.ApplicationException;
import com.efit.savaari.repo.EmailOtpRepo;
import com.efit.savaari.repo.IndustryRepo;
import com.efit.savaari.repo.UserRepo;
import com.efit.savaari.repo.VendorBankDetailsRepo;
import com.efit.savaari.repo.VendorDetailsRepo;
import com.efit.savaari.repo.VendorGstRepo;
import com.efit.savaari.repo.VendorRepo;
import com.efit.savaari.repo.VendorUsersRepo;
import com.efit.savaari.util.CryptoUtils;

@Service
public class VendorServiceImpl implements VendorService {

	private final PasswordEncoder passwordEncoder;

	public static final Logger LOGGER = LoggerFactory.getLogger(VendorServiceImpl.class);

	@Autowired
	VendorRepo vendorRepo;

	@Autowired
	VendorUsersRepo vendorUsersRepo;

	@Autowired
	VendorDetailsRepo vendorDetailsRepo;

	@Autowired
	UserRepo userRepo;

	@Autowired
	EmailOtpRepo emailOtpRepo;

	@Autowired
	IndustryRepo industryRepo;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	VendorGstRepo vendorGstRepo;

	@Autowired
	VendorBankDetailsRepo vendorBankDetailsRepo;

	VendorServiceImpl(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

//	@Override
//	public Map<String, Object> createUpdateVendor(
//	        VendorDTO vendorDTO,
//	        MultipartFile contractAttachment,
//	        MultipartFile backgroundVerification,
//	        MultipartFile securityCheck
//	) throws Exception  {

	@Override
	public Map<String, Object> createUpdateVendor(VendorDTO vendorDTO) throws Exception {
		VendorVO vendorVO;
		String message;

		// Updating vendor
		if (vendorDTO.getId() != null) {

			vendorVO = vendorRepo.findById(vendorDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid Vendor ID"));

			vendorUsersRepo.deleteAll(vendorUsersRepo.findByVendorVO(vendorVO));
			vendorDetailsRepo.deleteAll(vendorDetailsRepo.findByVendorVO(vendorVO));

			vendorGstRepo.deleteAll(vendorGstRepo.findByVendorVO(vendorVO));
			vendorBankDetailsRepo.deleteAll(vendorBankDetailsRepo.findByVendorVO(vendorVO));
			vendorVO.setUpdatedBy(vendorDTO.getCreatedBy());
			message = "Vendor Updated Successfully";

		} else {
			// Creating vendor
			vendorVO = new VendorVO();
			vendorVO.setCreatedBy(vendorDTO.getCreatedBy());
			vendorVO.setUpdatedBy(vendorDTO.getCreatedBy());
			message = "Vendor Created Successfully";
		}

//		
//		if (vendorDTO.getIndustryId() != null) {
//			IndustryVO industryVO = industryRepo.findById(vendorDTO.getIndustryId())
//					.orElseThrow(() -> new ApplicationException("Invalid Industry ID"));
//			vendorVO.setIndustry(industryVO);
//		}

		createUpdateVendorDTOByVendorVO(vendorDTO, vendorVO);

//	    if (vendorDTO.getVendorDetailsDTO() != null && !vendorDTO.getVendorDetailsDTO().isEmpty()) {
//	        VendorDetailsVO details = vendorVO.getVendorDetailsVO().get(0);
//
//	        if (contractAttachment != null) {
//	            details.setContractAttachment(contractAttachment.getBytes());
//	        }
//	        if (backgroundVerification != null) {
//	            details.setBackgroundVerification(backgroundVerification.getBytes());
//	        }
//	        if (securityCheck != null) {
//	            details.setSecurityCheck(securityCheck.getBytes());
//	        }
//	    }

		vendorRepo.save(vendorVO);
		
		vendorDTO.getVendorUsersDTO().forEach(u -> {
		Optional<UserVO> optionalUser = userRepo.findByEmail(u.getUsers());
		UserVO user;

		if (optionalUser.isPresent()) {
			user = optionalUser.get(); // existing user
		} else {
			user = new UserVO(); // new user
			try {
				user.setPassword(passwordEncoder
						.encode(CryptoUtils.getDecrypt("HzNOsmwTefzQ4WWqCURfjDYdOzoyMT4nlgQk6p77fso=")));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		user.setOrgId(vendorDTO.getOrgId());
		user.setVendorId(vendorVO.getId());
		user.setEmail(u.getUsers());
		user.setUserName(u.getUsers());
		user.setActive(true);
		user.setStatus(UserStatus.APPROVED);
		user.setType("Transporter");
		user.setOrganizationName(vendorDTO.getOrganization());
		user.setCreatedby(vendorDTO.getCreatedBy());
		user.setUpdatedby(vendorDTO.getCreatedBy());

		userRepo.save(user);
		});

		Map<String, Object> response = new HashMap<>();
		response.put("vendorVO", vendorVO);
		response.put("message", message);

		return response;
	}

	private void createUpdateVendorDTOByVendorVO(VendorDTO dto, VendorVO vo) {

		vo.setVendorCode(dto.getVendorCode());
		vo.setStatus(dto.getStatus());
		vo.setOrganization(dto.getOrganization());
		vo.setApprovalStatus("PENDING");
		vo.setPrimaryPhoneNumber(dto.getPrimaryPhoneNumber());
		vo.setPrimaryEmail(dto.getPrimaryEmail());
		vo.setAdditionalPhoneNumber(dto.getAdditionalPhoneNumber());
		vo.setAdditionalEmails(dto.getAdditionalEmails());
		vo.setAddress(dto.getAddress());
//	    vo.setAccountNumber(dto.getAccountNumber());
//	    vo.setIfsc(dto.getIfsc());
//	    vo.setAccountHolderName(dto.getAccountHolderName());
		vo.setVendorType(dto.getVendorType());
		vo.setAdvancePercent(dto.getAdvancePercent());
		vo.setCreditPeriod(dto.getCreditPeriod());
		vo.setTdsPercent(dto.getTdsPercent());
		vo.setVendorSpotId(dto.getVendorSpotId());
		vo.setVendoruuid(dto.getVendoruuid());
		vo.setTags(dto.getTags());
		vo.setPocName(dto.getPocName());
		vo.setPocEmail(dto.getPocEmail());
		vo.setPocNumber(dto.getPocNumber());
		vo.setActive(dto.isActive());
		vo.setOrgId(dto.getOrgId());
		vo.setBranchCode(dto.getBranchCode());
		vo.setBranch(dto.getBranch());

		// Vendor Users
		List<VendorUsersVO> usersList = new ArrayList<>();
		if (dto.getVendorUsersDTO() != null) {
			dto.getVendorUsersDTO().forEach(u -> {
				VendorUsersVO voUser = new VendorUsersVO();
				voUser.setUsers(u.getUsers());
				voUser.setVendorVO(vo);
				usersList.add(voUser);

//	            EmailOtpEntity
				Optional<EmailOtpEntity> optionalUsers = emailOtpRepo.findByEmail(u.getUsers());
				EmailOtpEntity emailOtpEntity;

				if (optionalUsers.isPresent()) {
					emailOtpEntity = optionalUsers.get();
				} else {
					emailOtpEntity = new EmailOtpEntity();
					emailOtpEntity.setCreatedAt(LocalDateTime.now());
				}

				// Default OTP stored always
				String defaultOtp = "123456";
				String encryptedOtp = passwordEncoder.encode(defaultOtp);

				emailOtpEntity.setEmail(u.getUsers());
				emailOtpEntity.setEncryptedOtp(encryptedOtp); // IMPORTANT - cannot be null
				emailOtpEntity.setEncryptedOtp(encryptedOtp); // if you want to store raw OTP (optional)
				emailOtpEntity.setVerified(true);
				emailOtpEntity.setExpiryTime(LocalDateTime.now()); // minimum valid default
				emailOtpEntity.setCreatedAt(LocalDateTime.now());

				emailOtpRepo.save(emailOtpEntity);

			});
		}

		// Vendor Details + File Attachments
		List<VendorDetailsVO> detailsList = new ArrayList<>();
		if (dto.getVendorDetailsDTO() != null) {
			dto.getVendorDetailsDTO().forEach(d -> {
				VendorDetailsVO dVO = new VendorDetailsVO();
				dVO.setEffectiveFrom(d.getEffectiveFrom());
				dVO.setEffectioveTo(d.getEffectioveTo());
				dVO.setContractAttachmentName(d.getContractAttachmentName());
				dVO.setBackgroundVerificationName(d.getBackgroundVerificationName());
				dVO.setSecuritycheckName(d.getSecuritycheckName());

				dVO.setVendorVO(vo);
				detailsList.add(dVO);
			});
		}

		List<VendorGstVO> gstList = new ArrayList<>();
		if (dto.getVendorGstDTO() != null) {
			dto.getVendorGstDTO().forEach(d -> {
				VendorGstVO dVO = new VendorGstVO();
				dVO.setGst(d.getGst());

				dVO.setVendorVO(vo);
				gstList.add(dVO);
			});
		}

		List<VendorBankDetailsVO> bankDetailsList = new ArrayList<>();
		if (dto.getVendorBankDetailsDTO() != null) {
			dto.getVendorBankDetailsDTO().forEach(d -> {
				VendorBankDetailsVO dVO = new VendorBankDetailsVO();
				dVO.setAccountNumber(d.getAccountNumber());
				dVO.setIfsc(d.getIfsc());
				dVO.setAccountHolderName(d.getAccountHolderName());
				dVO.setBankName(d.getBankName());
				dVO.setVendorVO(vo);
				bankDetailsList.add(dVO);
			});
		}

		vo.setVendorGstVO(gstList);
		vo.setVendorUsersVO(usersList);
		vo.setVendorDetailsVO(detailsList);
		vo.setVendorBankDetailsVO(bankDetailsList);

	}

	@Override
	public Map<String, Object> uploadVendorDocuments(Long vendorId, MultipartFile contractAttachment,
			MultipartFile backgroundVerification, MultipartFile securityCheck, String createdBy) throws Exception {

		VendorVO vendorVO = vendorRepo.findById(vendorId)
				.orElseThrow(() -> new ApplicationException("Invalid Vendor ID"));

		List<VendorDetailsVO> detailsList = vendorDetailsRepo.findByVendorVO(vendorVO);

		if (detailsList.isEmpty()) {
			throw new ApplicationException("Vendor details record not found");
		}

		vendorVO.setUpdatedBy(createdBy);
		VendorDetailsVO details = detailsList.get(0);

		int uploadedCount = 0;

		if (contractAttachment != null && !contractAttachment.isEmpty()) {
			details.setContractAttachment(contractAttachment.getBytes());
			uploadedCount++;
		}
		if (backgroundVerification != null && !backgroundVerification.isEmpty()) {
			details.setBackgroundVerification(backgroundVerification.getBytes());
			uploadedCount++;
		}
		if (securityCheck != null && !securityCheck.isEmpty()) {
			details.setSecurityCheck(securityCheck.getBytes());
			uploadedCount++;
		}

		vendorDetailsRepo.save(details);

		// Reload vendor with relationships to return full response
//	    VendorVO fullVendor = vendorRepo.findByIdWithDetailsAndUsers(vendorId);

		Map<String, Object> response = new HashMap<>();
		response.put("message", "Vendor Documents uploaded successfully");
		response.put("uploadedCount", uploadedCount);
		response.put("vendorVO", vendorVO);
//	    response.put("vendorId", vendorId);   // optional, but useful

		return response;
	}

	@Override
	public Map<String, Object> getVendorByOrgId(String branchCode, Long orgId, String search, int page, int count) {
		if (search == null)
			search = "";

		Pageable pageable = PageRequest.of(page - 1, count); // Page 1 → offset 0
		Page<VendorVO> vendorPage = vendorRepo.getVendorByOrgId(branchCode, orgId, search, pageable);

		Map<String, Object> result = new HashMap<>();
		result.put("totalCount", vendorPage.getTotalElements());
		result.put("totalPages", vendorPage.getTotalPages());
		result.put("currentPage", page);
		result.put("users", vendorPage.getContent());
		return result;
	}

	@Override
	public VendorVO getVendorById(Long id) throws ApplicationException {
		return vendorRepo.findById(id).orElseThrow(() -> new ApplicationException("Vendor not found"));
	}

	@Override
	public List<Map<String, Object>> getOrganizationAndCodeList(String branchCode, Long orgId) {

		if (branchCode != null && branchCode.trim().isEmpty()) {
			branchCode = null;
		}

		List<Object[]> results = vendorRepo.getOrganizationAndCodeList(branchCode, orgId);
		List<Map<String, Object>> list = new ArrayList<>();

		for (Object[] row : results) {
			Map<String, Object> map = new HashMap<>();

			map.put("organization", row[0] == null ? "" : row[0].toString());
			map.put("vendorCode", row[1] == null ? "" : row[1].toString());
			map.put("vendorId", row[2] == null ? "" : row[2].toString());

			list.add(map);
		}

		return list;
	}

}
