package com.efit.savaari.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.efit.savaari.dto.TripDTO;
import com.efit.savaari.entity.TripVO;
import com.efit.savaari.responseDTO.TripResponseDTO;

@Service
public interface TripService {

	Map<String, Object> createUpdateTrip(TripDTO dto);

	List<TripVO> getAllTripByOrgId(Long orgId);

	TripResponseDTO getTripById(Long id);
	
	String updateTripStartEnd(Long id, String status);
	
	
}
