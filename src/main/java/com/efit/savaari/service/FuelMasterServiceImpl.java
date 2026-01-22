package com.efit.savaari.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.efit.savaari.dto.FuelMasterDTO;
import com.efit.savaari.entity.FuelVO;
import com.efit.savaari.entity.TdriverVO;
import com.efit.savaari.entity.TvehicleVO;
import com.efit.savaari.entity.UserVO;
import com.efit.savaari.exception.ApplicationException;
import com.efit.savaari.repo.FuelRepo;
import com.efit.savaari.repo.TdriverRepo;
import com.efit.savaari.repo.TvehicleRepo;
import com.efit.savaari.repo.UserRepo;
import com.efit.savaari.responseDTO.FuelResponseDTO;

@Service
public class FuelMasterServiceImpl implements FuelMasterService {

	public static final Logger LOGGER = LoggerFactory.getLogger(FuelMasterServiceImpl.class);

	@Autowired
	FuelRepo fuelRepo;

	@Autowired
	TvehicleRepo vehicleRepo;

	@Autowired
	TdriverRepo driverRepo;
	
	@Autowired
	PaginationService paginationService;
	
	@Autowired
	UserRepo userRepo;

	@Override
	@Transactional
	public Map<String, Object> createUpdateFuelMaster(FuelMasterDTO dto) throws ApplicationException {


		FuelVO fuel;
		String message;

		if (dto.getId() != null) {
			fuel = fuelRepo.findById(dto.getId()).orElseThrow(() -> new ApplicationException("Invalid Fuel ID"));
			fuel.setUpdatedBy(dto.getCreatedBy());
			message = "Fuel Entry Updated Successfully";
		} else {
			fuel = new FuelVO();
			fuel.setCreatedBy(dto.getCreatedBy());
			fuel.setUpdatedBy(dto.getCreatedBy());
			message = "Fuel Entry Created Successfully";
		}

		// ===== VEHICLE =====
		if (dto.getVehicle() != null) {
			TvehicleVO vehicle = vehicleRepo.findById(Long.parseLong(dto.getVehicle()))
					.orElseThrow(() -> new ApplicationException("Invalid Vehicle"));
			fuel.setVehicle(vehicle);
		}

		// ===== DRIVER =====
		if (dto.getDriver() != null) {
			TdriverVO driver = driverRepo.findById(Long.parseLong(dto.getDriver()))
					.orElseThrow(() -> new ApplicationException("Invalid Driver"));
			fuel.setDriver(driver);
		}
		
		if (dto.getUser() != null) {
			UserVO user = userRepo.findById(dto.getUser()).orElseThrow(() -> new ApplicationException("Invalid User"));
			fuel.setUser(user);
		}



		// ===== BASIC MAPPING =====
		mapFuelDTOtoVO(dto, fuel);

		fuel = fuelRepo.save(fuel);

		FuelResponseDTO fuelResponseDTO = mapToFuelResponseDTO(fuel);

		Map<String, Object> response = new HashMap<>();
		response.put("fuel", fuelResponseDTO);
		response.put("message", message);

		return response;
	}

	public FuelResponseDTO mapToFuelResponseDTO(FuelVO fuel) {

		FuelResponseDTO dto = new FuelResponseDTO();

		dto.setId(fuel.getId());

		if (fuel.getVehicle() != null) {
			TvehicleVO vehicle = fuel.getVehicle();
			dto.setVehicleId(vehicle.getId());
			dto.setVehicle(vehicle.getVehicleNumber());
		}

		if (fuel.getDriver() != null) {
			TdriverVO driverVO = fuel.getDriver();
			dto.setDriverId(driverVO.getId());
			dto.setDriver(driverVO.getName());
		}
		
		if (fuel.getUser() != null) {
			UserVO userVo = fuel.getUser();
			dto.setUser(userVo.getId());
		}

		dto.setFuelType(fuel.getFuelType());
		dto.setQuantity(fuel.getQuantity());
		dto.setCost(fuel.getCost());
		fuel.setEfficiency(dto.getEfficiency());

		dto.setPreviousOdometer(fuel.getPreviousOdometer());
		dto.setOdometerReading(fuel.getOdometerReading());

		dto.setStation(fuel.getStation());
		dto.setReceiptNumber(fuel.getReceiptNumber());
		dto.setDate(fuel.getDate());
		dto.setTime(fuel.getTime());
		dto.setNotes(fuel.getNotes());
		dto.setActive(fuel.isActive());

		dto.setCreatedBy(fuel.getCreatedBy());
		dto.setOrgId(fuel.getOrgId());
		dto.setBranchCode(fuel.getBranchCode());
		dto.setBranchName(fuel.getBranchName());

		return dto;
	}

	public void mapFuelDTOtoVO(FuelMasterDTO dto, FuelVO fuel) {

		fuel.setFuelType(dto.getFuelType());
		fuel.setQuantity(dto.getQuantity());
		fuel.setCost(dto.getCost());
		fuel.setOdometerReading(dto.getOdometerReading());
		fuel.setPreviousOdometer(dto.getPreviousOdometer());
		fuel.setStation(dto.getStation());
		fuel.setEfficiency(dto.getEfficiency());


		if (dto.getDate() != null) {
			fuel.setDate(LocalDate.parse(dto.getDate()));
		}

		if (dto.getTime() != null) {
			fuel.setTime(LocalTime.parse(dto.getTime()));
		}

		fuel.setReceiptNumber(dto.getReceiptNumber());
		fuel.setNotes(dto.getNotes());

		fuel.setBranchCode(dto.getBranchCode());
		fuel.setBranchName(dto.getBranchName());
		fuel.setOrgId(dto.getOrgId());
	}

	@Override
	public Map<String, Object> getFuelByVehicle(Long vehicleId, int page, int count) {
		
		Pageable pageable = PageRequest.of(page - 1, count);
		Page<FuelVO> quotePage = fuelRepo.getFuelByVehicle(vehicleId,  pageable);
		
		Page<FuelResponseDTO> dtoPage = quotePage.map(this::mapToFuelResponseDTO);
		return  paginationService.buildResponse(dtoPage);
	}

	@Override
	public FuelVO getFuelById(Long fuelId) {
		return fuelRepo.findById(fuelId).orElseThrow();
	}

	@Override
	public Map<String, Object> getAllFuelByOrgId(Long orgId, int page, int count) {
		Pageable pageable = PageRequest.of(page - 1, count);
		Page<FuelVO> quotePage = fuelRepo.getFuelByOrgId(orgId,  pageable);
		
		Page<FuelResponseDTO> dtoPage = quotePage.map(this::mapToFuelResponseDTO);
		return  paginationService.buildResponse(dtoPage);
	}

}
