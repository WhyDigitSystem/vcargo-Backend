package com.efit.savaari.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.efit.savaari.dto.MaintenanceDTO;
import com.efit.savaari.dto.MaintenancePartDTO;
import com.efit.savaari.entity.MaintenancePartVO;
import com.efit.savaari.entity.MaintenanceVO;
import com.efit.savaari.entity.TvehicleVO;
import com.efit.savaari.entity.UserVO;
import com.efit.savaari.exception.ApplicationException;
import com.efit.savaari.repo.MaintenanceRepo;
import com.efit.savaari.repo.TvehicleRepo;
import com.efit.savaari.repo.UserRepo;
import com.efit.savaari.responseDTO.MaintenancePartResponseDTO;
import com.efit.savaari.responseDTO.MaintenanceResponseDTO;

@Service
public class MaintenanceServiceImpl implements MaintenanceService {

    @Autowired
    MaintenanceRepo maintenanceRepo;
    
    @Autowired
    UserRepo userRepo;
    
    @Autowired
    TvehicleRepo tvehicleRepo;
    
    @Autowired
    PaginationService paginationService;

    @Override
    @Transactional
    public Map<String, Object> createUpdateMaintenance(MaintenanceDTO dto) throws ApplicationException {

        MaintenanceVO maintenance;
        String message;

        // ===== CREATE / UPDATE =====
        if (dto.getId() != null) {
            maintenance = maintenanceRepo.findById(dto.getId())
                    .orElseThrow(() -> new ApplicationException("Invalid Maintenance ID"));
            maintenance.setUpdatedBy(dto.getCreatedBy());
            message = "Maintenance Updated Successfully";
        } else {
            maintenance = new MaintenanceVO();
            maintenance.setCreatedBy(dto.getCreatedBy());
            maintenance.setUpdatedBy(dto.getCreatedBy());
            message = "Maintenance Created Successfully";
        }

        // ===== VEHICLE =====
        if (dto.getVehicleId() != null) {
            TvehicleVO vehicle = tvehicleRepo.findById(dto.getVehicleId())
                    .orElseThrow(() -> new ApplicationException("Invalid Vehicle"));
            maintenance.setVehicle(vehicle);
        }

        // ===== USER =====
        if (dto.getUser() != null) {
            UserVO user = userRepo.findById(dto.getUser())
                    .orElseThrow(() -> new ApplicationException("Invalid User"));
            maintenance.setUser(user);
        }

        // ===== BASIC FIELDS =====
        mapMaintenanceDTOtoVO(dto, maintenance);

        // ===== PARTS =====
        mapParts(dto.getParts(), maintenance);

        // ===== TOTAL CALCULATION =====
        calculateTotals(maintenance);

        maintenance = maintenanceRepo.save(maintenance);

        MaintenanceResponseDTO responseDTO = mapToMaintenanceResponseDTO(maintenance);

        Map<String, Object> response = new HashMap<>();
        response.put("maintenance", responseDTO);
        response.put("message", message);

        return response;
    }


    private MaintenanceResponseDTO mapToMaintenanceResponseDTO(MaintenanceVO vo) {

        MaintenanceResponseDTO dto = new MaintenanceResponseDTO();

        dto.setId(vo.getId());
        dto.setTitle(vo.getTitle());
        dto.setType(vo.getType());
        dto.setStatus(vo.getStatus());
        dto.setPriority(vo.getPriority());
        dto.setScheduledDate(vo.getScheduledDate());
        dto.setCompletedDate(vo.getCompletedDate());
        dto.setOdometerReading(vo.getOdometerReading());
        dto.setEstimatedCost(vo.getEstimatedCost());
        dto.setTotalCost(vo.getTotalCost());
        dto.setTotalQty(vo.getTotalqty());
        dto.setServiceCenter(vo.getServiceCenter());
        dto.setMechanic(vo.getMechanic());
        dto.setDescription(vo.getDescription());
        dto.setNotes(vo.getNotes());
        dto.setActive(vo.isActive());

        dto.setCreatedBy(vo.getCreatedBy());
        dto.setBranchCode(vo.getBranchCode());
        dto.setBranchName(vo.getBranchName());
        dto.setOrgId(vo.getOrgId());

        if (vo.getVehicle() != null) {
            dto.setVehicleId(vo.getVehicle().getId());
            dto.setVehicle(vo.getVehicle().getVehicleNumber());
        }

        if (vo.getUser() != null) {
            dto.setUser(vo.getUser().getId());
        }

        if (vo.getParts() != null) {
        	dto.setParts(
        		    vo.getParts().stream()
        		        .map(p -> new MaintenancePartResponseDTO(
        		                p.getId(),   // ✅ use correct ID field
        		                p.getName(),
        		                p.getQuantity(),
        		                p.getCost()
        		        ))
        		        .collect(Collectors.toList()) // safer than toList() for Java < 16
        		);
        }

        return dto;
    }



