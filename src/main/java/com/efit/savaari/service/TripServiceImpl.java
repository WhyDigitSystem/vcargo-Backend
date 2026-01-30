package com.efit.savaari.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efit.savaari.dto.ConsentDTO;
import com.efit.savaari.dto.TraqoTripRequest;
import com.efit.savaari.dto.TripDTO;
import com.efit.savaari.dto.TripWaypointDTO;
import com.efit.savaari.entity.TdriverVO;
import com.efit.savaari.entity.TripVO;
import com.efit.savaari.entity.TripWaypointVO;
import com.efit.savaari.entity.TvehicleVO;
import com.efit.savaari.entity.UserVO;
import com.efit.savaari.repo.TdriverRepo;
import com.efit.savaari.repo.TripRepo;
import com.efit.savaari.repo.TvehicleRepo;
import com.efit.savaari.repo.UserRepo;
import com.efit.savaari.responseDTO.TraqoTripResponse;
import com.efit.savaari.responseDTO.TripResponseDTO;
import com.efit.savaari.responseDTO.TripWaypointResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@Service
public class TripServiceImpl implements TripService {

	@Autowired
	private TripRepo tripRepo;

	@Autowired
	UserRepo userRepo;

	@Autowired
	PaginationService paginationService;

	@Autowired
	TvehicleRepo vehicleRepo;

	@Autowired
	TdriverRepo driverRepo;

	@Autowired
	TraqoService traqoService;

	@Transactional(rollbackOn = Exception.class)
	@Override
	public Map<String, Object> createUpdateTrip(TripDTO dto) {

		TripVO trip;
		String message;

		if (dto.getId() != null) {
			trip = tripRepo.findById(dto.getId()).orElseThrow(() -> new RuntimeException("Invalid Trip ID"));
			trip.setUpdatedBy(dto.getCreatedBy());
			message = "Trip Updated Successfully";
		} else {
			trip = new TripVO();
			trip.setCreatedBy(dto.getCreatedBy());
			trip.setUpdatedBy(dto.getCreatedBy());
			message = "Trip Created Successfully";
		}

		mapTripDTOtoVO(dto, trip);

		if (dto.getUser() != null) {
			UserVO user = userRepo.findById(dto.getUser()).orElseThrow(() -> new RuntimeException("Invalid User"));
			trip.setUser(user);
		}

		if (dto.getVehicle() != null) {
			TvehicleVO vehicle = vehicleRepo.findById(Long.parseLong(dto.getVehicle()))
					.orElseThrow(() -> new RuntimeException("Invalid Vehicle"));
			trip.setVehicle(vehicle);
		}

		if (dto.getDriver() != null) {
			TdriverVO driver = driverRepo.findById(Long.parseLong(dto.getDriver()))
					.orElseThrow(() -> new RuntimeException("Invalid Driver"));
			trip.setDriver(driver);
		}

		if (trip.getWaypoints() != null) {
			trip.getWaypoints().clear();
		}

		if (dto.getWaypoints() != null) {
			for (TripWaypointDTO wpDTO : dto.getWaypoints()) {
				TripWaypointVO wp = new TripWaypointVO();
				wp.setLocation(wpDTO.getLocation());
				wp.setSequenceNo(wpDTO.getSequenceNo());
				wp.setTripVO(trip); // 🔥 VERY IMPORTANT
				trip.getWaypoints().add(wp);
			}
		}

		trip = tripRepo.save(trip);
		String driverPhone=null;
		if (trip.getDriver() != null) {
			driverPhone=trip.getDriver().getPhone();
		}
		
		TraqoTripRequest request = new TraqoTripRequest();
		request.setTel(driverPhone);

		request.setSrc(trip.getSourceLat() + "," + trip.getSourceLng());
		request.setDest(trip.getDestinationLat() + "," + trip.getDestinationLng());

		request.setSrcname(trip.getSource());
		request.setDestname(trip.getDestination());

		if (trip.getVehicle() != null)
			request.setTruck_number(trip.getVehicle().getVehicleNumber());

		request.setInvoice(trip.getId().toString());
		request.setEta_hrs(dto.getEstimatedDuration());

		TraqoTripResponse traqresponse = traqoService.createTrip(request);

		// 🔥 FAILURE → THROW EXCEPTION → ROLLBACK
		if (traqresponse == null || !"success".equalsIgnoreCase(traqresponse.getStatus())) {
			throw new RuntimeException(traqresponse != null ? traqresponse.getStatus() : "Traqo API failed");
		}

		// 🔥 SUCCESS → SAVE tripTrackId
		trip.setTripTrackId(traqresponse.getTripId());
		tripRepo.save(trip);

		TripResponseDTO responseDTO = mapToTripResponseDTO(trip);

		Map<String, Object> response = new HashMap<>();
		response.put("trip", responseDTO);
		response.put("message", message);

		return response;
	}

