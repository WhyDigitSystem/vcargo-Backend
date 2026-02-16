package com.efit.savaari.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.efit.savaari.dto.FuelMasterDTO;
import com.efit.savaari.entity.FuelVO;
import com.efit.savaari.exception.ApplicationException;

@Service
public interface FuelMasterService {

    FuelVO getFuelById(Long fuelId);

    List<FuelVO> getAllFuelByOrgId(Long orgId);
   
	Map<String, Object> createUpdateFuelMaster(FuelMasterDTO dto) throws ApplicationException;

	Map<String, Object> getFuelByVehicle(Long vehicleId, int page, int count);

}
