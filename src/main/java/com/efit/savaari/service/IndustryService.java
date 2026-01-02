package com.efit.savaari.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.efit.savaari.dto.IndustryDTO;
import com.efit.savaari.entity.IndustryVO;
import com.efit.savaari.entity.VendorVO;
import com.efit.savaari.exception.ApplicationException;

@Service
public interface IndustryService {

	// Industry

	IndustryVO getIndustryById(Long id);

	Map<String, Object> getAllIndustryByOrgId(Long orgId, String search, int page, int size);

	Map<String, Object> updateCreateIndustry(IndustryDTO industryDTO) throws ApplicationException;

	// image

	IndustryVO uploadImageFileIndustry(MultipartFile file, Long id) throws IOException;

	VendorVO uploadImageFileVendor(MultipartFile file, Long id) throws IOException;

	void approveUserAdmin(Long id, String action, String actionBy,String type) throws ApplicationException;

}