	private void mapTripDTOtoVO(TripDTO dto, TripVO trip) {

		trip.setSource(dto.getSource());
		trip.setDestination(dto.getDestination());
		trip.setCustomer(dto.getCustomer());
		trip.setDistance(dto.getDistance());
		trip.setEstimatedDuration(dto.getEstimatedDuration());
		trip.setSourceLat(dto.getSourceLat());
		trip.setSourceLng(dto.getSourceLng());
		trip.setDestinationLat(dto.getDestinationLat());
		trip.setDestinationLng(dto.getDestinationLng());
		trip.setStartDate(dto.getStartDate());
		trip.setStartTime(dto.getStartTime());
		trip.setEndDate(dto.getEndDate());
		trip.setEndTime(dto.getEndTime());

		trip.setStatus(dto.getStatus());
		trip.setTripType(dto.getTripType());
		trip.setGoodsType(dto.getGoodsType());

		trip.setGoodsWeight(dto.getGoodsWeight());
		trip.setGoodsValue(dto.getGoodsValue());
		trip.setTripCost(dto.getTripCost());
		trip.setRevenue(dto.getRevenue());
		trip.setProfit(dto.getProfit());

		trip.setFuelCost(dto.getFuelCost());
		trip.setTollCharges(dto.getTollCharges());
		trip.setOtherExpenses(dto.getOtherExpenses());

		trip.setNotes(dto.getNotes());
		trip.setBranchCode(dto.getBranchCode());
		trip.setBranchName(dto.getBranchName());
		trip.setOrgId(dto.getOrgId());
	}

	private TripResponseDTO mapToTripResponseDTO(TripVO trip) {

		TripResponseDTO dto = new TripResponseDTO();

		dto.setId(trip.getId());
		dto.setSource(trip.getSource());
		dto.setDestination(trip.getDestination());
		dto.setCustomer(trip.getCustomer());
		dto.setDistance(trip.getDistance());
		dto.setEstimatedDuration(trip.getEstimatedDuration());
		dto.setSourceLat(trip.getSourceLat());
		dto.setSourceLng(trip.getSourceLng());
		dto.setDestinationLat(trip.getDestinationLat());
		dto.setDestinationLng(trip.getDestinationLng());
		dto.setTripTrackId(trip.getTripTrackId());

		dto.setStartDate(trip.getStartDate());
		dto.setStartTime(trip.getStartTime());
		dto.setEndDate(trip.getEndDate());
		dto.setEndTime(trip.getEndTime());

		dto.setStatus(trip.getStatus());
		dto.setTripType(trip.getTripType());
		dto.setGoodsType(trip.getGoodsType());

		dto.setGoodsWeight(trip.getGoodsWeight());
		dto.setGoodsValue(trip.getGoodsValue());
		dto.setTripCost(trip.getTripCost());
		dto.setRevenue(trip.getRevenue());
		dto.setProfit(trip.getProfit());

		dto.setFuelCost(trip.getFuelCost());
		dto.setTollCharges(trip.getTollCharges());
		dto.setOtherExpenses(trip.getOtherExpenses());

		dto.setNotes(trip.getNotes());
		dto.setActive(trip.isActive());
		dto.setCreatedBy(trip.getCreatedBy());
		dto.setBranchCode(trip.getBranchCode());
		dto.setBranchName(trip.getBranchName());
		dto.setOrgId(trip.getOrgId());

		if (trip.getUser() != null)
			dto.setUser(trip.getUser().getId());

		if (trip.getVehicle() != null) {
			dto.setVehicleId(trip.getVehicle().getId());
			dto.setVehicle(trip.getVehicle().getVehicleNumber());
		}

		if (trip.getDriver() != null) {
			dto.setDriverId(trip.getDriver().getId());
			dto.setDriver(trip.getDriver().getName());
		}

		dto.setWaypoints(trip.getWaypoints().stream()
				.map(w -> new TripWaypointResponseDTO(w.getId(), w.getLocation(), w.getSequenceNo())).toList());

		return dto;
	}

	@Override
	public List<TripVO> getAllTripByOrgId(Long orgId) {
		return tripRepo.getTripByOrgId(orgId);

	}

	@Override
	public TripResponseDTO getTripById(Long id) {
		TripVO tripVO = tripRepo.findById(id).orElseThrow();
		TripResponseDTO tripResponseDTO = mapToTripResponseDTO(tripVO);
		return tripResponseDTO;
	}

	@Transactional
	@Override
	public String updateTripStartEnd(Long id, String status, boolean forceProceed)
			throws JsonMappingException, JsonProcessingException {

		TripVO trip = tripRepo.findById(id).orElseThrow(() -> new RuntimeException("Trip not found"));

		TdriverVO driver = trip.getDriver();

		if (driver == null) {
			throw new RuntimeException("Driver not assigned to trip");
		}

		if ("START".equalsIgnoreCase(status)) {

			int updated = tripRepo.updateTripStart(id);
			if (updated == 0) {
				throw new RuntimeException("Trip not found");
			}

			driverRepo.updateDriverStatus(driver.getId(), "Ontrip");

			return "Trip Started Successfully";

		} else if ("END".equalsIgnoreCase(status)) {

			int updated = tripRepo.updateTripEnd(id);
			if (updated == 0) {
				throw new RuntimeException("Trip not found");
			}

			driverRepo.updateDriverStatus(driver.getId(), "Active");

			return "Trip Completed Successfully";

		} else {
			throw new IllegalArgumentException("Invalid status value");
		}
	}

	@Override
	public Object checkTripConsent(Long tripId) {

		TripVO trip = tripRepo.findById(tripId).orElseThrow(() -> new RuntimeException("Trip not found"));

		TdriverVO driver = trip.getDriver();
		if (driver == null) {
			throw new RuntimeException("Driver not assigned");
		}

		ConsentDTO consentDTO = new ConsentDTO();
		consentDTO.setTel(driver.getPhone());

		Object consentResponse = traqoService.checkConsent(consentDTO);
		return consentResponse;
	}

}
