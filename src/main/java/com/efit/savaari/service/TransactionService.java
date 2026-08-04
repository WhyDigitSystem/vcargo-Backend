package com.efit.savaari.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.efit.savaari.dto.CustomerBookingRequestDTO;
import com.efit.savaari.dto.TdriverDTO;
import com.efit.savaari.dto.TvehicleDTO;
import com.efit.savaari.dto.VehicleHireDTO;
import com.efit.savaari.entity.CustomerBookingRequestVO;
import com.efit.savaari.entity.TdriverVO;
import com.efit.savaari.entity.TvehicleVO;
import com.efit.savaari.exception.ApplicationException;

@Service
public interface TransactionService {


	Map<String, Object> createUpdateCustomerBookingRequest(CustomerBookingRequestDTO customerBookingRequestDTO)
			throws Exception;

	CustomerBookingRequestVO getCustomerBookingRequestById(Long id) throws ApplicationException;

	Map<String, Object> getCustomerBookingRequestByOrgId(String branchCode, Long orgId, String search, int page, int count);

	Map<String, Object> createUpdateTvehicle(@Valid TvehicleDTO dto,MultipartFile[] rcFiles,
			MultipartFile[] insuranceFiles,
			MultipartFile[] fcFiles,
			MultipartFile[] permitFiles,
			MultipartFile[] pucFiles,
			MultipartFile[] otherFiles) throws Exception;

	TvehicleVO getTvehiclesById(Long id) throws ApplicationException;

	List<TvehicleVO> getTvehiclesByOrgId(String branchCode,Long orgId);


	TdriverVO getTdriverById(Long id) throws ApplicationException;

	List<TdriverVO> getTdriverByOrgId(String branchCode,Long orgId);

	List<TvehicleVO> uploadTVehicleExcel(MultipartFile file, String createdBy, Long orgId) throws Exception;

	List<TdriverVO> uploadTDriverExcel(MultipartFile file, String createdBy, Long orgId) throws Exception;

	
	ResponseEntity<byte[]> viewFile(HttpServletRequest request) throws IOException;

	Map<String, Object> createUpdateTdriver(TdriverDTO dto, MultipartFile[] dlFiles, MultipartFile[] aadharFiles,
			MultipartFile[] panFiles, MultipartFile[] photoFiles, MultipartFile[] expFiles,
			MultipartFile[] medicalFiles, MultipartFile[] otherFiles) throws ApplicationException;

	ResponseEntity<byte[]> viewDriverFile(HttpServletRequest request) throws IOException;

	Map<String, Object> tDriverExcelUpload(MultipartFile file,Long createdBy,Long orgId) throws Exception;

	Map<String, Object> tVehicleExcelUpload(MultipartFile file, Long createdBy, Long orgId) throws Exception;

	Map<String, Object> createUpdateHireTvehicle(VehicleHireDTO vehicleHireDTO) throws ApplicationException;

}
