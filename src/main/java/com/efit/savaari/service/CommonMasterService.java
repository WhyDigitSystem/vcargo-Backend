package com.efit.savaari.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.efit.savaari.dto.RolesDTO;
import com.efit.savaari.dto.ScreenNamesDTO;
import com.efit.savaari.entity.RolesVO;
import com.efit.savaari.entity.ScreenNamesVO;
import com.efit.savaari.exception.ApplicationException;

@Service
public interface CommonMasterService {


//	FinScreen
	List<ScreenNamesVO> getFinScreenById(Long id);


	List<Map<String, Object>> getAllScreenCode(Long orgId);

	// Screen Names
	Map<String, Object> createUpdateScreenNames(ScreenNamesDTO screenNamesDTO) throws ApplicationException;

	List<ScreenNamesVO> getAllScreenNames();

	ScreenNamesVO getScreenNamesById(Long id) throws ApplicationException;


	//Roles
	
	RolesVO getRolesById(Long id);
	
	Map<String, Object> createUpdateRoles(RolesDTO rolesDTO) throws ApplicationException;
	
	List<Map<String, Object>> getAllActiveRolesByOrgId(Long orgId);
	
	List<RolesVO> getRolesByOrgId(Long orgId);







	
	

}
