package com.efit.savaari.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.efit.savaari.dto.RolesPermissionHeaderDTO;
import com.efit.savaari.entity.RolesPermissionHeaderVO;
import com.efit.savaari.exception.ApplicationException;

@Service
public interface RolesResponsibilitiesService {

	List<RolesPermissionHeaderVO> getRolesPermissionHeaderByRoleandOrgid(String role, Long orgid);

	Map<String, Object> createUpdateRoleScreenPermission(RolesPermissionHeaderDTO rolesPermissionHeaderDTO) throws ApplicationException;

}