    public void calculateTotals(MaintenanceVO maintenance) {

        BigDecimal totalCost = BigDecimal.ZERO;
        BigDecimal totalQty = BigDecimal.ZERO;

        if (maintenance.getParts() != null) {
            for (MaintenancePartVO part : maintenance.getParts()) {
                if (part.getCost() != null && part.getQuantity() != null) {
                    totalCost = totalCost.add(
                            part.getCost().multiply(BigDecimal.valueOf(part.getQuantity()))
                    );
                    totalQty = totalQty.add(BigDecimal.valueOf(part.getQuantity()));
                }
            }
        }

        maintenance.setTotalCost(totalCost);
        maintenance.setTotalqty(totalQty);
    }



    public void mapParts(List<MaintenancePartDTO> partDTOs, MaintenanceVO maintenance) {

	    if (maintenance.getParts() == null) {
	        maintenance.setParts(new ArrayList<>());
	    } else {
	        maintenance.getParts().clear(); // orphanRemoval = true
	    }

	    if (partDTOs == null || partDTOs.isEmpty()) return;

	    for (MaintenancePartDTO dto : partDTOs) {

	        MaintenancePartVO part = new MaintenancePartVO();
	        part.setName(dto.getName());
	        part.setQuantity(dto.getQuantity());
	        part.setCost(dto.getCost());
	        part.setMaintenanceVO(maintenance);

	        maintenance.getParts().add(part);
	    }
	}



	private void mapMaintenanceDTOtoVO(MaintenanceDTO dto, MaintenanceVO vo) {

	    vo.setTitle(dto.getTitle());
	    vo.setType(dto.getType());
	    vo.setStatus(dto.getStatus());
	    vo.setPriority(dto.getPriority());
	    vo.setScheduledDate(dto.getScheduledDate());
	    vo.setCompletedDate(dto.getCompletedDate());
	    vo.setOdometerReading(dto.getOdometerReading());
	    vo.setEstimatedCost(dto.getEstimatedCost());
	    vo.setServiceCenter(dto.getServiceCenter());
	    vo.setMechanic(dto.getMechanic());
	    vo.setDescription(dto.getDescription());
	    vo.setNotes(dto.getNotes());
	    vo.setBranchCode(dto.getBranchCode());
	    vo.setBranchName(dto.getBranchName());
	    vo.setOrgId(dto.getOrgId());
	}



//	@Override
//	public Map<String, Object> getAllMaintenanceByOrgId(Long orgId) {
////		Pageable pageable = PageRequest.of(page - 1, count);
//		List<MaintenanceVO> quotePage = maintenanceRepo.getMaintenanceByOrgId(orgId);
//		
////		Page<MaintenanceResponseDTO> dtoPage = quotePage.map(this::mapToMaintenanceResponseDTO);
//		return  quotePage;
//	}
	
	
	@Override
	public List<MaintenanceVO> getAllMaintenanceByOrgId(Long orgId) {
	    return maintenanceRepo.getMaintenanceByOrgId(orgId);
	}

	@Override
	public MaintenanceResponseDTO getMaintenanceById(Long id) {
		MaintenanceVO maintenanceVO=maintenanceRepo.findById(id).orElseThrow();
		MaintenanceResponseDTO maintenanceResponseDTO= mapToMaintenanceResponseDTO(maintenanceVO);
		return maintenanceResponseDTO;
	}
}

