package com.efit.savaari.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.efit.savaari.dto.FuelMasterDTO;
import com.efit.savaari.entity.FuelVO;
import com.efit.savaari.exception.ApplicationException;

@Service
public interface FuelMasterService {

    FuelVO getFuelById(Long fuelId);

    Map<String, Object> getAllFuelByOrgId(Long orgId, int page, int count);
   
	Map<String, Object> createUpdateFuelMaster(FuelMasterDTO dto) throws ApplicationException;

	Map<String, Object> getFuelByVehicle(Long vehicleId, int page, int count);

}
