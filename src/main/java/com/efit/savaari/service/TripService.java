package com.efit.savaari.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.efit.savaari.dto.TripDTO;
import com.efit.savaari.responseDTO.TripResponseDTO;

@Service
public interface TripService {

	Map<String, Object> createUpdateTrip(TripDTO dto);

	Map<String, Object> getAllTripByOrgId(Long orgId, int page, int count);

	TripResponseDTO getTripById(Long id);
}
