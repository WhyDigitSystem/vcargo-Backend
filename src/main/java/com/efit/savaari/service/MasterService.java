package com.efit.savaari.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.efit.savaari.dto.ChargeTypeDTO;
import com.efit.savaari.dto.CompanyProfileDTO;
import com.efit.savaari.dto.CustomerDTO;
import com.efit.savaari.dto.CustomerRateDTO;
import com.efit.savaari.dto.RoutesDTO;
import com.efit.savaari.entity.ChargeTypeVO;
import com.efit.savaari.entity.CustomerRateVO;
import com.efit.savaari.entity.CustomerVO;
import com.efit.savaari.entity.RoutesVO;
import com.efit.savaari.exception.ApplicationException;
import com.efit.savaari.responseDTO.CompanyProfileResponseDTO;

@Service
public interface MasterService {

	

	Map<String, Object> createUpdateCustomer(CustomerDTO customerDTO) throws Exception;

	CustomerVO getCustomerById(Long id) throws ApplicationException;

	Map<String, Object> createUpdateCustomerRate(CustomerRateDTO customerRateDTO) throws Exception;

	CustomerRateVO getCustomerRateById(Long id) throws ApplicationException;

	Map<String, Object> getCustomerRateByOrgId(String branchCode,Long orgId, String search, int page, int count);

	List<Map<String, Object>> getCustomerNameByOrgId(String branchCode, Long orgId);

	Map<String, Object> createUpdateRoutes(RoutesDTO routesDTO) throws Exception;

	RoutesVO getRoutesById(Long id) throws ApplicationException;

	Map<String, Object> getRoutesByOrgId(String branchCode, Long orgId, String search, int page, int count);

	List<Map<String, Object>> getVehicleTypeListByOrgId(String branchCode, Long orgId);

	List<Map<String, Object>> getOriginAndDestinationList(String branchCode, Long orgId);

	List<Map<String, Object>> getVendorRateAndOriginList(String branchCode, Long orgId);

	List<Map<String, Object>> getCustomerRateAndOriginList(String branchCode, Long orgId);

	Map<String, Object> createUpdateChargeType(ChargeTypeDTO chargeTypeDTO) throws ApplicationException;

	ChargeTypeVO getChargeTypeById(Long id) throws ApplicationException;

	Map<String, Object> getChargeTypeByOrgId(String branchCode, Long orgId, String search, int page, int count);

	Map<String, Object> getChargeTypeList(String branchCode, Long orgId, String search, int page, int count);
	
	List<Map<String, Object>> getValueDescriptionByListOfValues(Long orgId, String listDescription);

	Map<String, Object> createUpdateCompanyProfile(CompanyProfileDTO companyProfileDTO, MultipartFile image);

	Map<String, Object> getAllCompanyProfileByOrgId(Long orgId, int page, int count);

	CompanyProfileResponseDTO getCompanyProfileById(Long id);

	List<CustomerVO> getCustomerByOrgId(String branchCode, Long orgId);

	String getNextCustomerCode();






//	Map<String, Object> createUpdateVechile(VehicleDTO vechicleDTO);
	
	




}
