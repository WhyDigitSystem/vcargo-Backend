package com.efit.savaari.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.efit.savaari.dto.TyreMasterDTO;
import com.efit.savaari.entity.TvehicleVO;
import com.efit.savaari.entity.TyreMasterVO;
import com.efit.savaari.entity.UserVO;
import com.efit.savaari.exception.ApplicationException;
import com.efit.savaari.repo.TvehicleRepo;
import com.efit.savaari.repo.TyreMasterRepo;
import com.efit.savaari.repo.UserRepo;
import com.efit.savaari.responseDTO.TyreMasterResponseDTO;

@Service
public class TyreMasterServiceImpl implements TyreMasterService {

	@Autowired
	TyreMasterRepo tyreRepo;

	@Autowired
	TvehicleRepo vehicleRepo;

	@Autowired
	UserRepo userRepo;
	
	@Autowired
	PaginationService paginationService;

	@Override
	@Transactional
	public Map<String, Object> createUpdateTyreMaster(TyreMasterDTO dto) throws ApplicationException {

		TyreMasterVO tyre;
		String message;

		// ===== CREATE / UPDATE =====
		if (dto.getId() != null) {
			tyre = tyreRepo.findById(dto.getId()).orElseThrow(() -> new ApplicationException("Invalid Tyre ID"));
			tyre.setUpdatedBy(dto.getCreatedBy());
			message = "Tyre Updated Successfully";
		} else {
			tyre = new TyreMasterVO();
			tyre.setCreatedBy(dto.getCreatedBy());
			tyre.setUpdatedBy(dto.getCreatedBy());
			message = "Tyre Created Successfully";
		}

		// ===== VEHICLE =====
		if (dto.getVehicle() != null) {
			TvehicleVO vehicle = vehicleRepo.findById(Long.parseLong(dto.getVehicle()))
					.orElseThrow(() -> new ApplicationException("Invalid Vehicle"));
			tyre.setVehicle(vehicle);
		}

		// ===== USER =====
		if (dto.getUser() != null) {
			UserVO user = userRepo.findById(dto.getUser()).orElseThrow(() -> new ApplicationException("Invalid User"));
			tyre.setUser(user);
		}

		// ===== BASIC MAPPING =====
		mapTyreDTOtoVO(dto, tyre);

		tyre = tyreRepo.save(tyre);

		TyreMasterResponseDTO responseDTO = mapToTyreResponseDTO(tyre);

		Map<String, Object> response = new HashMap<>();
		response.put("tyre", responseDTO);
		response.put("message", message);

		return response;
	}
	
	public void mapTyreDTOtoVO(TyreMasterDTO dto, TyreMasterVO tyre) {

	    tyre.setSerialNumber(dto.getSerialNumber());
	    tyre.setBrand(dto.getBrand());
	    tyre.setModel(dto.getModel());
	    tyre.setSize(dto.getSize());
	    tyre.setPosition(dto.getPosition());
	    tyre.setStatus(dto.getStatus());

	    if (dto.getPurchaseDate() != null) {
	        tyre.setPurchaseDate(LocalDate.parse(dto.getPurchaseDate()));
	    }

	    tyre.setPurchaseCost(dto.getPurchaseCost());
	    tyre.setOdometerReading(dto.getOdometerReading());
	    tyre.setTreadDepth(dto.getTreadDepth());
	    tyre.setRecommendedPressure(dto.getRecommendedPressure());
	    tyre.setPressure(dto.getPressure());
	    tyre.setNotes(dto.getNotes());

	    tyre.setBranchCode(dto.getBranchCode());
	    tyre.setBranchName(dto.getBranchName());
	    tyre.setOrgId(dto.getOrgId());
	}
	
	public TyreMasterResponseDTO mapToTyreResponseDTO(TyreMasterVO tyre) {

		TyreMasterResponseDTO dto = new TyreMasterResponseDTO();

	    dto.setId(tyre.getId());
	    dto.setSerialNumber(tyre.getSerialNumber());
	    dto.setBrand(tyre.getBrand());
	    dto.setModel(tyre.getModel());
	    dto.setSize(tyre.getSize());
	    dto.setPosition(tyre.getPosition());
	    dto.setStatus(tyre.getStatus());

	    dto.setPurchaseDate(tyre.getPurchaseDate());
	    dto.setPurchaseCost(tyre.getPurchaseCost());
	    dto.setOdometerReading(tyre.getOdometerReading());
	    dto.setTreadDepth(tyre.getTreadDepth());
	    dto.setRecommendedPressure(tyre.getRecommendedPressure());
	    dto.setPressure(tyre.getPressure());
	    dto.setNotes(tyre.getNotes());

	    dto.setActive(tyre.isActive());
	    dto.setCreatedBy(tyre.getCreatedBy());
	    dto.setOrgId(tyre.getOrgId());
	    dto.setBranchCode(tyre.getBranchCode());
	    dto.setBranchName(tyre.getBranchName());

	    if (tyre.getVehicle() != null) {
			TvehicleVO vehicle = tyre.getVehicle();
			dto.setVehicleId(vehicle.getId());
			dto.setVehicle(vehicle.getVehicleNumber());
		}

	    if (tyre.getUser() != null) {
			UserVO userVo = tyre.getUser();
			dto.setUser(userVo.getId());
		}

	    return dto;
	}

	@Override
	public Map<String, Object> getAllTyreByOrgId(Long orgId, int page, int count) {
		Pageable pageable = PageRequest.of(page - 1, count);
		Page<TyreMasterVO> quotePage = tyreRepo.getTyreByOrgId(orgId,  pageable);
		
		Page<TyreMasterResponseDTO> dtoPage = quotePage.map(this::mapToTyreResponseDTO);
		return  paginationService.buildResponse(dtoPage);
	}
	

	@Override
	public TyreMasterResponseDTO getTyreById(Long id) {
		TyreMasterVO tyreMasterVO=tyreRepo.findById(id).orElseThrow();
		TyreMasterResponseDTO tyreMasterResponseDTO= mapToTyreResponseDTO(tyreMasterVO);
		return tyreMasterResponseDTO;
	}



}
