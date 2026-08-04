package com.efit.savaari.service;

import java.util.Set;

import org.springframework.stereotype.Service;

@Service
public interface GlobalParameterService {
	// Global Parameter

	
	// to getAcces Global Param Dteails

	Set<Object[]> getGlobalParametersBranchAndBranchCodeByOrgIdAndUserName(Long orgid, String userName);


}