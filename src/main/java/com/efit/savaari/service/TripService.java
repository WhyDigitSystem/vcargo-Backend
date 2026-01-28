package com.efit.savaari.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.efit.savaari.dto.TripDTO;
import com.efit.savaari.entity.TripVO;
import com.efit.savaari.responseDTO.TripResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@Service
public interface TripService {

	Map<String, Object> createUpdateTrip(TripDTO dto);

	List<TripVO> getAllTripByOrgId(Long orgId);

	TripResponseDTO getTripById(Long id);
	

	String updateTripStartEnd(Long id, String status, boolean forceProceed) throws JsonMappingException, JsonProcessingException;


	Object checkTripConsent(Long id);
	
	
	
}
