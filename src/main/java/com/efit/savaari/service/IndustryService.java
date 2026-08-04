package com.efit.savaari.service;

import org.springframework.stereotype.Service;

import com.efit.savaari.exception.ApplicationException;

@Service
public interface IndustryService {


	void approveUserAdmin(Long id, String action, String actionBy,String type) throws ApplicationException;

}
