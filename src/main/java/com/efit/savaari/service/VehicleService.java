package com.efit.savaari.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.efit.savaari.dto.DriverDTO;
import com.efit.savaari.dto.VehicleDTO;
import com.efit.savaari.dto.VehicleTypeDTO;
import com.efit.savaari.entity.DriverVO;
import com.efit.savaari.entity.VehicleTypeVO;
import com.efit.savaari.entity.VehicleVO;
import com.efit.savaari.exception.ApplicationException;

@Service
public interface VehicleService {
	
		// Vehicle Service Start here
	
	    Map<String, Object> createUpdateVehicle(VehicleDTO dto) throws Exception;

	    VehicleVO getVehicleById(Long id) throws ApplicationException;

		Map<String, Object> getAllVehicle(String branchCode, Long orgId, String search, int page, int count);
		
		// End Vehicle Service here
		
		
		
		// Driver Service start here

		Map<String, Object> createUpdateDriver(DriverDTO driverDTO) throws Exception;

		DriverVO getDriverById(Long id) throws ApplicationException;

		Map<String, Object> getAllDriver(String branchCode, Long orgId, String search, int page, int count);
		
		// End Driver Service here
		
		
		// Vehicle Type Service start here

		Map<String, Object> createUpdateVehicleType(VehicleTypeDTO vehicleTypeDTO) throws Exception;

		VehicleTypeVO getVehicleTypeById(Long id) throws ApplicationException;

		Map<String, Object> getAllVehicleType(String branchCode, Long search2, String search, int page, int count);
		
		// End Vehicle Type Service here

}
