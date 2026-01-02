package com.efit.savaari.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.efit.savaari.dto.BranchDTO;
import com.efit.savaari.dto.ChargeTypeDTO;
import com.efit.savaari.dto.CustomerDTO;
import com.efit.savaari.dto.CustomerRateDTO;
import com.efit.savaari.dto.IndentsDTO;
import com.efit.savaari.dto.ListOfValuesDTO;
import com.efit.savaari.dto.PlaceDetailsDTO;
import com.efit.savaari.dto.RoutesDTO;
import com.efit.savaari.dto.VendorRateDTO;
import com.efit.savaari.entity.BranchVO;
import com.efit.savaari.entity.ChargeTypeVO;
import com.efit.savaari.entity.CustomerRateVO;
import com.efit.savaari.entity.CustomerVO;
import com.efit.savaari.entity.IndentsVO;
import com.efit.savaari.entity.ListOfValuesVO;
import com.efit.savaari.entity.PlaceDetailsVO;
import com.efit.savaari.entity.RoutesVO;
import com.efit.savaari.entity.VendorRateVO;
import com.efit.savaari.exception.ApplicationException;

@Service
public interface MasterService {

	//Branch
	List<BranchVO> getAllBranch(Long orgid);

	Optional<BranchVO> getBranchById(Long branchid);

	Map<String, Object> createUpdateBranch(BranchDTO branchDTO) throws Exception;

	void deleteBranch(Long branchid);

	Map<String, Object> createUpdateCustomer(CustomerDTO customerDTO) throws Exception;

	CustomerVO getCustomerById(Long id) throws ApplicationException;

	Map<String, Object> getCustomerByOrgId(String branchCode, Long orgId, String search, int page, int count);

	Map<String, Object> createUpdateIndents(IndentsDTO indentsDTO, List<MultipartFile> tripLinkedAttachment) throws Exception;

	IndentsVO getIndentsById(Long id) throws ApplicationException;

	Map<String, Object> getIndentsByOrgId(String branchCode, Long orgId, String search, int page, int count);

	Map<String, Object> createUpdatePlaceDetails(PlaceDetailsDTO placeDetailsDTO) throws Exception;

	PlaceDetailsVO getPlaceDetailsById(Long id) throws ApplicationException;

	Map<String, Object> getPlaceDetailsByOrgId(String branchCode, String search, int page, int count);

	Map<String, Object> createUpdateVendorRate(VendorRateDTO vendorRateDTO) throws Exception;

	VendorRateVO getVendorRateById(Long id) throws ApplicationException;

	Map<String, Object> getVendorRateByOrgId(String branchCode, Long orgId, String search, int page, int count);

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

	ListOfValuesVO updateCreateListOfValues(@Valid ListOfValuesDTO listOfValuesDTO) throws ApplicationException;

	List<ListOfValuesVO> getListOfValuesByOrgId(Long orgid);

	List<ListOfValuesVO> getListOfValuesById(Long id);

	List<Map<String, Object>> getValueDescriptionByListOfValues(Long orgId, String listDescription);




//	Map<String, Object> createUpdateVechile(VehicleDTO vechicleDTO);
	
	




}
