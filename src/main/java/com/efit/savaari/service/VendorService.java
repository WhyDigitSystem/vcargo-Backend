package com.efit.savaari.service;


import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.efit.savaari.dto.VendorDTO;
import com.efit.savaari.entity.VendorVO;
import com.efit.savaari.exception.ApplicationException;

@Service
public interface VendorService {

//	Map<String, Object> createUpdateVendor(VendorDTO vendorDTO, MultipartFile contractAttachment, MultipartFile backgroundVerification, MultipartFile securityCheck) throws  Exception;

	Map<String, Object> createUpdateVendor(VendorDTO vendorDTO) throws  Exception;

	Map<String, Object> getVendorByOrgId( String branchCode,Long orgId, String search, int page, int count);

	VendorVO getVendorById(Long id) throws ApplicationException;

	List<Map<String, Object>> getOrganizationAndCodeList(String branchCode, Long orgId);

	Map<String, Object> uploadVendorDocuments(Long vendorId, MultipartFile contractAttachment,
			MultipartFile backgroundVerification, MultipartFile securityCheck,String createdBy) throws Exception;

}
