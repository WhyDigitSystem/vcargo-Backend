package com.efit.savaari.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.efit.savaari.entity.UserVO;
import com.efit.savaari.exception.ApplicationException;
import com.efit.savaari.repo.UserRepo;

@Service

public class IndustryServiceImpl implements IndustryService {

	public static final Logger LOGGER = LoggerFactory.getLogger(IndustryServiceImpl.class);


	@Autowired
	PaginationService paginationService;


	@Autowired
	UserRepo userRepo;

	
	@Override
	public void approveUserAdmin(Long id, String action, String actionBy, String type) throws ApplicationException {

//		VendorVO vendorVO = vendorRepo.findByVendorTypeAndId(type,id);
//
//		if ( vendorVO == null) {
//			throw new ApplicationException("No Industry or Vendor details found for approval.");
//		}

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss a");

//		if (vendorVO != null) {
//
//			if ("Approved".equalsIgnoreCase(vendorVO.getVerification())) {
//				throw new ApplicationException("This VendorDetails is already Approved.");
//			}
//			if ("Rejected".equalsIgnoreCase(vendorVO.getVerification())) {
//				throw new ApplicationException("This VendorDetails is already Rejected.");
//			}
//
//			vendorVO.setVerification(action);
//			vendorVO.setApproveBy(actionBy);
//			vendorVO.setApproveOn(LocalDateTime.now().format(formatter).toUpperCase());
//			vendorRepo.save(vendorVO);
//
//			UserVO userVO = new UserVO();
//			userVO.setActive(true);
//			userVO.setEmail(vendorVO.getPrimaryEmail());
//			userVO.setType(vendorVO.getVendorType());
//			userVO.setOrganizationName(vendorVO.getVendorName());
//			userVO.setUserName(vendorVO.getUserName());
//			userVO.setPassword(vendorVO.getUserPassword());
//			userVO.setMobileNo(vendorVO.getPrimaryPhoneNumber());
//			userRepo.save(userVO);
//		}
	}



}
