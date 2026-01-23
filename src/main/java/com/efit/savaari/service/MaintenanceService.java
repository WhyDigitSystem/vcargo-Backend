package com.efit.savaari.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.efit.savaari.dto.MaintenanceDTO;
import com.efit.savaari.entity.MaintenanceVO;
import com.efit.savaari.exception.ApplicationException;
import com.efit.savaari.responseDTO.MaintenanceResponseDTO;

@Service
public interface MaintenanceService {

	List<MaintenanceVO> getAllMaintenanceByOrgId(Long orgId);

	Map<String, Object> createUpdateMaintenance(MaintenanceDTO dto) throws ApplicationException;

	MaintenanceResponseDTO getMaintenanceById(Long id);
}