package com.efit.savaari.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.efit.savaari.dto.ChangePasswordFormDTO;
import com.efit.savaari.dto.LoginFormDTO;
import com.efit.savaari.dto.RefreshTokenDTO;
import com.efit.savaari.dto.ResetPasswordDTO;
import com.efit.savaari.dto.ResetPasswordFormDTO;
import com.efit.savaari.dto.ResponsibilityDTO;
import com.efit.savaari.dto.RolesDTO;
import com.efit.savaari.dto.SignUpFormDTO;
import com.efit.savaari.dto.UserResponseDTO;
import com.efit.savaari.entity.ResponsibilityVO;
import com.efit.savaari.entity.RolesVO;
import com.efit.savaari.entity.UserVO;
import com.efit.savaari.exception.ApplicationException;
import com.efit.savaari.responseDTO.ResponseDTO;

@Service
public interface AuthService {

	public ResponseDTO signup(SignUpFormDTO signUpRequest) throws ApplicationException;

	public UserResponseDTO login(LoginFormDTO loginRequest, HttpServletRequest request) throws ApplicationException;

	public void logout(String userName);

	public void changePassword(ChangePasswordFormDTO changePasswordRequest);

	public void resetPassword(ResetPasswordFormDTO resetPasswordRequest);

	public RefreshTokenDTO getRefreshToken(String userName, String tokenId) throws ApplicationException;
	
	List<Map<String, Object>> getResponsibilityForRolesByOrgId(Long orgId);
	
	Map<String, Object> createUpdateRoles(RolesDTO rolesDTO) throws ApplicationException;
	
	public List<RolesVO> getAllRoles(Long orgId);

	public List<RolesVO> getAllActiveRoles(Long orgId);
	
	RolesVO getRolesById(Long id) throws ApplicationException;
	
	Map<String, Object> createUpdateResponsibilities(ResponsibilityDTO responsibilityDTO) throws ApplicationException;
	
	public List<ResponsibilityVO> getAllResponsibility(Long orgId);

	public List<ResponsibilityVO> getAllActiveResponsibility(Long orgId);
	
	ResponsibilityVO getResponsibilityById(Long id) throws ApplicationException;
	
	List<UserVO>getAllUsersByOrgId(Long orgId);
	
	public UserVO getUserById(Long userId);

	public UserVO getUserByUserName(String userName);

//	public Map<String, Object> getUsersByOrgId(Long orgId, String branchcode, int pageNumber, int count, String search);

	Map<String, Object> getAllUsersList( String branchCode, String search, int page, int count);

	 void checkUserExists(String email) throws ApplicationException;
	 
	    Map<String, Object> resetPassword(ResetPasswordDTO req) throws ApplicationException;

		public Map<String, Object> createApprovalUserList(Long id, String userName, String action,
				String actionBy) throws Exception;

}
