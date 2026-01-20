package com.efit.savaari.service;

import java.util.Map;

import com.efit.savaari.dto.FastagRequestDTO;
import com.fasterxml.jackson.databind.JsonNode;

public interface TrackingTokenService {

	Map<String, Object> getToken();

	Map<String, Object>  callFastagApi(FastagRequestDTO dto);
}
