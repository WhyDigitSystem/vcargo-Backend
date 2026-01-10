package com.efit.savaari.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.efit.savaari.dto.MaintenanceDTO;
import com.efit.savaari.exception.ApplicationException;
import com.efit.savaari.responseDTO.MaintenanceResponseDTO;

@Service
public interface MaintenanceService {

	Map<String, Object> getAllMaintenanceByOrgId(Long orgId, int page, int count);

	Map<String, Object> createUpdateMaintenance(MaintenanceDTO dto) throws ApplicationException;

	MaintenanceResponseDTO getMaintenanceById(Long id);
}