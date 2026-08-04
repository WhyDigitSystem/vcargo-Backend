package com.efit.savaari.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.efit.savaari.dto.RolesDTO;
import com.efit.savaari.dto.ScreenNamesDTO;
import com.efit.savaari.entity.RolesVO;
import com.efit.savaari.entity.ScreenNamesVO;
import com.efit.savaari.exception.ApplicationException;
import com.efit.savaari.repo.ResponsibilitiesRepo;
import com.efit.savaari.repo.RoleRepo;
import com.efit.savaari.repo.RolesRepo;
import com.efit.savaari.repo.ScreenNamesRepo;
import com.efit.savaari.repo.UserRepo;

@Service
public class CommonMasterServiceImpl implements CommonMasterService {

	public static final Logger LOGGER = LoggerFactory.getLogger(CommonMasterServiceImpl.class);

	@Autowired
	RolesRepo rolesRepo;

	@Autowired
	PasswordEncoder encoder;


	@Autowired
	UserRepo userRepo;

	@Autowired
	RoleRepo roleRepo;

	@Autowired
	ResponsibilitiesRepo responsibilitiesRepo;

	@Autowired
	ScreenNamesRepo screenNamesRepo;




	// FinScreen-----------------------------------------------------------------------------------
	@Override
	public List<ScreenNamesVO> getFinScreenById(Long id) {
		List<ScreenNamesVO> finScreenVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received FinScreen BY Id : {}", id);
			finScreenVO = screenNamesRepo.findFinScreenById(id);
		} else {
			LOGGER.info("Successfully Received FinScreen For All Id.");
			finScreenVO = screenNamesRepo.findAll();
		}
		return finScreenVO;
	}

	
	@Override
	public List<Map<String, Object>> getAllScreenCode(Long orgId) {
		Set<Object[]> getFinScreen = screenNamesRepo.findAllScreenCode(orgId);
		return getScreen(getFinScreen);
	}

	private List<Map<String, Object>> getScreen(Set<Object[]> getFinScreen) {
		List<Map<String, Object>> finScreenList = new ArrayList<>();

		for (Object[] finScreen : getFinScreen) {
			Map<String, Object> branchMap = new HashMap<>();
			branchMap.put("screenCode", finScreen[0] != null ? finScreen[0].toString() : "");
			branchMap.put("screenName", finScreen[1] != null ? finScreen[1].toString() : "");
			finScreenList.add(branchMap);
		}
		return finScreenList;
	}


	@Override
	public Map<String, Object> createUpdateScreenNames(ScreenNamesDTO screenNamesDTO) throws ApplicationException {
		ScreenNamesVO screenNamesVO = new ScreenNamesVO();
		String message = null;

		if (ObjectUtils.isEmpty(screenNamesDTO.getId())) {

			// Validate if responsibility already exists by responsibility name
			if (screenNamesRepo.existsByScreenName(screenNamesDTO.getScreenName())) {
				throw new ApplicationException("Screen Name already exists");
			}
			if (screenNamesRepo.existsByScreenCode(screenNamesDTO.getScreenCode())) {
				throw new ApplicationException("Screen Code already exists");
			}

			screenNamesVO.setCreatedBy(screenNamesDTO.getCreatedBy());
			screenNamesVO.setUpdatedBy(screenNamesDTO.getCreatedBy());
			screenNamesVO.setActive(screenNamesDTO.isActive());
			screenNamesVO.setScreenCode(screenNamesDTO.getScreenCode());
			screenNamesVO.setScreenName(screenNamesDTO.getScreenName());
			// Set the values from screenNamesDTO to responsibilityVO
			message = "ScreenName Created successfully";

		} else {

			// Retrieve the existing ResponsibilityVO from the repository
			screenNamesVO = screenNamesRepo.findById(screenNamesDTO.getId())
					.orElseThrow(() -> new ApplicationException("Screen Name not found"));

			// Validate and update unique fields if changed
			if (!screenNamesVO.getScreenName().equalsIgnoreCase(screenNamesDTO.getScreenName())) {
				if (screenNamesRepo.existsByScreenName(screenNamesDTO.getScreenName())) {
					throw new ApplicationException("Screen Name already exists");
				}
				screenNamesVO.setScreenName(screenNamesDTO.getScreenName());
			}
			if (!screenNamesVO.getScreenCode().equalsIgnoreCase(screenNamesDTO.getScreenCode())) {
				if (screenNamesRepo.existsByScreenCode(screenNamesDTO.getScreenCode())) {
					throw new ApplicationException("Screen Code already exists");
				}
				screenNamesVO.setScreenCode(screenNamesDTO.getScreenCode());
			}
			screenNamesVO.setActive(screenNamesDTO.isActive());
			screenNamesVO.setUpdatedBy(screenNamesDTO.getCreatedBy());
			// Update the remaining fields from screenNamesDTO to responsibilityVO
			message = "ScreenName Updated successfully";
		}

		screenNamesRepo.save(screenNamesVO);
		Map<String, Object> response = new HashMap<>();
		response.put("screenNamesVO", screenNamesVO);
		response.put("message", message);
		return response;
	}

	@Override
	public List<ScreenNamesVO> getAllScreenNames() {

		return screenNamesRepo.findAll();
	}

	@Override
	public ScreenNamesVO getScreenNamesById(Long id) throws ApplicationException {

		if (ObjectUtils.isEmpty(id)) {
			throw new ApplicationException("Invalid Id");
		}

		ScreenNamesVO screenNamesVO = screenNamesRepo.findById(id)
				.orElseThrow(() -> new ApplicationException("Screen Name not found for Id: " + id));

		return screenNamesVO;
	}

	
	// Roles

	@Override
	public List<Map<String, Object>> getAllActiveRolesByOrgId(Long orgId) {
		Set<Object[]> chType = rolesRepo.getAllActiveRolesByOrgId(orgId);
		return getAllActiveRoles(chType);
	}

	private List<Map<String, Object>> getAllActiveRoles(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("role", ch[0] != null ? ch[0].toString() : "");
			map.put("active", ch[1] != null ? ch[1].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public Map<String, Object> createUpdateRoles(RolesDTO rolesDTO) throws ApplicationException {

		RolesVO rolesVO = new RolesVO();
		String message;
		String screenCode = "DEPT";
		if (ObjectUtils.isNotEmpty(rolesDTO.getId())) {
			rolesVO = rolesRepo.findById(rolesDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid Roles details"));

			rolesVO.setUpdatedBy(rolesDTO.getCreatedBy());
			if (!rolesVO.getRole().equalsIgnoreCase(rolesDTO.getRole())) {
				if (rolesRepo.existsByRoleAndOrgId(rolesDTO.getRole(), rolesDTO.getOrgId())) {
					String errorMessage = String.format("The Role: %s already exists in This Organization.",
							rolesDTO.getRole());
					throw new ApplicationException(errorMessage);
				}
				rolesVO.setRole(rolesDTO.getRole().toUpperCase());
			}

			message = "Role Updated Successfully";
		} else {

			if (rolesRepo.existsByRoleAndOrgId(rolesDTO.getRole(), rolesDTO.getOrgId())) {
				String errorMessage = String.format("The DepartmentName : %s already exists in This Organization.",
						rolesDTO.getRole());
				throw new ApplicationException(errorMessage);
			}

			rolesVO.setCreatedBy(rolesDTO.getCreatedBy());
			rolesVO.setUpdatedBy(rolesDTO.getCreatedBy());
			message = "Department Created Successfully";
		}

		createUpdateRolesVOByRolesDTO(rolesDTO, rolesVO);
		rolesRepo.save(rolesVO);
		Map<String, Object> response = new HashMap<>();
		response.put("rolesVO", rolesVO);
		response.put("message", message);
		return response;
	}

	private void createUpdateRolesVOByRolesDTO(RolesDTO rolesDTO, RolesVO rolesVO) {
		rolesVO.setRole(rolesDTO.getRole().toUpperCase());
		rolesVO.setCreatedBy(rolesDTO.getCreatedBy().toUpperCase());
		rolesVO.setOrgId(rolesDTO.getOrgId());
		rolesVO.setUpdatedBy(rolesDTO.getUpdatedBy());
		rolesVO.setActive(rolesDTO.isActive());

	}

	@Override
	public RolesVO getRolesById(Long id) {

		return rolesRepo.getRolesById(id);
	}

	@Override
	public List<RolesVO> getRolesByOrgId(Long orgId) {
		// TODO Auto-generated method stub
		return rolesRepo.findRolesByOrgId(orgId);
	}

	

}