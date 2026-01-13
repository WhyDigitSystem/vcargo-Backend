package com.efit.savaari.service;

import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface DashBoardService {

//	Map<String, Object> getDashboardData(Long orgId);

	Map<String, Object> getAllDashBoardDetails(Long orgId);

	Map<String, Object> getDashboardData(Long orgId);

}
