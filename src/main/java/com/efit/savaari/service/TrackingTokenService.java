package com.efit.savaari.service;

import java.util.Map;

import com.efit.savaari.dto.FastagRequestDTO;

public interface TrackingTokenService {

	Map<String, Object> getToken();

	Map<String, Object>  callFastagApi(FastagRequestDTO dto);

	void autoRefreshIfExpired();


	Map<String, Object> getTokenDetails();
}
