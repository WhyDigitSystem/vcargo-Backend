package com.efit.savaari.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.efit.savaari.dto.TyreMasterDTO;
import com.efit.savaari.exception.ApplicationException;
import com.efit.savaari.responseDTO.TyreMasterResponseDTO;

@Service
public interface TyreMasterService {

    Map<String, Object> createUpdateTyreMaster(TyreMasterDTO dto) throws ApplicationException;

    Map<String, Object> getAllTyreByOrgId(Long orgId, int page, int count);
    
	TyreMasterResponseDTO getTyreById(Long fuelId);
    
}
